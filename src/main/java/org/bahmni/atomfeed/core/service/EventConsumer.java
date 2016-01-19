package org.bahmni.atomfeed.core.service;

import org.bahmni.atomfeed.core.model.Event;

import java.util.List;

public interface EventConsumer {

	void updateEventStatus(List<Event> event);

}
