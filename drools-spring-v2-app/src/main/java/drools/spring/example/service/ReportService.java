package drools.spring.example.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.drools.core.ClassObjectFilter;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.Report;

@Service
public class ReportService {
	
	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	private DiagnoseMedicamentService diagnoseMedicamentService;
	
	
	@Autowired
	private PatientService patientService;
	

	public ArrayList<Report> getReport(HttpServletRequest request){
		
		KieSession kieSession = (KieSession) request.getSession().getAttribute("kieSession");
		
		addDiagnosesToSession(kieSession);
		
		kieSession.getAgenda().getAgendaGroup("reports").setFocus();
        
        kieSession.fireAllRules();
        
        Collection<Report> reportsSession = (Collection<Report>) kieSession.getObjects(new ClassObjectFilter(Report.class));
	
        Iterator<Report> iter = reportsSession.iterator();
        
        ArrayList<Report> new_report = new ArrayList<>();
        
        while (iter.hasNext()){
        	Report report = iter.next();
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
        
        
        
        if (!new_report.isEmpty()){
        	for (Report report: new_report){
        		report.setPatient(patientService.findOne(report.getPatient().getId()));
        	}
        }
		
        kieSession.getObjects();
		
		for( Object object: kieSession.getObjects() ){
			kieSession.delete( kieSession.getFactHandle( object ) );
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
