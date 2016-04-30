package com.ukad.service;

import java.util.List;

import com.ukad.model.Advertisement;
import com.ukad.model.Announce;
import com.ukad.model.Event;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;
import com.ukad.model.Weblink;

public interface WeblinkService extends BaseService {

	List<Weblink> loadAllWeblinks(Class<Weblink> class1);
	
	List<Weblink> loadActiveWeblinks(Class<Weblink> class1);

}
