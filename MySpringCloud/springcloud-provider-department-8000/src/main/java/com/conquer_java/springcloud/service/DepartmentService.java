package com.conquer_java.springcloud.service;

import com.conquer_java.springcloud.pojo.Department;

import java.util.List;

public interface DepartmentService {
    public boolean addDept(Department department);

    public Department queryDeptById(long id);

    public List<Department> listAll();

    public boolean deleteDeptById(long id);
}
