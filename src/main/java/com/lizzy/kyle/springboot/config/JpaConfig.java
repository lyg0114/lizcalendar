package com.lizzy.kyle.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

public class JpaConfig {

    @Configuration
    @EnableJpaAuditing
    public class JapConfig{}
}
