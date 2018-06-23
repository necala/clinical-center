package drools.spring.example.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.model.Record;
import drools.spring.example.service.DiagnoseMedicamentService;
import drools.spring.example.service.DiagnoseService;
import drools.spring.example.service.DiagnoseSymptomService;
import drools.spring.example.service.IllnessService;

@RestController
@RequestMapping("/api")
public class DiagnoseController {

	private static Logger log = LoggerFactory.getLogger(DiagnoseController.class);
	
	private final IllnessService illnessService;
	
	private final DiagnoseMedicamentService diagnoseMedicamentService;
	
	private final DiagnoseService diagnoseService;
	
	private final DiagnoseSymptomService diagnoseSymptomService;
	
	@Autowired
    public DiagnoseController(IllnessService illnessService,
    						  DiagnoseMedicamentService diagnoseMedicamentService,
    						  DiagnoseService diagnoseService,
    						  DiagnoseSymptomService diagnoseSymptomService) {
		this.illnessService = illnessService;
		this.diagnoseMedicamentService = diagnoseMedicamentService;
		this.diagnoseService = diagnoseService;
		this.diagnoseSymptomService = diagnoseSymptomService;
	}
	
	@RequestMapping(value = "/diagnoses", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> diagnose(@RequestBody Record newRecord,
										HttpServletRequest request){
        
		
		String allergies = illnessService.setDiagnose(newRecord, request);
		
		if (allergies.equals("Patient allergic to: ")){
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(allergies, HttpStatus.BAD_REQUEST);
		
	}
	
	@RequestMapping(value = "/diagnoses", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<Diagnose>> getAll(){
        
		
		ArrayList<Diagnose> diagnoses = diagnoseService.findAll();
		
		if (diagnoses.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(diagnoses, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/diagnoses/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){
        
		
		Diagnose diagnose = diagnoseService.findOne(id);
		
		if (diagnose == null){
			return new ResponseEntity<>("Diagnose not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		diagnoseService.delete(diagnose);
		
		return new ResponseEntity<>("Diagose deleted!", HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/diagnoses/{id}/medicaments", method = RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<ArrayList<DiagnoseMedicament>> getMedicaments(@PathVariable Long id){
        
		
		Diagnose diagnose = diagnoseService.findOne(id);
		
		if (diagnose == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<DiagnoseMedicament> medciaments = diagnoseMedicamentService.findByDiagnoseId(id);
		
		if (medciaments.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		
		return new ResponseEntity<>(medciaments, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/diagnoses/{id}/symptoms", method = RequestMethod.GET,
			produces="application/json")
	public ResponseEntity<ArrayList<DiagnoseSymptom>> getSymptoms(@PathVariable Long id){
        
		
		Diagnose diagnose = diagnoseService.findOne(id);
		
		if (diagnose == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<DiagnoseSymptom> symtpoms = diagnoseSymptomService.findByDiagnoseId(id);
		
		if (symtpoms.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		
		return new ResponseEntity<>(symtpoms, HttpStatus.OK);
		
	}
}
