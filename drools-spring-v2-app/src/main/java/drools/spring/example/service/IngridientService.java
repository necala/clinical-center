package drools.spring.example.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.Medicament;
import drools.spring.example.repository.IngredientRepository;

@Service
public class IngridientService {
	
	@Autowired
	IngredientRepository ingredientRepository;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	    
    
    public Ingridient save(Ingridient ingridient){
    	return ingredientRepository.save(ingridient);
    }
    
    public Ingridient getByName(String name){
    	return ingredientRepository.findByName(name);
    }
    
    public Ingridient getOne(Long id){
    	return ingredientRepository.findById(id);
    }
    
    public ArrayList<Ingridient> getAll(){
    	return ingredientRepository.findAll();
    }
    
    public List<Ingridient> getByMedicament(Medicament medicament){
    	return ingredientRepository.findByMedicament(medicament);
    }
    
    
    public void delete(Ingridient ingridient){
    	ingredientRepository.delete(ingridient);
    }

    public List<Ingridient> saveAll(List<Ingridient> ingridients){
    	return ingredientRepository.save(ingridients);
    }
    
    public void deleteAll(List<Ingridient> ingridients){
    	ingredientRepository.delete(ingridients);
    }
}
