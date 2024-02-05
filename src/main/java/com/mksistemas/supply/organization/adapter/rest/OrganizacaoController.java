package com.mksistemas.supply.organization.adapter.rest;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mksistemas.supply.organization.OrganizationManagerUseCase;
import com.mksistemas.supply.organization.domain.Organization;

@RestController
@RequestMapping("api/v1/organization")
class OrganizacaoController {

	private final OrganizationManagerUseCase organizationUseCase;

	public OrganizacaoController(
			OrganizationManagerUseCase organizationUseCase) {
		this.organizationUseCase = organizationUseCase;
	}

	@PostMapping
	public ResponseEntity<Void> createOrganization(
			@RequestBody OrganizationManagerUseCase.OrganizationCommand command) {
		Organization organization = organizationUseCase.create(command);
		URI location = URI.create(String.format("/api/v1/organization/%s",
				organization.getId().toString()));
		return ResponseEntity.created(location).build();
	}
}
