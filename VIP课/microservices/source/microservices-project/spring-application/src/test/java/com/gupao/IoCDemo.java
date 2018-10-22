package com.gupao;

import org.springframework.beans.factory.annotation.Autowired;

public class IoCDemo {

    @Autowired
    private String name;

    private Integer age;

    @Autowired
    public IoCDemo(Integer age) {
        this.age = age;
    }

    @Autowired
    public void setName(String name){

    }




    public static void main(String[] args) {

    }
}
