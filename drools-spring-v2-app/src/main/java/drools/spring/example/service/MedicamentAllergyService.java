package drools.spring.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.repository.IngridientAllergyRepository;
import drools.spring.example.repository.MedicamentAllergyRepository;


@Service
public class MedicamentAllergyService {
	
	@Autowired
	MedicamentAllergyRepository medicamentAllergyRepository;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	MedicamentService medicamentService;
	
	@Autowired
	IngridientService ingridientService;
	    
	@Autowired
	IngridientAllergyService ingridientAllergyService;
	
    public MedicamentAllergy addAllergy(MedicamentAllergy medicamentAllergy){
    	return medicamentAllergyRepository.save(medicamentAllergy);
    }
    
    public MedicamentAllergy findOne(Long id){
    	return medicamentAllergyRepository.findById(id);
    }
    
    public MedicamentAllergy findByPatientIdAndMedicamentId(Long patientId, Long medicamentId){
    	return medicamentAllergyRepository.findByPatientIdAndMedicamentId(patientId, medicamentId);
    }
    
    public ArrayList<MedicamentAllergy> findByPatientId(Long patientId){
    	return medicamentAllergyRepository.findByPatientId(patientId);
    }
    
    public ArrayList<MedicamentAllergy> findByMedicamentId(Long medicamentId){
    	return medicamentAllergyRepository.findByMedicamentId(medicamentId);
    }
    
    
    public ArrayList<MedicamentAllergy> findAll(){
    	return medicamentAllergyRepository.findAll();
    }
    
    public void deleteAllergy(MedicamentAllergy allergy){
	
    	medicamentAllergyRepository.delete(allergy);
    }
    
    public void deleteAll(ArrayList<MedicamentAllergy> allergy){
    	medicamentAllergyRepository.delete(allergy);
    }

}
