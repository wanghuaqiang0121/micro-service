package org.module.organization.user.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CORSConfiguration {
	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
			}
		};
	}*/
}
