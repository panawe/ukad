package com.ukad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ukad.model.Advertisement;
import com.ukad.model.Announce;
import com.ukad.model.BaseEntity;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;
import com.ukad.model.Weblink;

@Service("weblinkService")
public class WeblinkServiceImpl extends BaseServiceImpl implements WeblinkService {

	@Override
	public List<Weblink> loadActiveWeblinks(Class<Weblink> class1) {
		// TODO Auto-generated method stub
		List<BaseEntity> wls = (List<BaseEntity>) loadAllByColumn(class1, "status", 0);
		List<Weblink> weblinks = null;
		if (wls != null) {
			weblinks = new ArrayList<Weblink>();
			for (BaseEntity wl : wls) {
				weblinks.add((Weblink)wl);
			}

		}
		return weblinks;
	}

	@Override
	public List<Weblink> loadAllWeblinks(Class<Weblink> class1) {
		// TODO Auto-generated method stub
		List<BaseEntity> wls = loadAll(class1);
		List<Weblink> weblinks = null;
		if (wls != null) {
			weblinks = new ArrayList<Weblink>();
			for (BaseEntity wl : wls) {
				weblinks.add((Weblink) wl);
			}

		}
		return weblinks;
	}
}
