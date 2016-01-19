package org.bahmni.atomfeed.lab;

import org.bahmni.atomfeed.core.Constants;
import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.FailedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LabPatientConsumerTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void ensureEventStatusIsUpdated(){
		FailedEvent expected = new FailedEvent("errorMsg","errorHashCode","failedAt",5);

		Event event = new Event("uuid","category","timestamp","object");
		LabPatientConsumer consumer = new LabPatientConsumer();
		consumer.setJdbcTemplate(jdbcTemplate);

		when(jdbcTemplate.queryForObject(any(String.class),eq(new Object[] { "tag:atomfeed.ict4h.org:uuid" }),any(RowMapper.class))).thenReturn(expected);
		consumer.updateEventStatus(Arrays.asList(event));

		assertNotNull(event.getStatus());
		assertEquals(1, event.getStatus().size());
		assertEquals(Constants.LAB, event.getStatus().get(0).getComponent());
		assertEquals(expected,event.getStatus().get(0).getFailedEvent());

	}

}
