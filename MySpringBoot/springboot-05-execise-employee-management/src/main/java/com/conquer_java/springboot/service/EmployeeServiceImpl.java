package com.conquer_java.springboot.service;

import com.conquer_java.springboot.dao.EmployeeDao;
import com.conquer_java.springboot.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeDao employeeDao;

    @Override
    public Collection<Employee> listAll() {
        return employeeDao.listAll();
    }

    @Override
    public void save(Employee e) {
        employeeDao.create(e);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeDao.getEmployeeById(id);
    }

    @Override
    public void delete(Integer id) {
        employeeDao.deleteEmployeeById(id);
    }
}
