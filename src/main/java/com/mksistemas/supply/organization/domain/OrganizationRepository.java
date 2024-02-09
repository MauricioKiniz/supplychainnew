package com.mksistemas.supply.organization.domain;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import com.mksistemas.supply.shared.domain.EntityNotFoundException;

@RepositoryDefinition(domainClass = Organization.class, idClass = Long.class)
public interface OrganizationRepository {
    Organization save(Organization organization);

    Optional<Organization> findOneByName(String name);

    Optional<Organization> findById(long id);

    void delete(Organization organization);

    default Organization getById(long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Organization not found"));
    }
}
