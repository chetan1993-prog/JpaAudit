/**
 * 
 */
package org.com.nts.JpaAuditTest.repository1;

import org.com.nts.JpaAuditTest.entity1.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chetan
 *
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
