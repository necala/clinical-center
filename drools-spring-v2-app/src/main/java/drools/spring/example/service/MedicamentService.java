package drools.spring.example.service;

import java.util.ArrayList;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Medicament;
import drools.spring.example.repository.MedicamentRepository;

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
