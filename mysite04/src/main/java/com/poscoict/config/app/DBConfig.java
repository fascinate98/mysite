package com.poscoict.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/poscoict/mysite/config/app/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	// Connection Pool DataSource
	@Bean
	public DataSource dataSource() {
		
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://192.168.0.42:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC");
//		dataSource.setUsername("webdb");
//		dataSource.setPassword("webdb");
//		
//		dataSource.setInitialSize(50);
//		dataSource.setMaxActive(100);
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize" , Integer.class));
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive" , Integer.class));
		
		
		return dataSource;
	}
}
