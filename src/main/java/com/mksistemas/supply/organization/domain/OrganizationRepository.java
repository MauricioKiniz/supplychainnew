package com.mksistemas.supply.organization.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Organization.class, idClass = Long.class)
public interface OrganizationRepository {
	Organization save(Organization organization);

	@Query(value = "SELECT true as founded FROM hub.organization o WHERE o.\"name\" = ?1", nativeQuery = true)
	Optional<Boolean> existsNameDuplicated(String name);
}
