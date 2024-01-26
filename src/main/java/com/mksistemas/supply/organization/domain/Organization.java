package com.mksistemas.supply.organization.domain;

import com.mksistemas.supply.shared.domain.EntityBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "Organization")
@Table(name = "organization", schema = "hub")
public class Organization extends EntityBase {
	private String name;
	private String identity;
	private String countryIsoCode;
	private String zoneId;

	public Organization() {
	}

	public Organization(Long id, String name, String identity,
			String countryIsoCode, String zoneId) {
		super(id);
		this.name = name;
		this.identity = identity;
		this.countryIsoCode = countryIsoCode;
		this.zoneId = zoneId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

}
