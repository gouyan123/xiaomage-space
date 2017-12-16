package sun.net.www.protocol.classpath;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * Classpath 协议 Handler
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/12/16
 */
public class Handler extends URLStreamHandler {

    private final String PROTOCOL_PREFIX = "classpath:/";

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        // url = "classpath:/META-INF/license.txt"
        // classpath  = META-INF/license.txt
        // 移除前缀 classpath:/
        // classpath:/META-INF/license.txt
        String urlString = url.toString();
        // META-INF/license.txt
        String classpath = urlString.substring(PROTOCOL_PREFIX.length());
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL classpathURL = classLoader.getResource(classpath);
        // 委派给 ClassLoader 实现
        return classpathURL.openConnection();
    }
}
