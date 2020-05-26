package com.shop.eb.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
    //        AutoCode.init(false);
        SpringApplication.run(UserApplication.class, args);
    }
}
