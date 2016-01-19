package org.bahmni.atomfeed.core.service;

import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.EventSearchRequest;
import org.bahmni.atomfeed.core.model.EventSearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EventRecordServiceImplTest {

	private EventRecordServiceImpl eventRecordService;

	@Mock
	private JdbcTemplateFactory mockJdbcTemplateFactory;

	@Mock
	private JdbcTemplate mockJdbcTemplate;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		eventRecordService = new EventRecordServiceImpl();
		eventRecordService.setJdbcTemplateFactory(mockJdbcTemplateFactory);
	}

	@Test
	public void testGetEvents() throws Exception {

		EventSearchRequest eventSearchRequest = new EventSearchRequest();
		eventSearchRequest.setCategory("Patient");
		eventSearchRequest.setComponent("mrs");
		eventSearchRequest.setStartDate(new SimpleDateFormat("yyyyddMM").parse("20150101"));
		eventSearchRequest.setEndDate(new SimpleDateFormat("yyyyddMM").parse("20151231"));

		when(mockJdbcTemplateFactory.getTemplate("mrs")).thenReturn(mockJdbcTemplate);

		Event event = new Event("uuid","category","timestamp","/openmrs/ws/rest/v1/patient/5bd8005c-6b2f-4904-86d9-3fd0ae66e1c3?v=full");
		when(mockJdbcTemplate.queryForList("select uuid,category,object,timestamp from event_records", Event.class)).thenReturn(
				Arrays.asList(event));

		List<Event> events = eventRecordService.getEvents(eventSearchRequest);
		assertEquals(1, events.size());
		assertEquals(event, events.get(0));

	}


}
