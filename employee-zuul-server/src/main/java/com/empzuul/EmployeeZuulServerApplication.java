package com.empzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.empzuul.filters.PostZuulFilter;
import com.empzuul.filters.PreZuulFilter;
import com.netflix.zuul.ZuulFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.empzuul", "com.exception" })
public class EmployeeZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeZuulServerApplication.class, args);
	}

	@Bean
	public ZuulFilter getPreFilter() {
		return new PreZuulFilter();
	}

	@Bean
	public ZuulFilter getPostFilter() {
		return new PostZuulFilter();
	}

//	@Bean
//	public ZuulFilter getErrorFilter() {
//		return new ErrorZuulFilter();
//	}

}
