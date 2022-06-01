package com.trainingapps.userms;

import com.trainingapps.userms.util.AuthenticationFiler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 1) @Configuration
 * 2) @ComponentScan
 * 3)@EnableAutoConfiguration
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	@Bean
	public AuthenticationFiler authenticationFilter(){
		return new AuthenticationFiler();
	}

	@Bean
	public FilterRegistrationBean<AuthenticationFiler>registerationFilter(){
		FilterRegistrationBean<AuthenticationFiler>registeration=new FilterRegistrationBean<>();
		registeration.addUrlPatterns("/users/*");
		registeration.setFilter(authenticationFilter());
		return registeration;
	}



}
