package com.example.c4q.learningsystem.models;

import java.util.List;

/**
 * Created by c4q on 9/9/18.
 */

public class User {
    String email;
    String password;
    String id;
    List<Lessons> lessons;

    public User(){

    }

    public User(String email, String password, String id, List<Lessons> lessons) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.lessons = lessons;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lessons> lessons) {
        this.lessons = lessons;
    }
}
