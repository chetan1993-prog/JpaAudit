/**
 * 
 */
package org.com.nts.JpaAuditTest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author chetan
 *
 */
@Entity
@Table(name = "employee_audit_json")
@EntityListeners(AuditingEntityListener.class)
public class EmployeeAuditJsonEntity {

	public EmployeeAuditJsonEntity() {
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String newJson;

	@Column
	private String oldJson;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "emp_id")
	@JsonBackReference
	private EmployeeEntity employeeEntity;

	@Column
	private String operation;

	@Column
	private String jsonBeforePoll;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getJsonBeforePoll() {
		return jsonBeforePoll;
	}

	public void setJsonBeforePoll(String jsonBeforePoll) {
		this.jsonBeforePoll = jsonBeforePoll;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNewJson() {
		return newJson;
	}

	public void setNewJson(String newJson) {
		this.newJson = newJson;
	}

	public String getOldJson() {
		return oldJson;
	}

	public void setOldJson(String oldJson) {
		this.oldJson = oldJson;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

}
