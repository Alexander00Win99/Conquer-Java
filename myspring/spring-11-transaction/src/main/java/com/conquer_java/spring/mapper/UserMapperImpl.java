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
    public int createUser(User user) {
        return sqlSession.getMapper(UserMapper.class).createUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return sqlSession.getMapper(UserMapper.class).deleteUser(id);
    }

    @Override
    public List<User> selectAll() {

        return sqlSession.getMapper(UserMapper.class).selectAll();
    }

    @Override
    public User selectOne(int id) {
        return sqlSession.getMapper(UserMapper.class).selectOne(id);
    }
}
