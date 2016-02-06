package com.ukad.service;

import java.util.List;

import com.ukad.model.Event;

public interface EventService  extends BaseService {

	List<Event> loadAllEvents(Class<Event> class1);


}
