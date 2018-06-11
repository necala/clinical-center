package drools.spring.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import drools.spring.example.model.Illness;
import drools.spring.example.model.Ingridient;
import drools.spring.example.model.Patient;
import drools.spring.example.model.Record;
import drools.spring.example.model.Symptom;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.events.ColdOrFeverEvent;
import drools.spring.example.model.events.HighPressureEvent;
import drools.spring.example.model.events.IllnessWithHighTempEvent;
import drools.spring.example.model.events.PatientUsesAntibioticsEvent;

@Service
public class IllnessService {
	
	private static Logger log = LoggerFactory.getLogger(IllnessService.class);

    private final KieContainer kieContainer;
   
    @Autowired
    public IllnessService(KieContainer kieContainer) {
    	log.info("Initialising a new example session.");
        this.kieContainer = kieContainer;
	}
    
    @Autowired
    MedicamentService medicamentService;
    
    @Autowired
    IngridientService ingridientService;
    
    public ArrayList<Illness> getIllnesses(Illness illness1) {
    	ArrayList<Symptom> symptoms = (ArrayList<Symptom>) illness1.getSymptoms();
    	symptoms = handleSymptoms(symptoms);
    	ArrayList<Illness> illness = new ArrayList<Illness>();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("illnesses", illness);
        kieSession.setGlobal("pId", illness1.getPatient().getPatientId());
        
        
        for (Symptom s: symptoms){
        	if (s.getTerm().equals(Symptom.Term.HIGH_PRESSURE)){
        		HighPressureEvent hp = new HighPressureEvent();
        		hp.setPatientId(illness1.getPatient().getPatientId());
        		kieSession.insert(hp);
        	}
        	kieSession.insert(s);
        }
        
        
        kieSession.getAgenda().getAgendaGroup("all-illnesses").setFocus();
        
        kieSession.fireAllRules();
        
        kieSession.getAgenda().getAgendaGroup("special-request-all-illnesses").setFocus();
        
        illness = (ArrayList<Illness>) kieSession.getGlobal("illnesses");
        kieSession.setGlobal("illnesses", illness);
        kieSession.fireAllRules();
        
        illness = (ArrayList<Illness>) kieSession.getGlobal("illnesses");
        
        Collections.sort(illness, symptomsSizeComparator);

        kieSession.dispose();
        return illness;
    }
    
    public Illness getOneIllness(Illness illness1) {
    	ArrayList<Symptom> symptoms = (ArrayList<Symptom>) illness1.getSymptoms();
    	symptoms = handleSymptoms(symptoms);
    	ArrayList<Illness> illnesses = new ArrayList<Illness>();
    	ArrayList<Illness> allSymptomIllnesses = new ArrayList<Illness>();
        //KieSession kieSession = kieContainer.newKieSession();
        
    	KieSession kieSession = kieContainer.newKieSession();
    	kieSession.setGlobal("illnesses", illnesses);
        kieSession.setGlobal("allSymptomIllnesses", allSymptomIllnesses);
        kieSession.setGlobal("pId", illness1.getPatient().getPatientId());
        
        for (Symptom s: symptoms){
        	if (s.getTerm().equals(Symptom.Term.HIGH_PRESSURE)){
        		HighPressureEvent hp = new HighPressureEvent();
        		hp.setPatientId(illness1.getPatient().getPatientId());
        		kieSession.insert(hp);
        	}
        	kieSession.insert(s);
        }
        
        kieSession.getAgenda().getAgendaGroup("one-illness").setFocus();
        
        kieSession.fireAllRules();
       
        kieSession.getAgenda().getAgendaGroup("special-request-one-illness").setFocus();
        
        allSymptomIllnesses = (ArrayList<Illness>) kieSession.getGlobal("allSymptomIllnesses");
        illnesses = (ArrayList<Illness>) kieSession.getGlobal("illnesses");
        
        kieSession.setGlobal("illnesses", illnesses);
        kieSession.setGlobal("allSymptomIllnesses", allSymptomIllnesses);
        
        
        kieSession.fireAllRules();
        
        allSymptomIllnesses = (ArrayList<Illness>) kieSession.getGlobal("allSymptomIllnesses");
       
        if (!allSymptomIllnesses.isEmpty()){
        	if (allSymptomIllnesses.size()==1){
        		if (allSymptomIllnesses.get(0).getName().equals("Chronic Kidney Disease") || 
        				allSymptomIllnesses.get(0).getName().equals("Acute Kidney Injury")){
        			if (allSymptomIllnesses.get(0).getSymptoms().size() > 2){
        				return allSymptomIllnesses.get(0);
        			}
        		}else{
        			return allSymptomIllnesses.get(0);
        		}
        		
        	}else{
        		Collections.sort(allSymptomIllnesses, symptomsSizeComparator);
        		if (allSymptomIllnesses.get(0).getName().equals("Chronic Kidney Disease") || 
        				allSymptomIllnesses.get(0).getName().equals("Acute Kidney Injury")){
        			if (allSymptomIllnesses.get(0).getSymptoms().size() > 2){
        				return allSymptomIllnesses.get(0);
        			}
        		}else{
        			return allSymptomIllnesses.get(0);
        		}
        		
            }
        }
        
        
        illnesses = (ArrayList<Illness>) kieSession.getGlobal("illnesses");
        kieSession.dispose();
        if (!illnesses.isEmpty()){
        	Collections.sort(illnesses, symptomsSizeComparator);

            return illnesses.get(0);
        }
        
        return null;
    }
    
