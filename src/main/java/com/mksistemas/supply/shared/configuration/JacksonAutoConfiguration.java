package com.mksistemas.supply.shared.configuration;

import org.hibernate.cfg.MappingSettings;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonAutoConfiguration {
    @Bean
    @Primary
    ObjectMapper objectMapper() {
        return new ObjectMapper()
            .registerModule(new JpaApiModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule());
    }

    @Bean
    HibernatePropertiesCustomizer jsonFormatMapperCustomizer(ObjectMapper objectMapper) {
        return (properties) -> properties.put(
            MappingSettings.JSON_FORMAT_MAPPER,
            new JacksonJsonFormatMapper(objectMapper)
        );
    }
}