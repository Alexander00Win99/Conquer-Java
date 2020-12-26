package com.conquer_java.spring.mapper;

import com.conquer_java.spring.pojo.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserMapper {
    @Override
    public List<User> selectAll() {
//        SqlSession sqlSession = getSqlSession();
//        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        mapper.selectAll();
        return getSqlSession().getMapper(UserMapper.class).selectAll();
    }

    @Override
    public User selectOne(int id) {
        return getSqlSession().getMapper(UserMapper.class).selectOne(id);
    }
}
