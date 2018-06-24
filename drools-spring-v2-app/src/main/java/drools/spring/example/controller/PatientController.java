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

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.model.User;
import drools.spring.example.service.DiagnoseMedicamentService;
import drools.spring.example.service.DiagnoseService;
import drools.spring.example.service.DiagnoseSymptomService;
import drools.spring.example.service.IngridientAllergyService;
import drools.spring.example.service.MedicamentAllergyService;
import drools.spring.example.service.PatientService;

@RestController
@RequestMapping("/api")
public class PatientController {
	
private static Logger log = LoggerFactory.getLogger(PatientController.class);
	
	private final PatientService patientService;
	
	private final IngridientAllergyService ingridientAllergyService;
	
	private final MedicamentAllergyService medicamentAllergyService;
	
	private final DiagnoseService diagnoseService;
	
	@Autowired
    public PatientController(PatientService patientService,
    						 IngridientAllergyService ingridientAllergyService,
    						 MedicamentAllergyService medicamentAllergyService,
    						 DiagnoseService diagnoseService) {
        this.patientService = patientService;
        this.ingridientAllergyService = ingridientAllergyService;
        this.medicamentAllergyService = medicamentAllergyService;
        this.diagnoseService = diagnoseService;
    }

	@RequestMapping(value = "/patients", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient patient){
		
		if (patientService.findByPatientId(patient.getPatientId()) != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		patient = patientService.addPatient(patient);
		
		return new ResponseEntity<>(patient, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/patients", method = RequestMethod.PUT, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Patient> editPatient(@RequestBody Patient patient){
		
		if (patientService.findByPatientId(patient.getPatientId()) == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Patient p = patientService.findOne(patient.getId());
		p.setFirstName(patient.getFirstName());
		p.setLastName(patient.getLastName());
		p.setPatientId(patient.getPatientId());
		
		patient = patientService.addPatient(p);
		
		ArrayList<Diagnose> diagnoses = diagnoseService.findByPatientId(p.getId());
		for (Diagnose d: diagnoses){
			d.setPatientName(p.getFirstName() + " " + p.getLastName());
			d =diagnoseService.addDiagnose(d);
		}
		
		return new ResponseEntity<>(patient, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/patients", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<Patient>> getAll(){
		
		ArrayList<Patient> patients = patientService.findAll();
		
		if (patients.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(patients, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<Patient> getOne(@PathVariable Long id){
		
		Patient patient = patientService.findOne(id);
		
		if (patient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(patient, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/patients/{id}/allergies/ingridients", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<List<IngridientAllergy>> getIngridientAllergies(@PathVariable Long id){
		
		Patient patient = patientService.findOne(id);
		
		if (patient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<IngridientAllergy> allergies = ingridientAllergyService.findByPatient(id);
		
		if (allergies.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(allergies, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/patients/{id}/allergies/medicaments", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<List<MedicamentAllergy>> getMedicamentAllergies(@PathVariable Long id){
		
		Patient patient = patientService.findOne(id);
		
		if (patient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<MedicamentAllergy> allergies = medicamentAllergyService.findByPatientId(id);
		
		if (allergies.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(allergies, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/patients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteOne(@PathVariable Long id){
		
		Patient patient = patientService.findOne(id);
		
		if (patient == null){
			return new ResponseEntity<>("Patient not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<MedicamentAllergy> medAll = medicamentAllergyService.findByPatientId(id);
		
		if (!medAll.isEmpty()){
			medicamentAllergyService.deleteAll(medAll);
		}
		
		ArrayList<IngridientAllergy> ingrAll = ingridientAllergyService.findByPatient(id);
		
		if (!ingrAll.isEmpty()){
			ingridientAllergyService.deleteAll(ingrAll);
		}
		
		ArrayList<Diagnose> diagnoses = diagnoseService.findByPatientId(id);
		
		if (!diagnoses.isEmpty()){
			for (Diagnose d: diagnoses){
				diagnoseService.delete(diagnoseService.findOne(d.getId()));
			}
		}
		
		patientService.deletePatient(patient);
		
		return new ResponseEntity<>("Patient deleted!", HttpStatus.OK);
	}
}
