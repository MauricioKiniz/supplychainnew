package com.mksistemas.supply.organization.domain;

import java.util.Locale;
import java.util.Locale.IsoCountryCode;
import java.util.Set;

import com.mksistemas.supply.shared.domain.BusinessException;

public interface OrganizationDefaultValidation {

    public static final String ORGANIZATION_NAME_DUPLICATED = "organization.name.duplicated";
    public static final String ORGANIZATION_COUNTRYISOCODE_NOTFOUND = "organizatiofalsen.countryisocode.notfound";

    default void verifyDuplicatedName(OrganizationRepository repository, String name) {
        if (repository.findOneByName(name).isPresent())
            throw new BusinessException(
                ORGANIZATION_NAME_DUPLICATED,
                "Organization duplicated"
            );
    }

    default void verifyCountryIsoCode(Organization organization) {
        Set<String> countries = Locale
            .getISOCountries(IsoCountryCode.PART1_ALPHA2);
        if (!countries.contains(organization.getCountryIsoCode()))
            throw new BusinessException(
                ORGANIZATION_COUNTRYISOCODE_NOTFOUND,
                "Country Iso Code invalid"
            );
    }
}
