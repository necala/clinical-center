package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long>{
	public Medicament findById(Long id);
	public Medicament findByName(String name);
	public ArrayList<Medicament> findAll();
}
