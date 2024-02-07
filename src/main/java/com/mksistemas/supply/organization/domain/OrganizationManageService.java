package com.mksistemas.supply.organization.domain;

import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.mksistemas.supply.organization.OrganizationManagerUseCase;
import com.mksistemas.supply.shared.domain.EntityNotFoundException;
import io.hypersistence.tsid.TSID;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Transactional
@Service
@Validated
class OrganizationManageService
    implements OrganizationManagerUseCase, OrganizationDefaultValidation {

  private final OrganizationRepository repository;

  public OrganizationManageService(OrganizationRepository repository) {
    this.repository = repository;
  }

  // Create Organization

  @Override
  public Organization create(@Valid OrganizationCommand command) {
    Organization organization = new Organization(TSID.Factory.getTsid().toLong(), command.name(),
        command.identity(), command.countryIsoCode(), command.zoneId());
    verifyDuplicatedName(repository, organization);
    verifyCountryIsoCode(organization);
    organization.generateUpdateEvent();
    return repository.save(organization);
  }

  // Update Organization

  @Override
  public Organization update(@Valid OrganizationCommand command, @Valid TSID id) {
    Organization organization = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
    checkElementEquality(command.name(), organization.getName(), name -> {
      organization.setName(name);
      verifyDuplicatedName(repository, organization);
    });
    checkElementEquality(command.identity(), organization.getIdentity(), organization::setIdentity);
    checkElementEquality(command.zoneId(), organization.getZoneId(), organization::setZoneId);
    checkElementEquality(command.countryIsoCode(), organization.getCountryIsoCode(),
        countryIsoCode -> {
          organization.setCountryIsoCode(countryIsoCode);
          verifyCountryIsoCode(organization);
        });
    organization.generateUpdateEvent();
    return repository.save(organization);
  }

  private void checkElementEquality(String newElement, String oldElement,
      Consumer<String> consumer) {
    if (Objects.nonNull(newElement) && !newElement.equals(oldElement)) {
      consumer.accept(newElement);
    }
  }

}
