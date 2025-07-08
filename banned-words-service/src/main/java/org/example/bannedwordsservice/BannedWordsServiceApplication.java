package org.example.bannedwordsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BannedWordsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BannedWordsServiceApplication.class, args);
    }

}
