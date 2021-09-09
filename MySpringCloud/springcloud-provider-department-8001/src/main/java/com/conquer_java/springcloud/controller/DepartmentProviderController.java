package com.conquer_java.springcloud.controller;

import com.conquer_java.springcloud.pojo.Department;
import com.conquer_java.springcloud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 通过Controller提供RestFUL服务，由于无须前端页面，全部通过JSON传输数据，因此，使用@RestController而非@Controller
@RestController
public class DepartmentProviderController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/dept/add")
    public boolean addDept(@RequestBody Department department) {
        return departmentService.addDept(department);
    }

    @GetMapping("/dept/get/{id}")
    public Department getDept(@PathVariable("id") long id) {
        return departmentService.queryDeptById(id);
    }

    @GetMapping("/dept/list")
    public List<Department> listDept() {
        return departmentService.listAll();
    }

    @PostMapping("/dept/delete/{id}")
    public boolean deleteDept(@PathVariable("id") long id) {
        return departmentService.deleteDeptById(id);
    }
}
