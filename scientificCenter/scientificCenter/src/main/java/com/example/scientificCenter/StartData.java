package com.example.scientificCenter;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StartData {
	

	@Autowired
	private IdentityService identityService;

	

	@PostConstruct
	private void init() throws Exception {
		saveCamundaUser("petarperic23252@gmail.com", "petar", "pera++5++", "peric", "petarperic");
		saveCamundaUser("nikolagrujcic@gmail.com", "nikola", "nikola++5++", "grujcic", "nikolagrujcic");
		saveCamundaUser("dana@gmail.com", "danica", "dana++5++", "markovic", "danicamarkovic");
		saveCamundaUser("ivana@gmail.com", "ivana", "ivana++5++", "blagojevic", "ivanablagojevic");
		saveCamundaUser("igrujcic@gmail.com", "ivana", "ivana++5++", "grujcic", "ivanagrujcic");
		saveCamundaUser("mira@gmail.com", "mira", "mira++5++", "grujcic", "miragrujcic");
		saveCamundaUser("ljuba@gmail.com", "ljuba", "ljuba++5++", "grujcic", "ljubagrujcic");
		
		
	}
	
	public void saveCamundaUser(String email,String name,String password,String surname,String username){
		if(this.identityService.getUserInfoKeys(username)==null) {
			User newUser = identityService.newUser(username);
			newUser.setEmail(email);
			newUser.setFirstName(name);
			newUser.setLastName(surname);
			newUser.setPassword(password);
			identityService.saveUser(newUser);
			System.out.println(newUser.getId());
		}

	}

}