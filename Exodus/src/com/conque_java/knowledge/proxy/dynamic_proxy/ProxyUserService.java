package com.conque_java.knowledge.proxy.dynamic_proxy;

/**
 * 【JVM动态代理】
 */
//public class ProxyUserService implements UserService {
//    UserService target;
//
//    public ProxyUserService(UserService target) {
//        this.target = target;
//    }
//
//    // 作用：代理目标<== 桥梁 ==>代理逻辑
//    public static class MyProxyHandler implements InvocationHandler {
//        UserService target;
//
//        public MyProxyHandler(UserService target) {
//            this.target = target;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println("前置逻辑");
//            try {
//                method.invoke(target, args);
//                return null;
//            } finally {
//                System.out.println("后置逻辑");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        ClassLoader loader = ProxyUserService.class.getClassLoader();
//        UserService target = new UserServiceProxyImpl();
////        UserService proxyService = (UserService) Proxy.newProxyInstance(loader, new Class[]{UserService.class}, new InvocationHandler() {
////            @Override
////            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
////                return null;
////            }
////        });
//        UserService proxyService = (UserService) Proxy.newProxyInstance(loader, new Class[]{UserService.class}, new MyInvocationHandler(target));
//
//        // 下面一种新的方式
//        int accessFlags = Modifier.PUBLIC | Modifier.FINAL;
//        byte[] bytes = ProxyGenerator.generateProxyClass("AlexProxy", new Class[]{UserService.class}, accessFlags);
//
//        try {
//            Files.write(new File(System.getProperty("user.dir") + "/alex$Proxy.class"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
