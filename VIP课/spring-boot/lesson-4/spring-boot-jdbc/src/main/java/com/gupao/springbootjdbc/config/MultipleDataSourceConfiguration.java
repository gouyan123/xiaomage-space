package com.gupao.springbootjdbc.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * 多数据源 DataSource 配置
 *
 * @author mercyblitz
 * @email mercyblitz@gmail.com
 * @date 2017-10-15
 **/
@Configuration
public class MultipleDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource masterDataSource(){

//        spring.datasource.driverClassName =com.mysql.jdbc.Driver
//        spring.datasource.url = jdbc:mysql://localhost:3306/test
//        spring.datasource.username=root
//        spring.datasource.password=123456
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        DataSource dataSource = dataSourceBuilder
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test")
                .username("root")
                .password("123456")
                .build();

        return dataSource;
    }

    @Bean
    public DataSource salveDataSource(){

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        DataSource dataSource = dataSourceBuilder
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test2")
                .username("root")
                .password("123456")
                .build();

        return dataSource;

    }

}
