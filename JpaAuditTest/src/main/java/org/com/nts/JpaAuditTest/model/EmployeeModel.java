/**
 * 
 */
package org.com.nts.JpaAuditTest.model;

import java.util.Date;

import javax.persistence.Column;

import org.com.nts.JpaAuditTest.util.JsonDateHandlerDeserializer;
import org.com.nts.JpaAuditTest.util.JsonDateHandlerSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author chetan
 *
 */
public class EmployeeModel {

	private Long id;

	
	private String name;

	private String email;

	private String city;

	@JsonSerialize(using = JsonDateHandlerSerializer.class)
	@JsonDeserialize(using = JsonDateHandlerDeserializer.class)
	private Date createdDate;

	@JsonSerialize(using = JsonDateHandlerSerializer.class)
	@JsonDeserialize(using = JsonDateHandlerDeserializer.class)
	private Date lastUpdateDate;

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

}
