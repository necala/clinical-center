package drools.spring.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Illness;
import drools.spring.example.model.Ingridient;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.Patient;
import drools.spring.example.model.Symptom;
import drools.spring.example.model.User;
import drools.spring.example.model.events.HighPressureEvent;
import drools.spring.example.repository.MedicamentRepository;
import drools.spring.example.repository.UserRepository;

@Service
public class MedicamentService {

	@Autowired
	MedicamentRepository medicamentRepository;
	
	private static Logger log = LoggerFactory.getLogger(MedicamentService.class);
	
	
	private final KieContainer kieContainer;
	   
    @Autowired
    public MedicamentService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
	}    
    
   

	public void addMedicamentAllergy(Medicament medicament, String patientId) {
		
		Patient patient = new Patient();
		patient.setPatientId(patientId);
		patient.setAllergicMedicament(medicament);
		
		System.out.println("Pacijent: " + patientId + " sa alergijom na lijek: " + medicament.getName() + " u grupi: "
				+ medicament.getCategory());
		
		KieSession kieSession = kieContainer.newKieSession();
		
		kieSession.insert(patient);
		
		
	}
	
	public void addIngridientAllergy(Ingridient ingridient, String patientId) {
		
		Patient patient = new Patient();
		patient.setPatientId(patientId);
		patient.setAllergicIngridient(ingridient);
		
		System.out.println("Pacijent: " + patientId + " sa alergijom na sastojak: " + ingridient.getName() );
		
		KieSession kieSession = kieContainer.newKieSession();
		
		kieSession.insert(patient);
		
	}
    
    public Medicament save(Medicament medicament){
    	return medicamentRepository.save(medicament);
    }
    
    public Medicament getByName(String name){
    	return medicamentRepository.findByName(name);
    }
    
    public Medicament getOne(Long id){
    	return medicamentRepository.findById(id);
    }
    
    public ArrayList<Medicament> getAll(){
    	return medicamentRepository.findAll();
    }
    
    public void delete(Medicament medicament){
    	medicamentRepository.delete(medicament);
    }
	
}
