package com.mksistemas.supply.organization.adapter.amqp;

import java.util.Objects;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.mksistemas.supply.organization.domain.OrganizationUpdateEvent;

@Component
class OrganizationReceiver {

  @RabbitListener(queues = OrganizationAmqpConfiguration.QUEUE_NAME)
  public void receiveMessage(OrganizationUpdateEvent event, Message message) {
    if (Objects.nonNull(event))
      System.out.println("Received <" + event + ">");
  }
}
