package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
	public Patient findById(Long id);
	public Patient findByPatientId(String patientId);
	public ArrayList<Patient> findAll();
}
