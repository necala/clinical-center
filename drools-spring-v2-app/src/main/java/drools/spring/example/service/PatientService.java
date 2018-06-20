package drools.spring.example.service;

import java.util.ArrayList;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Patient;
import drools.spring.example.model.User;
import drools.spring.example.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	PatientRepository patientRepository;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	    

    public Patient addPatient(Patient patient){
    	return patientRepository.save(patient);
    }
    
    public Patient findOne(Long id){
    	return patientRepository.findById(id);
    }
    
    public Patient findByPatientId(String patientId){
    	return patientRepository.findByPatientId(patientId);
    }
    
    public ArrayList<Patient> findAll(){
    	return patientRepository.findAll();
    }
    
    public void deletePatient(Patient patient){
    	patientRepository.delete(patient);
    }
}

