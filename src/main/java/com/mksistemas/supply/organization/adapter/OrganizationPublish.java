package com.mksistemas.supply.organization.adapter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.mksistemas.supply.organization.adapter.amqp.OrganizationAmqpConstants;
import com.mksistemas.supply.organization.events.OrganizationUpdateEvent;

@Component
class OrganizationPublish {

    private final RabbitTemplate rabbitTemplate;

    public OrganizationPublish(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void onOrganizationUpdate(OrganizationUpdateEvent event) {
        rabbitTemplate.convertAndSend(
            OrganizationAmqpConstants.EXCHANGE_NAME,
            StringUtils.EMPTY, event
        );
    }

}
