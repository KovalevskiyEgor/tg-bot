package com.example.dispatcher2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Dispatcher2Application {

    public static void main(String[] args) {
        SpringApplication.run(Dispatcher2Application.class, args);
    }

}
