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

import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.service.DiagnoseMedicamentService;
import drools.spring.example.service.IngridientAllergyService;
import drools.spring.example.service.IngridientService;
import drools.spring.example.service.MedicamentAllergyService;
import drools.spring.example.service.MedicamentService;
import drools.spring.example.service.PatientService;

@RestController
@RequestMapping("/api")
public class MedicamentController {

	private static Logger log = LoggerFactory.getLogger(MedicamentController.class);
	
	private final MedicamentService medicamentService;
	
	private final IngridientService ingridientService;
	
	private final IngridientAllergyService ingridientAllergyService;
	
	private final PatientService patientSerive;
	
	private final DiagnoseMedicamentService diagnoseMedicamentService;
	
	private final MedicamentAllergyService medicamentAllergyService;
	
	@Autowired
    public MedicamentController(MedicamentService medicamentService,
    							IngridientService ingridientService,
    							IngridientAllergyService ingridientAllergyService,
    							PatientService patientService,
    							MedicamentAllergyService medicamentAllergyService,
    							DiagnoseMedicamentService diagnoseMedicamentService) {
		this.medicamentService = medicamentService;
		this.ingridientService = ingridientService;
		this.ingridientAllergyService = ingridientAllergyService;
		this.patientSerive = patientService;
		this.medicamentAllergyService = medicamentAllergyService;
		this.diagnoseMedicamentService = diagnoseMedicamentService;
	}
	
	@RequestMapping(value = "/medicaments", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Medicament> save(@RequestBody Medicament medicament){
		
		Medicament m = medicamentService.getByName(medicament.getName());
		
		if (m != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Medicament medicament2 = medicamentService.save(medicament);
		
		return new ResponseEntity<>(medicament2, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "/medicaments", method = RequestMethod.GET,
			produces = "application/json")
	public ResponseEntity<ArrayList<Medicament>> getAll(){
		
		ArrayList<Medicament> medicaments = medicamentService.getAll();
		
		if (medicaments.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		for (Medicament m: medicaments){
			if (!m.getIngridients().isEmpty()){	
				for (Ingridient i: m.getIngridients()){
					i.setMedicament(null);
				}
			}
		}
		
		
		return new ResponseEntity<>(medicaments, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}", method = RequestMethod.GET)
	public ResponseEntity<Medicament> getOne(@PathVariable Long id){
		
		Medicament medicament2 = medicamentService.getOne(id);
		
		if (!medicament2.getIngridients().isEmpty()){
			for (Ingridient i: medicament2.getIngridients()){
				i.setMedicament(null);
			}
			
		}
		
		return new ResponseEntity<>(medicament2, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>("Medicament not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		List<MedicamentAllergy> medAll = medicamentAllergyService.findByMedicamentId(medicament.getId());
		
		if (!medAll.isEmpty()){
			medicamentAllergyService.deleteAll(medicamentAllergyService.findByMedicamentId(medicament.getId()));
		}
    	
    	List<Ingridient> ingridients = ingridientService.getByMedicament(medicament);
    	
    	if (!ingridients.isEmpty()){
    		for (Ingridient i: ingridients){
    			ingridientAllergyService.deleteAll(ingridientAllergyService.findByIngridientId(i.getId()));
    		}
    	}
		
		medicamentService.delete(medicament);
		
		return new ResponseEntity<>("Medicament deleted!", HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/medicaments", method = RequestMethod.PUT)
	public ResponseEntity<Medicament> change(@RequestBody Medicament medicament1){
		
		Medicament medicament = medicamentService.getOne(medicament1.getId());
		
		if (medicament == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Medicament m = medicamentService.getByName(medicament1.getName());
		
		if (m != null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		medicament.setCategory(medicament1.getCategory());
		medicament.setName(medicament1.getName());
		medicament = medicamentService.save(medicament);
		
		List<MedicamentAllergy> medAll = medicamentAllergyService.findByMedicamentId(medicament.getId());
		
		if (!medAll.isEmpty()){
			for (MedicamentAllergy ma: medAll){
				ma = medicamentAllergyService.findOne(ma.getId());
				ma.setMedicamentName(medicament1.getName());
				medicamentAllergyService.addAllergy(ma);
			}
		}
		
		List<DiagnoseMedicament> diagMed = diagnoseMedicamentService.findByMedicamentId(medicament.getId());
		
		if (!diagMed.isEmpty()){
			for (DiagnoseMedicament dm: diagMed){
				dm = diagnoseMedicamentService.findOne(dm.getId());
				dm.setMedicamentCategory(medicament.getCategory().toString());
				dm.setMedicamentName(medicament.getName());
				diagnoseMedicamentService.addDiagnoseMedicament(dm);
			}
		}
    	
    	List<Ingridient> ingridients = ingridientService.getByMedicament(medicament);
    	
    	if (!ingridients.isEmpty()){
    		for (Ingridient i: ingridients){
    			i = ingridientService.getOne(i.getId());
    			i.setMedicament(medicament1);
    			i = ingridientService.save(i);
    		}
    	}
		
    	medicament.getIngridients().clear();
		
		return new ResponseEntity<>(medicament, HttpStatus.OK);
	}
	
}
