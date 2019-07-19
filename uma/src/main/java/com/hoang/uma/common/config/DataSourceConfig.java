package com.hoang.uma.common.config;

import com.hoang.uma.common.config.properties.AppProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

/**
 * homertruong
 */

@Configuration
public class DataSourceConfig {

    @Autowired
    AppProperties appProperties;

    /* Data Source */
    @Primary
    @Bean(name = "dataSource")
    DataSource dataSource() {
        return getDataSource(appProperties.getDatasource().getModelsMaster());
    }

//    @Bean(name = "dataSourceSlave")
//    DataSource dataSourceSlave() {
//        return getDataSource(appProperties.getDatasource().getModelsSlave());
//    }

    /* JDBCTemplate */
    @Bean(name = "namedParameterJdbcTemplate")
    NamedParameterJdbcTemplate namedParameterJdbcTemplate () {
        return new NamedParameterJdbcTemplate(this.dataSource());
    }

    @Bean(name = "jdbcTransactionManager")
    PlatformTransactionManager jdbcTransactionManager (DataSource datasource) {
        return new DataSourceTransactionManager(this.dataSource());
    }


    /* JPA Vendor */
    @Bean(name = "jpaVendorAdapter")
    JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        //adapter.setShowSql(true);
        adapter.setGenerateDdl(true);

        return adapter;
    }

//    @Bean(name = "jpaVendorAdapterSlave")
//    JpaVendorAdapter jpaVendorAdapterSlave() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setDatabase(Database.MYSQL);
//        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
//        //adapter.setShowSql(true);
//        adapter.setGenerateDdl(true);
//
//        return adapter;
//    }

    /* Entity Manager Factory */
    @Primary
    @Bean(name = "entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        1. Application-managed—Entity managers are created when an application directly
//        requests one from an entity manager factory. With application-managed entity
//        managers, the application is responsible for opening or closing entity managers
//        and involving the entity manager in transactions. This type of entity manager is
//        most appropriate for use in standalone applications that don’t run in a Java EE
//        container.
//        -> LocalEntityManagerFactoryBean produces an application-managed Entity-ManagerFactory.

//        2. Container-managed—Entity managers are created and managed by a Spring/Java EE
//        container. The application doesn’t interact with the entity manager factory at
//        all. Instead, entity managers are obtained directly through injection or from
//        JNDI. The container is responsible for configuring the entity manager factories.
//        This type of entity manager is most appropriate for use by a Spring/Java EE container
//        that wants to maintain some control over JPA configuration beyond what’s specified in persistence.xml
//        -> LocalContainerEntityManagerFactoryBean produces a container-managed EntityManagerFactory.

        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setPersistenceUnitName("uma");
        emfb.setDataSource(dataSource());
        emfb.setJpaVendorAdapter(jpaVendorAdapter());
        emfb.setPackagesToScan("com.hoang.uma.service.model");

        Properties props = new Properties();
        if (appProperties.getDatasource().getModelsMaster().isShowSql()) {
            props.setProperty("hibernate.show_sql", "true");
            props.setProperty("hibernate.format_sql", "true");
        }
        props.setProperty("hibernate.use.second.level.cache", "true");
        props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        props.setProperty("org.hibernate.flushMode", "AUTO");
        props.setProperty("hibernate.jdbc.batch_size", "" + appProperties.getDatasource().getModelsMaster().getBatchSize());
        emfb.setJpaProperties(props);

        return emfb;
    }

//    @Bean(name = "entityManagerFactorySlave")
//    LocalContainerEntityManagerFactoryBean entityManagerFactorySlave() {
//        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
//        emfb.setPersistenceUnitName("common-java-app-slave");
//        emfb.setDataSource(dataSourceSlave());
//        emfb.setJpaVendorAdapter(jpaVendorAdapterSlave());
//        emfb.setPackagesToScan("com.hoang.uma.service.model");
//
//        Properties props = new Properties();
//        props.setProperty("hibernate.show_sql", "true");
//        props.setProperty("hibernate.format_sql", "true");
//        props.setProperty("hibernate.use.second.level.cache", "true");
//        props.setProperty("org.hibernate.flushMode", "AUTO");
//        emfb.setJpaProperties(props);
//
//        return emfb;
//    }

    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

//    @Bean(name = "transactionManagerSlave")
//    PlatformTransactionManager transactionManagerSlave() {
//        return new DataSourceTransactionManager(this.dataSourceSlave());
//    }

    /* Exception Translator */
    @Bean
    BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private DataSource getDataSource (AppProperties.Datasource.DatasourceProperties properties) {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setInitialSize(properties.getInitialSize());
        dataSource.setMinIdle(properties.getMinIdle());
        dataSource.setMaxActive(properties.getMaxActive());

        return dataSource;
    }
}
