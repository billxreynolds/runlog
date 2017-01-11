package com.breynolds.runlog.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.breynolds.runlog.service.RunLogService;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {

	@Bean(name = "runLogService")
	public RunLogService runLogService() {
		return new RunLogService();
	}

	//
	// allow external configuration of the warfile's properties
	//
	@Configuration
	@Profile("default")
	@PropertySources({ @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false),
			@PropertySource(value = "classpath:application-ext.properties", ignoreResourceNotFound = true) })
	static class DefaultsProps {
	}

}
