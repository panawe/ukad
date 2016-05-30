package com.ukad.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ukad.model.Advertisement;
import com.ukad.model.BaseEntity;
import com.ukad.model.Project;

@Service("advertisementService")
public class AdvertisementServiceImpl extends BaseServiceImpl implements AdvertisementService {

	@Override
	public List<Advertisement> loadActiveAdvertisements(Class<Advertisement> class1) {
		// TODO Auto-generated method stub
		List<BaseEntity> advts = (List<BaseEntity>) loadAllByColumn(class1, "status", 1);
		List<Advertisement> advertisements = null;
		if (advts != null) {
			advertisements = new ArrayList<Advertisement>();
			for (BaseEntity advt : advts) {
				advertisements.add((Advertisement)advt);
			}

		}
		return advertisements;
	}
	
	
	@Override
	public List<Advertisement> loadAllAdvertisementsBySponsor(Class<Advertisement> class1, Long sponsorId) {
		// TODO Auto-generated method stub
		List<BaseEntity> advts = loadAllByParentId(class1, "sponsor", "id", sponsorId);
		List<Advertisement> advertisements = null;
		if (advts != null) {
			advertisements = new ArrayList<Advertisement>();
			for (BaseEntity advt : advts) {
				advertisements.add((Advertisement)advt);
			}

		}
		return advertisements;
	}

}
