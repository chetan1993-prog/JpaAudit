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
//@EnableJpaRepositories(basePackages = "org.com.nts.JpaAuditTest.repository1", entityManagerFactoryRef = "secondEntityManager", transactionManagerRef = "secondTransactionManager")
//public class SecondDbAutoConfiguration {
//
//	@Autowired
//	private Environment env;
//
//	public SecondDbAutoConfiguration() {
//		super();
//	}
//
//	@Bean(name = "secondEntityManager")
//	public LocalContainerEntityManagerFactoryBean productEntityManager() {
//		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(productDataSource());
//		em.setPackagesToScan("org.com.nts.JpaAuditTest.entity1");
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
//	@ConfigurationProperties(prefix = "spring.second-datasource")
//	public DataSource productDataSource() {
//		return DataSourceBuilder.create().build();
//	}
//
//	@Bean(name = "secondTransactionManager")
//	public PlatformTransactionManager productTransactionManager() {
//		final JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(productEntityManager().getObject());
//		return transactionManager;
//	}
//}
