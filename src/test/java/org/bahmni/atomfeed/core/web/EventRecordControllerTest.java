package org.bahmni.atomfeed.core.web;

import org.bahmni.atomfeed.core.model.Consumer;
import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.EventSearchRequest;
import org.bahmni.atomfeed.core.model.EventSearchResponse;
import org.bahmni.atomfeed.core.model.EventStatus;
import org.bahmni.atomfeed.core.model.FailedEvent;
import org.bahmni.atomfeed.core.service.EventConsumer;
import org.bahmni.atomfeed.core.service.EventRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EventRecordControllerTest {

	@Mock
	private EventRecordService eventRecordService;

	private EventRecordController controller;

	private EventConsumer eventConsumer = new MockEventConsumer();

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		controller = new EventRecordController();
		controller.setEventRecordService(eventRecordService);
		controller.setEventConsumers(Arrays.asList(eventConsumer));
	}

	@Test
	public void testSearchEvents() throws Exception {

		EventSearchRequest eventSearchRequest = new EventSearchRequest();
		eventSearchRequest.setCategory("Patient");
		eventSearchRequest.setComponent("mrs");
		eventSearchRequest.setStartDate(new SimpleDateFormat("yyyyddMM").parse("20150101"));
		eventSearchRequest.setEndDate(new SimpleDateFormat("yyyyddMM").parse("20151231"));

		Event event = new Event("uuid","category","timestamp","atomEntry");

		when(eventRecordService.getEvents(eventSearchRequest)).thenReturn(Arrays.asList(event));

		EventSearchResponse response = controller.searchEvents(eventSearchRequest);
		assertNotNull(response.getEvents());
		assertEquals(1, response.getEvents().size());
		assertEquals("category", response.getEvents().get(0).getCategory());
		assertEquals("uuid", response.getEvents().get(0).getUuid());
		assertEquals("timestamp", response.getEvents().get(0).getTimestamp());
		assertEquals("atomEntry", response.getEvents().get(0).getObject());
		assertNotNull(response.getEvents().get(0).getStatus());
		assertEquals(1, response.getEvents().get(0).getStatus().size());
		assertEquals("elis", response.getEvents().get(0).getStatus().get(0).getComponent());

		Map<String,String> addnlInfo = response.getEvents().get(0).getStatus().get(0).getAddnlInfo();
		assertEquals("1234", addnlInfo.get("customer_id"));
	}

	@Test
	public void shouldNotUpdateEventStatusWhenThereAreNoConsumers() throws Exception {
			EventSearchRequest eventSearchRequest = new EventSearchRequest();
			eventSearchRequest.setCategory("Patient");
			eventSearchRequest.setComponent("lab");
			eventSearchRequest.setStartDate(new SimpleDateFormat("yyyyddMM").parse("20150101"));
			eventSearchRequest.setEndDate(new SimpleDateFormat("yyyyddMM").parse("20151231"));

			Event event = new Event("uuid","category","timestamp","atomEntry");

			when(eventRecordService.getEvents(eventSearchRequest)).thenReturn(Arrays.asList(event));

			EventSearchResponse response = controller.searchEvents(eventSearchRequest);
			assertNotNull(response.getEvents());
			assertEquals(1, response.getEvents().size());
			assertEquals("category", response.getEvents().get(0).getCategory());
			assertEquals("uuid", response.getEvents().get(0).getUuid());
			assertEquals("timestamp", response.getEvents().get(0).getTimestamp());
			assertEquals("atomEntry", response.getEvents().get(0).getObject());
			assertNotNull(response.getEvents().get(0).getStatus());
			assertEquals(0, response.getEvents().get(0).getStatus().size());

	}


	@Consumer(category = "Patient", source= "mrs")
	public static class MockEventConsumer implements EventConsumer{

		@Override
		public void updateEventStatus(List<Event> event) {

			if(event==null || event.size()==0)
				return;

			Map<String, String> addnlInfo = new HashMap<String, String>();
			addnlInfo.put("customer_id","1234");
			EventStatus eventStatus = new EventStatus("elis",null, addnlInfo);
			event.get(0).addEventStatus(eventStatus);
		}
	}


}
