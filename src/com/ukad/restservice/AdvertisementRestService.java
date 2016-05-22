package com.ukad.restservice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ukad.model.Advertisement;
import com.ukad.model.Announce;
import com.ukad.model.Event;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;
import com.ukad.security.model.User;
import com.ukad.security.service.UserService;
import com.ukad.service.AdvertisementService;
import com.ukad.service.EventService;
import com.ukad.util.SimpleMail;

@RestController
@RequestMapping("/service/marketing")

public class AdvertisementRestService {

	@Autowired
	AdvertisementService advertisementService;
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/receiveFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody String receiveFile(@RequestParam("file") MultipartFile file,
			@RequestParam("advertisementId") String advertisementId) {
		System.out.println("Received ");
		if (!file.isEmpty()) {
			try {
				String originalFileExtension = file.getOriginalFilename()
						.substring(file.getOriginalFilename().lastIndexOf("."));

				// transfer to upload folder
				String storageDirectory = null;
				int fileCount = 0;
				if (context != null) {

					storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "marketings"
							+ File.separator + advertisementId;
					File dir = new File(storageDirectory);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					fileCount = dir.listFiles().length;

				} else {
					return "Failure";

				}
				String newFilename = advertisementId + "_1.jpg";

				File newFile = new File(storageDirectory + File.separator + newFilename);
				file.transferTo(newFile);

			} catch (Exception e) {
				e.printStackTrace();
				return "Failure";
			}
		} else {
			return "Failure";
		}

		return "Success";
	}

	@RequestMapping(value = "/createMarketing", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Advertisement createAdvertisement(@RequestBody Advertisement advertisement) {
		System.out.println("Advertisement Created:" + advertisement);
		Sponsor sponsor = new Sponsor();
		sponsor.setId(Long.valueOf(advertisement.getSponsorId()));
		advertisement.setSponsor(sponsor);
		advertisementService.save(advertisement);
		return advertisement;
	}

	@RequestMapping(value = "/deleteMarketing", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String deleteAdvertisement(@RequestBody Advertisement advertisement) {
		System.out.println("delete Advertisement:" + advertisement);
		advertisementService.delete(advertisement);
		return "Success";
	}

	@RequestMapping(value = "/getAllMarketingsBySponsor", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Advertisement> getAllAdvertisementsBySponsor(@RequestBody String sponsorId) {
		System.out.println("Event list Requested - getAdvertisements By Id");

		List<Advertisement> retList = (List<Advertisement>) advertisementService
				.loadAllAdvertisementsBySponsor(Advertisement.class, Long.valueOf(sponsorId));
		for (Advertisement a : retList) { 
				a.setImagePath("images/marketings/" + a.getId() + "/" + a.getId() + "_1.jpg");			 
		}
		Collections.reverse(retList);
		return retList;
	}

	@RequestMapping(value = "/getActiveMarketings", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Advertisement> getActiveAdvertisements() {
		System.out.println("Advertisement list requested");

		List<Advertisement> advertisements = (List<Advertisement>) advertisementService.loadActiveAdvertisements(Advertisement.class);
		List<Advertisement> retList = new ArrayList<Advertisement>();
		String storageDirectory = null;
		if (context != null) {

			storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "marketings";

		}
		
		for (Advertisement a : advertisements) {
			File dir = new File(storageDirectory + File.separator + a.getId());
			int fileCount = 0;
			if (dir.exists()) {
				fileCount = dir.listFiles().length;
			}

			if (fileCount > 0) {
				a.setHasImage(true);
				a.setImagePath("images/marketings/" + a.getId() + "/" + a.getId() + "_1.jpg");
				retList.add(a);

			}
		}
		
		Collections.reverse(retList);
		return retList;
	}

}