package com.hgy.it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * java -jar -XX:+HeapDumpOnOutOfMemoryError my.jar
 * -XX:+HeapDumpOnOutOfMemoryError -Xms30M -Xmx30M -Xmn10m
 */
@RestController
@RequestMapping("/jvm")
public class MatController {
    @RequestMapping("/mat")
    public String mat() {
        ThreadLocal<Byte[]> localVariable = new ThreadLocal<>();
//        ThreadLocal<String> localString = new ThreadLocal<>(); // 都位于ThreadLocalMap中
        localVariable.set(new Byte[4096 * 1024]);// 为线程添加变量
//        localString.set(new String("这两个ThreadLocal示例都位于当前线程(Thread)的ThreadLocalMap" +
//                "中,其中localVariable,localString为Map的两个key"));
        // 当生命周期结束时候,localVariable指向内存(new ThreadLocal<>())会被JVM回收,如果ThreadLocalMap
        // 的key不是弱引用,将会导致new ThreadLocal<>()不能被回收
        // 这个key和localVariable之间的引用是天然会存在的(我们获取map中的v不是因为这弱引用存在,而是因为我们有key值),只要以localVariable作为key,
        // 这个地方就会存在引用关系,因此这个地方设计成了弱引用
        // 并且tomcat是单线程的,所以线程(GC Root)不会消失,存储数据的Map就永远不会被回收
        // 然而我们的实际业务一般是localVariable生命周期结束时候,Map数据也需要回收 ,localVariable.remove();
        //  ThreadLocal优化,set值时候有一定几率将k==null的数据覆盖掉,replaceStaleEntry(key, value, i);
        return "mat";
    }

    @RequestMapping("/mat1")
    public String mat1() {
        ThreadLocal<Byte[]> localVariable = new ThreadLocal<>();
        localVariable.set(new Byte[4096 * 1024]);// 为线程添加变量
        localVariable.remove();
        return "mat1";
    }
}
