package com.backend.java.shoppingapi.configuration;

import com.backend.java.shoppingapi.repository.ReportRepository;
import com.backend.java.shoppingapi.repository.ReportRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ReportRepository reportRepository(){
        return new ReportRepositoryImpl();
    }
}
