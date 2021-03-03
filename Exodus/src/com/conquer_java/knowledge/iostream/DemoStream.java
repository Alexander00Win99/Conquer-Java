package com.conquer_java.knowledge.iostream;

import java.io.*;
import java.util.Arrays;
import java.util.zip.*;

/**
 * 【目标】：掌握流的概念和文件压缩的基本原理
 *
 * 【结果】：完成
 *
 * IO: 人机之间 + 机器之间
 * 二进制单位：bit
 * 机器处理数据单位：byte = 8bit
 * 人类处理数据单位：2bytes（UTF-8编码中中文是3个字节）
 *
 * 【流的分类】
 * 字节流：通常用于处理文件、图片、音频、视频、多媒体等二进制数据
 * 字符流：通常用于处理文本文件(字符数据)
 * 节点流：可以和IO设备交互的流
 * 处理流：用于统一封装业已存在的流——节点流或者处理流——(装饰者模式)
 * 节点流(多种多样) —— 处理流(统一包装) —— 操作代码
 *
 * 1. Java IO是采用的是装饰者模式(使用处理流包装节点流，从而使得代码更加通用)。
 * 2. 节点流和处理流的区分方法：节点流在新建时需要一个数据源（文件、内存、网络）作为参数；处理流需要一个节点流作为参数。
 * 3. 节点流都是对应抽象基类的实现类，它们都实现了抽象基类的基础读写方法。其中read()方法如果返回-1，代表已经读到数据源末尾。
 * 4. 处理流的作用就是提高代码的通用性，编写的便捷性，从而提高软件性能。
 *
 * 【压缩和解压】
 * java.util.jar: JarInputStream, JarOutputStream
 * java.util.zip: GZIPInputStream, GZIPOutputStream
 * 压缩：分析文件之中不同字符的概率分布和重复情况，建立长短字符的映射规则，利用短字符替换长字符，从而实现压缩数据存储空间的目的。
 * 解压：利用方向映射规则，还原原始数据。
 * PS: JAR(Java Archive)包本质上是一种压缩文件。
 *
 *
 */
public class DemoStream {
    private static final boolean DEFAULT_DIR_FLAG = false;
    private static final int DEFAUTL_BUF_SIZE = 1024;

    public static void compress(String srcPath, String dstPath) throws Exception {
        compress(new File(srcPath), new File(dstPath), DEFAULT_DIR_FLAG);
    }

    public static void compress(File srcFile, File dstFile, boolean dirFlag) throws Exception {
        compress(srcFile, new ZipOutputStream(new FileOutputStream(dstFile)), dirFlag);
    }

    public static void compress(File srcFile, ZipOutputStream zos, boolean dirFlag) throws Exception {
        if (srcFile.isDirectory() && dirFlag) {
            String base = srcFile.getName() + File.separator;
            System.out.println(base);
            doCompress(srcFile, zos, srcFile.getName() + File.separator);
        } else {
            doCompress(srcFile, zos, "");
        }
        zos.close();
    }

