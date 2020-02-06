/**
 * 
 */
package org.com.nts.JpaAuditTest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.com.nts.JpaAuditTest.util.UserAuditListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author chetan
 *
 */
@Entity
@Table(name = "users")
@EntityListeners(UserAuditListener.class)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String email;

	private String city;

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<UserAudit> usersAudit = new ArrayList<UserAudit>();

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

	public List<UserAudit> getUsersAudit() {
		return usersAudit;
	}

	public void setUsersAudit(List<UserAudit> usersAudit) {
		this.usersAudit = usersAudit;
	}

}
