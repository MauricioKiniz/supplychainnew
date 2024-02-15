package com.mksistemas.supply.economicgroup.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import com.mksistemas.supply.shared.domain.EntityNotFoundException;

@RepositoryDefinition(domainClass = EconomicGroup.class, idClass = Long.class)
public interface EconomicGroupRepository {

    Optional<EconomicGroup> findOneByName(String name);

    EconomicGroup save(EconomicGroup economicGroup);

    Optional<EconomicGroup> findById(long id);

    void delete(EconomicGroup economicGroup);

    @Query(
        value = "select eg.id from hub.economic_group eg, jsonb_array_elements(eg.organizations->'organizationIds') orgid where orgid::::bigint  = :organizationId",
        nativeQuery = true
    )
    List<Long> findAllOrganizationByOrganizationId(long organizationId);

    default EconomicGroup getById(long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Economic Group not found"));
    }

}
