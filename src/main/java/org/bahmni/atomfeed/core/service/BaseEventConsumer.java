package org.bahmni.atomfeed.core.service;

import org.bahmni.atomfeed.core.Constants;
import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.EventStatus;
import org.bahmni.atomfeed.core.model.FailedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseEventConsumer implements EventConsumer{

	protected abstract String getAddnlInfoQuery();

	protected abstract String getComponent();

	protected abstract JdbcTemplate getJdbcTemplate();

	private static final String EVENT_ID_PREFIX="tag:atomfeed.ict4h.org:";

	private static final String FAILED_EVENT_QUERY = "select feed_uri,error_message,event_id,failed_at,error_hash_code,retries from failed_events where event_id = ?";

	@Override
	public void updateEventStatus(List<Event> events) {

		for(Event event: events){
			EventStatus eventStatus = queryEventStatus(event);
			event.addEventStatus(eventStatus);
		}
	}

	private EventStatus queryEventStatus(Event event) {
		return new EventStatus(getComponent(),queryForFailedEvent(event),getContextForEvent(event));
	}

	private Map<String, String> getContextForEvent(Event event) {
		List<Map<String, String>> result = getJdbcTemplate().query(getAddnlInfoQuery(), new Object[] { event.getUuid() },
				new RowMapper<Map<String, String>>() {

					@Override
					public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
						Map<String, String> map = new HashMap<String, String>();

						ResultSetMetaData meta = rs.getMetaData();
						while (rs.next()) {
							for (int i = 1; i <= meta.getColumnCount(); i++) {
								String key = meta.getColumnName(i);
								String value = rs.getString(key);
								map.put(key, value);
							}
						}

						return map;
					}
				});
		return result.size() > 0 ? result.get(0) : new HashMap<String, String>();
	}

	private FailedEvent queryForFailedEvent(Event event) {
		String eventId = EVENT_ID_PREFIX + event.getUuid();

		List<FailedEvent> failedEvents = getJdbcTemplate().query(FAILED_EVENT_QUERY, new Object[] { eventId },
				new RowMapper<FailedEvent>() {

					@Override
					public FailedEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
						simpleDateFormat.setTimeZone(Constants.GMT_TIMEZONE);
						FailedEvent failedEvent = new FailedEvent(rs.getString("error_message"),
								rs.getString("error_hash_code"), simpleDateFormat.format(rs.getTimestamp("failed_at")),
								rs.getInt("retries"));
						return failedEvent;
					}
				});

		return failedEvents.size() > 0 ? failedEvents.get(0): null;
	}

}
