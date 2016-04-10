package com.ukad.listener;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ukad.security.model.SessionHistory;
import com.ukad.security.model.User;
import com.ukad.security.service.UserService;

@Service
public class DelegationListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent se) {
        ApplicationContext context = 
            WebApplicationContextUtils.getWebApplicationContext(
                se.getSession().getServletContext()
            );

        HttpSessionListener target = 
            context.getBean("mySessionListener", HttpSessionListener.class);
        target.sessionCreated(se);
    }

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		 ApplicationContext context = 
		            WebApplicationContextUtils.getWebApplicationContext(
		                se.getSession().getServletContext()
		            );

		        HttpSessionListener target = 
		            context.getBean("mySessionListener", HttpSessionListener.class);
		        target.sessionDestroyed(se);		
	}
}