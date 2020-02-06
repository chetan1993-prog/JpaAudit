///**
// * 
// */
//package org.com.nts.JpaAuditTest.dbconfig;
//
//import java.util.HashMap;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
///**
// * @author chetan
// *
// */
//@Configuration
//@PropertySource({ "classpath:persistence-multiple-db-boot.properties" })
//@EnableJpaRepositories(basePackages = "org.com.nts.JpaAuditTest.repository", entityManagerFactoryRef = "firstEntityManager", transactionManagerRef = "firstTransactionManager")
//public class FirstDbAutoConfig {
//
//	@Autowired
//	private Environment env;
//
//	public FirstDbAutoConfig() {
//		super();
//	}
//
//	@Primary
//	@Bean(name = "firstEntityManager")
//	public LocalContainerEntityManagerFactoryBean userEntityManager() {
//		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(userDataSource());
//		em.setPackagesToScan("org.com.nts.JpaAuditTest.entity");
//
//		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		em.setJpaVendorAdapter(vendorAdapter);
//		final HashMap<String, Object> properties = new HashMap<String, Object>();
//		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//		em.setJpaPropertyMap(properties);
//
//		return em;
//	}
//
//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix = "spring.datasource")
//	public DataSource userDataSource() {
//		return DataSourceBuilder.create().build();
//	}
//
//	@Primary
//	@Bean(name = "firstTransactionManager")
//	public PlatformTransactionManager userTransactionManager() {
//		final JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(userEntityManager().getObject());
//		return transactionManager;
//	}
//}
