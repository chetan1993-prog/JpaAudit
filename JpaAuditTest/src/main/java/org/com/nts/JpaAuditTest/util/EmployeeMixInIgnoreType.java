/**
 * 
 */
package org.com.nts.JpaAuditTest.util;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * @author chetan
 *         <p>
 *         to ignore the field for employee class
 */

@JsonFilter("filter")
public class EmployeeMixInIgnoreType {

//	@JsonIgnore
//	List<EmployeeAuditEntity> auditEntities;
//
//	@JsonIgnore
//	List<EmployeeAuditJsonEntity> auditJsonEntities;

}
