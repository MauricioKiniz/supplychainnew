package com.mksistemas.supply.shared.domain;

import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
public class EntityBase {
	@Id
	@Tsid
	private Long id;

	@Version
	private Integer version;

	public EntityBase() {
	}

	public EntityBase(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public TSID getAsTsid() {
		return TSID.from(id);
	}

}
