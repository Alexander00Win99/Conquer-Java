package com.conquer_java.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 参考：https://blog.csdn.net/qq_32140607/article/details/96498148
 * 【@Autowired和@Bean的区别】
 * 1) @Autowired可以修饰变量或者方法，用于完成自动装配（自动注入所需外部资源——通知Spring给予使用@Bean注释创建的Bean实例）；
 *    默认使用byType方式自动装配依赖对象（默认要求依赖对象必须存在，如果运行null，可以设置属性required=false），如果需要byName方式进行装配，可以使用@Qualifer注解指定；
 * 2) @Bean只能修饰方法，表示初始化一个Bean对象并且交由Spring IoC容器管理；
 *    只能和@Component、@Configuration、@Controller、@Service、@Repository联合使用；
 *    可以按照byName、byType、byConstructor、autoDetect四种方式生成Bean实例；
 *
 * 【@Autowired和@Resource的区别】
 * 1) @Autowired是Spring注解（org.springframework.beans.factory.annotation.Autowired）；
 *    基于属性进行自动装配（xxxProperty或者setXXXProperty()二者只能选一标注Autowired注解）；
 * 2) @Resource是J2EE注解（javax.annotation.Resoure）；
 *    包含name、type两个重要属性，对应要求Spring分别按照byName、byType两种方式自动注入；
 *    @Resource的装配顺序如下：
 *    若同时指定name和type，则在Spring上下文中寻找唯一匹配的bean进行装配，若没有则抛出异常；
 *    若只是指定name，则在Spring上下文中按照id寻找唯一匹配的bean进行装配，若没有则抛出异常；
 *    若只是指定type，则在Spring上下文中按照type寻找类似匹配的bean进行装配，若没有或者存在多个则抛出异常；
 *    若name和type一个都没指定，则在Spring上下文中按照名字进行装配，若没有匹配则回退成为原始类型进行装配，此时若有匹配则自动装配；
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={TestAutowiredAnnotation.class})
public class DemoAutomaticAssembly {
    @Autowired
    private BeanA beanA;
    @Autowired
    private BeanB beanB;
    @Autowired
    private BeanC beanC;
    @Autowired
    private TestAutowiredAnnotation testAutowiredAnnotation;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
        System.out.println("Hello World!");
    }

    @Test
    public void TestBean() {
        System.out.println(beanA.toString());
        System.out.println(beanB.toString());
        System.out.println(beanC.toString());
        System.out.println(testAutowiredAnnotation.getBeanB().toString());
        System.out.println(testAutowiredAnnotation.getBeanC().toString());
    }

}

