package com.mksistemas.supply.organization;

import com.mksistemas.supply.organization.OrganizationManagerUseCase.OrganizationCommand;

public class OrganizationManagerSupplier {
	public static OrganizationCommand getDefaultCreateCommand() {
		return new OrganizationCommand("orgmaster", "1234567890", "BR",
				"-3");
	}

	public static OrganizationCommand getInvalidCountryIsoCodeCommand() {
		return new OrganizationCommand("orgmaster", "1234567890", "X1",
				"-3");
	}

	public static OrganizationCommand getInvalidZoneIdCommand() {
		return new OrganizationCommand("orgmaster", "1234567890", "BR",
				"-9");
	}

	public static OrganizationCommand getCreateCommandWithName(
			String name) {
		return new OrganizationCommand(name, "1234567890", "BR", "-3");
	}

	public static OrganizationCommand getCreateCommandWithIdentity(
			String identity) {
		return new OrganizationCommand("orgmaster", identity, "BR", "-3");
	}

	public static OrganizationCommand getCreateCommandWithCountryIsoCode(
			String countryIsoCode) {
		return new OrganizationCommand("orgmaster", "1234567890",
				countryIsoCode, "-3");
	}

	public static OrganizationCommand getCreateCommandWithNameAndIdentity(
			String name, String identity) {
		return new OrganizationCommand(name, identity, "BR", "-3");
	}

}
