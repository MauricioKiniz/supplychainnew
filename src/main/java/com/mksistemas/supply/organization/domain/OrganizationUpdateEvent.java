package com.mksistemas.supply.organization.domain;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public record OrganizationUpdateEvent(@JsonProperty("id") String id,
    @JsonProperty("name") String name, @JsonProperty("identity") String identity,
    @JsonProperty("countryIsoCode") String countryIsoCode, @JsonProperty("zoneId") String zoneId)
    implements Serializable {

}