    public static void doCompress(File file, ZipOutputStream zos, String baseDir) throws Exception {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                doCompress(f, zos, baseDir);
        } else {
            byte[] buf = new byte[DEFAUTL_BUF_SIZE];
            FileInputStream fis = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(baseDir + File.separator + file.getName()));
            int count;
            while((count = fis.read(buf, 0, DEFAUTL_BUF_SIZE)) != -1) {
                zos.write(buf, 0, count);
            };
            fis.close();
        }
    }

    public static void decompress(String path) throws Exception {
        decompress(new File(path));
    }

    public static void decompress(File file) throws Exception {
        File parent = file.getParentFile();
        decompress(file, parent);
    }

    public static void decompress(String srcPath, String dstPath) throws Exception {
        decompress(new File(srcPath), new File(dstPath));
    }

    public static void decompress(File srcFile, File dstFile) throws Exception {
        CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());
        ZipInputStream zis = new ZipInputStream(cis);
        doDecompress(zis, dstFile);
        zis.close();
    }

    private static void doDecompress(ZipInputStream zis, File dstFile) throws Exception {
        ZipEntry zipEntry = null;
        while ((zipEntry = zis.getNextEntry()) != null) {
            String dir = dstFile.getPath() + File.separator + zipEntry.getName();
            System.out.println("zipentry path" + zipEntry);
            System.out.println("zipentryy name" + zipEntry.getName());
            File dirFile = new File(dir);
            // 如果父文件夹不存在，递归创建
            fileProbe(dirFile);
            if (zipEntry.isDirectory()) { // 若是目录，创建目录
                dirFile.mkdirs();
            } else { // 若是文件，具体解压压缩文件的每个ZipEntry对象
                decompressFile(zis, dirFile);
            }
            zis.closeEntry();
        }
    }

    private static void decompressFile(ZipInputStream zis, File dstFile) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dstFile));
        int count;
        byte[] buf = new byte[DEFAUTL_BUF_SIZE];
        while ((count = zis.read(buf, 0, DEFAUTL_BUF_SIZE)) != -1) {
            bos.write(buf, 0, count);
        }
        bos.close();
    }

    public static void fileProbe(File dirFile) throws Exception {
        File parentFile = dirFile.getParentFile();
        if (parentFile != null)
            if (!parentFile.exists()) {
                System.out.println("父目录不存在！");
                fileProbe(parentFile);
                parentFile.mkdirs();
            }
    }

    public static void localCompress(String srcPath, String dstPath) throws Exception {
        // 创建原始文件+压缩文件
        File originalFile = new File(srcPath);
        File compressedFile = new File(dstPath);

        System.out.println("Start compressing......");

        // 创建输入文件节点流+输出文件节点流
        FileInputStream fis = new FileInputStream(originalFile);
        FileOutputStream fos = new FileOutputStream(compressedFile);

        // 创建输入缓冲处理流+输出缓冲处理流
        BufferedInputStream bis = new BufferedInputStream(fis);
        ZipOutputStream zos = new ZipOutputStream(fos);
        BufferedOutputStream bos = new BufferedOutputStream(zos);

        // 本地压缩：压缩文件存放地址==原始文件所在目录
        String baseDir = originalFile.getName();
        System.out.println("baseDir: " + baseDir);
        System.out.println(baseDir == srcPath);
        System.out.println(originalFile.getAbsolutePath());
        zos.putNextEntry(new ZipEntry(baseDir)); // 添加将被压缩的子文件或者子目录
//        BufferedOutputStream bos = new BufferedOutputStream(zos);
        int count = 0;
        byte[] buf = new byte[DEFAUTL_BUF_SIZE];
        while ((count = bis.read(buf)) != -1) {
            bos.write(count);
        }

        bis.close();
        fis.close();
        bos.close();
        zos.close();
        fos.close();
    }

    public static void main(String[] args) throws Exception {
        File file = new File(".");
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println(file.getAbsolutePath());
        String[] txtList = file.list((dir, name) -> name.endsWith(".txt"));
        System.out.println(Arrays.toString(txtList));

//        FileReader fr = null;
//        try {
//            fr = new FileReader("io.txt");
//            char[] buf = new char[1024];
//            int count = 0;
//            while ((count = fr.read(buf)) > 0) {
//                System.out.println(new String(buf, 0, count));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fr != null) {
//                try {
//                    fr.close()
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // JDK1.7之后，try语句()括号内部声明的资源，会被JVM在语句执行完毕后自动关闭释放。例如：
        try (FileReader fr = new FileReader("io.txt")) {
            char[] buf = new char[DEFAUTL_BUF_SIZE];
            int count = 0;
            while ((count = fr.read(buf)) > 0)
                System.out.println(new String(buf, 0, count));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 自主压缩文件无法解压成功
         */
        System.out.println("++++++++++++++++Begin Compress++++++++++++++++");
        localCompress("io.txt", "io.rar");
        System.out.println("----------------End Compress----------------");

        /**
         * 标准压缩解压过程
         */
        System.out.println("++++++++++++++++Begin Compress++++++++++++++++");
        compress("io.txt", "io.zip");
        decompress("io.zip", "recovery");
        System.out.println("----------------End Compress----------------");

        File fileChild = new File("D:\\Workspace\\Java\\Demo\\file.txt");
        System.out.println("fileChild.exists(): " + fileChild.exists());
        System.out.println("fileChild.getAbsolutePath(): " + fileChild.getAbsolutePath());
        File fileParent = fileChild.getParentFile();
        System.out.println("fileParent.exists(): " + fileParent.exists());
        System.out.println("fileParent.getAbsolutePath(): " + fileParent.getAbsolutePath());
    }
}
