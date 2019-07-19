package com.hoang.linkshortener.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.hoang.linkshortener.config.properties.AppProperties;

@Configuration
public class DatasourceConfig {

    @Autowired
    AppProperties appProperties;

    @Bean(name = "modelSlaveDatasource")
    @Primary
    DataSource modelSlaveDatasource () {
        return getDataSource(appProperties.getDatasource().getModelsSlave());
    }

    @Bean(name = "modelSlaveTemplate")
    @Primary
    JdbcTemplate modelSlaveTemplate () {
        return new JdbcTemplate(this.modelSlaveDatasource());
    }

    @Bean(name = "modelMasterDatasource")
    DataSource modelMasterDatasource () {
        return getDataSource(appProperties.getDatasource().getModelsMaster());
    }

    @Bean(name = "modelMasterTemplate")
    JdbcTemplate modelMasterTemplate () {
        return new JdbcTemplate(this.modelMasterDatasource());
    }

    @Bean("modelMasterNamedParameterJdbcTemplate")
    NamedParameterJdbcTemplate modelMasterNamedParameterJdbcTemplate () {
        return new NamedParameterJdbcTemplate(this.modelMasterDatasource());
    }

    @Bean(name = "modelMasterTransactionManager")
    PlatformTransactionManager modelMasterTransactionManager (DataSource datasource) {
        return new DataSourceTransactionManager(this.modelMasterDatasource());
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
