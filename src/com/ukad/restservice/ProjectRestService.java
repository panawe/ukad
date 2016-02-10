package com.ukad.restservice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.ukad.model.Event;
import com.ukad.model.Project;
import com.ukad.security.model.User;
import com.ukad.security.service.UserService;
import com.ukad.service.EventService;
import com.ukad.service.ProjectService;
import com.ukad.util.SimpleMail;

@RestController
@RequestMapping("/service/project")

public class ProjectRestService {

	@Autowired
	ProjectService projectService;
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/receiveFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public @ResponseBody String receiveFile(@RequestParam("file") MultipartFile file, @RequestParam("projectId") String projectId ) {

		if (!file.isEmpty()) {
			try {
				String originalFileExtension = file.getOriginalFilename()
						.substring(file.getOriginalFilename().lastIndexOf("."));
				
				// transfer to upload folder
				String storageDirectory = null;
				int fileCount = 0;
				if (context != null) {
					
					storageDirectory = context.getRealPath("/") + 
							File.separator + "images" + File.separator + "projects"+
							File.separator + projectId;
					File dir = new File(storageDirectory);
					if(!dir.exists()){
						dir.mkdirs();
					}
					fileCount=dir.listFiles().length;
					
				} else {
					return "Failure";
					
				}
				String newFilename = projectId + "_" + (fileCount+1) + ".jpg";

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

	@RequestMapping(value = "/createProject", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Project createProject(@RequestBody Project project) {
		
		projectService.save(project);
		
		Project pr= (Project) projectService.getById(Project.class, project.getId());
		SimpleDateFormat df = new  SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
		return pr;
	}


	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String deleteProject(@RequestBody Project project) {
		System.out.println("Delete Project:" + project);
		projectService.delete(project);
		return "Success";
	}

	
	@RequestMapping(value = "/getAllProjects", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Project> getProjects() {
		System.out.println("Project list Requested - getProjects");
		return (List<Project>) projectService.loadAllProjects(Project.class);
	}
}