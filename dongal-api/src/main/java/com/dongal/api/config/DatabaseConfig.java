package com.dongal.api.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author freddi
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.dongal.api.repository"})
@PropertySources({
        @PropertySource("classpath:config.properties"),
        @PropertySource("classpath:properties/database.properties")
})
public class DatabaseConfig {

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL= "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY="hibernate.ejb.naming_strategy";

    private static final String PROPERTY_NAME_HIBERNATE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    private static final String PROPERTY_NAME_HIBERNATE_USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String PROPERTY_NAME_HIBERNATE_USE_STRUCTURED_CACHE = "hibernate.cache.use_structured_entries";

    private static final String PROPERTY_NAME_PERSISTENCE_VALIDATION_MODE = "javax.persistence.validation.mode";

    private static final String PROPERTY_NAME_JADIRA_USERTYPE_DATABASEZONE = "jadira.usertype.autoRegisterUserTypes";
    private static final String PROPERTY_NAME_JADIRA_USERTYPE_AUTO_REGISTER = "jadira.usertype.databaseZone";
    private static final String PROPERTY_NAME_JADIRA_USERTYPE_JAVA_ZONE = "jadira.usertype.javaZone";

    private static final String PROPERTY_PACKAGES_TO_SCAN = "com.dongal.api.domain";

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty("dataSourceClassName"));
        dataSource.setUrl(environment.getRequiredProperty("dataSource.jdbcUrl"));
        dataSource.setUsername(environment.getRequiredProperty("dataSource.user"));
        dataSource.setPassword(environment.getRequiredProperty("dataSource.password"));
//        dataSource.setPoolPreparedStatements(true);
        dataSource.setInitialSize(30);

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY));

        jpaProperties.put(PROPERTY_NAME_HIBERNATE_USE_QUERY_CACHE, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_USE_QUERY_CACHE));
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_USE_SECOND_LEVEL_CACHE, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_USE_SECOND_LEVEL_CACHE));
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_USE_STRUCTURED_CACHE, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_USE_STRUCTURED_CACHE));

        jpaProperties.put(PROPERTY_NAME_PERSISTENCE_VALIDATION_MODE, environment.getRequiredProperty(PROPERTY_NAME_PERSISTENCE_VALIDATION_MODE));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

//        Properties jadiraProperties = new Properties();
//        jadiraProperties.put(PROPERTY_NAME_JADIRA_USERTYPE_DATABASEZONE, environment.getRequiredProperty(PROPERTY_NAME_JADIRA_USERTYPE_DATABASEZONE));
//        jadiraProperties.put(PROPERTY_NAME_JADIRA_USERTYPE_AUTO_REGISTER, environment.getRequiredProperty(PROPERTY_NAME_JADIRA_USERTYPE_AUTO_REGISTER));
//        jadiraProperties.put(PROPERTY_NAME_JADIRA_USERTYPE_JAVA_ZONE, environment.getRequiredProperty(PROPERTY_NAME_JADIRA_USERTYPE_JAVA_ZONE));
//
//        entityManagerFactoryBean.setJpaProperties(jadiraProperties);


        return entityManagerFactoryBean;
    }

}
