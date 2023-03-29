package com.example.appcamera.utils;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String password;
    private String token;

    public User(String name, String password, String token){
        this.name=name;
        this.password=password;
        this.token=token;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
