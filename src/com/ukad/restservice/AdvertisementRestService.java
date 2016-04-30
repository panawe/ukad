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
import com.ukad.model.Event;
import com.ukad.model.Project;
import com.ukad.model.Sponsor;
import com.ukad.security.model.User;
import com.ukad.security.service.UserService;
import com.ukad.service.AdvertisementService;
import com.ukad.service.EventService;
import com.ukad.util.SimpleMail;

@RestController
@RequestMapping("/service/advertisement")

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

					storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "advertisements"
							+ File.separator + advertisementId;
					File dir = new File(storageDirectory);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					fileCount = dir.listFiles().length;

				} else {
					return "Failure";

				}
				String newFilename = advertisementId + "_" + (fileCount + 1) + ".jpg";

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

	@RequestMapping(value = "/createAdvertisement", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Advertisement createAdvertisement(@RequestBody Advertisement advertisement) {
		System.out.println("Advertisement Created:" + advertisement);
		Sponsor sponsor = new Sponsor();
		sponsor.setId(Long.valueOf(advertisement.getSponsorId()));
		advertisement.setSponsor(sponsor);
		advertisementService.save(advertisement);
		return advertisement;
	}

	@RequestMapping(value = "/deleteAdvertisement", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String deleteAdvertisement(@RequestBody Advertisement advertisement) {
		System.out.println("delete Advertisement:" + advertisement);
		advertisementService.delete(advertisement);
		return "Success";
	}

	@RequestMapping(value = "/getAllAdvertisementsBySponsor", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Advertisement> getAllAdvertisementsBySponsor(@RequestBody String sponsorId) {
		System.out.println("Event list Requested - getAdvertisements By Id");

		List<Advertisement> retList = (List<Advertisement>) advertisementService
				.loadAllAdvertisementsBySponsor(Advertisement.class, Long.valueOf(sponsorId));
		Collections.reverse(retList);
		return retList;
	}

	@RequestMapping(value = "/getActiveAdvertisements", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Advertisement> getActiveAdvertisements() {
		System.out.println("Advertisement list requested");

		List<Advertisement> retList = (List<Advertisement>) advertisementService.findByColumn(Advertisement.class, "status", 0);
			
		Collections.reverse(retList);
		return retList;
	}
	
	@RequestMapping(value = "/getProjectAlbum", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<String> getProjectAlbum(@RequestBody Project project) {
		System.out.println("getProjectAlbum Project:" + project);
		String storageDirectory = null;

		List<Advertisement> advList = (List<Advertisement>) advertisementService.loadActiveAdvertisements(Advertisement.class);
		
		if (advList.isEmpty()) {
			return null;
		}
		
		if (context != null) {
			storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "advertisements";
		}
		List<String> retList = new ArrayList<String>();

		for (Advertisement adv : advList) {
			File dir = new File(storageDirectory + File.separator + adv.getId());
			if (dir.exists()) {
				File[] files = dir.listFiles();
				for (File file : files) {
					retList.add(file.getName());
				}
			}
		}
		Collections.reverse(retList);
		return retList;

	}

}