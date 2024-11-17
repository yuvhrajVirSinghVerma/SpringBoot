package com.mvc.mvcPractice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RequestEmployeeDto {

    private String name;
    private String email;
    private int age;
    private int id;

    public RequestEmployeeDto(){}
    public RequestEmployeeDto(int id, String email, String name, int age) {
        this.age = age;
        this.email = email;
        this.name = name;
        this.id=id;

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RequestEmployeeDto{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}