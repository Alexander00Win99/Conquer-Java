package com.conquer_java.spring.mapper;

import com.conquer_java.spring.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper {
    // 原来所有操作使用SqlSession执行，现在Spring-MyBatis使用sqlSessionTemplate执行
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> selectAll() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectAll();
    }

    @Override
    public User selectOne(int id) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.selectOne(id);
    }
}
