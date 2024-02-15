package com.mksistemas.supply.organization.adapter.amqp;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrganizationAmqpConfiguration {

    @Bean
    Declarables organizationDeclarables() {
        Queue organizationQueue = new Queue(OrganizationAmqpConstants.QUEUE_NAME, true);
        FanoutExchange organizationFanoutExchange = new FanoutExchange(
            OrganizationAmqpConstants.ORGANIZATION_EXCHANGE_NAME, true, false
        );
        return new Declarables(
            organizationQueue, organizationFanoutExchange
        );
    }

}
