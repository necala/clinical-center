package drools.spring.example.controller;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import drools.spring.example.model.Illness;
import drools.spring.example.model.Record;
import drools.spring.example.model.Symptom;
import drools.spring.example.service.IllnessService;


@RestController
@RequestMapping("/api")
public class IllnessController {
	
	private static Logger log = LoggerFactory.getLogger(IllnessController.class);
	
	private final IllnessService illnessService;
	
	@Autowired
    public IllnessController(IllnessService illnessService) {
		this.illnessService = illnessService;
	}
	
	@RequestMapping(value = "/illness/all", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<ArrayList<Illness>> group(@RequestBody Illness illness1){
		
		ArrayList<Illness> illness = illnessService.getIllnesses(illness1);
		
		return new ResponseEntity<>(illness, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/illness/one", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Illness> getOne(@RequestBody Illness illness1){
		
		Illness illness = illnessService.getOneIllness(illness1);
		
		if (illness == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(illness, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/illness/diagnose", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Record> diagnose(@RequestBody Record newRecord){
        
		System.out.println("USla u kontroler za dijagnozu");
		newRecord.setDate(new Date());
		Record record = illnessService.diagnose(newRecord);
		
		if (record == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(record, HttpStatus.OK);
		
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
	

}
