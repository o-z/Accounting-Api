package com.javacase.accountingapi;

import com.javacase.accountingapi.common.util.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
@ComponentScan(
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {DomainComponent.class})
    }
)
public class AccountingApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountingApiApplication.class, args);
  }

}
