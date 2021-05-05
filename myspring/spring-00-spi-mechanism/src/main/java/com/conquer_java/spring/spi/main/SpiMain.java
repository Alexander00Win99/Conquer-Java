package com.conquer_java.spring.spi.main;

import com.conquer_java.spring.spi.api.IParseData;
import com.conquer_java.spring.spi.impl01.JsonParser;
import com.conquer_java.spring.spi.impl02.XmlParser;
import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 【SPI机制的核心思想】
 * Java SPI  接口编程 + 策略模式 + 配置文件
 *
 * 注：
 * 配置文件 <====> 增加一层中间层（“任何软件工程问题均可通过增加一层中间层来解决”）
 */
public class SpiMain {
    public static void testSpi01() {
        // 通过ServiceLoader.load()加载服务提供接口（对外）
        ServiceLoader<IParseData> load = ServiceLoader.load(IParseData.class);
        // 获取加载的内部迭代器
        Iterator<IParseData> iterator = load.iterator();

        // 循环遍历
        while (iterator.hasNext()) {
            IParseData next = iterator.next();
            System.out.println(next.about());
            next.parse();
        }
    }

    public static void testSpi02() {
        // 通过Service.provider()获取内部迭代器
        Iterator<IParseData> providers = Service.providers(IParseData.class);

        // 循环遍历
        while (providers.hasNext()) {
            IParseData next = providers.next();
            System.out.println(next.about());
            next.parse();
        }
    }

    public static void main(String[] args) {
        System.out.println("++++++++++++++++Begin——原生方式==高耦合度++++++++++++++++");
        System.out.println("原生方式：需要知道第三方服务的实现类全路径！");
        JsonParser jsonParser = new com.conquer_java.spring.spi.impl01.JsonParser();
        System.out.println(jsonParser.about());
        jsonParser.parse();

        XmlParser xmlParser = new com.conquer_java.spring.spi.impl02.XmlParser();
        System.out.println(xmlParser.about());
        xmlParser.parse();
        System.out.println("----------------End——原生方式==高耦合度----------------");

        System.out.println("++++++++++++++++Begin——通过ServiceLoader加载服务++++++++++++++++");
        testSpi01();
        System.out.println("----------------End——通过ServiceLoader加载服务----------------");

        System.out.println("++++++++++++++++Begin——通过Service.providers()加载服务++++++++++++++++");
        testSpi02();
        System.out.println("----------------End——通过Service.providers()加载服务----------------");
    }
}
