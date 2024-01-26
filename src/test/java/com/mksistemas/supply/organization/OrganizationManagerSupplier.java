package com.mksistemas.supply.organization;

import com.mksistemas.supply.organization.OrganizationManagerUseCase.CreateOrganizationCommand;

public class OrganizationManagerSupplier {
	public static CreateOrganizationCommand getDefaultCreateCommand() {
		return new CreateOrganizationCommand("orgmaster", "1234567890", "BR",
				"-3");
	}

	public static CreateOrganizationCommand getInvalidCountryIsoCodeCommand() {
		return new CreateOrganizationCommand("orgmaster", "1234567890", "X1",
				"-3");
	}

	public static CreateOrganizationCommand getInvalidZoneIdCommand() {
		return new CreateOrganizationCommand("orgmaster", "1234567890", "BR",
				"-9");
	}

	public static CreateOrganizationCommand getCreateCommandWithName(
			String name) {
		return new CreateOrganizationCommand(name, "1234567890", "BR", "-3");
	}

	public static CreateOrganizationCommand getCreateCommandWithIdentity(
			String identity) {
		return new CreateOrganizationCommand("orgmaster", identity, "BR", "-3");
	}

	public static CreateOrganizationCommand getCreateCommandWithCountryIsoCode(
			String countryIsoCode) {
		return new CreateOrganizationCommand("orgmaster", "1234567890",
				countryIsoCode, "-3");
	}

}
