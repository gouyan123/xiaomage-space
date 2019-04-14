# 第十节 Spring Cloud 服务熔断：熔断是一种 对服务的保护；
> * 熔断描述1：3个client 并发调用 serviceA时，性能不受影响，当4个client 并发调用 serviceA时，性能会受到影响，因此应该对第4个client请求进行限制，因此可以
通过限制 最大并发访问数 对部分client访问进行熔断并返回容错方法，保护serviceA；
> * 熔断描述2：client 访问 serviceA，访问有超时时间，serviceA里面有线程池 处理client请求线程，serviceA里面线程池大小为200，理论上serviceA可以处理
200个并发，当并发数量超过200时，其他client请求线程会在任务队列里面排队等待，如果一个请求线程占用连接时间太久，影响serviceA性能，因此，限制client连接
时间，对连接超时的client进行熔断并返回容错对象；
> * 熔断策略：1、控制并发请求数量 即信号量Semaphore；2、设置请求连接超时时间 即timeout；见下图；

![1533656373153](assets\1533656373153.png)

##实战演练
> * 启动zk服务端 即注册中心：zkServer.cmd；启动 spring-cloud-server-application项目；
spring-cloud-server-application项目中 对ServerController#say()方法进行熔断，当连接超过100ms时，进行熔断，代码如下：
测试：访问 http://localhost:9090/say?message=world，返回 world说明没有熔断，返回 Fault说明熔断了；
```java
public class ServerController {
@HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")},//设置 熔断超时时间为 100ms
            fallbackMethod = "errorContent")       //当线程100ms后还没释放，直接释放线程，并调用errorContent()方法
    @GetMapping("/say")
    public String say(@RequestParam String message) throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发熔断
        int value = random.nextInt(200);
        System.out.println("say() costs " + value + " ms.");
        // 任务线程执行超过100ms，直接释放该线程，并调用容错方法errorContent()
        Thread.sleep(value);
        System.out.println("ServerController 接收到消息 - say : " + message);
        return "Hello, " + message;
    }
}
```


## 主要内容
### Spring Cloud Hystrix Client
> * 注意：方法签名：访问限定符，方法返回类型，方法名称(编译时预留，给dubug，ide的，不属于方法签名)，方法参数(数量一致 + 类型一致 + 顺序一致)

### 实现服务熔断：核心原理 普通方法封装到线程对象里 提交给线程池ExecutorService执行，future.get(timeout)去拿结果 如果超时，会报异常，捕获异常并调熔断方法
#### 低级版本（有容错实现）
> * spring-cloud-server-application项目中 创建ServerController#say2()方法，绑定/say2访问路径；
> * 测试：访问 http://localhost:9090/say2?message=world，返回 world说明没有熔断，返回 Fault说明熔断了；
```java
public class ServerController {
    /** Executor线程池接口 ← ExecutorService线程池子接口 ← 线程池实现类
    *   Executors线程池 工程，用来创建线程池，例如 Executors.newSingleThreadExecutor()
    * */
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @GetMapping("/say2")
    public String say2(@RequestParam String message) throws Exception {
    /**提交任务
    * lamda表达式是对匿名实现类的一种简写，例如正常匿名实现类写法如下：
    * Runable r = new Runable{
    *   @override
    *   public void run(){
    *       ...
    *   }
    * }
    * 匿名实现类简写形式如下，省略 new 接口名称，省略方法名称，只剩参数列表()和方法体
    * Runable r = () -> {
    *                    ...
    *                   }
    * */
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        // 100 毫秒超时
        String returnValue = null;
        try {
            //100s不返回就 报超时异常
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 超时就调用 容错方法 errorContent(message)
            returnValue = errorContent(message);
        }
        return returnValue;
    }
    //作为任务线程，提交给线程池
    private String doSay2(String message) throws InterruptedException {
        // 如果随机时间 大于 100 ，那么触发容错
        int value = random.nextInt(200);
        // > 100
        Thread.sleep(value);
        String returnValue = "Say 2 : " + message;
        return returnValue;
    }
}
```

