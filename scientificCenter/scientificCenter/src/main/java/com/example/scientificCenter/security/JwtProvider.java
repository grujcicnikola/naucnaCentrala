package com.example.scientificCenter.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	 	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	 	
	 	@Autowired
	 	private HttpServletRequest request;

	 	@Value("${com.example.app.jwtSecret}")
	    private String jwtSecret;

	    @Value("${com.example.app.jwtExpirationInMs}")
	    private int jwtExpiration;

	    public String generateJwtToken(Authentication authentication) {

	        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

	        return Jwts.builder()
			                .setSubject((userPrincipal.getUsername()))
			                .setIssuedAt(new Date())
			                //.setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
			                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
			                .signWith(SignatureAlgorithm.HS512, jwtSecret)
			                .compact();
	    }
	    
	    public boolean validateJwtToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
	            return true;
	        } catch (SignatureException e) {
	            logger.error("Invalid JWT signature -> Message: {} ", e);
	        } catch (MalformedJwtException e) {
	            logger.error("Invalid JWT token -> Message: {}", e);
	        } catch (ExpiredJwtException e) {
	            logger.error("Expired JWT token -> Message: {}", e);
	        } catch (UnsupportedJwtException e) {
	            logger.error("Unsupported JWT token -> Message: {}", e);
	        } catch (IllegalArgumentException e) {
	            logger.error("JWT claims string is empty -> Message: {}", e);
	        }
	        
	        return false;
	    }
	    
	    public String getEmailLoggedUser() {
			
			String header = request.getHeader("Authorization");
			
			if(header != null)
			{
				String parts[] = header.split(" "); 
				
				String email = this.getUserNameFromJwtToken(parts[1]);  
				
				return email;  
			}else
			{
				return null;
			}
		}
	    
	    public String getUserNameFromJwtToken(String token) {
	        return Jwts.parser()
				                .setSigningKey(jwtSecret)
				                .parseClaimsJws(token)
				                .getBody().getSubject();
	    }

		public String getUsernameLoggedUser() {
			 String header = request.getHeader("Authorization");
			 //System.out.println("Header AUTH "+ header);
			 if (header!=null) {
			     String parts[]=header.split(" ");
			     String username = this.getUserNameFromJwtToken(parts[1]);
			     return username;
			 }else {
			     return null;
			 }
    
		}
		
		
}
