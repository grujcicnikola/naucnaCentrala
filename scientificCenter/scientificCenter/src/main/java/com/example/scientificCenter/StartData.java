package com.example.scientificCenter;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StartData {
	

	@Autowired
	private IdentityService identityService;

	private final static String RECENZENT_GROUP_ID = "recenzent";
	
	private final static String AUTHOR_GROUP_ID = "author";
	
	private final static String EDITOR_GROUP_ID = "editor";

	@PostConstruct
	private void init() throws Exception {
		saveCamundaUser("petarperic23252@gmail.com", "petar", "pera++5++", "peric", "petarperic");
		saveCamundaUser("nikolagrujcic@gmail.com", "nikola", "nikola++5++", "grujcic", "nikolagrujcic");
		saveCamundaUser("dana@gmail.com", "danica", "dana++5++", "markovic", "danicamarkovic");
		saveCamundaUser("ivana@gmail.com", "ivana", "ivana++5++", "blagojevic", "ivanablagojevic");
		saveCamundaUser("igrujcic@gmail.com", "ivana", "ivana++5++", "grujcic", "ivanagrujcic");
		saveCamundaUser("mira@gmail.com", "mira", "mira++5++", "grujcic", "miragrujcic");
		saveCamundaUser("ljuba@gmail.com", "ljuba", "ljuba++5++", "grujcic", "ljubagrujcic");
		
		Group authorGroup = identityService.newGroup(AUTHOR_GROUP_ID);
		authorGroup.setName("Author");
		authorGroup.setType("");
		Group group =  identityService.createGroupQuery().groupId(AUTHOR_GROUP_ID).singleResult();
		if(group != null) {
			identityService.saveGroup(authorGroup);
		} 
		
		Group rGroup = identityService.newGroup(RECENZENT_GROUP_ID);
		rGroup.setName("Author");
		rGroup.setType("");
		Group group1 =  identityService.createGroupQuery().groupId(RECENZENT_GROUP_ID).singleResult();
		if(group1 != null) {
			identityService.saveGroup(rGroup);
		}
		
		Group eGroup = identityService.newGroup(EDITOR_GROUP_ID);
		eGroup.setName("Author");
		eGroup.setType("");
		Group group2 =  identityService.createGroupQuery().groupId(EDITOR_GROUP_ID).singleResult();
		if(group2 != null) {
			identityService.saveGroup(eGroup);
		}
		
	}
	
	public void saveCamundaUser(String email,String name,String password,String surname,String username){
		if(this.identityService.getUserInfoKeys(email)==null) {
			User newUser = identityService.newUser(email);
			newUser.setEmail(email);
			newUser.setFirstName(name);
			newUser.setLastName(surname);
			newUser.setPassword(password);
			identityService.saveUser(newUser);
			System.out.println(newUser.getId());
		}

	}

}