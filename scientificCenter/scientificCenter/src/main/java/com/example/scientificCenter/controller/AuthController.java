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

import org.camunda.bpm.engine.IdentityService;
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
@CrossOrigin(origins = "https://localhost:4202")
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
	
	@Autowired
	IdentityService identityService;
	

	
	@PostMapping(value = "/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm login) throws IOException{
		
		Optional<User> user = userServ.getByEmail(login.getEmail());
		
		if(user.get().isActivated())
		{
			try {
				
				Authentication authentication = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		login.getEmail(),
		                        login.getPassword()
		                )
		        );
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtProvider.generateJwtToken(authentication);
		        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		        System.out.println("Stigao token : "+jwt);
		        System.out.println("Username: " + userDetails.getUsername());
		        System.out.println("Password: " + userDetails.getPassword());
		        Optional<User> thisUser= this.userServ.getByEmail(login.getEmail());
		        if(user.isPresent()) {
		        	
		        }
		        identityService.setAuthenticatedUserId(userDetails.getUsername());
		        return new ResponseEntity<>(new JwtResponse(jwt), HttpStatus.OK);
		        //return new ResponseEntity<>(new JwtResponse(jwt,thisUser.get().getRoles()), HttpStatus.OK);
		        
			}catch(AuthenticationException e) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			
		}else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
   }
	
	@RequestMapping(value = "/logout/{id}/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> logout(@PathVariable("id") String email) {
Optional<User> user = userServ.getByEmail(email);
		return new ResponseEntity<>(new UserDTO(user.get()), HttpStatus.OK);
	}
	
}
