package org.module.organization.permission.intercept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Bean
	public SecurityInterceptor getUserSecurityInterceptor() {
		return new SecurityInterceptor();
	}
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getUserSecurityInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}