package com.ukad.service;

import java.util.List;

import com.ukad.model.Advertisement;
import com.ukad.model.Event;
import com.ukad.model.Project;

public interface AdvertisementService extends BaseService {

	List<Advertisement> loadActiveAdvertisements(Class<Advertisement> class1);
	
	List<Advertisement> loadAllAdvertisementsBySponsor(Class<Advertisement> class1, Long sponsorId);

}
