package drools.spring.example.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.drools.core.ClassObjectFilter;
import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.MonitoringIssue;
import drools.spring.example.model.User;
import drools.spring.example.model.events.OxygenLevelEvent;
import drools.spring.example.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	
	private final KieContainer kieContainer;
	
    @Autowired
    public UserService(KieContainer kieContainer) {
        log.info("Initialising a new user session.");
        this.kieContainer = kieContainer;
    }

    public boolean login(String username, String password, HttpServletRequest request){
		
    	User user = getByUsername(username);
    	if (user != null){
    		if (user.getPassword().equals(password)){
    			request.getSession().setAttribute("currentUser", user);
    			
    			KieServices ks = KieServices.Factory.get();
    			KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
    			kbconf.setOption(EventProcessingOption.STREAM);
    			KieBase kbase = kieContainer.newKieBase(kbconf);

    			KieSession kieSession = kbase.newKieSession();
    			
    			request.getSession().setAttribute("kieSession", kieSession);
    			return true;
    		}
    	}
    	return false;
    }
    
    
    public User save(User user){
    	return userRepository.save(user);
    }
    
    public User getByUsername(String username){
    	return userRepository.findByUsername(username);
    }
    
    public User getById(Long id){
    	return userRepository.findById(id);
    }
    
    public void delete(User user){
    	userRepository.delete(user);
    }

    public ArrayList<User> findAllDoctors(){
    	return userRepository.findByCategory(User.Category.DOCTOR);
    }
    
}
