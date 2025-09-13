package com.ganatan.backend_java.modules.continent;

import com.ganatan.backend_java.multitenancy.TenantAware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Filter;

@Entity
@Table(name = "continent")
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class Continent implements TenantAware {

	@Id
	private Long id;
	private String name;

	@Column(name = "tenant_id")
	private String tenantId;

	public Continent() {
	}

	public Continent(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
