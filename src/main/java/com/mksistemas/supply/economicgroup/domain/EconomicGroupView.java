package com.mksistemas.supply.economicgroup.domain;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.Subselect;
import org.springframework.data.annotation.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "EconomicGroupView")
@Immutable
@Table(name = "hub.economic_group_view")
@Subselect(
    """
        select
            gen_random_uuid() AS uuid, ecogroup.id id, ecogroup.name group_name, ecogroup.description group_description, ecogroup.orgid org_id, o.name org_name, o."identity" org_identity
        from
        (select
            eg.id,
            eg."name",
            eg.description,
            JSONB_ARRAY_ELEMENTS(eg.organizations -> 'organizationIds')::bigint orgid
        from hub.economic_group eg
        union all
        select
            eg.id,
            eg."name",
            eg.description,
            0 orgid
        from hub.economic_group eg where jsonb_array_length(eg.organizations -> 'organizationIds') = 0) ecogroup
        left join hub.organization o on o.id = ecogroup.orgid
            """
)
public class EconomicGroupView implements Serializable {
    private static final long serialVersionUID = 5539050077453382724L;

    @Id
    @Column(name = "uuid")
    @JsonIgnore
    private UUID uuid;
    @Column(name = "id")
    private long id;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "group_description")
    private String groupDescription;
    @Column(name = "org_id")
    private long orgId;
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "org_identity")
    private String orgIdentity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgIdentity() {
        return orgIdentity;
    }

    public void setOrgIdentity(String orgIdentity) {
        this.orgIdentity = orgIdentity;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
