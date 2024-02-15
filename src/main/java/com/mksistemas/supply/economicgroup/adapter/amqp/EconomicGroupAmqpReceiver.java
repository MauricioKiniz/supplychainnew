package com.mksistemas.supply.economicgroup.adapter.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.mksistemas.supply.shared.domain.EventKindEnum;

@Component
public class EconomicGroupAmqpReceiver {

    @RabbitListener(queues = { EconomicGroupAmqpConstants.QUEUE_NAME })
    public void receiveMessages(OrganizationUpdateEvent event, Message message) {
        if (event.eventKind() == EventKindEnum.REMOVE) {

        }
    }

}
