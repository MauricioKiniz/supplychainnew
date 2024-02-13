package com.mksistemas.supply.economicgroup.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase;
import com.mksistemas.supply.shared.domain.BusinessException;
import com.mksistemas.supply.shared.domain.ServiceUtils;

import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;

@Service
@Transactional
@Validated
class EconomicGroupManagerService implements EconomicGroupManagerUseCase {

    private final EconomicGroupRepository repository;

    public EconomicGroupManagerService(EconomicGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public EconomicGroup create(@Valid EconomicGroupCommand command) {
        EconomicGroup economicGroup = new EconomicGroup();
        economicGroup.setName(command.name());
        economicGroup.setDescription(command.description());

        if (repository.findOneByName(command.name()).isPresent())
            throw new BusinessException(ECONOMICGROUP_NAME_DUPLICATED, "Grupo economico com nome duplicado");

        economicGroup.generateUpdateEvent();
        return repository.save(economicGroup);
    }

    @Override
    public EconomicGroup update(@Valid EconomicGroupCommand command, @Valid TSID id) {
        EconomicGroup economicGroup = repository.getById(id.toLong());

        ServiceUtils.checkElementEquality(command.name(), economicGroup.getName(), name -> {
            if (repository.findOneByName(name).isPresent())
                throw new BusinessException(ECONOMICGROUP_NAME_DUPLICATED, "Grupo economico com nome duplicado");
            economicGroup.setName(name);
        });
        ServiceUtils.checkElementEquality(command.description(), economicGroup.getDescription(), economicGroup::setDescription);

        economicGroup.generateUpdateEvent();
        return repository.save(economicGroup);
    }

    @Override
    public EconomicGroup remove(@Valid TSID id) {
        EconomicGroup economicGroup = repository.getById(id.toLong());
        economicGroup.generateDeleteEvent();
        repository.delete(economicGroup);
        return economicGroup;
    }

    @Override
    public EconomicGroup linkWithOrganizations(@Valid TSID id, @Valid EconomicGroupLinkOrganizationCommand linkCommand) {
        EconomicGroup economicGroup = repository.getById(id.toLong());

        if ((Objects.nonNull(linkCommand.unlinkAll())) && Boolean.FALSE.equals(linkCommand.unlinkAll())) {
            updateOrganizationIds(linkCommand, economicGroup);
            economicGroup.generateUpdateEvent();
        } else {
            economicGroup.getOrganizations().clearOrganizationIds();
        }
        repository.save(economicGroup);

        return economicGroup;
    }

    private void updateOrganizationIds(EconomicGroupLinkOrganizationCommand linkCommand, EconomicGroup economicGroup) {
        OrganizationInEconomicGroup organizations = Objects.nonNull(economicGroup.getOrganizations())
            ? economicGroup.getOrganizations()
            : new OrganizationInEconomicGroup();

        List<Long> ids = new ArrayList<>(organizations.getOrganizationIds());

        List<EconomicGroupLinkOrganizationElement> elements = Objects.nonNull(linkCommand.linkElements())
            ? linkCommand.linkElements()
            : List.of();

        elements
            .stream()
            .forEach(item -> {
                Long organizationId = TSID.from(item.organizationId()).toLong();
                if (item.link()) {
                    if (!ids.contains(organizationId)) {
                        ids.add(organizationId);
                    }
                } else {
                    ids.remove(organizationId);
                }
            });

        organizations.setOrganizationIds(ids);
        economicGroup.setOrganizations(organizations);
    }

}
