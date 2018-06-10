package drools.spring.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.User;
import drools.spring.example.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	
	private final KieContainer kieContainer;
	   
    @Autowired
    public UserService(KieContainer kieContainer) {
        //log.info("Initialising a new example session.");
        this.kieContainer = kieContainer;
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password){
    	User user = getByUsername(username);
    	if (user != null){
    		if (user.getPassword().equals(password)){
    			
    			KieSession kieSession = kieContainer.newKieSession();
    			kieSession.insert(user);
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public User register(User user){
    	return userRepository.save(user);
    }
    
    public User getByUsername(String username){
    	return userRepository.findByUsername(username);
    }
    

}
