package drools.spring.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.repository.DiagnoseMedicamentRepository;
import drools.spring.example.repository.DiagnoseRepository;
import drools.spring.example.repository.DiagnoseSymptomRepository;

import org.springframework.stereotype.Service;

@Service
public class DiagnoseMedicamentService {
	
	@Autowired
	DiagnoseMedicamentRepository diagnoseMedicamentRepository;
	
	
	private static Logger log = LoggerFactory.getLogger(DiagnoseMedicamentService.class);
	    

    public DiagnoseMedicament addDiagnoseMedicament(DiagnoseMedicament diagnoseMedicament){
    	return diagnoseMedicamentRepository.save(diagnoseMedicament);
    }
    
    public DiagnoseMedicament findOne(Long id){
    	return diagnoseMedicamentRepository.findOne(id);
    }
    
    public DiagnoseMedicament findByDiagnoseIdAndMedicamentId(Long diagnoseId, Long medicamentId){
    	return diagnoseMedicamentRepository.findByDiagnoseIdAndMedicamentId(diagnoseId, medicamentId);
    }
    
    public ArrayList<DiagnoseMedicament> findByDiagnoseId(Long diagnoseId){
    	return diagnoseMedicamentRepository.findByDiagnoseId(diagnoseId);
    }
    
    public ArrayList<DiagnoseMedicament> findByMedicamentId(Long medicamentId){
    	return diagnoseMedicamentRepository.findByMedicamentId(medicamentId);
    }
    
    public ArrayList<DiagnoseMedicament> findAll(){
    	return diagnoseMedicamentRepository.findAll();
    }
    
    public void delete(DiagnoseMedicament diagnoseSymptom){
    	diagnoseMedicamentRepository.delete(diagnoseSymptom);
    }

    public void deleteAll(ArrayList<DiagnoseMedicament> symptoms){
    	diagnoseMedicamentRepository.delete(symptoms);
    }

}
