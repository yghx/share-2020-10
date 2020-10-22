package com.hgy.it.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = null;
        try {
            data = loadByte(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.defineClass(data, 0, data.length);
    }

    private byte[] loadByte(String name) throws IOException {
        File file = new File(String.format(Locale.ENGLISH, "/my/classes/%s", name));
        FileInputStream fi = new FileInputStream(file);
        int len = fi.available(); // 不安全
        byte[] b = new byte[len];
        fi.read(b);
        return b;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader();
        Class clazz = classLoader.loadClass("CLDemo.class");
        Object o = clazz.newInstance();
//        CLDemo clDemo1 = (CLDemo) clazz.newInstance(); // com.hgy.it.classloader.CLDemo cannot be cast to com.hgy.it.classloader.CLDemo
        // com.hgy.it.classloader.CLDemo cannot be cast to com.hgy.it.classloader.CLDemo
//        Class<CLDemo> clazz2 = (Class<CLDemo>) classLoader.loadClass("CLDemo.class");
//        CLDemo clDemo2 = clazz2.newInstance();
//        clDemo2.test();
        Method method = clazz.getMethod("test");
        method.invoke(o);
    }
}