    public String diagnose(Record record){
    	ArrayList<Symptom> symptoms = (ArrayList<Symptom>) record.getIllness().getSymptoms();
    	symptoms = handleSymptoms(symptoms);
    	record.getIllness().setSymptoms(symptoms);
    	record.setDate(new Date());
    	KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(record);
        kieSession.insert(record.getIllness());
        
        Patient patient = new Patient();
        patient.setPatientId(record.getPatient().getPatientId());
        patient.setAllergicMedicament(medicamentService.getOne(4L));
        
        kieSession.insert(patient);
        
        kieSession.setGlobal("pId", record.getPatient().getPatientId());
        
        
        
        if (record.getIllness().getName().equals("Cold")){
        	ColdOrFeverEvent cf = new ColdOrFeverEvent();
        	cf.setIllnessName("Cold");
        	cf.setPatientId(record.getPatient().getPatientId());
        	kieSession.insert(cf);
        }
        
        if (record.getIllness().getName().equals("Fever")){
        	ColdOrFeverEvent cf = new ColdOrFeverEvent();
        	cf.setIllnessName("Fever");
        	cf.setPatientId(record.getPatient().getPatientId());
        	kieSession.insert(cf);
        	
        	IllnessWithHighTempEvent il = new IllnessWithHighTempEvent();
        	il.setIllnessName("Fever");
        	il.setPatientId(record.getPatient().getPatientId());
        	
        	kieSession.insert(il);
        }
        
        if (record.getIllness().getName().equals("Sinus Infection")){
        	IllnessWithHighTempEvent il = new IllnessWithHighTempEvent();
        	il.setIllnessName("Sinus Infection");
        	il.setPatientId(record.getPatient().getPatientId());
        	kieSession.insert(il);
        }
        
        if (record.getIllness().getName().equals("Tonsil Inflamation")){
        	IllnessWithHighTempEvent il = new IllnessWithHighTempEvent();
        	il.setIllnessName("Tonsil Inflamation");
        	il.setPatientId(record.getPatient().getPatientId());
        	kieSession.insert(il);
        }
        
        ArrayList<Medicament> medicaments = new ArrayList<>();
        
        boolean flag = true;
        if (!record.getMedicaments().isEmpty()){
        	for (Medicament m: record.getMedicaments()){
        	
        		if (!medicaments.isEmpty()){
	        		for (Medicament m1: medicaments){
	        			if (m1.getName().equals(m.getName())){
	        				flag = false;
	        				break;
	        			}
	        		}
        		}
    			if (flag){
    				medicaments.add(m);
    			}
        	}
        }
        
        for (Medicament m: medicaments){
        	if (m.getCategory().equals(Medicament.Category.ANTIBIOTICS)){
        		PatientUsesAntibioticsEvent pantb = new PatientUsesAntibioticsEvent();
        		pantb.setIllnessName(record.getIllness().getName());
        		pantb.setPatientId(record.getPatient().getPatientId());
        		kieSession.insert(pantb);
        	}
        	kieSession.insert(m);
        	if (!m.getIngridients().isEmpty()){
        		for (Ingridient i1: m.getIngridients()){
            		kieSession.insert(i1);
            	}
        	}
        	
        }
        
        
        kieSession.getAgenda().getAgendaGroup("check-med-allergies").setFocus();
        
        ArrayList<Medicament> allergicMedicaments = new ArrayList<Medicament>();
        
        kieSession.setGlobal("allergics", allergicMedicaments);
        
        ArrayList<Ingridient> allergicIngridients = new ArrayList<Ingridient>();
        
        kieSession.setGlobal("allergicsIngr", allergicIngridients);
        
        kieSession.fireAllRules();
    	
        allergicMedicaments = (ArrayList<Medicament>) kieSession.getGlobal("allergics");
        
        kieSession.getAgenda().getAgendaGroup("check-ingr-allergies").setFocus();
        
        
        kieSession.fireAllRules();
        
        allergicIngridients = (ArrayList<Ingridient>) kieSession.getGlobal("allergicsIngr");
        
        
        String allergies = "Patient allergic to: ";
        
        if (!allergicMedicaments.isEmpty()){
        	for (Medicament m1: allergicMedicaments){
        		allergies+= m1.getName() + " ,";
        	}
        }
        if (!allergicIngridients.isEmpty()){
        	for (Ingridient i: allergicIngridients){
        		allergies += i.getName() + ", ";
        	}
        }
        if (!allergies.equals("Patient allergic to: ")){
        	allergies = allergies.substring(0, allergies.length()-2);
        	allergies+="!";
        }
        
        kieSession.dispose();
    	
        return allergies;
    }
    
