package com.example.search.coffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
 
 
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
class PersistenceContext {
 
    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }
}