package com.hgy.it.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/hello")
public class HelloWorld {

//    private HelloC helloC; // 有包名不能调用无包名的类

    @RequestMapping("/world") // SpringMVC 没有指定方法默认就是GET
    public String helloWorld(String lang) {
        if ("java".equalsIgnoreCase(lang)) {
            return "hello world for java!";
        } else if ("c".equalsIgnoreCase(lang)) {
            //            sayHelloC();
            return "hello world for c/c++!";
        } else {
            return "non this language, sorry!";
        }
    }

    @RequestMapping("/callable") // 提高体统吞吐量(尤其是nio模式情况下)
    public Callable<String> callable(String lang) {
       return  () -> "this interface use callable!";
    }

    public native String sayHelloC();
}
