package drools.spring.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Illness;
import drools.spring.example.model.Symptom;
import drools.spring.example.repository.SymptomRepository;

@Service
public class SymptomService {
	
	@Autowired
	SymptomRepository symptomRepository;
	
	public Symptom addSymptom(Symptom symptom){
    	return symptomRepository.save(symptom);
    }
    
    public void delete(Symptom symptom){
    	symptomRepository.delete(symptom);
    }
    
    public Symptom findById(Long id){
    	return symptomRepository.findById(id);
    }
    
    public ArrayList<Symptom> findByTerm(Symptom.Term term){
    	return symptomRepository.findByTerm(term);
    }
    
    public ArrayList<Symptom> findByIllness(Illness illness){
    	return symptomRepository.findByIllness(illness);
    }
    
    public ArrayList<Symptom> findByIllnessSorted(Illness illness){
    	return symptomRepository.findAllByIllnessOrderBySpecificsDesc(illness);
    }
    
    
    
    public ArrayList<Symptom> findAll(){
    	return symptomRepository.findAll();
    }

}
