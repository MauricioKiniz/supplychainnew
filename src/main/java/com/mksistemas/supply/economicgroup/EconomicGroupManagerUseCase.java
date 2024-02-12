package com.mksistemas.supply.economicgroup;

import java.util.List;

import org.springframework.lang.Nullable;

import com.mksistemas.supply.economicgroup.domain.EconomicGroup;

import io.hypersistence.tsid.TSID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface EconomicGroupManagerUseCase {

    public static final String ECONOMICGROUP_NAME_DUPLICATED = "economicgroup.name.duplicated";

    EconomicGroup create(@Valid EconomicGroupCommand command);

    EconomicGroup update(@Valid EconomicGroupCommand command, @Valid TSID id);

    EconomicGroup remove(@Valid TSID id);

    EconomicGroup linkWithOrganizations(@Valid TSID id, @Valid EconomicGroupLinkOrganizationCommand linkCommand);

    record EconomicGroupCommand(@NotBlank String name, String description) {}

    record EconomicGroupLinkOrganizationCommand(@Nullable Boolean unlinkAll,
        @Nullable List<EconomicGroupLinkOrganizationElement> linkElements) {}

    record EconomicGroupLinkOrganizationElement(@NotBlank String organizationId, boolean link) {}

}