    public ArrayList<Symptom> getIllnessSymptoms(Illness illness){
    	ArrayList<Symptom> symptoms = new ArrayList<>();
    	
    	KieSession kieSession = kieContainer.newKieSession();
    	kieSession.insert(illness);
    	
        kieSession.getAgenda().getAgendaGroup("symptoms").setFocus();
        
        kieSession.fireAllRules();
        
        symptoms = (ArrayList<Symptom>) illness.getSymptoms();
        
        Collections.sort(symptoms, symptomsSpecificComparator);
        return symptoms;
    }
    
    public ArrayList<Symptom> handleSymptoms(ArrayList<Symptom> symptoms){
    	ArrayList<Symptom> newSymptoms = new ArrayList<>();
    	for (Symptom s: symptoms){
    		Symptom s1 = new Symptom();
    		if (s.getTerm().toString().equals("TEMPERATURE")){
    			if (s.getTemperature() >=  38 && s.getTemperature() < 40){
    				s1.setTerm(Symptom.Term.TEMP_OVER_38);
    			}
    			if (s.getTemperature() >=  40 && s.getTemperature() <= 41){
    				s1.setTerm(Symptom.Term.TEMP_BETWEEN_40_AND_41);
    			}
    		}else{
    			s1.setTerm(s.getTerm());
    		}
    		boolean flag = true;
    		for (Symptom s2: newSymptoms){
    			if (s2.getTerm().toString().equals(s1.getTerm().toString())){
    				flag = false;
    				break;
    			}
    		}
			if (flag){
				newSymptoms.add(s1);
			}
    	}
    	
    	return newSymptoms;
    }

    
    Comparator<Illness> symptomsSizeComparator = new Comparator<Illness>()
    {
        @Override
        public int compare(Illness i1, Illness i2)
        {
            return Integer.compare(i2.getSymptoms().size(), i1.getSymptoms().size());
        }
    };
    
    Comparator<Symptom> symptomsSpecificComparator = new Comparator<Symptom>()
    {
        @Override
        public int compare(Symptom s1, Symptom s2)
        {
            return Boolean.compare(s2.getSpecific(), s1.getSpecific());
        }
    };


}
