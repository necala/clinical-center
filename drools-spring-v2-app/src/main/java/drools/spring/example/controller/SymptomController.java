package drools.spring.example.controller;

import java.util.ArrayList;
import java.util.List;

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

import drools.spring.example.model.Illness;
import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.Symptom;
import drools.spring.example.service.IllnessService;
import drools.spring.example.service.IngridientAllergyService;
import drools.spring.example.service.IngridientService;
import drools.spring.example.service.MedicamentService;
import drools.spring.example.service.PatientService;
import drools.spring.example.service.SymptomService;

@RestController
@RequestMapping("/api")
public class SymptomController {
	
private static Logger log = LoggerFactory.getLogger(SymptomController.class);
	
	private final SymptomService symptomService;
	
	private final IllnessService illnessService;
	
	@Autowired
    public SymptomController(SymptomService symptomService,
    						 IllnessService illnessService) {
		this.symptomService = symptomService;
		this.illnessService = illnessService;
	}
	
	@RequestMapping(value = "/illnesses/{id}/symptoms", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Symptom> addSymptomToIllness(@RequestBody Symptom symptom,
															@PathVariable Long id){
		
		Illness illness = illnessService.findById(id);
		
		if (illness == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		if (!illness.getSymptoms().isEmpty()){
			for (Symptom s: illness.getSymptoms()){
				if (s.getTerm().equals(symptom.getTerm())){
					return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
				}
			}
		}
		
		symptom.setIllness(illness);
		
		if (symptom.getTerm().toString().equals("TEMPERATURE")){
			if (symptom.getTemperature() >=  38 && symptom.getTemperature() < 40){
				symptom.setTerm(Symptom.Term.TEMP_OVER_38);
			}
			if (symptom.getTemperature() >=  40 && symptom.getTemperature() <= 41){
				symptom.setTerm(Symptom.Term.TEMP_BETWEEN_40_AND_41);
			}
		}
		
		symptom =  symptomService.addSymptom(symptom);
		
		symptom.setIllness(null);
		
		return new ResponseEntity<>(symptom, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/illnesses/{idI}/symptoms/{idS}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteSymptomFromIllness(@PathVariable Long idI,
													@PathVariable Long idS){
		
		Illness illness = illnessService.findById(idI);
		
		if (illness == null){
			return new ResponseEntity<>("Symptom not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		Symptom s = symptomService.findById(idS);
		
		if (s == null){
			return new ResponseEntity<>("Symptom not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		illness.getSymptoms().remove(s);
		
		illnessService.addIllness(illness);
		
		symptomService.delete(s);
		
		return new ResponseEntity<>("Symptom deleted!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/illnesses/{id}/symptoms", method = RequestMethod.PUT)
	public ResponseEntity<Symptom> change(@PathVariable Long id, @RequestBody Symptom symptom1){
		
		Illness illness = illnessService.findById(id);
		
		if (illness == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Symptom symptom = symptomService.findById(symptom1.getId());
		
		if (symptom == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		symptom.setHelper(symptom1.getHelper());
		symptom.setTerm(symptom1.getTerm());
		
		if (symptom.getTerm().toString().equals("TEMPERATURE")){
			if (symptom.getTemperature() >=  38 && symptom.getTemperature() < 40){
				symptom.setTerm(Symptom.Term.TEMP_OVER_38);
			}
			if (symptom.getTemperature() >=  40 && symptom.getTemperature() <= 41){
				symptom.setTerm(Symptom.Term.TEMP_BETWEEN_40_AND_41);
			}
		}
		
		symptom.setSpecific(symptom1.getSpecific());
		symptom.setTemperature(symptom1.getTemperature());
		
		symptom = symptomService.addSymptom(symptom);
		
    	if (!illness.getSymptoms().isEmpty()){
    		for (int i=0; i< illness.getSymptoms().size(); i++){
    			if (illness.getSymptoms().get(i).getId() == symptom1.getId()){
    				illness.getSymptoms().get(i).setTerm(symptom1.getTerm());
    				illness.getSymptoms().get(i).setHelper(symptom1.getHelper());
    				illness.getSymptoms().get(i).setSpecific(symptom1.getSpecific());
    				illness.getSymptoms().get(i).setTemperature(symptom1.getTemperature());
    				illnessService.addIllness(illness);
    				break;
    			}
    		}
    	}
		
    	symptom.setIllness(null);
		
		return new ResponseEntity<>(symptom, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/illnesses/{idI}/symptoms", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<List<Symptom>> getMedicamentIngridients(@PathVariable Long idI){
		
		Illness illness = illnessService.findById(idI);
		
		if (illness == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		List<Symptom> symptoms =  symptomService.findByIllness(illness);
		
		for (Symptom s: symptoms){
			s.setIllness(null);
		}
		
		return new ResponseEntity<>(symptoms, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/illnesses/{idI}/symptoms/{idS}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<Symptom> getOneSymptom(@PathVariable Long idI, @PathVariable Long idS){
		
		Illness illness = illnessService.findById(idI);
		
		if (illness == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		Symptom symptom = symptomService.findById(idS);
		
		if (symptom == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		symptom.setIllness(null);
		
		return new ResponseEntity<>(symptom, HttpStatus.OK);
	}
	

}
