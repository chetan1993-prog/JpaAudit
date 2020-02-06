/**
 * 
 */
package org.com.nts.JpaAuditTest.dbconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author chetan
 *
 */
@Configuration
public class TransactionManagerConfig {

//	@Primary
	@Bean(name = "chainedTransactionManager")
	public ChainedTransactionManager transactionManager(
			@Qualifier("firstTransactionManager") PlatformTransactionManager pgsqlTransactionManager,

			@Qualifier("secondTransactionManager") PlatformTransactionManager db2TransactionManager) {

//		ChainedTransactionManager transactionManager = new ChainedTransactionManager(
//				new PlatformTransactionManager[] { pgsqlTransactionManager, db2TransactionManager });

		return new ChainedTransactionManager(db2TransactionManager, pgsqlTransactionManager);

		// return transactionManager;
	}

}
