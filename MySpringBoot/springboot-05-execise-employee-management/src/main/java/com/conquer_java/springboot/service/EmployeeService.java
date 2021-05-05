package com.conquer_java.springboot.service;

import com.conquer_java.springboot.pojo.Employee;

import java.util.Collection;

public interface EmployeeService {
    Collection<Employee> listAll();
    void save(Employee e);
    Employee getEmployeeById(Integer id);
    void delete(Integer id);
}
