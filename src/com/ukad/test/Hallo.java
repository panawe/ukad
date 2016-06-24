package com.ukad.test;

import com.ukad.util.SimpleMail;

public class Hallo {

	public static void main(String[] args){
		
		sendMail();
	}
	
	public static void sendMail(){
		String mail = "<blockquote><h2><b>Cher Membre</b></h2><h2>Vous avez demander a recevoir votre mot de passe sur cet e-mail </h2>"
				+ "<h2>Le Voici: <strong>Test</strong></h2>"
				+ "<h2>Encore une fois, merci de votre interet en notre association.</h2><h2><b>Le President.</b></h2></blockquote>";
		try {
			SimpleMail.sendMail("Votre Mot de passe A.G.W.E", mail, "agwe@agwedc.com", "panawe@gmail.com",
					"smtp.office365.com", "agwe@agwedc.com", "agwedc123!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
