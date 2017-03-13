package com.ravlinko.concordion.extension.mockserver;

import com.ravlinko.concordion.extension.mockserver.tag.HttpMethodTag;
import com.ravlinko.concordion.extension.mockserver.tag.MockServerTag;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:mockserver-extension.properties")
@Configuration
@ComponentScan
public class MockServerConfig {

	@Bean
	public MockServerTag getHttpMethodCommand() {
		return new HttpMethodTag("GET");
	}

	@Bean
	public MockServerTag postHttpMethodCommand() {
		return new HttpMethodTag("POST");
	}

}
