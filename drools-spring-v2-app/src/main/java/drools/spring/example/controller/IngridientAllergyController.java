package drools.spring.example.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.service.IngridientAllergyService;
import drools.spring.example.service.IngridientService;
import drools.spring.example.service.PatientService;

@RestController
@RequestMapping("/api")
public class IngridientAllergyController {

	private static Logger log = LoggerFactory.getLogger(IngridientAllergyController.class);
	
	private final IngridientAllergyService ingridientAllergyService;
	
	private final IngridientService ingridientService;
	
	private final PatientService patientService;
	
	@Autowired
    public IngridientAllergyController(IngridientAllergyService ingridientAllergyService,
    							PatientService patientService,
    							IngridientService ingridientService) {
		this.ingridientAllergyService = ingridientAllergyService;
		this.patientService = patientService;
		this.ingridientService = ingridientService;
	}
	
	@RequestMapping(value = "/allergies/ingridients", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<IngridientAllergy> addIngridient(@RequestBody IngridientAllergy ia){
		
		
		Ingridient i = ingridientService.getOne(ia.getIngridientId());
		
		if (i == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		Patient p = patientService.findOne(ia.getPatientId());
		
		if (p == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		IngridientAllergy al = ingridientAllergyService.findByPatientAndIngridient(p.getId(), i.getId());
		
		if (al != null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		ia.setIngridientName(i.getName());
		
		al =  ingridientAllergyService.addAllergy(ia);
		
		return new ResponseEntity<>(al, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allergies/ingridients", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<IngridientAllergy>> all(){
		
		ArrayList<IngridientAllergy> al = ingridientAllergyService.findAll();
		
		if (al.isEmpty()){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(al, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allergies/ingridients/{id}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<IngridientAllergy> one(@PathVariable Long id){
		
		IngridientAllergy al = ingridientAllergyService.findOne(id);
		
		if (al == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(al, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allergies/ingridients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){
		
		IngridientAllergy al = ingridientAllergyService.findOne(id);
		
		if (al == null){
			return new ResponseEntity<>("Ingridient allergy not deleted!", HttpStatus.BAD_REQUEST);
		}

		ingridientAllergyService.deleteAllergy(al);
		
		return new ResponseEntity<>("Ingridient allergy deleted!", HttpStatus.OK);
	}
	
}
