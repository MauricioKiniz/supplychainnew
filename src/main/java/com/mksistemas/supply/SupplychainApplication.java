package com.mksistemas.supply;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SupplychainApplication {

    public static void main(String[] args) {
        // ConfigurableApplicationContext ctx = SpringApplication.run(SupplychainApplication.class,
        // args);
        // OrganizationManagerUseCase service = ctx.getBean(OrganizationManagerUseCase.class);
        // service.create(new OrganizationCommand("orgmaster", "1234567890", "BR", "-3"));
        SpringApplication.run(SupplychainApplication.class, args);

        // String id = TSID.from(1001).toLowerCase();
    }

}
