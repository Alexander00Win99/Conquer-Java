import com.conquer_java.spring.service.UserService;
import com.conquer_java.spring.service.UserServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 1) 使用接口进行强制类型装换：正确
        UserService userService01 = (UserService) context.getBean("userService");
        userService01.create();

        // 2) 指定bean类型为接口类型：正确
        UserService userService02 = context.getBean("userService", UserService.class);
        userService02.retrieve();

        System.out.println("userService01 == userService02: " + (userService01 == userService02));

        // 3) 指定bean类型为具体实现类类型：错误
        // 下面报错原因在于：JDK动态代理——代理接口类型而非具体实现类！
        // Exception in thread "main" org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'userService' is expected to be of type 'com.conquer_java.spring.service.UserServiceImpl' but was actually of type 'com.sun.proxy.$Proxy4'
        UserServiceImpl userService03 = context.getBean("userService", UserServiceImpl.class);
        userService03.update();
        userService03.delete();

        System.out.println("userService01 == userService03: " + (userService01 == userService03));
    }
}
