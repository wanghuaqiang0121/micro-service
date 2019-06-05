package org.module.organization.configure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("org.module.organization.configure.dao.**")
public class OrganizationConfigureApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationConfigureApplication.class, args);
	}

}