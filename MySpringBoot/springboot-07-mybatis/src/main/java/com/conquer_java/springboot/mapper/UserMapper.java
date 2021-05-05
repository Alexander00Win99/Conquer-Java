package com.conquer_java.springboot.mapper;

import com.conquer_java.springboot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 说明本类是MyBatis的Mapper接口类
// 效果等同==SpringBoot主类SpringbootApplication上面添加@MapperScan("com.conquer_java.springboot.mapper")注解
@Mapper
@Repository
public interface UserMapper {
    List<User> queryAll();
    User queryUserById(int id);
    int addUser(User user);
    int modifyUser(User user);
    int deleteUser(int id);
}
