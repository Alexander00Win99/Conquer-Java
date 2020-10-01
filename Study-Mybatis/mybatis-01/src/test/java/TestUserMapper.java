import com.conquer_java.dao.UserDao;
import com.conquer_java.dao.UserDaoImpl;
import com.conquer_java.mapper.UserMapper;
import com.conquer_java.pojo.User;
import com.conquer_java.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestUserMapper {
    @Test
    public void traditionalCRUD() throws Exception {
        Timestamp timestamp;
        timestamp = new Timestamp(new Date().getTime());
        timestamp = new Timestamp(Calendar.getInstance().get(Calendar.YEAR) - 1900,
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND),
                0);
        User user = new User(81, "天神下凡", "999999");
        user.setTimestamp(timestamp);

        UserDao userDao = new UserDaoImpl();
//        userDao.getUserList();
//        userDao.getUserById(6);
//        userDao.createUser(user);
//        userDao.updateUser();
//        User user1 = new User(7, "天神下凡", "999999");
//        user1.setTimestamp(new Timestamp(new Date().getTime()));
//        userDao.createUser(user1);
//        User user2 = new User(8, "天神下凡", "999999");
//        user2.setTimestamp(new Timestamp(new Date().getTime()));
//        userDao.createUser(user2);
        userDao.deleteUserById(7, 8);
    }

    @Test
    public void getUserList() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    @Test
    public void updateUser() {
        try (SqlSession sqlSession = MybatisUtil.getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.updateUser(new User(4, "hlpl_wen", "666666"));
            sqlSession.commit();
        }
    }
}
