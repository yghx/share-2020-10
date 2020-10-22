package com.hgy.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

/**
 * 类说明：
 */
@RestController
@RequestMapping("/jvm")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/heap")
    public String test(String mat, Integer count) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    downLatch.countDown();// 减1
                    downLatch.await();// 等待并发
                    ResponseEntity<String> responseEntity = restTemplate.exchange(String.format(Locale.ENGLISH, "http://localhost:8080/jvm/%s", mat), HttpMethod.GET, null, String.class);
                    System.out.printf("body = %s", responseEntity.getBody());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "heap-mat").start();
        }
        return "down";
    }
}
