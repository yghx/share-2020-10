package com.hgy.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * java -jar -XX:+HeapDumpOnOutOfMemoryError my.jar
 * -XX:+HeapDumpOnOutOfMemoryError -Xms30M -Xmx30M -Xmn10m
 */
@SpringBootApplication
public class JVMApplication {

    public static void main(String[] args) {
        SpringApplication.run(JVMApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(180000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }
}
