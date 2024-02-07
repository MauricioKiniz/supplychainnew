package com.mksistemas.supply.shared.domain;

import org.hibernate.annotations.SoftDelete;
import org.springframework.data.domain.AbstractAggregateRoot;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
@SoftDelete
public abstract class EntityBase<TEntity extends AbstractAggregateRoot<TEntity>>
    extends AbstractAggregateRoot<TEntity> {

  @Id
  @Tsid
  private Long id;

  @Version
  private Integer version = 0;

  protected EntityBase() {}

  protected EntityBase(Long id) {
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

  public abstract void generateUpdateEvent();

  public abstract void generateDeleteEvent();
}
