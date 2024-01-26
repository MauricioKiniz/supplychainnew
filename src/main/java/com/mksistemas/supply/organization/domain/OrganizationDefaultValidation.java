package com.mksistemas.supply.organization.domain;

import java.util.List;
import java.util.Locale;
import java.util.Locale.IsoCountryCode;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Stream;

import com.mksistemas.supply.shared.library.railway.Result;

public interface OrganizationDefaultValidation {

	public static final String ORGANIZATION_NAME_DUPLICATED = "organization.name.duplicated";
	public static final String ORGANIZATION_COUNTRYISOCODE_NOTFOUND = "organizatiofalsen.countryisocode.notfound";
	public static final String ORGANIZATION_ZONEID_NOTFOUND = "organization.zoneid.notfound";

	default Result<String, Organization> verifyDuplicatedName(
			OrganizationRepository repository, Organization organization) {
		return repository.existsNameDuplicated(organization.getName())
				.isPresent()
						? Result.ofFailure(ORGANIZATION_NAME_DUPLICATED)
						: Result.ofSuccess(organization);
	}

	default Result<String, Organization> verifyCountryIsoCode(
			Organization organization) {
		Set<String> countries = Locale
				.getISOCountries(IsoCountryCode.PART1_ALPHA2);
		return countries.contains(organization.getCountryIsoCode())
				? Result.ofSuccess(organization)
				: Result.ofFailure(ORGANIZATION_COUNTRYISOCODE_NOTFOUND);
	}

	default Result<String, Organization> verifyZoneId(
			Organization organization) {
		final List<String> timeZonesInCountryIsoCode = Stream
				.of(TimeZone.getAvailableIDs()).filter(zoneId -> zoneId
						.startsWith(organization.getCountryIsoCode()))
				.toList();

		return timeZonesInCountryIsoCode.contains(organization.getZoneId())
				? Result.ofFailure(ORGANIZATION_ZONEID_NOTFOUND)
				: Result.ofSuccess(organization);
	}

}
