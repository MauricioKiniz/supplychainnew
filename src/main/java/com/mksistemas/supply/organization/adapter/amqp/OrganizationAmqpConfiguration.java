package com.mksistemas.supply.organization.adapter.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationAmqpConfiguration {

  public static final String ORGANIZATION_EXCHANGE_NAME = "organization-exchange";
  public static final String QUEUE_NAME = "organization";
  public static final String ORGANIZATION_KEY = "organization.#";

  @Bean
  Queue queue() {
    return new Queue(QUEUE_NAME, true);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(ORGANIZATION_EXCHANGE_NAME, true, true);
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(ORGANIZATION_KEY);
  }

}
