package com.conquer_java.springboot;

import com.conquer_java.springboot.dao.DepartmentDao;
import com.conquer_java.springboot.dao.EmployeeDao;
import com.conquer_java.springboot.pojo.Department;
import com.conquer_java.springboot.pojo.Employee;

public class Main {
    public static void main(String[] args) {
        System.out.println("总计" + DepartmentDao.departments.size() + "个部门：");
        for (Department d : DepartmentDao.departments.values()) {
            System.out.println(d.toString());
        }

        System.out.println("总计" + EmployeeDao.employees.size() + "个员工：");
        for (Employee e : EmployeeDao.employees.values()) {
            System.out.println(e.toString());
        }
    }
}
