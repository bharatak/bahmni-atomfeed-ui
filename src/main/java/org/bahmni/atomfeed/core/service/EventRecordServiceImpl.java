package org.bahmni.atomfeed.core.service;

import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.EventSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventRecordServiceImpl implements EventRecordService{

	private static final Logger log = LoggerFactory.getLogger(EventRecordServiceImpl.class);

	private JdbcTemplateFactory jdbcTemplateFactory;

	private static final String EVENT_QUERY = "select uuid,category,object,timestamp from event_records where category=? and timestamp between ? and ?";

	@Autowired
	public void setJdbcTemplateFactory(JdbcTemplateFactory jdbcTemplateFactory) {
		this.jdbcTemplateFactory = jdbcTemplateFactory;
	}

	@Override
	public List<Event> getEvents(EventSearchRequest searchRequest) {
		JdbcTemplate jdbcTemplate = jdbcTemplateFactory.getTemplate(searchRequest.getComponent());

		if(jdbcTemplate == null){
			log.error("No database configuration found for the component ["+ searchRequest.getComponent()+"]");
			return new ArrayList<Event>();
		}

		List<Event> events = jdbcTemplate.query(EVENT_QUERY,new Object[]{searchRequest.getCategory(), searchRequest.getStartDate(), searchRequest.getEndDate()}, new EventRowMapper());

		return events;
	}
}
