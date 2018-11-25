package com.gupao.designpatterndemos.adapter;

import java.io.*;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/14
 */
public class FileIODemo {

    public static void main(String[] args) throws Exception {
        // 目前拥有的实例
        InputStream inputStream = new FileInputStream("abc.txt");
        // 需要的对象
        // InputStream -> Reader
        Reader reader = new InputStreamReader(inputStream,"UTF-8");

        print(reader);

    }

    private static void print(Reader reader){

    }
}
