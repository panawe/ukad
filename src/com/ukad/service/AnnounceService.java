package com.ukad.service;

import java.util.List;

import com.ukad.model.Advertisement;
import com.ukad.model.Announce;
import com.ukad.model.Event;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;

public interface AnnounceService extends BaseService {

	List<Announce> loadAllAnnounces(Class<Announce> class1);
	
	List<Announce> loadActiveAnnounces(Class<Announce> class1);

}
