package com.deepak.questions.int_q.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DerbyClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(DerbyClientConfig.class);
    
    @Bean
    public DataSource derbyTestDbDataSource() {
        String dbUrl = "jdbc:derby://localhost:1527/dee_testing_db;create=true";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
        dataSource.setUrl(dbUrl);
//        dataSource.setUsername();
//        dataSource.setPassword();
        
//        dataSource.setValidationQuery("SELECT 1");
        
        logger.info("Created datasource to connect to {}", dbUrl);
        return dataSource;
    }
    
    @Bean 
    NamedParameterJdbcTemplate derbyDbJdbcTemplate() {
        return new NamedParameterJdbcTemplate(derbyTestDbDataSource());
    }
}
