package com.wu;

import com.wu.dao.DepartmentDao;
import com.wu.pojo.Department;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

@SpringBootTest
class Springboot03WebApplicationTests {

    @Test
    void contextLoads() {
        Collection<Department> department = new DepartmentDao().getDepartment();
        for (Department department1 : department) {
            System.out.println(department1);
        }
    }

}
