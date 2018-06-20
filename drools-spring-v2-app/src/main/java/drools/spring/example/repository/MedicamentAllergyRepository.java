package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.MedicamentAllergy;

public interface MedicamentAllergyRepository extends JpaRepository<MedicamentAllergy, Long>{
	public MedicamentAllergy findById(Long id);
	public MedicamentAllergy findByPatientIdAndMedicamentId(Long patientId, Long medicamentId);
	public ArrayList<MedicamentAllergy> findAll();
	public ArrayList<MedicamentAllergy> findByPatientId(Long patientId);
	public ArrayList<MedicamentAllergy> findByMedicamentId(Long medicamentId);
}
