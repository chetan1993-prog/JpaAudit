package org.com.nts.JpaAuditTest.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.com.nts.JpaAuditTest.util.JsonDateHandlerDeserializer;
import org.com.nts.JpaAuditTest.util.JsonDateHandlerSerializer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * @author chetan
 *
 */
@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(Include.NON_EMPTY)
@JsonFilter("employeeFilter")
public class EmployeeEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	private String city;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateHandlerSerializer.class)
	@JsonDeserialize(using = JsonDateHandlerDeserializer.class)
	@Column
	private Date createdDate;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = JsonDateHandlerSerializer.class)
	@JsonDeserialize(using = JsonDateHandlerDeserializer.class)
	@Column
	private Date lastUpdateDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	@JsonManagedReference
	private List<EmployeeAuditEntity> auditEntities = new ArrayList<EmployeeAuditEntity>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeEntity")
	@JsonManagedReference
	private List<EmployeeAuditJsonEntity> auditJsonEntities = new ArrayList<EmployeeAuditJsonEntity>();

	public EmployeeEntity() {

	}

	public EmployeeEntity(Long id, String name, String email, String city) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.city = city;
	}

	public List<EmployeeAuditJsonEntity> getAuditJsonEntities() {
		return auditJsonEntities;
	}

	public void setAuditJsonEntities(List<EmployeeAuditJsonEntity> auditJsonEntities) {
		this.auditJsonEntities = auditJsonEntities;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public List<EmployeeAuditEntity> getAuditEntities() {
		return auditEntities;
	}

	public void setAuditEntities(List<EmployeeAuditEntity> auditEntities) {
		this.auditEntities = auditEntities;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", name=" + name + ", email=" + email + ", city=" + city + ", createdDate="
				+ createdDate + ", lastUpdateDate=" + lastUpdateDate + "]";
	}

}
