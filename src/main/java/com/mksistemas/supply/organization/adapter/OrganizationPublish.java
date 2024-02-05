package com.mksistemas.supply.organization.adapter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mksistemas.supply.organization.adapter.amqp.OrganizationAmqpConfiguration;
import com.mksistemas.supply.organization.domain.OrganizationUpdateEvent;

@Component
class OrganizationPublish {

  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper mapper;

  public OrganizationPublish(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
    this.rabbitTemplate = rabbitTemplate;
    this.mapper = mapper;
  }

  @EventListener
  public void onOrganizationUpdate(OrganizationUpdateEvent event) {

    try {
      String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
      rabbitTemplate.convertAndSend(OrganizationAmqpConfiguration.ORGANIZATION_EXCHANGE_NAME,
          "organization.test", data);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

}
