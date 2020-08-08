package com.conque_java.knowledge.class_load;

import java.io.*;

public class MyClassLoader extends ClassLoader {
    private String path;
    private String classLoaderName;

    public MyClassLoader(String path, String name) {
        this.path = path;
        this.classLoaderName = name;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            byte[] data = loadClassData(path + "\\" + name + ".class");
            System.out.println("能够找到.class文件");
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] loadClassData(String name) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;

        try {
            fis = new FileInputStream(new File(name));
            baos = new ByteArrayOutputStream();
            int count = 0;
            while ((count = fis.read()) != -1) {
                baos.write(count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        System.out.println("能够生成.class文件字节流");
        return baos == null ? null : baos.toByteArray();
    }
}
