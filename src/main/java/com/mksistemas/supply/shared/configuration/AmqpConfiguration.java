package com.mksistemas.supply.shared.configuration;

import java.time.ZonedDateTime;
import java.util.Map;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.hypersistence.tsid.TSID;

@Configuration
class AmqpConfiguration {

  @Bean
  RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    rabbitTemplate.setCorrelationDataPostProcessor((message, correlationData) -> {
      message.getMessageProperties().setHeaders(Map.of("messageId",
          TSID.Factory.getTsid().toLowerCase(), "timestamp", ZonedDateTime.now().toString()));
      return correlationData;
    });
    return rabbitTemplate;
  }

  @Bean
  Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }


}
