package com.mksistemas.supply.economicgroup.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = EconomicGroupView.class, idClass = UUID.class)
public interface EconomicGroupViewRepository {
    List<EconomicGroupView> findAll();

    List<EconomicGroupView> findById(long id);
}
