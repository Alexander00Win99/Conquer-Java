import com.conquer_java.spring.mapper.UserMapper;
import com.conquer_java.spring.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 【实验目的】
 * 证明：showUserInGroup()操作存在事务问题，无法保证事务的ACID特性：
 * showUserInGroup()包括三大操作：1) createUser(user);2)deleteUser(user.getId());3)selectAll();
 * 假设：创建用户正确执行；删除用户执行报错，那么检查发现：用户能够1)成功添加，但是无法2)成功删除（因为，delete语句报错）
 * 结果：用户能够成功添加，但是删除报错。显然，这不符合事务的定义。
 */
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user_99 = context.getBean("user_99", User.class);
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.selectAll()) {
            System.out.println(user);
        }

//        userMapper.createUser(user_99);
//        for (User user : userMapper.selectAll()) {
//            System.out.println(user);
//        }
//        userMapper.deleteUser(99);
//        for (User user : userMapper.selectAll()) {
//            System.out.println(user);
//        }
        List<User> userList = userMapper.showUserInGroup(user_99);
    }
}
