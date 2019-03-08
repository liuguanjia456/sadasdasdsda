package com.bjpowernode.activemq.model;

import java.io.Serializable;

/**
 * ClassName:User
 * Package:com.bjpowernode.activemq.model
 * Description:
 *
 * @date:2019/3/4 16:39
 * @author:Felix
 */
public class User implements Serializable {

    private static final long serialVersionUID = -7987164106117686206L;

    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
