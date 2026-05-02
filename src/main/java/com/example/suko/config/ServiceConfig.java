package com.example.suko.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.example.suko.service",
        "com.example.suko.controller",
        "com.example.suko.config"
})
public class ServiceConfig {
}
