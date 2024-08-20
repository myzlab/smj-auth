package com.smartjob.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig implements TransactionManagementConfigurer {

    @Bean(name = "ds-master")
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource dataSourceMaster() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbc-master")
    public JdbcTemplate jdbcTemplateMaster(@Qualifier("ds-master") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "ds-slave")
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSource dataSourceSlave() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbc-slave")
    public JdbcTemplate jdbcTemplateSlave(@Qualifier("ds-slave") DataSource ds) {
        return new JdbcTemplate(ds);
    }
    
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSourceMaster());
    }

    @Bean(name = "txManagerSlave")
    public PlatformTransactionManager annotationDrivenTransactionManagerDataSource2() {
        return new DataSourceTransactionManager(dataSourceSlave());
    }
}
