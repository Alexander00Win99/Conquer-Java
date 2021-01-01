import com.conquer_java.spring.pojo.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        // xml参数可以传递多个：new ClassPathXmlApplicationContext(String...)
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 控制反转，bean对象无须new创建，直接从IoC Container中获取即可。
        Hello hello = context.getBean("hello", Hello.class);
        System.out.println(hello.toString());
    }
}
