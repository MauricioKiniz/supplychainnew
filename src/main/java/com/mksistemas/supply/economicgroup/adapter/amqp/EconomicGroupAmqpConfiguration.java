package com.mksistemas.supply.economicgroup.adapter.amqp;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mksistemas.supply.organization.adapter.amqp.OrganizationAmqpConstants;

@Configuration
class EconomicGroupAmqpConfiguration {

    @Bean
    Declarables economicGroupDeclarables() {
        Queue economicGroupQueue = new Queue(EconomicGroupAmqpConstants.QUEUE_NAME, true);
        FanoutExchange economicGroupFanoutExchange = new FanoutExchange(
            EconomicGroupAmqpConstants.EXCHANGE_NAME, true, false
        );
        FanoutExchange organizationFanoutExchange = new FanoutExchange(
            OrganizationAmqpConstants.EXCHANGE_NAME, true, false
        );

        return new Declarables(
            economicGroupQueue, economicGroupFanoutExchange,
            BindingBuilder.bind(economicGroupQueue).to(organizationFanoutExchange)
        );
    }

}
