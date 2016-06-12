package com.ukad.service;

import java.util.List;

import com.ukad.model.Announce;

public interface AnnounceService extends BaseService {

	List<Announce> loadAllAnnounces(Class<Announce> class1);
	
	List<Announce> loadActiveAnnounces(Class<Announce> class1);
	
	Announce getNextAnnounce(Long announceId);
	
	Announce getPreviousAnnounce(Long announceId);

}
