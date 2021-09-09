package com.conquer_java.springcloud.service;

import com.conquer_java.springcloud.mapper.DepartmentMapper;
import com.conquer_java.springcloud.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public boolean addDept(Department department) {
        return departmentMapper.addDept(department);
    }

    @Override
    public Department queryDeptById(long id) {
        return departmentMapper.queryDeptById(id);
    }

    @Override
    public List<Department> listAll() {
        return departmentMapper.listAll();
    }

    @Override
    public boolean deleteDeptById(long id) {
        return departmentMapper.deleteDeptById(id);
    }
}
