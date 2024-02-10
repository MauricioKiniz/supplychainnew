package com.mksistemas.supply.economicgroup.events;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrganizationInEconomicGroupEvent(@JsonProperty("organizationIds") List<String> organizationIds)
    implements Serializable {

}
