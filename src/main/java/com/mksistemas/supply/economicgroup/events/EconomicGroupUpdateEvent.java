package com.mksistemas.supply.economicgroup.events;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mksistemas.supply.shared.domain.EventKindEnum;

public record EconomicGroupUpdateEvent(@JsonProperty("id") String id,
    @JsonProperty("name") String name, @JsonProperty("description") String description,
    @JsonProperty("organizations") OrganizationInEconomicGroupEvent organizations,
    @JsonProperty("eventKind") EventKindEnum eventKind) implements Serializable {

}
