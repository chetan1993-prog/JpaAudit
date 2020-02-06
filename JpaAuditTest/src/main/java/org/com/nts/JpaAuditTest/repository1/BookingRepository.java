package org.com.nts.JpaAuditTest.repository1;

import org.com.nts.JpaAuditTest.entity1.Booking;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author chetan
 *
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
