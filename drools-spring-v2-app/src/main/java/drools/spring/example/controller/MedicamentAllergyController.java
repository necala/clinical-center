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

import drools.spring.example.model.Medicament;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.service.MedicamentAllergyService;
import drools.spring.example.service.MedicamentService;
import drools.spring.example.service.PatientService;

@RestController
@RequestMapping("/api")
public class MedicamentAllergyController {
	
	private static Logger log = LoggerFactory.getLogger(MedicamentAllergyController.class);
	
	private final MedicamentAllergyService medicamentAllergyService;
	
	private final MedicamentService medicamentService;
	
	private final PatientService patientService;
	
	@Autowired
    public MedicamentAllergyController(MedicamentAllergyService medicamentAllergyService,
    								   MedicamentService medicamentService,
    								   PatientService patientService) {
		this.medicamentAllergyService = medicamentAllergyService;
		this.medicamentService = medicamentService;
		this.patientService = patientService;
	}
	
	@RequestMapping(value = "/allergies/medicaments", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<MedicamentAllergy> addMedicamentAllergy(@RequestBody MedicamentAllergy ma){
	
		
		Medicament m = medicamentService.getOne(ma.getMedicamentId());
		
		if (m == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		Patient p = patientService.findOne(ma.getPatientId());
		
		if (p == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		MedicamentAllergy al = medicamentAllergyService.findByPatientIdAndMedicamentId(p.getId(), m.getId());
		
		if (al != null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		ma.setMedicamentName(m.getName());
		
		al = medicamentAllergyService.addAllergy(ma);
		
		return new ResponseEntity<>(al, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allergies/medicaments", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<MedicamentAllergy>> all(){
		
		ArrayList<MedicamentAllergy> al = medicamentAllergyService.findAll();
		
		if (al.isEmpty()){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(al, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allergies/medicaments/{id}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<MedicamentAllergy> one(@PathVariable Long id){
		
		MedicamentAllergy al = medicamentAllergyService.findOne(id);
		
		if (al == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}

		
		
		return new ResponseEntity<>(al, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allergies/medicaments/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){
		
		MedicamentAllergy al = medicamentAllergyService.findOne(id);
		
		if (al == null){
			return new ResponseEntity<>("Medicament allergy not deleted!", HttpStatus.BAD_REQUEST);
		}

		medicamentAllergyService.deleteAllergy(al);
		
		return new ResponseEntity<>("Medicament allergy deleted!", HttpStatus.OK);
	}

}
