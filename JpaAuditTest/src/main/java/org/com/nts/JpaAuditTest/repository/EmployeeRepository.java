/**
 * 
 */
package org.com.nts.JpaAuditTest.repository;

import org.com.nts.JpaAuditTest.entity.EmployeeEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chetan
 *
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}
