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

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.Medicament;
import drools.spring.example.service.IngridientService;
import drools.spring.example.service.MedicamentService;

@RestController
@RequestMapping("/api")
public class MedicamentController {

	private static Logger log = LoggerFactory.getLogger(IllnessController.class);
	
	private final MedicamentService medicamentService;
	

	private final IngridientService ingridientService;
	
	@Autowired
    public MedicamentController(MedicamentService medicamentService,
    							IngridientService ingridientService) {
		this.medicamentService = medicamentService;
		this.ingridientService = ingridientService;
	}
	
	@RequestMapping(value = "/medicaments", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Medicament> savw(@RequestBody Medicament medicament){
		
		Medicament medicament2 = medicamentService.save(medicament);
		
		return new ResponseEntity<>(medicament2, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/medicaments/{id}/ingridients", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<Ingridient>> addIngridients(@RequestBody List<Ingridient> ingridients,
															@PathVariable Long id){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		
		for (Ingridient i: ingridients){
			i.setMedicament(medicament);
		}
		
		ingridients =  ingridientService.saveAll(ingridients);
		
		for (Ingridient i: ingridients){
			i.setMedicament(null);
		}
		
		return new ResponseEntity<>(ingridients, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}/ingridient", method = RequestMethod.POST, 
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<Ingridient> addIngridient(@RequestBody Ingridient ingridient,
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
	
	
	@RequestMapping(value = "/medicaments/{idM}/ingridient/{idI}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteIngridient(@PathVariable Long idM,
													@PathVariable Long idI){
		
		Medicament medicament = medicamentService.getOne(idM);
		
		if (medicament == null){
			return new ResponseEntity<>("Ingridient not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		Ingridient i = ingridientService.getOne(idI);
		
		if (i == null){
			return new ResponseEntity<>("Ingridient not deleted!", HttpStatus.BAD_REQUEST);
		}
		
		medicament.getIngridients().remove(i);
		
		medicamentService.save(medicament);
		
		ingridientService.delete(i);
		
		return new ResponseEntity<>("Ingridient deleted!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}/ingridients", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<List<Ingridient>> addIngridients(@PathVariable Long id){
		
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
		
		ingridientService.deleteAll(medicament.getIngridients());
		
		medicamentService.delete(medicament);
		
		return new ResponseEntity<>("Medicament deleted!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}/allergies/{patientId}", method = RequestMethod.GET)
	public ResponseEntity<String> addMedicamentAllergy(@PathVariable Long id, @PathVariable String patientId){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>("Medicament not found!", HttpStatus.BAD_REQUEST);
		}
		
		
		medicamentService.addMedicamentAllergy(medicament, patientId);
		
		return new ResponseEntity<>("Patient allergic to medicament!", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/medicaments/{id}/ingridients/{idI}/allergies/{patientId}", method = RequestMethod.GET)
	public ResponseEntity<String> addIngridientAllergy(@PathVariable Long id, 
													   @PathVariable Long idI,
													   @PathVariable String patientId){
		
		Medicament medicament = medicamentService.getOne(id);
		
		if (medicament == null){
			return new ResponseEntity<>("Medicament not found!", HttpStatus.BAD_REQUEST);
		}
		
		Ingridient ingridient = ingridientService.getOne(idI);
		
		if (ingridient == null){
			return new ResponseEntity<>("Ingridient not found!", HttpStatus.BAD_REQUEST);
		}
		
		medicamentService.addIngridientAllergy(ingridient, patientId);
		
		return new ResponseEntity<>("Patient allergic to ingridient!", HttpStatus.OK);
	}
	
}
