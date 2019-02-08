package com.gupao.micro.services.spring.cloud.client.event;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIEvent {

    public static void main(String[] args) throws Exception {
        /**JFrame：窗体*/
        final JFrame frame = new JFrame("简单 GUI 程序 - Java 事件/监听机制");

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                System.out.printf("[%s] 事件 : %s\n", Thread.currentThread().getName(), event);
            }
        });

        frame.setBounds(300, 300, 400, 300);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }

            @Override
            public void windowClosed(WindowEvent event) {
                System.exit(0);
            }
        });

    }
}
