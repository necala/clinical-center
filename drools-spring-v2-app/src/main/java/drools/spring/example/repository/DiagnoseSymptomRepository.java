package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.model.MedicamentAllergy;

public interface DiagnoseSymptomRepository extends JpaRepository<DiagnoseSymptom, Long>{
	public DiagnoseSymptom findById(Long id);
	public DiagnoseSymptom findByDiagnoseIdAndSymptomTerm(Long diagnoseId, String term);
	public ArrayList<DiagnoseSymptom> findAll();
	public ArrayList<DiagnoseSymptom> findByDiagnoseId(Long diagnoseId);
	public ArrayList<DiagnoseSymptom> findBySymptomTerm(String term);
}
