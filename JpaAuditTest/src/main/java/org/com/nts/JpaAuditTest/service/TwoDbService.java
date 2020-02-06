/**
 * 
 */
package org.com.nts.JpaAuditTest.service;

import org.com.nts.JpaAuditTest.first.daoImpl.DaoImpl;
import org.com.nts.JpaAuditTest.repository.EmployeeRepository;
import org.com.nts.JpaAuditTest.repository1.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chetan
 *
 */
@Service
public class TwoDbService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DaoImpl daoImpl;

	// @Transactional(value = "secondTransactionManager")
//	@Transactional
	// @javax.transaction.Transactional
	@Transactional(value = "chainedTransactionManager", rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
	public void reviseOpn(Long patientId, Long empId) {

		try {
			daoImpl.updateEmpPatient(patientId, empId);
		} catch (Exception e) {
			// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
		}

	}

}
