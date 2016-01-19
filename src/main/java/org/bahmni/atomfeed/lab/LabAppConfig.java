package org.bahmni.atomfeed.lab;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class LabAppConfig {

	@Bean(name="labDataSource")
	@ConfigurationProperties(prefix="datasource.lab")
	public DataSource labDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate labJdbcTemplate(){
		return new JdbcTemplate(labDataSource(),true);
	}

}
