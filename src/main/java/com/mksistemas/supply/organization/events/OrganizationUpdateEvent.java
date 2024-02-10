package com.mksistemas.supply.organization.events;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mksistemas.supply.shared.domain.EventKindEnum;

public record OrganizationUpdateEvent(@JsonProperty("id") String id,
    @JsonProperty("name") String name, @JsonProperty("identity") String identity,
    @JsonProperty("countryIsoCode") String countryIsoCode, @JsonProperty("zoneId") String zoneId,
    @JsonProperty("eventKind") EventKindEnum eventKind) implements Serializable {}
