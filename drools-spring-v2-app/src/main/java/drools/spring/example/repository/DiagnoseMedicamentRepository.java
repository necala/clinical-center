package drools.spring.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.model.DiagnoseMedicament;

public interface DiagnoseMedicamentRepository extends JpaRepository<DiagnoseMedicament, Long>{
	public DiagnoseMedicament findById(Long id);
	public DiagnoseMedicament findByDiagnoseIdAndMedicamentId(Long diagnoseId, Long medicamentId);
	public ArrayList<DiagnoseMedicament> findAll();
	public ArrayList<DiagnoseMedicament> findByDiagnoseId(Long diagnoseId);
	public ArrayList<DiagnoseMedicament> findByMedicamentId(Long medicamentId);
}
