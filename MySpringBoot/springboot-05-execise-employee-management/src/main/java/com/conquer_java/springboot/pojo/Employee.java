package com.conquer_java.springboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date birthdate;
    private Integer gender;
    private String email;
    private Department department;
}
