package org.bahmni.atomfeed.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Event {
	private String uuid;
	private String category;
	private String timestamp;
	private String object;
	private List<EventStatus> status;

	public Event(String uuid, String category, String timestamp, String object) {
		this.uuid = uuid;
		this.category = category;
		this.timestamp = timestamp;
		this.object = object;
		this.status = new ArrayList<EventStatus>();
	}

	public void addEventStatus(EventStatus eventStatus){
		status.add(eventStatus);
	}

	public String getUuid() {
		return uuid;
	}

	public String getCategory() {
		return category;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getObject() {
		return object;
	}

	public List<EventStatus> getStatus() {
		return Collections.unmodifiableList(status);
	}
}
