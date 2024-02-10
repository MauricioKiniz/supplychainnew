package com.mksistemas.supply.economicgroup.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase;
import com.mksistemas.supply.shared.domain.BusinessException;
import com.mksistemas.supply.shared.domain.EntityNotFoundException;
import com.mksistemas.supply.shared.domain.ServiceUtils;

import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;

@Service
@Transactional
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
        EconomicGroup economicGroup = repository.findById(id.toLong())
            .orElseThrow(() -> new EntityNotFoundException("Economic Group not found"));

        ServiceUtils.checkElementEquality(command.name(), economicGroup.getName(), name -> {
            if (repository.findOneByName(name).isPresent())
                throw new BusinessException(ECONOMICGROUP_NAME_DUPLICATED, "Grupo economico com nome duplicado");
            economicGroup.setName(name);
        });
        ServiceUtils.checkElementEquality(command.description(), economicGroup.getDescription(), economicGroup::setDescription);

        economicGroup.generateUpdateEvent();
        return repository.save(economicGroup);
    }

}