#### 中级版本，实现连接超时熔断
> * spring-cloud-server-application项目中 创建CircuitBreakerHandlerInterceptor类实现org.springframework.web.servlet.HandlerInterceptor接口，
拦截web请求，并重写afterCompletion()方法，并创建WebMvcConfig类，将CircuitBreakerHandlerInterceptor拦截器注册上去；
> * 测试：访问 http://localhost:9090/middle/say?message=world，返回 world说明没有熔断，返回 Fault说明熔断了；
```java
//创建web请求拦截类处理异常，但是该类处理异常方式不好，可以选择CircuitBreakerControllerAdvice类，并注掉CircuitBreakerHandlerInterceptor
public class CircuitBreakerHandlerInterceptor implements HandlerInterceptor {
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,Object handler,@Nullable Exception ex) throws Exception {
        //TimeoutException可以通过@ExceptionHandler处理，见高级版本CircuitBreakerControllerAdvice类
        if ("/middle/say".equals(request.getRequestURI()) && ex instanceof TimeoutException) {
            Writer writer = response.getWriter();
            writer.write(errorContent(""));
        }
    }
    public String errorContent(String message) {
        return "Fault";
    }
}
//主要优化CircuitBreakerHandlerInterceptor类的异常处理，这两个类只能用一个
//CircuitBreakerControllerAdvice类的 @ExceptionHandler会有一个瑕疵，会拦截所有TimeoutException，因此@RestControllerAdvice注解中增加(assignableTypes = ServerController.class)
//表示只拦截ServerController类的 TimeoutException异常
@RestControllerAdvice(assignableTypes = ServerController.class)
public class CircuitBreakerControllerAdvice {
    @ExceptionHandler
    public void onTimeoutException(TimeoutException timeoutException,Writer writer) throws IOException {
        writer.write(errorContent("")); // 网络 I/O 被web容器管理，关闭不关闭都行；
        writer.flush();//刷出去，要不然要等 buffer装满才刷出去
        writer.close();
    }
    //熔断 降级方法
    public String errorContent(String message) {
        return "Fault";
    }
}
//将web请求拦截类注册上去
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CircuitBreakerHandlerInterceptor());
    }
}
public class ServerController {
    @GetMapping("/middle/say")
    public String middleSay(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        // 100 毫秒超时
        String returnValue = null;
        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // 取消执行
            throw e; //抛出异常，会被CircuitBreakerControllerAdvice
        }
        return returnValue;
    }
}
```
#### 高级版本，用AOP实现 目标方法拦截 无侵入性，不使用注解 实现连接超时熔断
> * 创建 切面类ServerControllerAspect的切面方法，拦截 ServerController类的方法 即切入点，当http请求ServerController时，会触发切面方法，重点：切面方法将 目标方法封装到线程对象中，然后提交给ExecutorService线程池
> * @Around表示 切面方法 对 切入点方法 进行 环绕处理，point表示代理对象，point.getTarget()表示目标类，point.proceed()执行代理方法，里面会调目标方法；
> * 测试：启动zk，启动spring-cloud-server-application，访问 http://localhost:9090/advanced/say?message=world
```java
/**@Aspect切面类 里面 定义 切面方法，切面方法上定义 切入点，切入点即某个类的某个方法，当切入点方法执行时，自动执行切面方法*/
@Aspect
@Component
public class ServerControllerAspect {
    /**线程池：Executor是线程池顶层接口，ExecutorService是Executor的子接口，Executors是创建线程池ExecutorService的工厂类*/
    private ExecutorService executorService = Executors.newFixedThreadPool(20);
    /**spring容器关闭之前，关闭线程池*/
    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }
    /*切面方法上定义切入点*/
    @Around("execution(* com.gupao.micro.services.spring.cloud.server.controller.ServerController.advancedSay(..)) && args(message) ")
    public Object advancedSayInTimeout(ProceedingJoinPoint point, String message) throws Throwable {
        return doInvoke(point, message, 100);
    }
    private Object doInvoke(ProceedingJoinPoint point,String message, long timeout) throws Throwable {
        Future<Object> future = executorService.submit(() -> {
            Object returnValue = null;
            try {
                returnValue = point.proceed(new Object[]{message});
            } catch (Throwable ex) {
            }
            return returnValue;
        });

        Object returnValue = null;
        try {/**超时会抛出异常*/
            returnValue = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            /**处理异常，并调用 熔断方法*/
            future.cancel(true); // 取消执行
            returnValue = errorContent("");
        }
        return returnValue;
    }
}
public class ServerController {
    @GetMapping("/advanced/say")
    public String advancedSay(@RequestParam String message) throws Exception {
        return doSay2(message);
    }
}
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true) // 激活 AOP
public class SpringCloudServerApplication {
    //
}
```
#### 高级版本，用AOP实现无侵入性，使用注解 实现连接超时熔断
> * 创建 @interface TimeoutCircuitBreaker，用于存 超时时间timeout，在环绕方法中，通过反射取出该注解
```java
@Target(ElementType.METHOD) // 标注在方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保存注解信息
@Documented //文档化
public @interface TimeoutCircuitBreaker {
    /**超时时间*/
    long timeout();
}
@RestController
public class ServerController {
    @GetMapping("/advanced/say2")
    @TimeoutCircuitBreaker(timeout = 100)/**连接时间超过100ms，则连接超时*/
    public String advancedSay2(@RequestParam String message) throws Exception {
        return doSay2(message);
    }
}
@Aspect
@Component
public class ServerControllerAspect {
    @Around("execution(* com.gupao.micro.services.spring.cloud.server.controller.ServerController.advancedSay2(..)) && args(message) ")
    public Object advancedSay2InTimeout(ProceedingJoinPoint point,String message) throws Throwable {
         long timeout = -1;
         /**
         * point.getTarget().getClass().newInstance(); point为代理对象，point.getTarget()为目标对象
         * */
//        if (point instanceof MethodInvocationProceedingJoinPoint) {
//            MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
//            MethodSignature signature = (MethodSignature) methodPoint.getSignature();
//            Method method = signature.getMethod();
//            TimeoutCircuitBreaker circuitBreaker = method.getAnnotation(TimeoutCircuitBreaker.class);
//            timeout = circuitBreaker.timeout();
//        }
//        return doInvoke(point, message, timeout);
        //用反射 获取方法上的注解
        Method method = point.getTarget().getClass().getDeclaredMethod("advancedSay2",new Class[]{String.class});
        TimeoutCircuitBreaker timeoutCircuitBreaker = method.getAnnotation(TimeoutCircuitBreaker.class);
        timeout = timeoutCircuitBreaker.timeout();
        return doInvoke(point, message, timeout);
    }
}
```
#### 高级版本（信号灯实现 = 单机版限流方案）
> * semaphore.tryAcquire()获取到信号，就调用服务方法，然后释放信号 semaphore.release()，如果semaphore.tryAcquire()获取不到信号，就调用 容错方法
```java
@Target(ElementType.METHOD) // 标注在方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保存注解信息
@Documented
public @interface SemaphoreCircuitBreaker {
    /**信号量：限制并发量*/
    int value();
}

@Aspect
@Component
public class ServerControllerAspect {
    //信号量
    private volatile Semaphore semaphore = null;
    @Around("execution(* com.gupao.micro.services.spring.cloud.server.controller.ServerController.advancedSay3(..)) && args(message) && @annotation(circuitBreaker) ")
    public Object advancedSay3InSemaphore(ProceedingJoinPoint point,String message,SemaphoreCircuitBreaker circuitBreaker) throws Throwable {
        int value = circuitBreaker.value();
        if (semaphore == null) {
            semaphore = new Semaphore(value);
        }
        Object returnValue = null;
        try {
            if (semaphore.tryAcquire()) {/**semaphore.tryAcquire() 获得信号 相当于 获取锁*/
                returnValue = point.proceed(new Object[]{message});
                Thread.sleep(1000);
            } else {/**获取到 信号semaphore 就调 服务方法，获取不到信号量就调  熔断方法*/
                returnValue = errorContent("");
            }
        } finally {
            semaphore.release();/**semaphore.release() 释放信号 相当于 释放锁*/
        }
        return returnValue;
    }
}
@RestController
public class ServerController {
    /**高级版本 + 注解（信号量）-熔断*/
    @GetMapping("/advanced/say3")
    @SemaphoreCircuitBreaker(1)
    public String advancedSay3(@RequestParam String message) throws Exception {
        return doSay2(message);
    }
}
```

## 下节预习

### 回顾去年 VIP

[第六节 Spring Cloud Feign](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-6)



