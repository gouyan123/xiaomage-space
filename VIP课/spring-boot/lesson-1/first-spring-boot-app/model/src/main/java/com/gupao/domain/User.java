package com.gupao.domain;

/**
 * 用户模型
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-18
 **/
public class User {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
