package com.mksistemas.supply.organization;

import com.mksistemas.supply.organization.domain.Organization;

import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface OrganizationManagerUseCase {
	Organization create(@Valid OrganizationCommand command);

	record OrganizationCommand(@NotBlank String name, @NotBlank String identity,
			@NotBlank String countryIsoCode, @NotBlank String zoneId) {
	}

	Organization update(@Valid OrganizationCommand command, @Valid TSID id);
}
