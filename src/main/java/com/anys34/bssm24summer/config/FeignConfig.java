package com.anys34.bssm24summer.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.anys34.bssm24summer.feign")
public class FeignConfig {
}
