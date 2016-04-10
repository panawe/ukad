package com.ukad.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ukad.security.model.SessionHistory;
import com.ukad.security.model.User;
import com.ukad.security.service.UserService;

@Service("mySessionListener")
public class MySessionListener implements HttpSessionListener {
	@Autowired
	UserService userService;
	
	public void sessionCreated(HttpSessionEvent se) {

		try {


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		try {
			System.out.println("Closing the session ...");

			Long userId = (Long)se.getSession().getAttribute("userId");
			Long sessionHistoryId = (Long)se.getSession().getAttribute("sessionHistoryId");
			User user = new User();
			user.setId(userId);
			if (user != null) {
				SessionHistory sh = (SessionHistory) userService.getById(SessionHistory.class, sessionHistoryId);
				sh.setEndDate(new Date());
				userService.update(sh, user);				
			}
		} catch (Exception e) {
			System.out.println("Impossible to close connection");
			e.printStackTrace();
		}
	}
}
