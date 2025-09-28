package com.service.productCatalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
@EnableScheduling
public class JobScheduleConfig {

    @Scheduled(fixedRate = 1000)
    public void runJob(){
        System.out.println("Shailesh Kumar yadav -"+System.currentTimeMillis());
    }

}
