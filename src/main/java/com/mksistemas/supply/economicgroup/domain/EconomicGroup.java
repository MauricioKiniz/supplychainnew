package com.mksistemas.supply.economicgroup.domain;

import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import com.mksistemas.supply.economicgroup.events.EconomicGroupUpdateEvent;
import com.mksistemas.supply.economicgroup.events.OrganizationInEconomicGroupEvent;
import com.mksistemas.supply.shared.domain.EntityBase;
import com.mksistemas.supply.shared.domain.EventKindEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity(name = "EconomicGroup")
@Table(name = "economic_group", schema = "hub")
@Audited
@SoftDelete
public class EconomicGroup extends EntityBase<EconomicGroup> {

    @NotNull
    private String name;
    @Column(columnDefinition = "text", nullable = true)
    private String description;

    @Column(nullable = true)
    @JdbcTypeCode(SqlTypes.JSON)
    private OrganizationInEconomicGroup organizations = new OrganizationInEconomicGroup();

    public EconomicGroup() {}

    public EconomicGroup(Long id) {
        super(id);
    }

    public EconomicGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public EconomicGroup(String name, String description, OrganizationInEconomicGroup organizations) {
        this.name = name;
        this.description = description;
        this.organizations = organizations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrganizationInEconomicGroup getOrganizations() {
        return organizations;
    }

    public void setOrganizations(OrganizationInEconomicGroup organizations) {
        this.organizations = organizations;
    }

    @Override
    public void generateUpdateEvent() {
        generateEventWithKind(EventKindEnum.UPDATE);
    }

    @Override
    public void generateDeleteEvent() {
        generateEventWithKind(EventKindEnum.REMOVE);
    }

    private EconomicGroupUpdateEvent generateEventWithKind(EventKindEnum kind) {
        OrganizationInEconomicGroupEvent organizationInEconomicGroupEvent = new OrganizationInEconomicGroupEvent(
            (Objects.nonNull(organizations))
                ? organizations.getOrganizationIdsAsString()
                : List.of()
        );
        return new EconomicGroupUpdateEvent(
            getAsTsid().toLowerCase(), name, description, organizationInEconomicGroupEvent, kind
        );
    }

}
