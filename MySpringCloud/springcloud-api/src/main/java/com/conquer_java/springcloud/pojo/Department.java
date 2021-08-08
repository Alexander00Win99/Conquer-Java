package com.conquer_java.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分布式：
 * 1) 实体类；
 * 2) 序列化——网络传输；
 * 3) ORM——对象关系映射；
 * 注：在微服务架构中，一个服务对应一个数据库，同一信息可能存储于不同数据库。
 */
@Data
@NoArgsConstructor
@Accessors(chain = true) // 链式写法new Department().setDept_id("xxx").setDept_name("xxx").setDb_source("xxx")
public class Department implements Serializable {
    private long dept_id;
    private String dept_name;
    private String db_source;

    public Department(String dept_name) {
        this.dept_name = dept_name;
    }
}
