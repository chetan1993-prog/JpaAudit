package org.com.nts.JpaAuditTest;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.com.nts.JpaAuditTest.first.daoImpl.DaoImpl;
import org.com.nts.JpaAuditTest.service.TwoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

/**
 * 
 * @author chetan
 * 
 */
@SpringBootApplication
@EntityScan(basePackages = { "org.com.nts.JpaAuditTest.entity", "org.com.nts.JpaAuditTest.dummy.entity" })
@ComponentScan(basePackages = { "org.com.nts.JpaAuditTest", "org.com.nts.JpaAuditTest.dbconfig" })
//@EnableJpaRepositories
//(basePackages = { "org.com.nts.JpaAuditTest.repository", "org.com.nts.JpaAuditTest.repository1" })
@EnableJpaAuditing
//@EnableCircuitBreaker
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class JpaAuditTestApplication implements CommandLineRunner {

	@Autowired
	private DaoImpl daoImpl;

	@Autowired
	private TwoDbService dbService;

	public static void main(String[] args) {
		SpringApplication.run(JpaAuditTestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
		System.out.println("------Employee Details------");
		daoImpl.getEmployees().stream().forEach(x -> {
			System.out.println(x.toString());
		});

		System.out.println("----------Patients Details------------");

		daoImpl.getPatients().stream().forEach(x -> {
			System.out.println(x.toString());
		});

		// modify patient whose id is 3
		// modify employee whose id is 2

		// daoImpl.updateEmpPatient(3L, 2L);
		dbService.reviseOpn(3L, 2L);
	}

	@Bean
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);
		return userTransactionImp;
	}

	@Bean(initMethod = "init", destroyMethod = "close")
	public TransactionManager transactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

}
