package com.ukad.restservice;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ukad.listener.MySessionListener;
import com.ukad.model.Event;
import com.ukad.model.Mail;
import com.ukad.model.Transaction;
import com.ukad.security.model.Contribution;
import com.ukad.security.model.Donation;
import com.ukad.security.model.Search;
import com.ukad.security.model.SessionHistory;
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
	MySessionListener mySessionListener;
	@Autowired
	ServletContext context;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody User login(@RequestBody User user) {
		System.out.println("User Login :" + user);
		user = userService.getUser(user.getUserName(), user.getPassword());

		if (user != null) {
			Long sessionHistoryId = (Long) request.getSession().getAttribute("sessionHistoryId");
			if (sessionHistoryId == null)
				addGuestCount();
			sessionHistoryId = (Long) request.getSession().getAttribute("sessionHistoryId");

			request.getSession().setAttribute("userId", user.getId());
			if (sessionHistoryId != null) {
				SessionHistory sh = (SessionHistory) userService.getById(SessionHistory.class, sessionHistoryId);
				sh.setUser(user);
				userService.update(sh, user);
			}
		}

		return user;
	}

	@RequestMapping(value = "/sendPassword", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String sendPassword(@RequestBody User user) {
		System.out.println("User sendPassword :" + user);

		try {
			user = (User) userService.findByColumn(User.class, "userName", user.getUserName());
			if (user != null) {
				String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2>Vous avez demander a recevoir votre mot de passe sur cet e-mail </h2>"
						+ "<h2>Le Voici: <strong>" + user.getPassword() + "</strong></h2>"
						+ "<h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
				SimpleMail.sendMail("Votre Mot de passe A.G.W.E", mail, "agwedc@gmail.com", user.getEmail(),
						"smtp.gmail.com", "agwedc@gmail.com", "agwedc123");
				return "Success";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failure";
		}

		return "Failure";
	}

	@RequestMapping(value = "/addGuestCount", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addGuestCount() {
		MySessionListener.sessions.add(request.getSession().getId());

		SessionHistory sessionHistory = new SessionHistory();
		sessionHistory.setBeginDate(new Date());
		sessionHistory.setUser(null);
		sessionHistory.setSessionId(request.getSession().getId());
		sessionHistory.setHostIp(request.getRemoteAddr());
		sessionHistory.setHostName(request.getRemoteHost());
		sessionHistory.setLanguage(request.getLocalName());
		sessionHistory.setOsuser(request.getRemoteUser());
		sessionHistory.setBrowser(request.getHeader("User-Agent"));
		userService.save(sessionHistory);

		request.getSession().setAttribute("sessionHistoryId", sessionHistory.getId());
	}

	@RequestMapping(value = "/getGuestCount", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Long getGuestCount() {
		return (long) MySessionListener.sessions.size();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Boolean logout() {
		System.out.println("User Logout :" + request.getSession().getAttribute("userId"));

		mySessionListener.sessionDestroyed(request);

		request.getSession().setAttribute("userId", null);
		request.getSession().setAttribute("sessionHistoryId", null);

		return true;
	}

	@RequestMapping(value = "/getMessagingUsers", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<User> getMessagingUsers() {

		List<User> members = userService.loadAllMembersWithOnlineStatus();

		return members;
	}

	@RequestMapping(value = "/createUser", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody User createUser(@RequestBody User user) {
		user.setUserName(user.getEmail());
		user.setMembershipDate(new Date());

		Calendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		user.setMembershipRenewDate(date.getTime());
		userService.add(user);
		System.out.println("User Created:" + user);
		try {
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2>Nous avons bien recu votre demande d'adhesion a A.G.W.E </h2><h2>Votre demande va etre etudier et vous serez notifie d'ici peu.</h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a A.G.W.E  bien recue", mail, "agwedc@gmail.com",
					user.getEmail(), "smtp.gmail.com", "agwedc@gmail.com", "agwedc123");

			mail = "<blockquote><h2><b>Nom: " + user.getLastName() + "</b></h2><h2><b>Prenom:" + user.getFirstName()
					+ "</b></h2><h2><b>E-mail:" + user.getEmail()
					+ "</b></h2><div><b>Veuillez Approver en allant sur le site: <a href=\"localhost:8080/ukadtogo \" target=\"\">localhost:8080/ukadtogo </a></b></div></blockquote>";
			SimpleMail.sendMail("Demand d'adhesion de " + user.getFirstName() + " " + user.getLastName(), mail,
					"agwedc@gmail.com", "agwedc@gmail.com", "smtp.gmail.com", "agwedc@gmail.com", "agwedc123");

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
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2><span style=\"color: inherit;\">Nous somme heureux de vous annoncer que votre demande d'adhesion a ete acceptee. Restez aux nouvelles de l'association en visitant <a href=\"localhost:8080/ukadtogo\" target=\"\">localhost:8080/ukadtogo</a> </span><br/></h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a A.G.W.E Approvee", mail, "agwedc@gmail.com",
					user.getEmail(), "smtp.gmail.com", "agwedc@gmail.com", "agwedc123");
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
			String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2><span style=\"color: inherit;\">Nous somme desole de vous annoncer que votre demande d'adhesion a ete rejetee. Restez aux nouvelles de l'association en visitant <a href=\"localhost:8080/ukadtogo\" target=\"\">localhost:8080/ukadtogo</a> </span><br/></h2><h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre demande d'adhesion a A.G.W.E  Rejetee", mail, "agwedc@gmail.com",
					user.getEmail(), "smtp.gmail.com", "agwedc@gmail.com", "agwedc123");
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
					sb.substring(0, sb.length() - 1), "smtp.gmail.com", "agwedc@gmail.com", "agwedc123");

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
					sb.substring(0, sb.length() - 1), "smtp.gmail.com", "agwedc@gmail.com", "agwedc123");

			mail.setStatus((short) 1);
			userService.save(mail, mail.getSender());
			userService.update(event, mail.getSender());
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

	@RequestMapping(value = "/getAllExpenses", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Transaction> getAllExpenses() {
		System.out.println("YearlySummary list Requested - getAllExpenses");
		return userService.getAllExpenses();
	}

	@RequestMapping(value = "/deleteExpense", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String deleteExpense(@RequestBody Transaction exp) {
		System.out.println("delete deleteExpense:" + exp);
		userService.delete(exp);
		return "Success";
	}

	@RequestMapping(value = "/getContributions", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Contribution> getContributions() {
		System.out.println("getContributions list Requested - getContributions");
		return userService.getContributions();
	}

	@RequestMapping(value = "/getDonations", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody List<Donation> getDonations() {
		System.out.println("getContributions list Requested - getContributions");
		return userService.getDonations();
	}
}
