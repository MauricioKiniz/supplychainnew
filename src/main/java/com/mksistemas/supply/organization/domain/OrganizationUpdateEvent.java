package com.mksistemas.supply.organization.domain;

public record OrganizationUpdateEvent(String id, String name, String identity,
		String countryIsoCode, String zoneId) {

}
