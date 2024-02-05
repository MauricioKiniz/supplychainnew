package com.mksistemas.supply.organization.adapter.amqp;

import java.util.Objects;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mksistemas.supply.organization.domain.OrganizationUpdateEvent;

@Component
class OrganizationReceiver {

  private final ObjectMapper mapper;

  public OrganizationReceiver(ObjectMapper mapper) {
    this.mapper = mapper;

  }

  @RabbitListener(queues = OrganizationAmqpConfiguration.QUEUE_NAME)
  public void receiveMessage(String message) throws JsonMappingException, JsonProcessingException {
    OrganizationUpdateEvent event = mapper.readValue(message, OrganizationUpdateEvent.class);
    if (Objects.nonNull(event))
      System.out.println("Received <" + message + ">");
  }
}
