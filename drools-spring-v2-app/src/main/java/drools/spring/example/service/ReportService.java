package drools.spring.example.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.model.Illness;
import drools.spring.example.model.Report;
import drools.spring.example.model.Symptom;

@Service
public class ReportService {
	
	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	private DiagnoseMedicamentService diagnoseMedicamentService;
	
	@Autowired
	private DiagnoseSymptomService diagnoseSymptomService;
	
	@Autowired
	private PatientService patientService;
	
	private final KieContainer kieContainer;
	   
    @Autowired
    public ReportService(KieContainer kieContainer) {
    	
        this.kieContainer = kieContainer;
	}

	public ArrayList<Report> getReport(){
		
		ArrayList<Report> reports = new ArrayList<>();
		
		KieSession kieSession = this.kieContainer.newKieSession();
		
		addDiagnosesToSession(kieSession);
		
		kieSession.setGlobal("reports", reports);
		
		kieSession.getAgenda().getAgendaGroup("reports").setFocus();
        
        kieSession.fireAllRules();
        
        reports = (ArrayList<Report>) kieSession.getGlobal("reports");
        
        ArrayList<Report> new_report = new ArrayList<>();
        
        if (!reports.isEmpty()){
        	for (Report report:reports){
        		
        		boolean flag = true;
        		if (!new_report.isEmpty()){
        			for (Report r2: new_report){
            			if (r2.getCategory().equals(report.getCategory()) && r2.getPatient().getId() == report.getPatient().getId()
            					&& r2.getHelper().equals(report.getHelper())){
            				flag = false;
            				break;
            			}
            		}
        		}
    			if (flag){
    				new_report.add(report);
    			}
        		
        	}
        }
        
        
        if (!new_report.isEmpty()){
        	for (Report report: new_report){
        		report.setPatient(patientService.findOne(report.getPatient().getId()));
        	}
        }
		
        
		return new_report;
	}
	
	
	public void addDiagnosesToSession(KieSession kieSession){
		ArrayList<Diagnose> diagnoses = diagnoseService.findAll();
        System.out.println("Dosla sam do funkcije za ubacivanje u sesiju");
        if (!diagnoses.isEmpty()){
        	for (Diagnose d: diagnoses){
        		kieSession.insert(d);
        		System.out.println("Ubacujem u sesiju dijagnozu: " + d.getIllnessName());
        		ArrayList<DiagnoseMedicament> diagnoseMedicaments = diagnoseMedicamentService.findByDiagnoseId(d.getId());
        		if (!diagnoseMedicaments.isEmpty()){
        			for (DiagnoseMedicament dm: diagnoseMedicaments){
        				if (dm.getMedicamentCategory() != null){
        					kieSession.insert(dm);
        					System.out.println("Ubacujem u sesiju dijagnozu lijek: " + dm.getMedicamentName());
        				}
        			}
        		}
        		
        	}
        }
    }
}
