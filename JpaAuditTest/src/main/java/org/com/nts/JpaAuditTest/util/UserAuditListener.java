/**
 * 
 */
package org.com.nts.JpaAuditTest.util;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.com.nts.JpaAuditTest.entity.UserEntity;
import org.com.nts.JpaAuditTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chetan
 *
 */
@Component
public class UserAuditListener {

//	@Autowired
//	private UserRepository userRepository;
//
//	@PrePersist
//	void onPrePersist(UserEntity userEntity) {
//		System.out.println("UserAuditListener.onPrePersist(): " + userEntity.getName());
//	}
//
//	
//	@PreUpdate
//	void onPreUpdate(UserEntity user) {
//		System.out.println("UserAuditListener.onPreUpdate(): " + user.getId());
//
//		System.out.println("findUser invoked..");
////		Optional<UserEntity> userEntity = userRepository.findById(user.getId());
////		System.out.println("findUser finsihed..");
////		if (userEntity.isPresent()) {
////
////			System.out.println("oldUserEntity::" + userEntity.get().getName());
////		}
//	}

}
