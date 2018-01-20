package com.gupao.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Service;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * 嵌入式 Tomcat 服务器
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/1/20
 */
public class EmbeddedTomcatServer {

    public static void main(String[] args) throws Exception {
        // classes 目录绝对路径
        // E:\Downloads\tomcat\target\classes
        String classesPath = System.getProperty("user.dir")
                + File.separator + "target" + File.separator + "classes";

        System.out.println(classesPath);

        Tomcat tomcat = new Tomcat();
        // 设置端口 12345
        tomcat.setPort(12345);

        // 设置 Host
        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        // 设置 Context
        // E:\Downloads\tomcat\src\main\webapp
        String webapp = System.getProperty("user.dir") + File.separator +
                "src" + File.separator + "main" + File.separator + "webapp";
        String contextPath = "/";
        // 设置 webapp 绝对路径到 Context，作为它的 docBase
        Context context = tomcat.addWebapp(contextPath, webapp);
        if (context instanceof StandardContext) {
            StandardContext standardContext = (StandardContext) context;
            // 设置默认的web.xml文件到 Context
            standardContext.setDefaultWebXml(classesPath + File.separator + "conf/web.xml");

            // 设置 Classpath 到 Context
            // 添加 DemoServlet 到 Tomcat 容器
            Wrapper wrapper = tomcat.addServlet(contextPath, "DemoServlet", new DemoServlet());
            wrapper.addMapping("/demo");

        }

        // 设置 Service
        Service service = tomcat.getService();

        // 设置 Connector
        /**
         *     <Connector port="8080" protocol="HTTP/1.1"
         connectionTimeout="20000"
         redirectPort="8443" URIEncoding="UTF-8" />
         */
        Connector connector = new Connector();
        connector.setPort(9090);
        connector.setURIEncoding("UTF-8");
        connector.setProtocol("HTTP/1.1");
        service.addConnector(connector);

        // 启动 Tomcat 服务器
        tomcat.start();
        // 强制 Tomcat Server 等待，避免 main 线程执行结束关闭
        tomcat.getServer().await();
    }
}
