package org.bahmni.atomfeed.core.model;

import java.util.Map;

public class EventStatus {
	private String component;
	private FailedEvent failedEvent;
	private Map<String,String> addnlInfo;

	public EventStatus(String component, FailedEvent failedEvent, Map<String, String> addnlInfo) {
		this.component = component;
		this.failedEvent = failedEvent;
		this.addnlInfo = addnlInfo;
	}

	public String getComponent() {
		return component;
	}

	public FailedEvent getFailedEvent() {
		return failedEvent;
	}

	public Map<String, String> getAddnlInfo() {
		return addnlInfo;
	}
}
