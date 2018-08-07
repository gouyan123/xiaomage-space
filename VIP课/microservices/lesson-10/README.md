# 第十节 Spring Cloud 服务熔断



> ```
> JSR 305 meta-annotations
> 注解做编译约束
> ```



## 主要内容



### Spring Cloud Hystrix Client



> 注意：方法签名
>
> * 访问限定符
>
> * 方法返回类型
>
> * 方法名称
>
> * 方法参数
>
>   * 方法数量
>   * 方法类型+顺序
>   * ~~方法名称（编译时预留，IDE，Debug）~~
>
>   



### 实现服务熔断（Future）



#### 低级版本（无容错实现）

```java
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 简易版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/say2")
    public String say2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        // 100 毫秒超时
        String returnValue = future.get(100, TimeUnit.MILLISECONDS);
        return returnValue;
    }
```



#### 低级版本+（带容错实现）

```java
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 简易版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/say2")
    public String say2(@RequestParam String message) throws Exception {
        Future<String> future = executorService.submit(() -> {
            return doSay2(message);
        });
        // 100 毫秒超时
        String returnValue = null;
        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // 超级容错 = 执行错误 或 超时
            returnValue = errorContent(message);
        }
        return returnValue;
    }
```



#### 中级版本



```java
    /**
     * 中级版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
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
            throw e;
        }
        return returnValue;
    }
```





```java
@RestControllerAdvice(assignableTypes = ServerController.class)
public class CircuitBreakerControllerAdvice {

    @ExceptionHandler
    public void onTimeoutException(TimeoutException timeoutException,
                                   Writer writer) throws IOException {
        writer.write(errorContent("")); // 网络 I/O 被容器
        writer.flush();
        writer.close();
    }

    public String errorContent(String message) {
        return "Fault";
    }

}
```



#### 高级版本（无注解实现）



```java
    /**
     * 高级版本
     *
     * @param message
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/advanced/say")
    public String advancedSay(@RequestParam String message) throws Exception {
        return doSay2(message);
    }
```



```java
@Aspect
@Component
public class ServerControllerAspect {

    private ExecutorService executorService = newFixedThreadPool(20);

    @Around("execution(* com.gupao.micro.services.spring.cloud." +
            "server.controller.ServerController.advancedSay(..)) && args(message) ")
    public Object advancedSayInTimeout(ProceedingJoinPoint point, String message) throws Throwable {
        Future<Object> future = executorService.submit(() -> {
            Object returnValue = null;
            try {
                returnValue = point.proceed(new Object[]{message});
            } catch (Throwable ex) {
            }
            return returnValue;
        });

        Object returnValue = null;
        try {
            returnValue = future.get(100, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // 取消执行
            returnValue = errorContent("");
        }
        return returnValue;
    }

    public String errorContent(String message) {
        return "Fault";
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

}
```



#### 高级版本（带注解实现）



* Aspect 注解实现

```java
    @Around("execution(* com.gupao.micro.services.spring.cloud." +
            "server.controller.ServerController.advancedSay2(..)) && args(message) && @annotation(circuitBreaker)")
    public Object advancedSay2InTimeout(ProceedingJoinPoint point,
                                        String message,
                                        CircuitBreaker circuitBreaker) throws Throwable {
        long timeout = circuitBreaker.timeout();
        return doInvoke(point, message, timeout);
    }
```



* 反射API 实现

```java
    @Around("execution(* com.gupao.micro.services.spring.cloud." +
            "server.controller.ServerController.advancedSay2(..)) && args(message) ")
    public Object advancedSay2InTimeout(ProceedingJoinPoint point,
                                        String message) throws Throwable {

        long timeout = -1;
        if (point instanceof MethodInvocationProceedingJoinPoint) {
            MethodInvocationProceedingJoinPoint methodPoint = (MethodInvocationProceedingJoinPoint) point;
            MethodSignature signature = (MethodSignature) methodPoint.getSignature();
            Method method = signature.getMethod();
            CircuitBreaker circuitBreaker = method.getAnnotation(CircuitBreaker.class);
            timeout = circuitBreaker.timeout();
        }
        return doInvoke(point, message, timeout);
    }
```



#### 高级版本（信号灯实现 = 单机版限流方案）

```java
    @Around("execution(* com.gupao.micro.services.spring.cloud." +
            "server.controller.ServerController.advancedSay3(..))" +
            " && args(message)" +
            " && @annotation(circuitBreaker) ")
    public Object advancedSay3InSemaphore(ProceedingJoinPoint point,
                                          String message,
                                          SemaphoreCircuitBreaker circuitBreaker) throws Throwable {
        int value = circuitBreaker.value();
        if (semaphore == null) {
            semaphore = new Semaphore(value);
        }
        Object returnValue = null;
        try {
            if (semaphore.tryAcquire()) {
                returnValue = point.proceed(new Object[]{message});
                Thread.sleep(1000);
            } else {
                returnValue = errorContent("");
            }
        } finally {
            semaphore.release();
        }

        return returnValue;

    }
```





## 下节预习

### 回顾去年 VIP

[第六节 Spring Cloud Feign](http://git.gupaoedu.com/vip/xiaomage-space/tree/master/VIP%E8%AF%BE/spring-cloud/lesson-6)



