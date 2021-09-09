package com.conquer_java.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 1) pojo实体类务必实现序列化，否则将来数据传输报错！
 * 2) 在微服务架构中，一个服务对应一个数据库，相同信息可能存储于不同数据库，所以Entity类通常包含数据库来源信息；
 * 3) @Accessors是Lombok的链式注解，支持链式写法：new Department().setDeptName().setDbHome()
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Department implements Serializable {
    private long deptId;
    private String deptName;
    private String dbHome;

    public Department(String deptName) {
        this.deptName = deptName;
    }
}
