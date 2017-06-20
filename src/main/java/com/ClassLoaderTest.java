package com;

import java.io.IOException;
import java.io.InputStream;

/**
 * jvm 类加载过程：加载，验证，准备，解析，初始化，
 * 1. 启动类加载器(Bootstrap)，由C++实现,负责加载JAVA_HOME/lib下的类
 * 2. 扩展类加载器，负责加载JAVA_HOME/lib/ext目录中
 * 3. 应用程序类加载器,负责加载用户classpath路径上指定的类库
 *
 * 双亲委派模型
 * 概念:除了顶层启动类加载器之外，其余类加载器都有自己的父类加载器，
 * 这里的父子关系不是继承实现，而是通过组合的方式复用父加载器
 * 过程: 一个类加载器收到类加载请求，它首先不会自己尝试加载这个类，而是把类加载请求发送到父加载器，
 * 如果父加载器无法完成这个类加载，子类才会尝试加载这个类
 *
 *
 * Created by wangyong on 2017/6/13.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    System.out.println("fileName:" + fileName);
                    InputStream is = getClass().getResourceAsStream(fileName);

                    if (is == null) {
                        return super.loadClass(name);
                    }

                    byte[] bytes = new byte[is.available()];
                    is.read(bytes);

                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = loader.loadClass("com.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof com.ClassLoaderTest);

    }

}
