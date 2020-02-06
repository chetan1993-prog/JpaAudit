/**
 * 
 */
package org.com.nts.JpaAuditTest.repository;

import org.com.nts.JpaAuditTest.entity.EmployeeAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 *
 */
public interface EmployeeAuditRepository extends JpaRepository<EmployeeAuditEntity, Long> {

}
