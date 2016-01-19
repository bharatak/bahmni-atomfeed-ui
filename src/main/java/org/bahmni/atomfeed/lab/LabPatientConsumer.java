package org.bahmni.atomfeed.lab;

import org.bahmni.atomfeed.core.Constants;
import org.bahmni.atomfeed.core.model.Consumer;
import org.bahmni.atomfeed.core.service.BaseEventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Consumer(category = "patient",source = "mrs")
public class LabPatientConsumer extends BaseEventConsumer {

	private static final String PATIENT_QUERY = "select first_name,last_name from person inner join patient on patient.person_id=person.id where patient.uuid=?";

	private JdbcTemplate jdbcTemplate;

	@Override
	protected String getAddnlInfoQuery() {
		return PATIENT_QUERY;
	}

	@Override
	protected String getComponent() {
		return Constants.LAB;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate labJdbcTemplate) {
		this.jdbcTemplate = labJdbcTemplate;
	}

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
