import com.conquer_java.spring.pojo.Address;
import com.conquer_java.spring.pojo.Student;
import com.conquer_java.spring.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", "beans.xml");
        Student alex = context.getBean("alex", Student.class);
        System.out.println(alex);
        Address alexHometown = context.getBean("alexHometown", Address.class);
        System.out.println(alexHometown);
        List<String> reading = (List) context.getBean("books");
        System.out.println(reading);
        List<String> singing = (List) context.getBean("songs");
        System.out.println(singing);
        List<String> moives = (List) context.getBean("moives");
        System.out.println(moives);
        List<String> sports = (List) context.getBean("sports");
        System.out.println(sports);
        List<String> games = (List) context.getBean("games");
        System.out.println(games);

        User user = context.getBean("user", User.class);
        System.out.println(user);
//        User userC = context.getBean("userC", User.class);
//        System.out.println(userC);
        User userP = context.getBean("userP", User.class);
        System.out.println(userP);
    }
}
