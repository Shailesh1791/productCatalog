package com.service.productCatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
//@EnableDiscoveryClient
@EnableCaching
public class ProductCatalogApplication {

	public static void main(String[] args) {
		ApplicationContext configurableApplicationContext =SpringApplication.run(ProductCatalogApplication.class, args);
		long beanCount=configurableApplicationContext.getBeanDefinitionCount();
		System.out.println(beanCount);
		String[] beanNames = configurableApplicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			//System.out.println(beanName);
		}
	}

}
