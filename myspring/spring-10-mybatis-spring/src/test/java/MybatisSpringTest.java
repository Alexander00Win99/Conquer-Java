import com.conquer_java.spring.mapper.UserMapper;
import com.conquer_java.spring.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MybatisSpringTest {
    @Test
    public void selectAll() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.selectAll()) {
            System.out.println(user);
        }
    }

    @Test
    public void selectOne() {
        // "applicationcontext.xml"和"spring-dao"均可
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        User user = userMapper.selectOne(81);
        System.out.println(user);
    }
}
