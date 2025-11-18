package com.project.сonfig;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class ConfigClientConfig {
    // @RefreshScope позволяет обновлять конфигурацию
    // из Spring Cloud Config Server без перезапуска приложения.
}
