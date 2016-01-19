package org.bahmni.atomfeed.core.web;

import org.bahmni.atomfeed.core.model.Consumer;
import org.bahmni.atomfeed.core.model.Event;
import org.bahmni.atomfeed.core.model.EventSearchRequest;
import org.bahmni.atomfeed.core.model.EventSearchResponse;
import org.bahmni.atomfeed.core.service.EventConsumer;
import org.bahmni.atomfeed.core.service.EventRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EventRecordController {

	private EventRecordService eventRecordService;

	private List<EventConsumer> eventConsumers;

	@Autowired
	public void setEventConsumers(List<EventConsumer> eventConsumers) {
		this.eventConsumers = eventConsumers;
	}

	@Autowired
	public void setEventRecordService(EventRecordService eventRecordService) {
		this.eventRecordService = eventRecordService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false, 10));
	}


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public EventSearchResponse searchEvents(@Valid EventSearchRequest eventSearchRequest){
		List<Event> events = eventRecordService.getEvents(eventSearchRequest);

		updateEventStatus(eventSearchRequest, events);

		return new EventSearchResponse(events);
	}

	private void updateEventStatus(EventSearchRequest eventSearchRequest, List<Event> events) {
		for(EventConsumer consumer: eventConsumers){
			Consumer consumerAnnotation = consumer.getClass().getAnnotation(Consumer.class);
			if(consumerAnnotation != null && eventSearchRequest.getComponent().equals(consumerAnnotation.source()) && eventSearchRequest.getCategory().equals(consumerAnnotation.category())){
				consumer.updateEventStatus(events);
			}
		}
	}
}
