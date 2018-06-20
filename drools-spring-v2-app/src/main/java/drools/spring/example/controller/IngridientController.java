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
import drools.spring.example.model.Illness;
import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.service.IngridientAllergyService;
import drools.spring.example.service.IngridientService;
import drools.spring.example.service.MedicamentAllergyService;
import drools.spring.example.service.MedicamentService;
import drools.spring.example.service.PatientService;

@RestController
@RequestMapping("/api")
public class IngridientController {
	
	private static Logger log = LoggerFactory.getLogger(MedicamentController.class);
	
	private final MedicamentService medicamentService;
	
	private final IngridientService ingridientService;
	
	private final IngridientAllergyService ingridientAllergyService;
	
	private final PatientService patientSerive;
	
	
	@Autowired
    public IngridientController(MedicamentService medicamentService,
    							IngridientService ingridientService,
    							IngridientAllergyService ingridientAllergyService,
    							PatientService patientService) {
		this.medicamentService = medicamentService;
		this.ingridientService = ingridientService;
		this.ingridientAllergyService = ingridientAllergyService;
		this.patientSerive = patientService;
	}

	@RequestMapping(value = "/medicaments/{id}/ingridients", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Ingridient> addIngridientsToMedicament(@RequestBody Ingridient ingridient,
															@PathVariable Long id){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		ingridient.setMedicament(medicament);
		
		ingridient =  ingridientService.save(ingridient);
		
		ingridient.setMedicament(null);
		
		return new ResponseEntity<>(ingridient, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{idM}/ingridients/{idI}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteIngridientFromMedicament(@PathVariable Long idM,
													@PathVariable Long idI){
		
		Medicament medicament = medicamentService.getOne(idM);
		
		if (medicament == null){
			return new ResponseEntity<>("Ingridient not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		Ingridient i = ingridientService.getOne(idI);
		
		if (i == null){
			return new ResponseEntity<>("Ingridient not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		ArrayList<IngridientAllergy> ingrAll = ingridientAllergyService.findByIngridientId(i.getId());
    	
    	if (!ingrAll.isEmpty()){
    		ingridientAllergyService.deleteAll(ingrAll);
    		
    	}
		
		medicament.getIngridients().remove(i);
		
		medicamentService.save(medicament);
		
		ingridientService.delete(i);
		
		return new ResponseEntity<>("Ingridient deleted!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}/ingridients", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<List<Ingridient>> getMedicamentIngridients(@PathVariable Long id){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		List<Ingridient> ingridients =  ingridientService.getByMedicament(medicament);
		
		for (Ingridient i: ingridients){
			i.setMedicament(null);
		}
		
		return new ResponseEntity<>(ingridients, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}/ingridients/{idI}", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<Ingridient> getOneIngridients(@PathVariable Long id, @PathVariable Long idI){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		Ingridient ingridient = ingridientService.getOne(idI);
		
		if (ingridient == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		ingridient.setMedicament(null);
		
		return new ResponseEntity<>(ingridient, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/medicaments/{id}/ingridients", method = RequestMethod.PUT)
	public ResponseEntity<Ingridient> change(@PathVariable Long id, @RequestBody Ingridient ingridient1){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Ingridient ingridient = ingridientService.getOne(ingridient1.getId());
		
		if (ingridient == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		ingridient.setName(ingridient1.getName());
		ingridient = ingridientService.save(ingridient);
		
		List<IngridientAllergy> ingrAll = ingridientAllergyService.findByIngridientId(ingridient1.getId());
		
		if (!ingrAll.isEmpty()){
			for (IngridientAllergy ia: ingrAll){
				ia = ingridientAllergyService.findOne(ia.getId());
				ia.setIngridientName(ingridient1.getName());
				ingridientAllergyService.addAllergy(ia);
			}
		}
		
		
    	
    	if (!medicament.getIngridients().isEmpty()){
    		for (int i=0; i< medicament.getIngridients().size(); i++){
    			if (medicament.getIngridients().get(i).getId() == ingridient1.getId()){
    				medicament.getIngridients().get(i).setName(ingridient1.getName());
    				medicamentService.save(medicament);
    				break;
    			}
    		}
    	}
		
    	ingridient.setMedicament(null);
		
		return new ResponseEntity<>(ingridient, HttpStatus.OK);
	}

}

