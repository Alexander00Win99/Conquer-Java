import com.conquer_java.spring.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = context.getBean("user", User.class);
        user.showName();
        User alias = context.getBean("alias_of_user", User.class);
        user.showName();
        User user01 = context.getBean("user01", User.class);
        user01.showName();
        User user02 = context.getBean("user02", User.class);
        user02.showName();
        User user03 = context.getBean("user03", User.class);
        user03.showName();

        System.out.println("下面测试别名：");
        User alias01 = context.getBean("alias05", User.class);
        alias01.showName();
    }
}
