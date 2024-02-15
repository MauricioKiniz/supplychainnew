package com.mksistemas.supply.economicgroup.adapter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.mksistemas.supply.economicgroup.adapter.amqp.EconomicGroupAmqpConstants;
import com.mksistemas.supply.economicgroup.events.EconomicGroupUpdateEvent;

@Component
class EconomicGroupPublish {

    private final RabbitTemplate rabbitTemplate;

    public EconomicGroupPublish(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void onEconomicGroupUpdate(EconomicGroupUpdateEvent event) {
        rabbitTemplate.convertAndSend(
            EconomicGroupAmqpConstants.EXCHANGE_NAME,
            StringUtils.EMPTY, event
        );
    }

}
