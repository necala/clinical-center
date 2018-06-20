package drools.spring.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Ingridient;
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.Patient;

public interface IngridientAllergyRepository extends JpaRepository<IngridientAllergy, Long>{
	public IngridientAllergy findOne(Long id);
	public ArrayList<IngridientAllergy> findAll();
	public ArrayList<IngridientAllergy> findByPatientId(Long patientId);
	public ArrayList<IngridientAllergy> findByIngridientId(Long ingridientId);
	public IngridientAllergy findByPatientIdAndIngridientId(Long patientId, Long ingridientId);
}
