package org.bahmni.atomfeed.core.service;

import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.EventSearchRequest;

import java.util.List;

public interface EventRecordService {

	List<Event> getEvents(EventSearchRequest searchRequest);

}
