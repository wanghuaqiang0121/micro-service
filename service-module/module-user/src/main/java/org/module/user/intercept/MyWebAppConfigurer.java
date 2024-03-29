package org.module.user.intercept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Bean
	public UserSecurityInterceptor getUserSecurityInterceptor() {
		return new UserSecurityInterceptor();
	}
	@Bean
	public ItvDoctorSecurityInterceptor getDoctorSecurityInterceptor() {
		return new ItvDoctorSecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getUserSecurityInterceptor()).addPathPatterns("/user/**");
		registry.addInterceptor(getDoctorSecurityInterceptor()).addPathPatterns("/itv/doctor/**");
		super.addInterceptors(registry);
	}
}