/**
 * 
 */
package org.com.nts.JpaAuditTest.first.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.com.nts.JpaAuditTest.entity.EmployeeEntity;
import org.com.nts.JpaAuditTest.entity1.Patient;
import org.com.nts.JpaAuditTest.repository.EmployeeRepository;
import org.com.nts.JpaAuditTest.repository1.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 *
 */
@Repository
public class DaoImpl {

	@Autowired
	@Qualifier("firstEntityManager")
	private EntityManager entityManager;

	@Autowired
	@Qualifier("secondEntityManager")
	private EntityManager entityManager2;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserTransaction userTransaction;

	@SuppressWarnings("unchecked")
	public List<EmployeeEntity> getEmployees() {
		return (List<EmployeeEntity>) entityManager.createQuery("from EmployeeEntity").getResultList();
	}

	public List<Patient> getPatients() {
		return (List<Patient>) entityManager2.createQuery("from Patient").getResultList();
	}

	public void updateEmpPatient(Long patientId, Long empId) {
		try {

			System.out.println("dao invoked");

			EmployeeEntity emp = entityManager.find(EmployeeEntity.class, empId);
			emp.setCity("mumbai");

			Patient patient = entityManager2.find(Patient.class, patientId);
			patient.setAge(22L);
			patient.setName("uuuu");

			System.out.println("before merege");
			EmployeeEntity e = entityManager.merge(emp);
			Patient p = entityManager2.merge(patient);

			System.out.println("updated employee" + e.getName());
			System.out.println("updated patient" + p.getName());
			System.out.println("dao complete");

		} catch (Exception e) {
			System.out.println("error caught...");
			e.printStackTrace();

		}

	}
}
