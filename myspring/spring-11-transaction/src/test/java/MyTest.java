import com.conquer_java.spring.mapper.UserMapper;
import com.conquer_java.spring.pojo.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
        User user_99 = context.getBean("user_99", User.class);
        System.out.println(user_99);
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        System.out.println(userMapper);
        for (User user : userMapper.selectAll()) {
            System.out.println(user);
        }
        userMapper.createUser(user_99);
        for (User user : userMapper.selectAll()) {
            System.out.println(user);
        }
        userMapper.deleteUser(99);
        for (User user : userMapper.selectAll()) {
            System.out.println(user);
        }
    }
}
