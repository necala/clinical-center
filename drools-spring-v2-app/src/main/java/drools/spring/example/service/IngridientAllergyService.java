package drools.spring.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.repository.IngridientAllergyRepository;


@Service
public class IngridientAllergyService {
	
	@Autowired
	IngridientAllergyRepository ingridientAllergyRepository;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	IngridientService ingridientService;
	
	private static Logger log = LoggerFactory.getLogger(IngridientAllergyService.class);
	    

    public IngridientAllergy addAllergy(IngridientAllergy ingridientAllergy){
    	return ingridientAllergyRepository.save(ingridientAllergy);
    }
    
    public IngridientAllergy findOne(Long id){
    	return ingridientAllergyRepository.findOne(id);
    }
    
    public IngridientAllergy findByPatientAndIngridient(Long patientId, Long ingridientId){
    	return ingridientAllergyRepository.findByPatientIdAndIngridientId(patientId, ingridientId);
    }
    
    public ArrayList<IngridientAllergy> findByPatient(Long patientId){
    	return ingridientAllergyRepository.findByPatientId(patientId);
    }
    
    public ArrayList<IngridientAllergy> findByIngridientId(Long ingridientId){
    	return ingridientAllergyRepository.findByIngridientId(ingridientId);
    }
    
    
    public ArrayList<IngridientAllergy> findAll(){
    	return ingridientAllergyRepository.findAll();
    }
    
    public void deleteAllergy(IngridientAllergy allergy){
    	
    	ingridientAllergyRepository.delete(allergy);
    }

    public void deleteAll(ArrayList<IngridientAllergy> allergy){
    	ingridientAllergyRepository.delete(allergy);
    }
}
