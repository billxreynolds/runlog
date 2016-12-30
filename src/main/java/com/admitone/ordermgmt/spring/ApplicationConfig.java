package com.admitone.ordermgmt.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.admitone.ordermgmt.service.OrderMgmtService;

@Configuration
@EnableTransactionManagement
public class ApplicationConfig {

  @Bean(name = "orderMgmtService")
  public OrderMgmtService orderMgmtService() {
    return new OrderMgmtService();
  }

  //
  // allow external configuration of the warfile's properties
  // 
  @Configuration
  @Profile("default")         
  @PropertySources({
    @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false),
    @PropertySource(value = "classpath:application-ext.properties", ignoreResourceNotFound = true)})  
  static class DefaultsProps {
  }


}
