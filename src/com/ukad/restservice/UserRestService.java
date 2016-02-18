package com.ukad.restservice;

import java.io.File;
import java.io.IOException;
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
import com.ukad.model.Mail;
import com.ukad.model.Transaction;
import com.ukad.security.model.Search;
import com.ukad.security.model.User;
import com.ukad.security.model.YearlySummary;
import com.ukad.security.service.UserService;
import com.ukad.util.SimpleMail;

@RestController
@RequestMapping("/service/user")

public class UserRestService {

	@Autowired
	UserService userService;
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody User login(@RequestBody User user) {
		System.out.println("User Login :" + user);
		return userService.getUser(user.getUserName(), user.getPassword());
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody User createUser(@RequestBody User user) {
		user.setUserName(user.getEmail());

		userService.add(user);
		System.out.println("User Created:" + user);
		try {
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2>Nous avons bien recu votre demande d'adhesion a U.K.A.D e.V. </h2><h2>Votre demande va etre etudier et vous serez notifie d'ici peu.</h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a UKAD eV bien recue", mail, "ukadtogo@gmail.com",
					user.getEmail(), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");

			mail = "<blockquote><h2><b>Nom: " + user.getLastName() + "</b></h2><h2><b>Prenom:" + user.getFirstName()
					+ "</b></h2><h2><b>E-mail:" + user.getEmail()
					+ "</b></h2><div><b>Veuillez Approver en allant sur le site: <a href=\"www.ukadtogo.com \" target=\"\">www.ukadtogo.com </a></b></div></blockquote>";
			SimpleMail.sendMail("Demand d'adhesion de " + user.getFirstName() + " " + user.getLastName(), mail,
					"ukadtogo@gmail.com", "ukadtogo@gmail.com", "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userService.getUser(user.getUserName(), user.getPassword());
	}
	
	
	@RequestMapping(value = "/makePayment", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String makePayment(@RequestBody Transaction tran) {
		System.out.println("makePayment called:" + tran);
		
		
		try {
			userService.save(tran);
			/*
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2>Nous avons bien recu votre demande d'adhesion a U.K.A.D e.V. </h2><h2>Votre demande va etre etudier et vous serez notifie d'ici peu.</h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a UKAD eV bien recue", mail, "ukadtogo@gmail.com",
					user.getEmail(), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");

			mail = "<blockquote><h2><b>Nom: " + user.getLastName() + "</b></h2><h2><b>Prenom:" + user.getFirstName()
					+ "</b></h2><h2><b>E-mail:" + user.getEmail()
					+ "</b></h2><div><b>Veuillez Approver en allant sur le site: <a href=\"www.ukadtogo.com \" target=\"\">www.ukadtogo.com </a></b></div></blockquote>";
			SimpleMail.sendMail("Demand d'adhesion de " + user.getFirstName() + " " + user.getLastName(), mail,
					"ukadtogo@gmail.com", "ukadtogo@gmail.com", "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");
					*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failure";
		}

		return "Success";
	}

	
	@RequestMapping(value = "/saveExpense", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String saveExpense(@RequestBody Transaction tran) {
		System.out.println("saveExpense called:" + tran);
		
		
		try {
			userService.save(tran);
			/*
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2>Nous avons bien recu votre demande d'adhesion a U.K.A.D e.V. </h2><h2>Votre demande va etre etudier et vous serez notifie d'ici peu.</h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a UKAD eV bien recue", mail, "ukadtogo@gmail.com",
					user.getEmail(), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");

			mail = "<blockquote><h2><b>Nom: " + user.getLastName() + "</b></h2><h2><b>Prenom:" + user.getFirstName()
					+ "</b></h2><h2><b>E-mail:" + user.getEmail()
					+ "</b></h2><div><b>Veuillez Approver en allant sur le site: <a href=\"www.ukadtogo.com \" target=\"\">www.ukadtogo.com </a></b></div></blockquote>";
			SimpleMail.sendMail("Demand d'adhesion de " + user.getFirstName() + " " + user.getLastName(), mail,
					"ukadtogo@gmail.com", "ukadtogo@gmail.com", "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");
					*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failure";
		}

		return "Success";
	}


	@RequestMapping(value = "/saveUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody User saveUser(@RequestBody User user) {
		userService.add(user);
		return userService.getUser(user.getUserName(), user.getPassword());
	}

	@RequestMapping(value = "/receiveFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String receiveFile(@RequestParam("file") MultipartFile file,
			@RequestParam("userId") String userId) {
		if (!file.isEmpty()) {
			try {
				String originalFileExtension = file.getOriginalFilename()
						.substring(file.getOriginalFilename().lastIndexOf("."));

				// transfer to upload folder
				String storageDirectory = null;
				if (context != null) {

					storageDirectory = context.getRealPath("/") + File.separator + "images" + File.separator
							+ "members";
					File dir = new File(storageDirectory);
					if (!dir.exists()) {
						dir.mkdirs();
					}

				} else {
					return "Failure";

				}
				String newFilename = userId + ".jpg";

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

	@RequestMapping(value = "/getUsers", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<User> getUsers() {
		System.out.println("User list Requested - getUsers");
		return userService.loadAllUsers();
	}

	@RequestMapping(value = "/findMembers", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<User> findMembers(@RequestBody Search searchText) {
		System.out.println("User list Requested - findMembers" + searchText);
		return userService.findMembers(searchText.getSearchText());
	}

	@RequestMapping(value = "/getPendingMembers", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<User> getPending() {
		System.out.println("User list Requested -getPending ");
		return userService.loadAllMembersPending();
	}
	
	@RequestMapping(value = "/getLeaders", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<User> getLeaders() {
		System.out.println("User list Requested -getPending ");
		return userService.getLeaders();
	}

	@RequestMapping(value = "/getAllMembers", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<User> getAllMembers() {
		System.out.println("User list Requested - getAllMembers");
		return userService.loadAllMembers();
	}

	@RequestMapping(value = "/approveMember", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String approveMember(@RequestBody User user) {
		System.out.println("Member Approval" + user);
		user.setStatus((short) 1);
		userService.update(user, user);
		try {
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2><span style=\"color: inherit;\">Nous somme heureux de vous annoncer que votre demande d'adhesion a ete acceptee. Restez aux nouvelles de l'association en visitant <a href=\"www.ukadtogo.com\" target=\"\">www.ukadtogo.com</a> </span><br/></h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a UKAD eV Approvee", mail, "ukadtogo@gmail.com",
					user.getEmail(), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";
	}

	@RequestMapping(value = "/rejectMember", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String rejectMember(@RequestBody User user) {
		System.out.println("rejectMember" + user);
		user.setStatus((short) 1);
		userService.update(user, user);
		try {
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2><span style=\"color: inherit;\">Nous somme desole de vous annoncer que votre demande d'adhesion a ete rejetee. Restez aux nouvelles de l'association en visitant <a href=\"www.ukadtogo.com\" target=\"\">www.ukadtogo.com</a> </span><br/></h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a UKAD eV Rejetee", mail, "ukadtogo@gmail.com",
					user.getEmail(), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String sendMail(@RequestBody Mail mail) {
		System.out.println("Mail Being sent");
		if (mail == null || mail.getBody() == null || mail.getSubject() == null || mail.getSender() == null) {
			return "Failure";
		}
		List<User> users = userService.loadAllMembers();
		StringBuffer sb = new StringBuffer();

		for (User user : users) {
			sb.append(user.getEmail() + ",");
		}

		try {
			SimpleMail.sendMail(mail.getSubject(), mail.getBody(), mail.getSender().getEmail(),
					sb.substring(0, sb.length() - 1), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");

			mail.setStatus((short) 1);
			userService.save(mail, mail.getSender());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Success";
	}

	@RequestMapping(value = "/saveReportAndMail", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String saveReportAndMail(@RequestBody Mail mail) {
		System.out.println("Mail Being sent");
		if (mail == null || mail.getBody() == null || mail.getSubject() == null || mail.getSender() == null
				|| mail.getEventId() == null) {
			return "Failure";
		}

		Event event = (Event) userService.getById(Event.class, mail.getEventId());

		event.setReport(mail.getBody()); 

		List<User> users = userService.loadAllMembers();
		StringBuffer sb = new StringBuffer();

		for (User user : users) {
			sb.append(user.getEmail() + ",");
		}

		try {
			SimpleMail.sendMail(mail.getSubject(), mail.getBody(), mail.getSender().getEmail(),
					sb.substring(0, sb.length() - 1), "smtp.gmail.com", "ukadtogo@gmail.com", "ukadtogo123");

			mail.setStatus((short) 1);
			userService.save(mail, mail.getSender());
			userService.save(event, mail.getSender());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Success";
	}
	
	@RequestMapping(value = "/getYearlySummary", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<YearlySummary> getYearlySummary() {
		System.out.println("YearlySummary list Requested - YearlySummary");
		return userService.getYearlySmry();
	}


}
