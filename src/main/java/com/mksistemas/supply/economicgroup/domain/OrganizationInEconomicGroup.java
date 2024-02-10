package com.mksistemas.supply.economicgroup.domain;

import java.util.ArrayList;
import java.util.List;

public class OrganizationInEconomicGroup {
    private List<String> organizationIds = new ArrayList<>();

    public List<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<String> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public void addOrganizationId(String organizationId) {
        organizationIds.add(organizationId);
    }
}
