package drools.spring.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.service.UserService;
import drools.spring.example.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	
	@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> register(@RequestBody User user){
		
		if (userService.getByUsername(user.getUsername()) != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		user.setCategory(User.Category.DOCTOR);
		user.setLoggedIn(false);
		User u1 = userService.register(user);
		
		return new ResponseEntity<>(u1, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<User> login(@RequestBody User user){
		
		Boolean response = userService.login(user.getUsername(), user.getPassword());
		
		if (!response){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else{
			User u = userService.getByUsername(user.getUsername());
			u.setPassword("");
			return new ResponseEntity<>(u, HttpStatus.OK);
		}
		
	}
}
