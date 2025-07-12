package org.example.postservice.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.example.postservice.openfeign")
public class OpenFeignConfiguration {
}
