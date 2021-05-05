package com.conquer_java.springboot.service;

import com.conquer_java.springboot.dao.DepartmentDao;
import com.conquer_java.springboot.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentDao departmentDao;

    @Override
    public Collection<Department> listAll() {
        return departmentDao.listAll();
    }
}
