package org.module.organization.team;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("org.module.organization.team.dao.**")
public class OrganizationTeamApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationTeamApplication.class, args);
	}
}
