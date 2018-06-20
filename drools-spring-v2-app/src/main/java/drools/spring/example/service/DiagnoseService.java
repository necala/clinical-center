package drools.spring.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.repository.DiagnoseRepository;

import org.springframework.stereotype.Service;

@Service
public class DiagnoseService {
	
	@Autowired
	DiagnoseRepository diagnoseRepository;
	
	@Autowired
	DiagnoseMedicamentService diagnoseMedicamentService;
	
	@Autowired
	DiagnoseSymptomService diagnoseSymptomService;
	
	private static Logger log = LoggerFactory.getLogger(DiagnoseService.class);
	    

    public Diagnose addDiagnose(Diagnose diagnose){
    	return diagnoseRepository.save(diagnose);
    }
    
    public Diagnose findOne(Long id){
    	return diagnoseRepository.findOne(id);
    }
    
    public Diagnose findByPatientAndDoctorAndIllness(Long patientId, Long doctorId, String illness){
    	return diagnoseRepository.findByPatientIdAndDoctorIdAndIllnessName(patientId, doctorId, illness);
    }
    
    public ArrayList<Diagnose> findByPatientId(Long patientId){
    	return diagnoseRepository.findByPatientId(patientId);
    }
    
    public ArrayList<Diagnose> findByDoctorId(Long doctorId){
    	return diagnoseRepository.findByDoctorId(doctorId);
    }
    
    public ArrayList<Diagnose> findByIllnessName(String illnessName){
    	return diagnoseRepository.findByIllnessName(illnessName);
    }
    
    public ArrayList<Diagnose> findAll(){
    	return diagnoseRepository.findAll();
    }
    
    public void delete(Diagnose diagnose){
    	
    	ArrayList<DiagnoseSymptom> symtpoms = diagnoseSymptomService.findByDiagnoseId(diagnose.getId());
		
		if (!symtpoms.isEmpty()){
			diagnoseSymptomService.deleteAll(symtpoms);
		}
		
		ArrayList<DiagnoseMedicament> medciaments = diagnoseMedicamentService.findByDiagnoseId(diagnose.getId());
		
		if (!medciaments.isEmpty()){
			diagnoseMedicamentService.deleteAll(medciaments);
		}
    	
    	diagnoseRepository.delete(diagnose);
    }

    public void deleteAll(List<Diagnose> diagnoses){
    	diagnoseRepository.delete(diagnoses);
    }

}
