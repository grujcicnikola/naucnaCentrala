package com.example.scientificCenter.service;

import java.security.Security;
import java.util.ArrayList;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.FormSubmissionDTO;

@Service
public class NewUserService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ScientificAreaService areaService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Start save data, user "+execution.getVariable("username").toString());
		
		com.example.scientificCenter.domain.User user = new com.example.scientificCenter.domain.User();
		user.setActivated(false);
		user.setCity(execution.getVariable("city").toString());
		user.setCountry(execution.getVariable("state").toString());
		user.setEmail(execution.getVariable("email").toString());
		user.setName(execution.getVariable("name").toString());
		user.setSurname(execution.getVariable("surname").toString());
		user.setUsername(execution.getVariable("username").toString());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(execution.getVariable("password").toString());
		user.setPassword(hashedPassword);
		if(execution.getVariable("title").toString() !=null) {
			user.setTitle(execution.getVariable("title").toString());
		}
		if(execution.getVariable("isRecenzent").toString().equals("true")) {
			user.setIsRecenzent(true);
		}else {
			user.setIsRecenzent(false);
		}
		@SuppressWarnings("unchecked")
		List<FormSubmissionDTO> fieldsDTO = (List<FormSubmissionDTO>)execution.getVariable("registration");
		List<ScientificArea> areas= new ArrayList<ScientificArea>();
		for(int i = 0; i < fieldsDTO.size(); i++) {
			if(fieldsDTO.get(i).getFieldId().equals("scientificAreas")){
				for(int j =0; j< fieldsDTO.get(i).getAreas().size(); j++) {
					//System.out.println(fieldsDTO.get(i).getAreas().get(j));
					areas.add(areaService.findById(Long.parseLong(fieldsDTO.get(i).getAreas().get(j))).get());
				}
			}
		}
		user.setAreas(areas);
		userService.save(user);
		//System.out.println()
		//sendConfirmationEmail(execution);
		
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

	public void sendConfirmationEmail(DelegateExecution execution)throws MailException, InterruptedException{
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(execution.getVariable("email").toString());
		mail.setFrom("petarperic23252@gmail.com");
		mail.setSubject("Scientific central - confirm registration");
		mail.setText("Hello,\n Please confirm your identiny at this link "+
		"http://localhost:4200/confirm/"+execution.getVariable("username").toString()+"/"+execution.getProcessInstanceId());
		
		javaMailSender.send(mail);
		System.out.println("Email poslat!");
	}
}
