package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.MedicamentAllergy;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long>{
	public Diagnose findById(Long id);
	public Diagnose findByPatientIdAndDoctorIdAndIllnessName(Long patientId, Long doctorId, String illnessName);
	public ArrayList<Diagnose> findAll();
	public ArrayList<Diagnose> findByPatientId(Long patientId);
	public ArrayList<Diagnose> findByDoctorId(Long doctorId);
	public ArrayList<Diagnose> findByIllnessName(String illnessName);
}
