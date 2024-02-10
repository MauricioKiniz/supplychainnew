package com.mksistemas.supply.economicgroup;

import com.mksistemas.supply.economicgroup.domain.EconomicGroup;

import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface EconomicGroupManagerUseCase {

    public static final String ECONOMICGROUP_NAME_DUPLICATED = "economicgroup.name.duplicated";

    EconomicGroup create(@Valid EconomicGroupCommand command);

    EconomicGroup update(@Valid EconomicGroupCommand command, @Valid TSID id);

    record EconomicGroupCommand(@NotBlank String name, String description) {}

}
