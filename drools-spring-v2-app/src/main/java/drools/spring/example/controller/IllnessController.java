package drools.spring.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	
	@RequestMapping(value = "/illness/symptoms", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Symptom>> getSymptoms(@RequestBody Illness illness,
			  											HttpServletRequest request){
		
		illness = illnessService.findByName(illness.getName());
		
		if (illness == null ){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<Symptom> symptoms = illnessService.getIllnessSymptoms(illness, request);
		
		if (symptoms.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for (Symptom s: symptoms){
			s.setIllness(null);
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

	
	@RequestMapping(value = "/illness/one/{patientId}", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Illness>> getOneIllness(@RequestBody ArrayList<Symptom> symptoms,
										  @PathVariable Long patientId,
										  HttpServletRequest request){
		
		ArrayList<Illness> illnesses = illnessService.getOneIllness(symptoms, patientId, request);
		
		if (illnesses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for (Illness illness : illnesses){
			illness.getSymptoms().clear();
			illness.getSymptomsTerms().clear();
		}
		
		return new ResponseEntity<>(illnesses, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/illness/all/{patientId}", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Illness>> getAllIllness(@RequestBody ArrayList<Symptom> symptoms,
										  @PathVariable Long patientId,
										  HttpServletRequest request){
		
		ArrayList<Illness> illnesses = illnessService.getAllIllness(symptoms, patientId, request);
		
		if (illnesses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		for (Illness illness : illnesses){
			illness.getSymptoms().clear();
			illness.getSymptomsTerms().clear();
		}
		
		return new ResponseEntity<>(illnesses, HttpStatus.OK);
		
	}
	
}
