// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
package com.ukad.restservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.ukad.model.Mail;
import com.ukad.security.model.User;
import com.ukad.util.GenerateAccessToken;
import com.ukad.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

/**
 * @author Panawe Batanado
 * 
 */

@RestController
@RequestMapping("/service/payment")

public class PaymentRestService {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(PaymentRestService.class);
	Map<String, String> map = new HashMap<String, String>();

	public void init()   {
		// ##Load Configuration
		// Load SDK configuration for
		// the resource. This intialization code can be
		// done as Init Servlet.
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
		}

	}
	
	public static void main(String args[]){
		PaymentRestService pp = new PaymentRestService();
	 	try {
			 pp.init();
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
//		Payment pay= pp.createPayment();
		
	/*	System.out.println("");
		
		pay.getLinks().get(1);
		
		String accessToken = "Bearer A101.TBAANnc6UB8GGYzFjUN71hRg_iklCrgpcO8A5zNsdsBfCp6CbL8Q_Els3GLEvoRx.4qRYIQkSg4biBRYns624bUuEe6y";
		APIContext apiContext = new APIContext(accessToken);
		apiContext.setConfigurationMap(sdkConfig);

		Payment payment = new Payment("PAY-3RX95691919558119K4ORIQY");
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId("LP67TK98L744U");
		payment.execute(apiContext, paymentExecute);*/
		
		//System.out.print(pay);
	}

/*	@RequestMapping(value = "/cancel", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody Payment createPayment(@RequestBody User user) {
		
		
	}*/
	
	@RequestMapping(value = "/createPayment", method = RequestMethod.POST, headers = "Accept=application/json")
	public  @ResponseBody String  createPayment(@RequestBody Payment payment) {
		Payment createdPayment = null;
		APIContext apiContext = null;
		String accessToken = null;
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
		}
		
		try {
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			 e.printStackTrace();
		}

	 	try {
	 		
	 		// ###Payer
			// A resource representing a Payer that funds a payment
			// Payment Method
			// as 'paypal'
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");
			payment.setPayer(payer);
			
			// ###Redirect URLs
						RedirectUrls redirectUrls = new RedirectUrls(); 
						redirectUrls.setCancelUrl("http://localhost:8080/ukadtogo/#/pages/cancelDonate");
						redirectUrls.setReturnUrl("http://localhost:8080/ukadtogo/#/pages/donate");
						payment.setRedirectUrls(redirectUrls);
						
			createdPayment = payment.create(apiContext);
			LOGGER.info("Created payment with id = " + createdPayment.getId() + " and status = "
					+ createdPayment.getState());
			// ###Payment Approval Url
			Iterator<Links> links = createdPayment.getLinks().iterator();
			while (links.hasNext()) {
				Links link = links.next();
				if (link.getRel().equalsIgnoreCase("approval_url")) {
					//req.setAttribute("redirectURL", link.getHref());
					LOGGER.info("Redirect URL"+link.getHref());
					return link.getHref();
				}
			} 
		} catch (PayPalRESTException e) {
			e.printStackTrace(); 
		}

		return null;
	}
	
	@RequestMapping(value = "/makePayment", method = RequestMethod.POST, headers = "Accept=application/json")
	public  @ResponseBody String  makePayment(@RequestBody Object data) {
		String paymentId=null;
		String token=null;
		String payerId=null;
		try{
		String theData[] = data.toString().replaceAll("\\{", "").replaceAll("\\}", "").split(",");
		for (String a:theData){
			String b[]=a.split("=");
			if(b[0].trim().equals("paymentId")){
				paymentId=b[1];
			}else if(b[0].trim().equals("token")){
				token=b[1];
			}else if(b[0].trim().equals("PayerID")){
				payerId=b[1];
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		APIContext apiContext = null;
		String accessToken = null;
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
		}
		
		try {
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			 e.printStackTrace();
		}

	 	try {
	 		
	 		/*String accessToken = "Bearer A101.7jG3RdU33VJYIORnl57CbhAR3xtXIdIolQ_tnViuYwJ7A79XrN4XPg14VFzBTMNM.itofwTu_b8ZMcJgd12d_DaTiDHa";
	 		APIContext apiContext = new APIContext(accessToken);
	 		apiContext.setConfigurationMap(sdkConfig);*/

	 		Payment payment = new Payment();
	 		payment.setId(paymentId);
	 		PaymentExecution paymentExecute = new PaymentExecution();
	 		paymentExecute.setPayerId(payerId);
	 		Payment resultPayment=payment.execute(apiContext, paymentExecute);
	 		System.out.println("------------------------------");
	 		System.out.println(resultPayment);
	 		System.out.println("------------------------------");
	 		
		} catch (PayPalRESTException e) {
			e.printStackTrace(); 
		}

		return null;
	}
}
