package com.conquer_java.springboot.service;

import com.conquer_java.springboot.pojo.Department;

import java.util.Collection;

public interface DepartmentService {
    Collection<Department> listAll();
}
