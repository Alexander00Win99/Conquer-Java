import com.conquer_java.spring.dao.UserMapper;
import com.conquer_java.spring.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisNonSpringTest {
    @org.junit.Test
    public void selectAll() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true); // 自动提交事务

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.selectAll();

        for (User user : userList) {
            System.out.println(user);
        }
    }

    public static void main(String[] args) {
        System.out.println("JUnit没有必要使用main()进行测试！！！");
    }
}
