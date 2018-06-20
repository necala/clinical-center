package drools.spring.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.Illness;
import drools.spring.example.model.Ingridient;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.model.Record;
import drools.spring.example.model.Symptom;
import drools.spring.example.service.IllnessService;
import drools.spring.example.service.SymptomService;


@RestController
@RequestMapping("/api")
public class IllnessController {
	
	private static Logger log = LoggerFactory.getLogger(IllnessController.class);
	
	private final IllnessService illnessService;
	
	private final SymptomService symptomService;
	
	@Autowired
    public IllnessController(IllnessService illnessService,
    						 SymptomService symptomService) {
		this.illnessService = illnessService;
		this.symptomService = symptomService;
	}
	
	@RequestMapping(value = "/illness/all/{patientId}", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Illness>> group(@RequestBody Illness illness1,
													@PathVariable Long patientId){
		
		ArrayList<Illness> illness = illnessService.getIllnesses(illness1, patientId);
		
		return new ResponseEntity<>(illness, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/illness/one/{patientId}", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Illness>> getOne(@RequestBody Illness illness1,
										  @PathVariable Long patientId){
		
		ArrayList<Illness> illness = illnessService.getOneIllness(illness1, patientId);
		
		if (illness.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(illness, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/illness/symptoms", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Symptom>> getSymptoms(@RequestBody Illness illness){
		
		ArrayList<Symptom> symptoms = illnessService.getIllnessSymptoms(illness);
		
		if (symptoms.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(symptoms, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/illnesses", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Illness> addIllness(@RequestBody Illness illness1){
		
		Illness illness = illnessService.findById(illness1.getId());
		
		if (illness != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Illness i = illnessService.findByName(illness1.getName());
		
		if (i != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		illness = illnessService.addIllness(illness1);
		
		return new ResponseEntity<>(illness, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/illnesses", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<Illness>> findAll(){
		
		ArrayList<Illness> illnesses = illnessService.findAll();
		
		if (illnesses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for (Illness i: illnesses){
			i.getSymptoms().clear();
		}
		
		return new ResponseEntity<>(illnesses, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/illnesses/{id}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<Illness> findOne(@PathVariable Long id){
		
		Illness illness = illnessService.findById(id);
		
		if (illness == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		illness.getSymptoms().clear();
		
		return new ResponseEntity<>(illness, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/illnesses/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){
		
		Illness illness = illnessService.findById(id);
		
		if (illness == null){
			return new ResponseEntity<>("Illness not deleted!", HttpStatus.BAD_REQUEST);
		}
		
    	
    	List<Symptom> symptoms = symptomService.findByIllness(illness);
    	
    	if (!symptoms.isEmpty()){
    		for (Symptom s: symptoms){
    			symptomService.delete(symptomService.findById(s.getId()));
    		}
    	}
		
		illnessService.deleteIllness(illness);
		
		return new ResponseEntity<>("Illness deleted!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/illnesses", method = RequestMethod.PUT)
	public ResponseEntity<Illness> change(@RequestBody Illness illness1){
		
		Illness illness = illnessService.findById(illness1.getId());
		
		if (illness == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Illness i = illnessService.findByName(illness1.getName());
		
		if (i != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		illness.setCategory(illness1.getCategory());
		illness.setName(illness1.getName());
		illness = illnessService.addIllness(illness1);
		
		
    	List<Symptom> symptoms = symptomService.findByIllness(illness);
    	
    	if (!symptoms.isEmpty()){
    		for (Symptom s: symptoms){
    			s = symptomService.findById(s.getId());
    			s.setIllness(illness1);
    			s = symptomService.addSymptom(s);
    		}
    	}
		
    	illness.getSymptoms().clear();
		
		return new ResponseEntity<>(illness, HttpStatus.OK);
	}
	
	
}
