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
import com.ukad.service.WeblinkService;
import com.ukad.util.SimpleMail;

@RestController
@RequestMapping("/service/weblink")

public class WeblinkRestService {

	@Autowired
	WeblinkService weblinkService;
	
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/receiveFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody String receiveFile(@RequestParam("file") MultipartFile file,
			@RequestParam("weblinkId") String weblinkId) {
		System.out.println("Received ");
		if (!file.isEmpty()) {
			try {
				String originalFileExtension = file.getOriginalFilename()
						.substring(file.getOriginalFilename().lastIndexOf("."));

				// transfer to upload folder
				String storageDirectory = null;
				int fileCount = 0;
				if (context != null) {

					storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator + "weblinks"
							+ File.separator + weblinkId;
					File dir = new File(storageDirectory);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					fileCount = dir.listFiles().length;

				} else {
					return "Failure";

				}
				String newFilename = weblinkId + "_" + (fileCount + 1) + ".jpg";

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

	@RequestMapping(value = "/createWeblink", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Weblink createWeblink(@RequestBody Weblink weblink) {
		System.out.println("Weblink Created:" + weblink);
		weblinkService.save(weblink);
		return weblink;
	}

	@RequestMapping(value = "/deleteWeblink", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String deleteWeblink(@RequestBody Weblink weblink) {
		System.out.println("delete Weblink:" + weblink);
		weblinkService.delete(weblink);
		return "Success";
	}

	@RequestMapping(value = "/getActiveWeblinks", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Weblink> getActiveWeblinks() {
		System.out.println("Weblink list requested");

		List<Weblink> retList = (List<Weblink>) weblinkService.loadActiveWeblinks(Weblink.class);
			
		Collections.reverse(retList);
		return retList;
	}
	
	@RequestMapping(value = "/getAllWeblinks", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Weblink> getWeblinks() {
		System.out.println("Weblink list Requested - getWeblinks");
		List<Weblink> weblinks = weblinkService.loadAllWeblinks(Weblink.class);
		
		return weblinks;
	}

}