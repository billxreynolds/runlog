package com.breynolds.runlog.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry)
	 * { registry.addViewController("/login").setViewName("login");
	 * registry.addViewController("/search").setViewName("search");
	 * registry.addViewController("/searchResults").setViewName("searchResults")
	 * ; }
	 */

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}