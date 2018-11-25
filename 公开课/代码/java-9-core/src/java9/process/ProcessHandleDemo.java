package java9.process;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import static java.lang.System.out;

/**
 * TODO:小马哥，写点注释吧！
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-20
 **/
public class ProcessHandleDemo {

    public static void main(String[] args) {

        echoCurrentProcessOnExit();

        echoOnExit();

        echoAllProcessors();
    }

    private static void echoCurrentProcessOnExit(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                 long pid = ProcessHandle.current().pid();// Java 9
                RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
                String value = runtimeMXBean.getName(); // value = pid@xxx
                String pidString = value.substring(0,value.indexOf("@"));

                System.out.printf("Current Process[pid : %d , pidString : %s] will on exited!\n",
                        pid,pidString);
            }
        });
    }

    private static void echoOnExit() {
        ProcessHandle.current().onExit().thenAccept(out::println);
    }


    private static void echoAllProcessors() {
        ProcessHandle.allProcesses().forEach(out::println);
    }


}
