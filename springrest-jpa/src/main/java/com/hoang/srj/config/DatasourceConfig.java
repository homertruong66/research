package com.hoang.srj.config;

import com.hoang.srj.config.properties.AppProperties;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Configuration
public class DatasourceConfig {

    @Autowired
    AppProperties appProperties;

    /* Data Source */
    @Primary
    @Bean(name = "datasource")
    DataSource datasource() {
        return getDataSource(appProperties.getDatasource().getModelsMaster());
    }

//    @Bean(name = "datasourceSlave")
//    DataSource datasourceSlave() {
//        return getDataSource(appProperties.getDatasource().getModelsSlave());
//    }


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
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setPersistenceUnitName("srjPU");
        emfb.setDataSource(datasource());
        emfb.setJpaVendorAdapter(jpaVendorAdapter());
        emfb.setPackagesToScan("com.hoang.srj.model");

        Properties props = new Properties();
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.use.second.level.cache", "true");
        props.setProperty("org.hibernate.flushMode", "AUTO");
        emfb.setJpaProperties(props);

        return emfb;
    }

//    @Bean(name = "entityManagerFactorySlave")
//    LocalContainerEntityManagerFactoryBean entityManagerFactorySlave() {
//        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
//        emfb.setPersistenceUnitName("srjPU_Slave");
//        emfb.setDataSource(datasourceSlave());
//        emfb.setJpaVendorAdapter(jpaVendorAdapterSlave());
//        emfb.setPackagesToScan("com.hoang.srj.model");
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
        return new DataSourceTransactionManager(this.datasource());
    }

//    @Bean(name = "transactionManagerSlave")
//    PlatformTransactionManager transactionManagerSlave() {
//        return new DataSourceTransactionManager(this.datasourceSlave());
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
