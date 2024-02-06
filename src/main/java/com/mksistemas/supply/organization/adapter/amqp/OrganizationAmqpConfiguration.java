package com.mksistemas.supply.organization.adapter.amqp;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationAmqpConfiguration {

  public static final String ORGANIZATION_EXCHANGE_NAME = "organization-exchange";
  public static final String QUEUE_NAME = "organization";


  @Bean
  Declarables fanoutOrganizationBindings() {
    Queue organizationQueue = new Queue(QUEUE_NAME, true);
    FanoutExchange organizationFanoutExchange = new FanoutExchange(ORGANIZATION_EXCHANGE_NAME);

    return new Declarables(organizationQueue, organizationFanoutExchange,
        BindingBuilder.bind(organizationQueue).to(organizationFanoutExchange));
  }
}
