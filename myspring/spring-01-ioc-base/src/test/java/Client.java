import com.conquer_java.spring.dao.UserDaoImpl;
import com.conquer_java.spring.dao.UserDaoMysqlImpl;
import com.conquer_java.spring.dao.UserDaoOracleImpl;
import com.conquer_java.spring.dao.UserDaoSqlserverImpl;
import com.conquer_java.spring.service.UserService;
import com.conquer_java.spring.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Choose-01: 需求变动，程序员修改代码——private UserDao userDao = new UserDaoImpl() -> new UserDaoXXXImpl();
 * Choose-02: 需求变动，客户端修改代码——((UserServiceImpl) userService).setUserDao(new UserDaoImpl() -> new UserDaoXXXImpl());
 * Choose-03: 需求变动，客户修改配置文件——例如，通过下拉列表提示用户选择具体选项实现修改配置文件。
 */
public class Client {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ((UserServiceImpl)userService).setUserDao(new UserDaoImpl());
        //((UserServiceImpl)userService).setUserDao(new UserDaoMysqlImpl());
        //((UserServiceImpl)userService).setUserDao(new UserDaoOracleImpl());
        //((UserServiceImpl)userService).setUserDao(new UserDaoSqlserverImpl());
        userService.getUser();

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.getBean("userServiceImpl", UserServiceImpl.class).getUser();
    }
}
