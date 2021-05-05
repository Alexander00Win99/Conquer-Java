package com.conquer_java.springboot.controller;

import com.conquer_java.springboot.pojo.Department;
import com.conquer_java.springboot.pojo.Employee;
import com.conquer_java.springboot.service.DepartmentServiceImpl;
import com.conquer_java.springboot.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * ！！！【Thymeleaf语法格式必须正确】！！！——否则编译运行报错
 * <tr th:each="emp:${employees}">
 * ==>错写成为==>
 * <tr th:each="emp:$(employees)">
 *
 * ==>报错如下==>：
 * org.thymeleaf.TemplateEngine             : [THYMELEAF][http-nio-8888-exec-7] Exception processing template "employee/list": An error happened during template parsing (template: "class path resource [templates/employee/list.html]")
 * Caused by: org.attoparser.ParseException: Could not parse as each: "emp:$(employees)" (template: "employee/list" - line xxx, col xxx)
 * Servlet.service() for servlet [dispatcherServlet] in context with path [/springboot] threw exception [Request processing failed; nested exception is org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing (template: "class path resource [templates/employee/list.html]")] with root cause
 * org.thymeleaf.exceptions.TemplateProcessingException: Could not parse as each: "emp:$(employees)" (template: "employee/list" - line xxx, col xxx)
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeServiceImpl employeeServiceImpl;
    @Autowired
    DepartmentServiceImpl departmentServiceImpl;

    // 退出登录
    @RequestMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    /**
     * 返回视图设置属性：model.addAttribute("employees", employees);
     * <==>
     * 前端页面提取参数：<tr th:each="e:${employees}">
     *
     * 【标签高亮处理】——当前页面list.html作为实参传参形参active
     * <div th:replace="~{/common/common::sidebar-common(active='list.html')}"></div>
     * 公共部分common.html中的根据形参active实际取值判断决定是否高亮
     * <a th:class="${active=='list.html'?'nav-link active':'nav-link'}" th:href="@{/employee}">
     *
     * 【性别格式处理】——整型转为字符
     * [[${e.getGender()==1?'男':'女'}]]
     *
     * 【日期格式处理】——Thymeleaf前端展示
     * Thu Jan 01 08:00:01 CST 1970 —— 原始
     * ==>
     * January 1, 1970 8:00:01 AM CST —— [[${#dates.format(e.getBirthdate())}]]
     * ==>
     * 	1970-01-01 —— [[${#dates.format(e.getBirthdate(), 'yyyy-MM-dd')}]]
     *
     * @param model
     * @return
     */
    // 员工列表页面
    @RequestMapping("/employee")
    public String list(Model model) { // 后端服务传递数据：employees(前端页面提取信息)
        // 获取员工信息
        Collection<Employee> employees = employeeServiceImpl.listAll();
        model.addAttribute("employees", employees);
        return "/employee/list";
    }

    // 增加页面
    // RESTful风格：GET请求到达添加页面
    @GetMapping("/employee/add")
    public String addPage(Model model) { // 后台服务传递数据：departments(前端页面提取信息)
        // 获取部门信息
        Collection<Department> departments = departmentServiceImpl.listAll();
        model.addAttribute("departments", departments);
        return "/employee/add";
    }

    // 增加员工
    // RESTful风格：POST请求到达添加页面
    @PostMapping("/employee/add")
    public String addEmployee(Employee e) { // 前端页面传递数据：员工对象(后台服务加工处理)
        employeeServiceImpl.save(e);
        System.out.println("Complete to add employee received from front end: " + e.toString());
        return "redirect:/employee";
    }

    // 修改页面
    // RESTful风格：GET请求到达修改页面
    @GetMapping("/employee/update{id}")
    public String updatePage(@PathVariable("id") Integer id, Model model) { // RequestURL里面提取变量：员工id
        Employee e = employeeServiceImpl.getEmployeeById(id);
        System.out.println("Ready to modify employee assigned by front end: " + e.toString());
        model.addAttribute("candidate", e);
        Collection<Department> departments = departmentServiceImpl.listAll();
        model.addAttribute("departments", departments);
        return "/employee/update";
    }

    // 修改员工
    // RESTful风格：POST请求到达修改页面
    @PostMapping("/employee/update")
    public String updateEmployee(Employee e) { // 前端页面传递数据：员工对象
        employeeServiceImpl.save(e);
        System.out.println("Complete to modify employee assigned by front end: " + e.toString());
        return "redirect:/employee";
    }

    // 删除员工
    @RequestMapping("/employee/delete{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, Model model) { // RequestURL里面提取变量：员工id
        Employee e = employeeServiceImpl.getEmployeeById(id);
        employeeServiceImpl.delete(id);
        System.out.println("Complete to delete employee assigned by front end: " + e.toString());
        return "redirect:/employee";
    }
}
