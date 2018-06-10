package drools.spring.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.Medicament;

public interface IngredientRepository extends JpaRepository<Ingridient, Long>{
	public Ingridient findById(Long id);
	public Ingridient findByName(String name);
	public ArrayList<Ingridient> findAll();
	public List<Ingridient> findByMedicament(Medicament medicament); 
}
