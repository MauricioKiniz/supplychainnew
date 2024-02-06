package com.mksistemas.supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.mksistemas.supply.organization.OrganizationManagerUseCase;
import com.mksistemas.supply.organization.OrganizationManagerUseCase.OrganizationCommand;

@SpringBootApplication
public class SupplychainApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(SupplychainApplication.class, args);
    OrganizationManagerUseCase service = ctx.getBean(OrganizationManagerUseCase.class);
    service.create(new OrganizationCommand("orgmaster", "1234567890", "BR", "-3"));
  }

}
