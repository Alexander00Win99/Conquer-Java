import com.conquer_java.spring.dao.UserDaoImpl;
import com.conquer_java.spring.dao.UserDaoMysqlImpl;
import com.conquer_java.spring.dao.UserDaoOracleImpl;
import com.conquer_java.spring.dao.UserDaoSqlserverImpl;
import com.conquer_java.spring.service.UserService;
import com.conquer_java.spring.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest1 {
    public static void main(String[] args) {
        /**
         * Choose-01: 需求变动，程序员修改代码——private UserDao userDao = new UserDaoImpl();
         * Choose-02: 需求变动，客户端修改代码——((UserServiceImpl) userService).setUserDao(new UserDaoImpl());
         * Choose-03: 需求变动，客户修改配置文件——例如，通过下拉列表提示用户选择具体选项实现修改配置文件。
         */

        // 三层架构，用户调用业务层，业务层调用DAO层
        UserService userService = new UserServiceImpl();

        // 用户自主选择某个实现
        ((UserServiceImpl) userService).setUserDao(new UserDaoImpl());
        ((UserServiceImpl) userService).setUserDao(new UserDaoMysqlImpl());
        ((UserServiceImpl) userService).setUserDao(new UserDaoOracleImpl());
        ((UserServiceImpl) userService).setUserDao(new UserDaoSqlserverImpl());

        userService.getUser();
//
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//        UserServiceImpl userServiceImpl = (UserServiceImpl) applicationContext.getBean("userServiceImpl");
//        userServiceImpl.getUser();
    }
}
