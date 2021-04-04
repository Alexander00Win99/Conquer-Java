package com.conquer_java.springboot.dao;

import com.conquer_java.springboot.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DepartmentDao {
    private static Map<Integer, Department> departments = null;
    // 初始化数据库表
    static {
        departments = new HashMap<Integer, Department>();
        departments.put(0, new Department(0, "董事会"));
        departments.put(0, new Department(1, "固网部"));
        departments.put(0, new Department(2, "无线部"));
        departments.put(0, new Department(3, "光网部"));
        departments.put(0, new Department(4, "研究院"));
        departments.put(0, new Department(5, "市场部"));
        departments.put(0, new Department(6, "财务部"));
        departments.put(0, new Department(7, "人力部"));
        departments.put(0, new Department(8, "后勤部"));
        departments.put(0, new Department(9, "党委&工会"));
    }

    // 数据库表操作——查询所有部门
    public Collection<Department> listDepartments() {
        return departments.values();
    }

    // 数据库表操作——查询指定部门
    public Department getDepartmentById(Integer id) {
        return departments.get(id);
    }
}
