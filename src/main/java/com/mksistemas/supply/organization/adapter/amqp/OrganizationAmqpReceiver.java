package com.mksistemas.supply.organization.adapter.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrganizationAmqpReceiver {

    @RabbitListener(queues = { OrganizationAmqpConstants.QUEUE_NAME })
    public void receiveMessages(Message message) {}

}
