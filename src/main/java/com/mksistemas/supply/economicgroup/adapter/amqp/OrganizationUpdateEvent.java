package com.mksistemas.supply.economicgroup.adapter.amqp;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mksistemas.supply.shared.domain.EventKindEnum;

record OrganizationUpdateEvent(@JsonProperty("id") String id,
    @JsonProperty("eventKind") EventKindEnum eventKind) implements Serializable {
}
