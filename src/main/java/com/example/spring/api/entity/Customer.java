package com.example.spring.api.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Primary;

import java.sql.Timestamp;


@Entity
public class Customer {

    @Id
    private int rollNo;

    @NotNull(message = "Please enter the Name")
    private String name;
    @NotNull(message = "Please enter the Company name")
    private String company;

    @Min(value = 18,message = "Age cannot be below 18")
    @Max(value = 60,message = "Age cannot be above 60")
    private int age;
    @Email(message = "Please enter valid email address")
    private String email;

    @CreationTimestamp
    private Timestamp time;

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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }




}
