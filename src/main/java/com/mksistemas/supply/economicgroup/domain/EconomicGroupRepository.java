package com.mksistemas.supply.economicgroup.domain;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import com.mksistemas.supply.shared.domain.EntityNotFoundException;

@RepositoryDefinition(domainClass = EconomicGroup.class, idClass = Long.class)
public interface EconomicGroupRepository {

    Optional<EconomicGroup> findOneByName(String name);

    EconomicGroup save(EconomicGroup economicGroup);

    Optional<EconomicGroup> findById(long id);

    void delete(EconomicGroup economicGroup);

    //void removeOrganizationFromEconomicGroups(long organizationId);

    default EconomicGroup getById(long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Economic Group not found"));
    }

}
