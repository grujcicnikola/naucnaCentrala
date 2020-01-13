package com.example.scientificCenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.dto.UserDTO;
import com.example.scientificCenter.security.JwtProvider;
import com.example.scientificCenter.security.JwtResponse;
import com.example.scientificCenter.security.LoginForm;
import com.example.scientificCenter.service.UserRoleService;
import com.example.scientificCenter.service.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {


	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired 
	private UserService userServ;

	@Autowired
	private UserRoleService roleServ;
	
	
	@Autowired
    private PasswordEncoder encoder;
	
	@Autowired
    private JwtProvider jwtProvider;
	

	
	@PostMapping(value = "/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm login) throws IOException{
		
		Optional<User> user = userServ.getByEmail(login.getEmail());
		
		if(user.get().isActivated())
		{
			boolean success = false;
			try {
				//System.out.println("Stigao "+ login.getEmail());
				//System.out.println("Stigla sifra: " + login.getPassword());
				System.out.println("pre authentication");
				Authentication authentication = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		login.getEmail(),//ovde me je strah da stavim encode
		                        login.getPassword()
		                )
		        );
				System.out.println("pre SecurityContextHolder");
				//postavljanje autentifikacije u context
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				System.out.println("pre jwt");
				String jwt = jwtProvider.generateJwtToken(authentication);
		        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		        System.out.println("Stigao token : "+jwt);
		        System.out.println("Username: " + userDetails.getUsername());
		        System.out.println("Password: " + userDetails.getPassword());
		        
		       // return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(),userDetails.getAuthorities()));
		        
		       /* 	if (LoggerController.getInstance().logInfo() == true) {
		        		logger.info(" 1 1 " + userServ.getByEmail(login.getEmail()).get().getId() + " 3 0");
		        	}else {
		        		System.out.println("Handling log exeption");
		        	}
		        	
		       */
		        return new ResponseEntity<>(new JwtResponse(jwt), HttpStatus.OK);
		        
			}catch(AuthenticationException e) {
				return new ResponseEntity<>("bla bla bla", HttpStatus.NOT_ACCEPTABLE);
			}
			
		}else
		{
			//Nije potvrdio registraciju email-om pa ne zelimo ni da pokusava sa autentifikacijom 
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
   }
	
	@RequestMapping(value = "/logout/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> logout(@PathVariable("id") String email) {

		System.out.println("Pogodio logout get po mailu");
		System.out.println("Stigao mejl: " + email);
		Optional<User> user = userServ.getByEmail(email);
		return new ResponseEntity<>(new UserDTO(user.get()), HttpStatus.OK);
	}
	
}
