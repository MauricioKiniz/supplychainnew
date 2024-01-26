package com.mksistemas.supply.organization.domain;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.mksistemas.supply.organization.OrganizationManagerUseCase;
import com.mksistemas.supply.shared.domain.BusinessException;
import com.mksistemas.supply.shared.library.railway.Result;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Transactional
@Service
@Validated
class OrganizationManageService
		implements
			OrganizationManagerUseCase,
			OrganizationDefaultValidation {

	private final OrganizationRepository repository;

	public OrganizationManageService(OrganizationRepository repository) {
		this.repository = repository;
	}

	@Override
	public Organization create(@Valid CreateOrganizationCommand command) {
		return createEntity(command)
				.then(org -> verifyDuplicatedName(repository, org))
				.then(this::verifyCountryIsoCode).then(this::saveEntity)
				.onFailureThrow(failure -> new BusinessException(failure,
						"Erro de processamento"), success -> success);
	}

	private Result<String, Organization> createEntity(
			CreateOrganizationCommand command) {
		return Result.ofSuccess(
				new Organization(null, command.name(), command.identity(),
						command.countryIsoCode(), command.zoneId()));
	}

	private Result<String, Organization> saveEntity(Organization organization) {
		repository.save(organization);
		return Result.ofSuccess(organization);
	}

}
