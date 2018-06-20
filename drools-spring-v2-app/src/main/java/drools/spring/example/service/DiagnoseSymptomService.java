package drools.spring.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.repository.DiagnoseRepository;
import drools.spring.example.repository.DiagnoseSymptomRepository;

import org.springframework.stereotype.Service;

@Service
public class DiagnoseSymptomService {
	
	@Autowired
	DiagnoseSymptomRepository diagnoseSymptomRepository;
	
	
	private static Logger log = LoggerFactory.getLogger(DiagnoseSymptomService.class);
	    

    public DiagnoseSymptom addDiagnoseSymtpom(DiagnoseSymptom diagnoseSymptom){
    	return diagnoseSymptomRepository.save(diagnoseSymptom);
    }
    
    public DiagnoseSymptom findOne(Long id){
    	return diagnoseSymptomRepository.findOne(id);
    }
    
    public DiagnoseSymptom findByDiagnoseIdAndSymptomTerm(Long diagnoseId, String symptomTerm){
    	return diagnoseSymptomRepository.findByDiagnoseIdAndSymptomTerm(diagnoseId, symptomTerm);
    }
    
    public ArrayList<DiagnoseSymptom> findByDiagnoseId(Long diagnoseId){
    	return diagnoseSymptomRepository.findByDiagnoseId(diagnoseId);
    }
    
    public ArrayList<DiagnoseSymptom> findBySymptomTerm(String term){
    	return diagnoseSymptomRepository.findBySymptomTerm(term);
    }
    
    public ArrayList<DiagnoseSymptom> findAll(){
    	return diagnoseSymptomRepository.findAll();
    }
    
    public void delete(DiagnoseSymptom diagnoseSymptom){
    	diagnoseSymptomRepository.delete(diagnoseSymptom);
    }

    public void deleteAll(ArrayList<DiagnoseSymptom> symptoms){
    	diagnoseSymptomRepository.delete(symptoms);
    }

}
