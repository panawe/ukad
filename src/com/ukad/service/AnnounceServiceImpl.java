package com.ukad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ukad.model.Advertisement;
import com.ukad.model.Announce;
import com.ukad.model.BaseEntity;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;

@Service("announceService")
public class AnnounceServiceImpl extends BaseServiceImpl implements AnnounceService {

	@Override
	public List<Announce> loadActiveAnnounces(Class<Announce> class1) {
		// TODO Auto-generated method stub
		List<BaseEntity> ances = (List<BaseEntity>) loadAllByColumn(class1, "status", 0);
		List<Announce> announces = null;
		if (ances != null) {
			announces = new ArrayList<Announce>();
			for (BaseEntity ance : ances) {
				announces.add((Announce)ance);
			}

		}
		return announces;
	}

	@Override
	public List<Announce> loadAllAnnounces(Class<Announce> class1) {
		// TODO Auto-generated method stub
		List<BaseEntity> ans = loadAll(class1);
		List<Announce> announces = null;
		if (ans != null) {
			announces = new ArrayList<Announce>();
			for (BaseEntity a : ans) {
				announces.add((Announce) a);
			}

		}
		return announces;
	}
}
