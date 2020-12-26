package com.conquer_java.design_pattern.singleton;

import com.mysql.cj.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//// 单例容器：利用类名注册一组单例实现后续按需提取(类似Spring)
//public class ContainerSingleton {
//    private static String prop;
//    // HashMap无法保证线程安全，HashMap是线程安全的但是性能损耗大，所以，选择ConcurrentHashMap。
//    private static Map<String, ContainerSingleton> map = new ConcurrentHashMap<>();
//    static {
//        ContainerSingleton singleton = new ContainerSingleton();
//        map.put(singleton.getClass().getName(), singleton);
//    }
//    protected ContainerSingleton() {} // 默认无参构造函数
//    public static String getProp() {
//        return ContainerSingleton.prop;
//    }
//    public static void setProp(String prop) {
//        ContainerSingleton.prop = prop;
//    }
//    public static ContainerSingleton getInstance(String name) {
//        if (name == null) {
//            name = ContainerSingleton.class.getName();
//            System.out.println("name == null --> name == " + name);
//        }
//        if (map.get(name) == null) {
//            try {
//                map.put(name, (ContainerSingleton) Class.forName(name).newInstance());
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return map.get(name);
//    }
//    public static void about() {
//        System.out.println("This is Singleton instance registration container!");
//    }
//
//    public static void main(String[] args) {
////        ContainerSingleton instance01 = ContainerSingleton.getInstance("singleton01");
////        ContainerSingleton instance02 = ContainerSingleton.getInstance("singleton02");
//
//        ContainerSingleton instance01 = ContainerSingleton.getInstance("ContainerSingleton");
//        ContainerSingleton instance02 = ContainerSingleton.getInstance("ContainerSingleton");
//
//        System.out.println(instance01 == instance02);
//    }
//}

// 单例容器：利用类名注册一组单例实现后续按需提取(类似Spring，用户需要提供具体类名)
public class ContainerSingleton {
    // HashMap无法保证线程安全，HashMap是线程安全的但是性能损耗大，所以，选择ConcurrentHashMap。
    private static Map<String, Object> map = new ConcurrentHashMap<>();

    private ContainerSingleton() {}

    public static Object getInstance(String key) {
        return map.get(key);
    }

    public static void putInstance(String key, Object instance) {
        if (!StringUtils.isNullOrEmpty(key) && instance != null)
            if (!map.containsKey(key))
                map.put(key, instance);
    }

    static class FirstSingleton {
        private static final FirstSingleton FIRST_SINGLETON = new FirstSingleton();
        private FirstSingleton() {}
    }

    static class SecondSingleton {
        private static final SecondSingleton SECOND_SINGLETON = new SecondSingleton();
        private SecondSingleton() {}
    }

    static class ThirdSingleton {
        private static final ThirdSingleton THIRD_SINGLETON = new ThirdSingleton();
        private ThirdSingleton() {}
    }

    public static void about() {
        System.out.println("This is Singleton instance registration container!");
    }

    public static void main(String[] args) {
        Object instance01 = ContainerSingleton.getInstance("ContainerSingleton.FirstSingleton.FIRST_SINGLETON");
        Object instance02 = ContainerSingleton.getInstance("ContainerSingleton.SecondSingleton.SECOND_SINGLETON");
        Object instance03 = ContainerSingleton.getInstance("ContainerSingleton.ThirdSingleton.THIRD_SINGLETON");

        System.out.println(instance01 == instance02);
        System.out.println(instance01 == null);
    }
}
