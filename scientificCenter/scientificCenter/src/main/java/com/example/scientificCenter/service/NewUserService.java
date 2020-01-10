package com.example.scientificCenter.service;

import java.security.Security;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.scientificCenter.dto.FormSubmissionDTO;

@Service
public class NewUserService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Start save data, user "+execution.getVariable("username").toString());
		User newUser = identityService.newUser(execution.getVariable("username").toString());
		newUser.setFirstName(execution.getVariable("name").toString());
		newUser.setLastName(execution.getVariable("surname").toString());
		newUser.setEmail(execution.getVariable("email").toString());
		newUser.setPassword(execution.getVariable("password").toString());
		identityService.saveUser(newUser);
		
		sendConfirmationEmail(execution.getVariable("email").toString());
       // Get a Properties object
        /*Properties props = System.getProperties();
        props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
        final String username = "petarperic23252@gmail.com";
        final String password = "Nidza++5++";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
	        // -- Create a new message --
	        Message msg = new MimeMessage(session);
	
	        // -- Set the FROM and TO fields --
	        System.out.println("++++ "+execution.getVariable("email").toString());
	        msg.setFrom(new InternetAddress(username));
	        msg.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(execution.getVariable("email").toString(), false));
	        msg.setSubject("Scientific centre");
	        msg.setText("Poštovani,!" +
                    "\n\n ");
	        Transport.send(msg);
	
	        System.out.println("Message sent.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }*/

	}

	public void sendConfirmationEmail(String email)throws MailException, InterruptedException{
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("petarperic23252@gmail.com");
		mail.setSubject("MegaTravel - potvrda registracije");
		mail.setText("Zdravo  ,\n Hvala vam sto koristite nas sajt.");

				
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
}
