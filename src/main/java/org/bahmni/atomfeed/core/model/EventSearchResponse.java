package org.bahmni.atomfeed.core.model;

import java.util.List;

public class EventSearchResponse {
	private List<Event> events;

	public EventSearchResponse(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEvents() {
		return events;
	}
}
