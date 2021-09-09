package com.conquer_java.springcloud.mapper;

import com.conquer_java.springcloud.pojo.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {
    public boolean addDept(Department department);

    public Department queryDeptById(long id);

    public List<Department> listAll();

    public boolean deleteDeptById(long id);
}
