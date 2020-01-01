import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 参考：
 * https://www.cnblogs.com/tdyang/p/10706893.html
 * https://blog.csdn.net/qq_38531678/article/details/98485063
 * https://www.cnblogs.com/ysmdbk/p/11398482.html
 * https://www.cnblogs.com/moxiaotao/p/9304810.html
 * https://www.cnblogs.com/nin-w/p/5959038.html
 *
 * bean 【各类数据对象目录】
 *
 * -- do(model) 与数据库表结构一一对应，通过DAO层向上传输数据源对象。
 *
 * -- dto 数据传输对象，Service或Manager向外传输的对象。
 *
 * -- request 请求传入对象包装
 *
 * -- response 响应输出对象包装
 *
 * common 【共用对象，工具目录】
 *
 * -- constants 比如响应码，状态码，各种常数
 *
 * -- utils 工具库
 *
 * dao 【数据连接对象目录】
 *
 * -- mapper mybatis生成的，如果只用mybatis则直接放到dao下，或者dao直接命名成mapper
 *
 * -- repository
 *
 * config 【配置文件目录】
 *
 * controller 【控制器目录】
 *
 * service 【服务层目录】
 *
 * -- impl 服务实现目录
 *
 * 服务接口
 *
 * 阿里巴巴Java开发手册中的DO、DTO、BO、AO、VO、POJO定义
 *
 * 分层领域模型规约：
 *
 * DO（ Data Object）：与数据库表结构一一对应，通过DAO层向上传输数据源对象。
 * DTO（ Data Transfer Object）：数据传输对象，Service或Manager向外传输的对象。
 * BO（ Business Object）：业务对象。 由Service层输出的封装业务逻辑的对象。
 * AO（ Application Object）：应用对象。 在Web层与Service层之间抽象的复用对象模型，极为贴近展示层，复用度不高。
 * VO（ View Object）：显示层对象，通常是Web向模板渲染引擎层传输的对象。
 * POJO（ Plain Ordinary Java Object）：在本手册中， POJO专指只有setter/getter/toString的简单类，包括DO/DTO/BO/VO等。
 * Query：数据查询对象，各层接收上层的查询请求。 注意超过2个参数的查询封装，禁止使用Map类来传输。
 * 领域模型命名规约：
 *
 * 数据对象：xxxDO，xxx即为数据表名。
 * 数据传输对象：xxxDTO，xxx为业务领域相关的名称。
 * 展示对象：xxxVO，xxx一般为网页名称。
 * POJO是DO/DTO/BO/VO的统称，禁止命名成xxxPOJO。
 */
public class TestXML {
    private static ApplicationContext ctx;

    public void greet(String nationality, String name) {
        Human human = (Human) ctx.getBean(nationality);
        human.greet(name);
    }

    public void selfIntroduce(String nationality) {
        Human human = (Human) ctx.getBean(nationality);
        human.selfIntroduce();
    }
    /**
     * bean的作用域(两种)：
     * 1) singleton(单例)：在容器初始化时生成bean实例
     * 2) prototype(多例)：在获取bean时生成bean实例
     * @param args
     */
    public static void main(String[] args) {
        String nationality;
        String name;
        // 加载xml配置文件生成spring容器
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestXML testXML = new TestXML();

//        nationality = "chinese";
//        name = "Alexander 温";
//        testXML.greet(nationality, name);
//        testXML.selfIntroduce(nationality);
//
//        nationality = "englishman";
//        name = "Alex Wen";
//        testXML.greet(nationality, name);
//        testXML.selfIntroduce(nationality);

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // scope==singleton，单例模式在容器初始化时生成bean实例
        Chinese chinese1 = (Chinese) applicationContext.getBean("chinese");
        Chinese chinese2 = (Chinese) applicationContext.getBean("chinese");
        System.out.println(chinese1 == chinese2);
        System.out.println(chinese1);
        System.out.println(chinese2);
        // scope==prototype，原型模式在容器初始化时不生成bean实例，在获取bean时生成bean实例
        Englishman englishman1 = (Englishman) applicationContext.getBean("englishman");
        Englishman englishman2 = (Englishman) applicationContext.getBean("englishman");
        System.out.println(englishman1 == englishman2);
        System.out.println(englishman1);
        System.out.println(englishman2);

        StringBuilder sb = new StringBuilder("123");
    }
}
