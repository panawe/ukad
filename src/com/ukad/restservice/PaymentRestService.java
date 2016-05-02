// #Create Payment Using PayPal Sample
// This sample code demonstrates how you can process a 
// PayPal Account based Payment.
// API used: /v1/payments/payment
package com.ukad.restservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ukad.model.Mail;
import com.ukad.model.PaymentHistory;
import com.ukad.model.PaymentType;
import com.ukad.model.Transaction;
import com.ukad.security.model.User;
import com.ukad.service.EventService;
import com.ukad.service.PaymentService;
import com.ukad.util.GenerateAccessToken;
import com.ukad.util.ResultPrinter;
import com.ukad.util.SimpleMail;
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
	@Autowired
	PaymentService paymentService;
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(PaymentRestService.class);
	Map<String, String> map = new HashMap<String, String>();

	public void init() {
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

	public static void main(String args[]) {
		PaymentRestService pp = new PaymentRestService();
		try {
			pp.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/createPayment", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String createPayment(@RequestBody Payment payment) throws PayPalRESTException {
		Payment createdPayment = null;
		APIContext apiContext = null;
		String accessToken = null;
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
			throw e;
		}

		try {
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			e.printStackTrace();
			throw e;
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
					// req.setAttribute("redirectURL", link.getHref());
					LOGGER.info("Redirect URL" + link.getHref());
					return link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/submitFee", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String submitFee(@RequestBody Payment payment) throws PayPalRESTException {
		Payment createdPayment = null;
		APIContext apiContext = null;
		String accessToken = null;
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
			throw e;
		}

		try {
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			e.printStackTrace();
			throw e;
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
			redirectUrls.setReturnUrl("http://localhost:8080/ukadtogo/#/pages/fees");
			payment.setRedirectUrls(redirectUrls);

			createdPayment = payment.create(apiContext);
			LOGGER.info("Created payment with id = " + createdPayment.getId() + " and status = "
					+ createdPayment.getState());
			// ###Payment Approval Url
			Iterator<Links> links = createdPayment.getLinks().iterator();
			while (links.hasNext()) {
				Links link = links.next();
				if (link.getRel().equalsIgnoreCase("approval_url")) {
					// req.setAttribute("redirectURL", link.getHref());
					LOGGER.info("Redirect URL" + link.getHref());
					return link.getHref();
				}
			}
		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	@RequestMapping(value = "/makePayment", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody PaymentHistory makePayment(@RequestBody Object data) throws PayPalRESTException {
		String paymentId = null;
		String token = null;
		String payerId = null;
		Long userId = null;
		try {
			String theData[] = data.toString().replaceAll("\\{", "").replaceAll("\\}", "").split(",");
			for (String a : theData) {
				String b[] = a.split("=");
				if (b[0].trim().equals("paymentId")) {
					paymentId = b[1];
				} else if (b[0].trim().equals("token")) {
					token = b[1];
				} else if (b[0].trim().equals("PayerID")) {
					payerId = b[1];
				} else if (b[0].trim().equals("userId")) {
					userId = b[1] == null ? null : new Long(b[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		APIContext apiContext = null;
		String accessToken = null;
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
			throw e;
		}

		try {
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			e.printStackTrace();
			throw e;
		}
		PaymentHistory ph = null;
		try {

			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerId);
			Payment resultPayment = payment.execute(apiContext, paymentExecute);
			ph = new PaymentHistory(resultPayment);
			if (userId != null && userId > 0) {
				User user = (User) paymentService.getById(User.class, userId);
				ph.setUser(user);
			}
			// 3 == Don.
			PaymentType pType = (PaymentType) paymentService.getById(PaymentType.class, 3L);
			ph.setPaymentType(pType);
			ph.setPaymentId(paymentId);
			paymentService.save(ph);
			Transaction trans = new Transaction(ph);
			paymentService.save(trans);
			LOGGER.debug(trans);
			LOGGER.debug(ph);
			LOGGER.debug(resultPayment);
			try {
				// send e-mail
				String mail = 				"<div class=\"row\">                                                 "+
						"		<div class=\"col-xs-12\">                                                        "+
						"			<i class=\"fa fa-thumbs-up fa-5x text-green-300\"> Merci pour                "+
						"				votre don!</i>                                                           "+
						"			<hr />                                                                       "+
						"			<div class=\"invoice-title\">                                                "+
						"				<h2>Recu</h2>                                                            "+
						"				<h3 class=\"pull-right\">Don #"+ ph.getPaymentId()+ "</h3>                    "+
						"				<h3 class=\"pull-right\"><strong>Montant $"+ ph.getAmount()+ "</strong></h3>                    "+
						"			</div>                                                                       "+
						"			<hr>                                                                         "+
						"			<div class=\"row\">                                                          "+
						"				<div class=\"col-xs-6\">                                                 "+
						"					<address>                                                            "+
						"						<strong>Donateur:</strong><br>"+ ph.getFirstName()+" "+ph.getLastName()+" <br>"+
						ph.getBaLine1()+"<br>"+ph.getBaLine2()+"<br>"+ph.getBaCity()+", "+ph.getBaState()+" "+ph.getBaPostalCode()
						+"<br>"+ph.getBaCountryCode() +
						"					</address>                                                           "+
						"				</div> <br>                                                                  "+
						"				<div class=\"col-xs-6 text-right\">                                      "+
						"					<address>                                                            "+
						"						<strong>En faveur de:</strong><br> Association des               "+
						"						Gabonais de Washington, D.C et ses Environs (A.G.W.E). <br>      "+
						"						1234 Main<br> Washington DC, DC 54321                            "+
						"					</address>                                                           "+
						"				</div>                                                                   "+
						"			</div>    <br>                                                                   "+
						"			<div class=\"row\">                                                          "+
						"				<div class=\"col-xs-6\">                                                 "+
						"					<address>                                                            "+
						"						<strong>Mode de payment:</strong><br>                            "+
						"						"+ph.getPaymentMethod()+"<br>"+ph.getEmail()+
						"					</address>                                                           "+
						"				</div>  <br>                                                                 "+
						"				<div class=\"col-xs-6 text-right\">                                      "+
						"					<address>                                                            "+
						"						<strong>Date de don:</strong><br> "+ph.getCreateDate()+"<br>     "+
						"						<br>                                                             "+
						"					</address>                                                           "+
						"				</div>                                                                   "+
						"			</div>                                                                       "+
						"		</div>                                                                           "+
						"	</div>                                                                               ";

				
				SimpleMail.sendMail("Merci pour votre Don de $"+ph.getAmount()+" a A.G.W.E", mail,
						"agwedc@gmail.com", ph.getEmail(), "smtp.gmail.com", "agwedc@gmail.com",
						"agwedc123");

			} catch (Exception e) {
				LOGGER.error(e);
			}

		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw e;
		}

		return ph;
	}
	
	
	
	@RequestMapping(value = "/payFee", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody PaymentHistory payFee(@RequestBody Object data) throws PayPalRESTException {
		String paymentId = null;
		String token = null;
		String payerId = null;
		Long userId = null;
		try {
			String theData[] = data.toString().replaceAll("\\{", "").replaceAll("\\}", "").split(",");
			for (String a : theData) {
				String b[] = a.split("=");
				if (b[0].trim().equals("paymentId")) {
					paymentId = b[1];
				} else if (b[0].trim().equals("token")) {
					token = b[1];
				} else if (b[0].trim().equals("PayerID")) {
					payerId = b[1];
				} else if (b[0].trim().equals("userId")) {
					userId = b[1] == null ? null : new Long(b[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		APIContext apiContext = null;
		String accessToken = null;
		InputStream is = PaymentRestService.class.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
			throw e;
		}

		try {
			accessToken = GenerateAccessToken.getAccessToken();
			apiContext = new APIContext(accessToken);
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			e.printStackTrace();
			throw e;
		}
		PaymentHistory ph = null;
		try {

			Payment payment = new Payment();
			payment.setId(paymentId);
			PaymentExecution paymentExecute = new PaymentExecution();
			paymentExecute.setPayerId(payerId);
			Payment resultPayment = payment.execute(apiContext, paymentExecute);
			ph = new PaymentHistory(resultPayment);
			if (userId != null && userId > 0) {
				User user = (User) paymentService.getById(User.class, userId);
				Calendar date = new GregorianCalendar();
				// reset hour, minutes, seconds and millis
				date.set(Calendar.HOUR_OF_DAY, 0);
				date.set(Calendar.MINUTE, 0);
				date.set(Calendar.SECOND, 0);
				date.set(Calendar.MILLISECOND, 0);
				// next day
				date.add(Calendar.YEAR, 1);
				user.setMembershipRenewDate(date.getTime());
				user.setStatus((short) 1);
				paymentService.save(user);
				ph.setUser(user);
			}
			// 3 == Frais Annuels.
			PaymentType pType = (PaymentType) paymentService.getById(PaymentType.class, 2L);
			ph.setPaymentType(pType);
			ph.setPaymentId(paymentId);
			paymentService.save(ph);
			Transaction trans = new Transaction(ph);
			paymentService.save(trans);
			LOGGER.debug(trans);
			LOGGER.debug(ph);
			LOGGER.debug(resultPayment);
			try {
				// send e-mail
				String mail = 				"<div class=\"row\">                                                 "+
						"		<div class=\"col-xs-12\">                                                        "+
						"			<i class=\"fa fa-thumbs-up fa-5x text-green-300\"> Merci d'avoir paye vos    "+
						"				frais de membre annuel.</i>                                              "+
						"			<hr />                                                                       "+
						"			<div class=\"invoice-title\">                                                "+
						"				<h2>Recu</h2>                                                            "+
						"				<h3 class=\"pull-right\">Payement #"+ ph.getPaymentId()+ "</h3>          "+
						"				<h3 class=\"pull-right\"><strong>Montant $"+ ph.getAmount()+ "</strong></h3>"+
						"			</div>                                                                       "+
						"			<hr>                                                                         "+
						"			<div class=\"row\">                                                          "+
						"				<div class=\"col-xs-6\">                                                 "+
						"					<address>                                                            "+
						"						<strong>Payeur:</strong><br>"+ ph.getFirstName()+" "+ph.getLastName()+" <br>"+
						ph.getBaLine1()+"<br>"+ph.getBaLine2()+"<br>"+ph.getBaCity()+", "+ph.getBaState()+" "+ph.getBaPostalCode()
						+"<br>"+ph.getBaCountryCode() +
						"					</address>                                                           "+
						"				</div> <br>                                                              "+
						"				<div class=\"col-xs-6 text-right\">                                      "+
						"					<address>                                                            "+
						"						<strong>En faveur de:</strong><br> Association des               "+
						"						Gabonais de Washington, D.C et ses Environs (A.G.W.E). <br>      "+
						"						1234 Main<br> Washington DC, DC 54321                            "+
						"					</address>                                                           "+
						"				</div>                                                                   "+
						"			</div>    <br>                                                               "+
						"			<div class=\"row\">                                                          "+
						"				<div class=\"col-xs-6\">                                                 "+
						"					<address>                                                            "+
						"						<strong>Mode de payment:</strong><br>                            "+
						"						"+ph.getPaymentMethod()+"<br>"+ph.getEmail()+
						"					</address>                                                           "+
						"				</div>  <br>                                                             "+
						"				<div class=\"col-xs-6 text-right\">                                      "+
						"					<address>                                                            "+
						"						<strong>Date de payement:</strong><br> "+ph.getCreateDate()+"<br>"+
						"						<br>                                                             "+
						"					</address>                                                           "+
						"				</div>                                                                   "+
						"			</div>                                                                       "+
						"		</div>                                                                           "+
						"	</div>                                                                               ";

				
				SimpleMail.sendMail("Merci d'avoir paye vos frais de membre annuel de "+ph.getAmount()+" a A.G.W.E", mail,
						"agwedc@gmail.com", ph.getEmail(), "smtp.gmail.com", "agwedc@gmail.com",
						"agwedc123");

			} catch (Exception e) {
				LOGGER.error(e);
			}

		} catch (PayPalRESTException e) {
			e.printStackTrace();
			throw e;
		}

		return ph;
	}

}
