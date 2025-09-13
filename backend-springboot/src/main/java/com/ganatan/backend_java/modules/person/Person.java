package com.ganatan.backend_java.modules.person;

import com.ganatan.backend_java.multitenancy.TenantAware;
import com.ganatan.backend_java.shared.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity<Long> implements TenantAware {

	private String name;

	public Person(Long id, String name) {
		this.setId(id);
		this.name = name;
	}
}
