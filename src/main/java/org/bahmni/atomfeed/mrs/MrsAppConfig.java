package org.bahmni.atomfeed.mrs;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MrsAppConfig {

	@Bean(name="mrsDataSource")
	@Primary
	@ConfigurationProperties(prefix="datasource.mrs")
	public DataSource mrsDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JdbcTemplate mrsJdbcTemplate(){
		return new JdbcTemplate(mrsDataSource(),true);
	}

}
