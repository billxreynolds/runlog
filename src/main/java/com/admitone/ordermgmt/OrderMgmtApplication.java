package com.admitone.ordermgmt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.admitone.ordermgmt")
@SpringBootApplication
@EnableJpaRepositories
public class OrderMgmtApplication extends SpringBootServletInitializer {
  
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return configureApplication(builder);
  }

  public static void main(String[] args) {
      configureApplication(new SpringApplicationBuilder()).run(args);
  }

  private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
      return builder.sources(OrderMgmtApplication.class);
  }

}
