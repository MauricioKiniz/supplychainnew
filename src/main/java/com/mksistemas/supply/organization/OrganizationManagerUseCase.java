package com.mksistemas.supply.organization;

import com.mksistemas.supply.organization.domain.Organization;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface OrganizationManagerUseCase {
	Organization create(@Valid CreateOrganizationCommand command);

	record CreateOrganizationCommand(@NotBlank String name,
			@NotBlank String identity, @NotBlank String countryIsoCode,
			@NotBlank String zoneId) {
	}

}
