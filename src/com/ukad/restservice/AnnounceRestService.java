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
import com.ukad.model.Weblink;
import com.ukad.security.model.User;
import com.ukad.security.service.UserService;
import com.ukad.service.AdvertisementService;
import com.ukad.service.AnnounceService;
import com.ukad.service.EventService;
import com.ukad.util.SimpleMail;

@RestController
@RequestMapping("/service/announce")

public class AnnounceRestService {

	@Autowired
	AnnounceService announceService;
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/receiveFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody String receiveFile(@RequestParam("file") MultipartFile file,
			@RequestParam("announceId") String announceId) {
		System.out.println("Received ");
		if (!file.isEmpty()) {
			try {
				String originalFileExtension = file.getOriginalFilename()
						.substring(file.getOriginalFilename().lastIndexOf("."));

				// transfer to upload folder
				String storageDirectory = null;
				int fileCount = 0;
				if (context != null) {

					storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "announces"
							+ File.separator + announceId;
					File dir = new File(storageDirectory);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					fileCount = dir.listFiles().length;

				} else {
					return "Failure";

				}
				String newFilename = announceId + "_" + (fileCount + 1) + ".jpg";

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

	@RequestMapping(value = "/createAnnounce", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Announce createAnnounce(@RequestBody Announce announce) {
		System.out.println("Announce Created:" + announce);
		announceService.save(announce);
		return announce;
	}

	@RequestMapping(value = "/deleteAnnounce", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String deleteAnnounce(@RequestBody Announce announce) {
		System.out.println("delete Announce:" + announce);
		announceService.delete(announce);
		return "Success";
	}

	@RequestMapping(value = "/getActiveAnnounces", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Announce> getActiveAnnounces() {
		System.out.println("Announce list requested");

		List<Announce> retList = (List<Announce>) announceService.loadActiveAnnounces(Announce.class);
			
		Collections.reverse(retList);
		return retList;
	}
	
	@RequestMapping(value = "/getAllAnnounces", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Announce> getAnnounces() {
		System.out.println("Announce list Requested - getAnnounces");
		List<Announce> announces = announceService.loadAllAnnounces(Announce.class);
		
		return announces;
	}
	
	@RequestMapping(value = "/getAllAnnouncesWithAlbum", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Announce> getAllAnnouncesWithAlbum() {
		System.out.println("Announce list Requested - getAllAnnouncesWithAlbum");
		List<Announce> announces = (List<Announce>) announceService.loadActiveAnnounces(Announce.class);
		String storageDirectory = null;

		if (context != null) {
			storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "announces";
		}
		List<Announce> retList = new ArrayList<Announce>();
		for (Announce a : announces) {
			File dir = new File(storageDirectory + File.separator + a.getId());
			int fileCount = 0;
			if (dir.exists()) {
				fileCount = dir.listFiles().length;
			}
			if (fileCount > 0) {
				retList.add(a);
			}
		}
		Collections.reverse(retList);
		return retList;
	}

	
	@RequestMapping(value = "/getAnnounceAlbum", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<String> getAnnounceAlbum(@RequestBody Announce announce) {
		System.out.println("getAnnounceAlbum Announce:" + announce);
		String storageDirectory = null;

		if (context != null) {
			storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "events";
		}
		List<String> retList = new ArrayList<String>();

		File dir = new File(storageDirectory + File.separator + announce.getId());

		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				retList.add(file.getName());
			}
		}

		Collections.reverse(retList);
		return retList;

	}


}