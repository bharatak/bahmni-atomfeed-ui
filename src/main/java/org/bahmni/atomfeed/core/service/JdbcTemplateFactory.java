package org.bahmni.atomfeed.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateFactory {

	private JdbcTemplate mrsJdbcTemplate;

	@Autowired
	public void setMrsJdbcTemplate(JdbcTemplate mrsJdbcTemplate) {
		this.mrsJdbcTemplate = mrsJdbcTemplate;
	}

	public JdbcTemplate getTemplate(String category){
		if("mrs".equalsIgnoreCase(category)){
			return mrsJdbcTemplate;
		}

		return null;
	}

}
