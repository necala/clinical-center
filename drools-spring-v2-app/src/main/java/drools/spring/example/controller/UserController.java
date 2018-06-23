package drools.spring.example.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.service.DiagnoseMedicamentService;
import drools.spring.example.service.DiagnoseService;
import drools.spring.example.service.DiagnoseSymptomService;
import drools.spring.example.service.UserService;
import drools.spring.example.model.Diagnose;
import drools.spring.example.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	
	private final DiagnoseService diagnoseService;
	
	@Autowired
    public UserController(UserService userService,
    		DiagnoseService diagnoseService) {
        this.userService = userService;
        this.diagnoseService = diagnoseService;
    }
	
	@RequestMapping(value = "/users", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> register(@RequestBody User user){
		
		if (userService.getByUsername(user.getUsername()) != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		user.setCategory(User.Category.DOCTOR);
		User u1 = userService.save(user);
		
		return new ResponseEntity<>(u1, HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<User>> getDoctors(){
		
		ArrayList<User> doctors = userService.findAllDoctors();
		
		if (doctors.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(doctors, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/users", method = RequestMethod.PUT, 
			consumes = "application/json",produces = "application/json")
	public ResponseEntity<User> getDoctor(@RequestBody User user){
		
		User doctor = userService.getById(user.getId());
		
		if (doctor == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (userService.getByUsername(user.getUsername())!= null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		doctor.setFirstName(user.getFirstName());
		doctor.setLastName(user.getLastName());
		doctor.setUsername(user.getUsername());
		doctor.setEmail(user.getEmail());
		
		doctor = userService.save(doctor);
		
		return new ResponseEntity<>(doctor, HttpStatus.OK);

	}
	

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){
		
		User doctor = userService.getById(id);
		
		if (doctor == null){
			return new ResponseEntity<>("Doctor not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<Diagnose> doctor_diagnoses = diagnoseService.findByDoctorId(id);
		
		if (!doctor_diagnoses.isEmpty()){
			for (Diagnose diagnose: doctor_diagnoses){
				diagnoseService.delete(diagnoseService.findOne(diagnose.getId()));
			}
		}
		
		userService.delete(doctor);
		
		return new ResponseEntity<>("Doctor deleted!", HttpStatus.OK);

	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<User> getDoctor(@PathVariable Long id){
		
		User doctor = userService.getById(id);
		
		if (doctor == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(doctor, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<User> login(@RequestBody User user, HttpServletRequest request){
		
		Boolean response = userService.login(user.getUsername(), user.getPassword(), request);
		
		if (!response){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else{
			User u = userService.getByUsername(user.getUsername());
			u.setPassword("");
			return new ResponseEntity<>(u, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<String> logout(HttpServletRequest request){
		
		try{
			request.getSession().invalidate();
			return new ResponseEntity<>("User logged out!",HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>("Could not logout!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
