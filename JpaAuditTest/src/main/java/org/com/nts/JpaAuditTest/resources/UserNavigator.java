/**
 * 
 */
package org.com.nts.JpaAuditTest.resources;

import org.com.nts.JpaAuditTest.entity.EmployeeEntity;
import org.com.nts.JpaAuditTest.entity.UserEntity;
import org.com.nts.JpaAuditTest.entity1.Patient;
import org.com.nts.JpaAuditTest.model.EmployeeModel;
import org.com.nts.JpaAuditTest.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chetan
 *
 */
@RestController
@Scope("prototype")
public class UserNavigator {

	@Autowired
	private UserServiceImpl userService;

	/**
	 * <p>
	 * handler method to persist user entry
	 * 
	 * @param entity of type {@link UserEntity}
	 * @return {@link ResponseEntity} of type {@link ?}
	 */
	@PostMapping(value = "/users")
	public ResponseEntity<?> persistUser(@RequestBody UserEntity entity) {
		return userService.offerUser(entity);
	}

	/**
	 * <p>
	 * handler method to update existing user with new value
	 * 
	 * @param entity of type {@link UserEntity}
	 * @return {@link ResponseEntity} of type {@link ?}
	 */
	@PutMapping(value = "/user")
	public ResponseEntity<?> reviseUser(@RequestBody UserEntity entity) {
		return userService.updateUser(entity);
	}

	/**
	 * <p>
	 * method to create new employee
	 * 
	 * @param entity of type {@link EmployeeEntity}
	 * @return {@link ResponseEntity} of type {@link ?}
	 */
	@PostMapping(value = "/employees")
	public ResponseEntity<?> persistEmployee(@RequestBody EmployeeEntity entity) {
		return userService.offerEmployee(entity);
	}

	/**
	 * <p>
	 * method to update employee
	 * 
	 * @param employee entity of type {@link EmployeeEntity}
	 * @return {@link ResponseEntity} of type {@link ?}
	 */
	@PutMapping(value = "/employee")
	public ResponseEntity<?> reviseEmployee(@RequestBody EmployeeEntity employee) {

		return userService.updateEmployee(employee);
	}

	/**
	 * 
	 * @param employee of type {@link EmployeeEntity}
	 * @return {@link ResponseEntity} of type {@link ?}
	 */
	@PutMapping(value = "/revise_employee")
	public ResponseEntity<?> reformEmployee(@RequestBody EmployeeEntity employee) {
		return userService.reviseEmployee(employee);
	}

	/**
	 * <p>
	 * method to return single {@link EmployeeEntity}
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/employee")
	public ResponseEntity<EmployeeModel> getEmployee(@RequestParam(name = "id", required = true) Long id) {
		return userService.findEmployee(id);
	}
	/////// for diffrent database operation/////////////

	@PostMapping(value = "/patients")
	public ResponseEntity<?> persitPatient(@RequestBody Patient patient) {
		return userService.pushPatient(patient);
	}

	/**
	 * <p>
	 * handler method to get single patient information
	 * 
	 * @param pid
	 * @return
	 */
	@GetMapping(value = "/patient")
	public ResponseEntity<Patient> getPatientDetails(@RequestParam(name = "pid", required = true) Long pid) {
		return userService.getPatient(pid);
	}

}
