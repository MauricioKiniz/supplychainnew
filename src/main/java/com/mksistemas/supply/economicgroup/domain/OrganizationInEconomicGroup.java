package com.mksistemas.supply.economicgroup.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.hypersistence.tsid.TSID;

public class OrganizationInEconomicGroup {
    private List<Long> organizationIds = new ArrayList<>();

    public List<Long> getOrganizationIds() {
        return organizationIds;
    }

    @JsonIgnore
    public List<String> getOrganizationIdsAsString() {
        return organizationIds.isEmpty()
            ? List.of()
            : organizationIds.stream().map(item -> TSID.from(item).toLowerCase()).toList();
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public void addOrganizationId(Long organizationId) {
        organizationIds.add(organizationId);
    }

    public void clearOrganizationIds() {
        organizationIds.clear();
    }

    public boolean remove(long id) {
        return organizationIds.remove(id);
    }
}
