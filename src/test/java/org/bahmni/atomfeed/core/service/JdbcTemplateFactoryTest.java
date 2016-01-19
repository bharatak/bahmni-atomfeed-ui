package org.bahmni.atomfeed.core.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class JdbcTemplateFactoryTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void testEnsureMrsJdbcTemplateIsAcquired(){
		JdbcTemplateFactory factory = new JdbcTemplateFactory();
		factory.setMrsJdbcTemplate(jdbcTemplate);

		assertEquals(jdbcTemplate,factory.getTemplate("mrs"));
	}
}
