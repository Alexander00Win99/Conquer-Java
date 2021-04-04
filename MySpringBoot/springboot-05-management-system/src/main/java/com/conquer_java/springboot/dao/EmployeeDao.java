package com.conquer_java.springboot.dao;

import com.conquer_java.springboot.pojo.Department;
import com.conquer_java.springboot.pojo.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class EmployeeDao {
    private static Map<Integer, Employee> employees = null;
    //@Autowired // Autowired annotation is not supported on static fields
    private DepartmentDao departmentDao; // 外表操作

    static {
        employees = new HashMap<Integer, Employee>();
        employees.put(0, new Employee(0, "Alexander00Win99", new Date(1981-01-01), 1, "1034672298@qq.com", new DepartmentDao().getDepartmentById(0)));
        employees.put(1, new Employee(1, "Alex00Win99", new Date(1981-01-01), 1, "1034672298@qq.com", new DepartmentDao().getDepartmentById(1)));
        employees.put(2, new Employee(2, "Alexander Wen", new Date(1981-01-01), 1, "1034672298@qq.com", new DepartmentDao().getDepartmentById(2)));
        employees.put(3, new Employee(3, "Alex Wen", new Date(1981-01-01), 1, "1034672298@qq.com", new DepartmentDao().getDepartmentById(3)));
        employees.put(4, new Employee(4, "Alexander-温", new Date(1981-01-01), 1, "1034672298@qq.com", new DepartmentDao().getDepartmentById(4)));
    }

    // 主键自增
    private static Integer initId = 10000;

    // 数据库表操作——查询所有员工
    public Collection<Employee> listEmployees() {
        return employees.values();
    }

    // 数据库表操作——查询指定员工
    public Employee getEmployeeById(Integer id) {
        return employees.get(id);
    }

    // 增加员工
    public void create(Employee employee) {
        if (employee.getId() == null) employee.setId(initId++);
        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employees.put(employee.getId(), employee);
    }

    // 删除员工
    public void delete(Integer id) {
        employees.remove(id);
    }
}
