package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Illness;
import drools.spring.example.model.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
	public Symptom findById(Long id);
	public ArrayList<Symptom> findByTerm(Symptom.Term term);
	public ArrayList<Symptom> findByIllness(Illness illness);
	public ArrayList<Symptom> findAll();
}

