package com.ukad.service;

import java.util.List;

import com.ukad.model.Event;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;

public interface SponsorService extends BaseService {

	List<Sponsor> loadAllSponsors(Class<Sponsor> class1);

}
