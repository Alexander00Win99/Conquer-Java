package com.conquer_java.knowledge.JSR133;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 【目标】：掌握volatile关键字的可见性原理 + Log4j用法
 *
 * 【结果】：完成(报错参考：http://logging.apache.org/log4j/1.2/faq.html#noconfig)
 *
 * Log4J包括三个重要组件：日志信息的优先级，日志信息的输出目的地，日志信息的输出格式。
 *
 * 一、配置根Logger
 * log4j.rootLogger = [level == FATAL|ERROR|WARN|INFO|DEBUG|ALL|OFF], appenderName1, appenderName2, ......, appenderNameN
 * 应用程序中低于当前配置的日志级别的信息，将无法显示。
 *
 * 二、设置各个appenderName日志对应log4j里的Appender类
 * log4j.appender.appenderName == Fully Qualified Name of Log4j Appender Class包括如下选项：
 * 1) org.apache.log4j.ConsoleAppender —— 控制台
 * 2) org.apache.log4j.FileAppender —— 文件
 * 3) org.apache.log4j.DailyRollingFileAppender —— 每天产生一个日志文件
 * 4) org.apache.log4j.RollingFileAppender —— 当文件大小到达指定尺寸时自动产生一个新的文件
 * 5) org.apache.log4j.WriterAppender —— 将日志信息以流格式发送到指定位置
 *
 * 三、配置各个appenderName日志的具体参数
 * log4j.appender.appenderName.option中的option可以替换成为如下选项：
 * 1) ConsoleAppender —— 控制台选项
 *      Threshold=DEBUG: 指定日志消息输出级别
 *      ImmediateFlush=true: 默认值是true，所有消息立即输出
 *      Target=System.err: 默认值是System.out，指定消息输出位置是控制台
 * 2) FileAppender —— 文件选项
 *      Threshold=DEBUG: 指定日志消息输出级别
 *      ImmediateFlush=true: 默认值是true，所有消息立即输出
 *      File=filename.log: 指定消息输出到filename.log文件中
 *      Append=false: 默认值是true，消息追加到指定文件末尾，false是将消息覆盖指定文件内容
 * 3) RollingFileAppender —— 每日文件选项
 *      Threshold=DEBUG: 指定日志消息输出级别
 *      ImmediateFlush=true: 默认值是true，所有消息立即输出
 *      File=filename.log: 指定消息输出到filename.log文件中
 *      Append=false: 默认值是true，消息追加到指定文件末尾，false是将消息覆盖指定文件内容
 *      MaxFileSize=100KB: 单位可以是KB, MB或者GB。在日志文件到达指定大小时，将会自动滚动，即将原来日志内容移到filename.log.1,2...n文件
 *      MaxBackupIndex=2: 指定可以产生的日志文件的最大滚动数量
 *
 * 四、设置日志信息布局呈现
 * log4j.appender.appenderName.layout == Fully Qualified Name of Log4j Layout Class可以替换成为如下选项：
 * 1) org.apache.log4j.HTMLLayout —— HTML表格形式布局
 * 2) org.apache.log4j.PatternLayout —— 指定模式自由布局
 * 3) org.apache.log4j.SimpleLayout —— 简单布局(包含日志级别以及日志信息)
 * 4) org.apache.log4j.TTCCLayout —— TTCC布局(包含时间、线程、类别等信息)
 * ConversionPattern —— 日志信息转换模式含义如下：
 *
 * 五、通过additivity设置父子Logger继承关系
 * log4j.additivity.filename = false
 * 默认子Logger会继承父Logger的appender，子Logger会在父Logger的appender里输出。若将additivity设置为false，子Logger只会在自己的appender里输出，而不会在父Logger的appender里输出。
 *
 *  -X号: X信息输出时左对齐；
 *  %p: 输出日志信息优先级，即DEBUG，INFO，WARN，ERROR，FATAL
 *  %d: 输出日志时间点的日期或时间格式，默认格式为ISO8601，也可自行指定格式，比如：%d{yyyy MM dd HH:mm:ss,SSS}，输出类似：2000年01月01日 12:00:00,000
 *  %r: 输出自应用启动到输出该log信息所耗费的毫秒数
 *  %c: 输出日志信息所属的类目，通常就是所在类的全名
 *  %t: 输出产生该日志事件的线程名
 *  %l: 输出日志事件的发生行号位置。相当于%C:%M(%F:%L)的组合，包括类名、发生的线程以及在代码中的行数。举例：Testlog4.main (TestLog4.java:100)
 *  %x: 输出和当前线程相关联的NDC(嵌套诊断环境)，尤其用于像Java Servlets这样的多客户多线程的应用之中
 *  %%: 输出一个"%"字符
 *  %F: 输出日志消息产生时所在的文件名称
 *  %L: 输出代码中的行号
 *  %m: 输出代码中指定的消息，产生的日志具体信息
 *  %n: 输出一个回车换行符，Windows系统为"\r\n"，Linux或者Unix系统为"\n"
 *
 * 注：DatePattern=${Time-Format}包括如下选项：
 * 1) '.'yyyy-MM —— 每月
 * 2) '.'yyyy-ww —— 每周
 * 3) '.'yyyy-MM-dd —— 每天
 * 4) '.'yyyy-MM-dd-a —— 每天两次
 * 5) '.'yyyy-MM-dd-HH —— 每时
 * 6) '.'yyyy-MM-dd-HH-mm —— 每分
 *
 * 可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度以及文本对齐方式。如下：
 *  1)%20c: 指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，默认右对齐
 *  2)%-20c: 指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，"-"号代表左对齐
 *  3)%.40c: 指定输出category的名称，最大的宽度是40，如果category的名称大于40的话，就会将左边多出的字符截掉，但是小于40的话也不会有空格
 *  4)%20.40c: 如果category的名称小于20就补空格，并且右对齐，如果名称长于40字符，就会将左边多出的字符截掉
 *
 * 【举例】
 * #### 配置根 ####
 * log4j.rootLogger = DEBUG,console,fileAppender,dailyRollingFile,ROLLING_FILE,MAIL,DATABASE
 *
 * #### 设置输出SQL的级别，其中logger后面的内容全部为jar包中所包含的包名 ####
 * log4j.logger.org.apache = DEBUG
 * log4j.logger.java.sql.Connection = DEBUG
 * log4j.logger.java.sql.Statement = DEBUG
 * log4j.logger.java.sql.PreparedStatement = DEBUG
 * log4j.logger.java.sql.ResultSet = DEBUG
 *
 * #### 配置输出到控制台 ####
 * log4j.appender.console = org.apache.log4j.ConsoleAppender
 * log4j.appender.console.Target = System.out
 * log4j.appender.console.layout = org.apache.log4j.PatternLayout
 * log4j.appender.console.layout.ConversionPattern = %d{ABSOLUTE} %5p %c{ 1 }:%L - %m%n
 *
 * #### 配置输出到文件 ####
 * log4j.appender.fileAppender = org.apache.log4j.FileAppender
 * log4j.appender.fileAppender.File = logs/log.log
 * log4j.appender.fileAppender.Append = true
 * log4j.appender.fileAppender.Threshold = DEBUG
 * log4j.appender.fileAppender.layout = org.apache.log4j.PatternLayout
 * log4j.appender.fileAppender.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss} [ %t:%r ] - [ %p ] %m%n
 *
 * #### 配置输出到每日文件 ####
 * log4j.appender.dailyRollingFile = org.apache.log4j.DailyRollingFileAppender
 * log4j.appender.dailyRollingFile.File = logs/log.log
 * log4j.appender.dailyRollingFile.Append = true
 * log4j.appender.dailyRollingFile.Threshold = DEBUG
 * log4j.appender.dailyRollingFile.layout = org.apache.log4j.PatternLayout
 * log4j.appender.dailyRollingFile.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss} [ %t:%r ] - [ %p ] %m%n
 *
 * #### 配置输出到文件(到达指定尺寸自动产生新的文件) ####
 * log4j.appender.ROLLING_FILE = org.apache.log4j.RollingFileAppender
 * log4j.appender.ROLLING_FILE.Threshold = ERROR
 * log4j.appender.ROLLING_FILE.File = rolling.log
 * log4j.appender.ROLLING_FILE.Append = true
 * log4j.appender.ROLLING_FILE.MaxFileSize = 10KB
 * log4j.appender.ROLLING_FILE.MaxBackupIndex = 1
 * log4j.appender.ROLLING_FILE.layout = org.apache.log4j.PatternLayout
 * log4j.appender.ROLLING_FILE.layout.ConversionPattern = [framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
 *
 * #### 配置输出到邮件 ####
 * log4j.appender.MAIL = org.apache.log4j.net.SMTPAppender
 * log4j.appender.MAIL.Threshold = FATAL
 * log4j.appender.MAIL.BufferSize = 10
 * log4j.appender.MAIL.From = xyz@abc.com
 * log4j.appender.MAIL.SMTPHost = mail.abc.com
 * log4j.appender.MAIL.Subject = Log4J Message
 * log4j.appender.MAIL.To = root@abc.com
 * log4j.appender.MAIL.layout = org.apache.log4j.PatternLayout
 * log4j.appender.MAIL.layout.ConversionPattern = [framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
 *
 * #### 配置输出到数据库 ####
 * log4j.appender.DATABASE = org.apache.log4j.jdbc.JDBCAppender
 * log4j.appender.DATABASE.URL = jdbc:mysql://localhost:3306/demo
 * log4j.appender.DATABASE.driver = com.mysql.jdbc.Driver
 * log4j.appender.DATABASE.user = root
 * log4j.appender.DATABASE.password = root
 * log4j.appender.DATABASE.sql = INSERT INTO LOG4J (Message) VALUES ('[framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n')
 * log4j.appender.DATABASE.layout = org.apache.log4j.PatternLayout
 * log4j.appender.DATABASE.layout.ConversionPattern = [framework] %d - %c -%-4r [%t] %-5p %c %x - %m%n
 * log4j.appender.A1 = org.apache.log4j.DailyRollingFileAppender
 * log4j.appender.A1.File = SampleMessages.log4j
 * log4j.appender.A1.DatePattern = yyyyMMdd-HH'.log4j'
 * log4j.appender.A1.layout = org.apache.log4j.xml.XMLLayout
 *
 */
public class DemoVolatileVisibility {
    // 1) 获取日志记录器：log4j + commons-logging = 日志支持
    public static Logger logger = Logger.getLogger(DemoVolatileVisibility.class);
    // 2) 配置Log4J环境，包括如下三种：
    /**
     * BasicConfigurator.configure() // 自动快速使用Log4J环境
     * PropertyConfigurator.confiugre(String configFilename) // 读取Java属性文件
     * DOMConfigurator.configure(String filename) // 读取XML配置文件
     */
    static {
        PropertyConfigurator.configure("src/log4j.properties");
    }

    // 主内存的共享变量initFlag如果没有volatile修饰，工作内存的变量副本就会无法获取更新信息
    private static volatile boolean initFlag = false;

    public static void load() {
        while (!initFlag) {
            // 死循环中。。。。。。
        }
        logger.info("线程：" + Thread.currentThread().getName() + "嗅探到了initFlag状态已经发生改变，跳出循环！");
    }

    public static void refresh() {
        logger.info("start refreshing data......");
        initFlag = true;
        logger.info("data refreshed successfully......");
    }

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            load();
        }, "Thread-A");
        threadA.start();

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread threadB = new Thread(() -> {
            refresh();
        }, "Thread-B");
        threadB.start();
    }
}
