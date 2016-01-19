package org.bahmni.atomfeed.core.service;

import org.bahmni.atomfeed.core.Constants;
import org.bahmni.atomfeed.core.model.Event;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class EventRowMapper implements RowMapper<Event> {

	private SimpleDateFormat simpleDateFormat;

	public EventRowMapper(){
		simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		simpleDateFormat.setTimeZone(Constants.GMT_TIMEZONE);
	}

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		Timestamp timestamp = rs.getTimestamp("timestamp");
		return new Event(rs.getString("uuid"), rs.getString("category"), simpleDateFormat.format(timestamp),rs.getString("object"));
	}
}
