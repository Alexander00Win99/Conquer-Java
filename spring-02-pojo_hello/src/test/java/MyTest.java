import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        // 获取spring的context对象
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 直接获取bean对象
        System.out.println(context.getBean("hello"));
    }
}
