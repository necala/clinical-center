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
import drools.spring.example.model.IngridientAllergy;
import drools.spring.example.model.Diagnose;
import drools.spring.example.model.DiagnoseMedicament;
import drools.spring.example.model.DiagnoseSymptom;
import drools.spring.example.model.Record;
import drools.spring.example.model.Symptom;
import drools.spring.example.model.User;
import drools.spring.example.model.Medicament;
import drools.spring.example.model.MedicamentAllergy;
import drools.spring.example.model.Patient;
import drools.spring.example.model.events.ColdOrFeverEvent;
import drools.spring.example.model.events.HighPressureEvent;
import drools.spring.example.model.events.IllnessWithHighTempEvent;
import drools.spring.example.model.events.PatientUsesAntibioticsEvent;
import drools.spring.example.repository.IllnessRepository;

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
    MedicamentAllergyService medicamentAllergyService;
    
    @Autowired
    IngridientAllergyService ingridientAllergyService;
    
    @Autowired
    IngridientService ingridientService;
    
    @Autowired
    DiagnoseService diagnoseService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    PatientService patientService;
    
    @Autowired
    IllnessRepository illnessRepository;
    
    @Autowired
    DiagnoseSymptomService diagnoseSymptomService;
    
    @Autowired
    DiagnoseMedicamentService diagnoseMedicamentService;
    
    public ArrayList<Illness> getIllnesses(Illness illness1, Long patientId) {
    	ArrayList<Symptom> symptoms = (ArrayList<Symptom>) illness1.getSymptoms();
    	symptoms = handleSymptoms(symptoms);
    	ArrayList<Illness> illness = new ArrayList<Illness>();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("illnesses", illness);
        kieSession.setGlobal("pId", patientId);
        
        
        for (Symptom s: symptoms){
        	
        	kieSession.insert(s);
        }
        
        insertDiagnosesInSession(kieSession, patientId);
        
        
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
    
    public ArrayList<Illness> getOneIllness(Illness illness1, Long patientId) {
    	ArrayList<Symptom> symptoms = (ArrayList<Symptom>) illness1.getSymptoms();
    	symptoms = handleSymptoms(symptoms);
    	ArrayList<Illness> illnesses = new ArrayList<Illness>();
    	ArrayList<Illness> allSymptomIllnesses = new ArrayList<Illness>();
        
    	KieSession kieSession = kieContainer.newKieSession();
    	kieSession.setGlobal("illnesses", illnesses);
        kieSession.setGlobal("allSymptomIllnesses", allSymptomIllnesses);
        kieSession.setGlobal("pId", patientId);
        
        for (Symptom s: symptoms){
        	
        	kieSession.insert(s);
        }
        
        insertDiagnosesInSession(kieSession, patientId);
        
        kieSession.getAgenda().getAgendaGroup("one-illness").setFocus();
        
        kieSession.fireAllRules();
       
        kieSession.getAgenda().getAgendaGroup("special-request-one-illness").setFocus();
        
        allSymptomIllnesses = (ArrayList<Illness>) kieSession.getGlobal("allSymptomIllnesses");
        illnesses = (ArrayList<Illness>) kieSession.getGlobal("illnesses");
        
        kieSession.setGlobal("illnesses", illnesses);
        kieSession.setGlobal("allSymptomIllnesses", allSymptomIllnesses);
        
        
        kieSession.fireAllRules();
        
        allSymptomIllnesses = (ArrayList<Illness>) kieSession.getGlobal("allSymptomIllnesses");
        
        ArrayList<Illness> return_illnesses = new ArrayList<>();
       
        if (!allSymptomIllnesses.isEmpty()){
        	
        	if (allSymptomIllnesses.size()==1){
        		if (allSymptomIllnesses.get(0).getName().equals("Chronic Kidney Disease") || 
        				allSymptomIllnesses.get(0).getName().equals("Acute Kidney Injury")){
        			if (allSymptomIllnesses.get(0).getSymptoms().size() > 2){
        				
        				ArrayList<Symptom> newSymptoms = new ArrayList<>();
        				
        				for (Symptom s: allSymptomIllnesses.get(0).getSymptoms()){
        					boolean flag = true;
        					if (!newSymptoms.isEmpty()){
        						for (Symptom s1: newSymptoms){
        							
        							if (s1.getTerm().equals(s.getTerm()) ){
        								flag = false;
        								break;
        							}
        						}
        					}
    						if (flag){
								newSymptoms.add(s);
							}
        				}
        				allSymptomIllnesses.get(0).setSymptoms(newSymptoms);
        				
        				return_illnesses.add(allSymptomIllnesses.get(0));
        			}
        		}else{
        			return_illnesses.add(allSymptomIllnesses.get(0));
        		}
        		
        	}else{
        		Collections.sort(allSymptomIllnesses, symptomsSizeComparator);
        		if (allSymptomIllnesses.get(0).getName().equals("Chronic Kidney Disease") || 
        				allSymptomIllnesses.get(0).getName().equals("Acute Kidney Injury")){
        			if (allSymptomIllnesses.get(0).getSymptoms().size() > 2){
        				return_illnesses.add(allSymptomIllnesses.get(0));
        			}
        		}else{
        			for (Illness i: allSymptomIllnesses){
        				boolean flag = true;
        				if (!return_illnesses.isEmpty()){
        					for(Illness i2: return_illnesses){
        						if (i2.getClass().equals(i.getCategory())){
        							flag = false;
        							break;
        						}
        					}
        				}
        				if (flag){
        					return_illnesses.add(i);
        				}
        			}
        		}
            }
        }
        
        
        illnesses = (ArrayList<Illness>) kieSession.getGlobal("illnesses");
        kieSession.dispose();
        
        if (!illnesses.isEmpty()){
        	Collections.sort(illnesses, symptomsSizeComparator);

        	if (illnesses.get(0).getCategory().equals(Illness.Category.FIRST)){
        		boolean flag = true;
        		for (Illness i: return_illnesses){
        			if (i.getCategory().equals(Illness.Category.FIRST)){
        				flag = false;
        				break;
        			}
        		}
        		if (flag){
        			return_illnesses.add(illnesses.get(0));
        		}
        	}
        }
        
        return return_illnesses;
    }
    
    public void insertDiagnosesInSession(KieSession kieSession, Long patientId){
    	ArrayList<Diagnose> diagnoses = diagnoseService.findByPatientId(patientId);
        
        if (!diagnoses.isEmpty()){
        	for (Diagnose d: diagnoses){
        		kieSession.insert(d);
        		System.out.println("Ubacujem u sesiju dijagnozu: " + d.getIllnessName());
        		ArrayList<DiagnoseMedicament> diagnoseMedicaments = diagnoseMedicamentService.findByDiagnoseId(d.getId());
        		if (!diagnoseMedicaments.isEmpty()){
        			for (DiagnoseMedicament dm: diagnoseMedicaments){
        				if (dm.getMedicamentCategory() != null && dm.getMedicamentCategory().equals("ANTIBIOTICS")){
        					kieSession.insert(dm);
        					System.out.println("Ubacujem u sesiju dijagnozu lijek: " + dm.getMedicamentName());
        				}
        			}
        		}
        		
        		ArrayList<DiagnoseSymptom> diagnoseSymptoms = diagnoseSymptomService.findByDiagnoseId(d.getId());
        		if (!diagnoseSymptoms.isEmpty()){
        			for (DiagnoseSymptom ds: diagnoseSymptoms){
        				if (ds.getSymptomTerm().equals("TEMP_OVER_38") || ds.getSymptomTerm().equals("TEMP_BETWEEN_40_AND_41") ||
        						ds.getSymptomTerm().equals("HIGH_PRESSURE")){
        					kieSession.insert(ds);
        					System.out.println("Ubacujem u sesiju dijagnozu simptom: " + ds.getSymptomTerm());
        				}
        			}
        		}
        	}
        }
    }
    
   
    
    public String setDiagnose(Record record){
    	ArrayList<Symptom> symptoms = (ArrayList<Symptom>) record.getIllness().getSymptoms();
    	symptoms = handleSymptoms(symptoms);
    	record.getIllness().setSymptoms(symptoms);
    	record.setDate(new Date());
    	
    	
    	
    	String allergies = "Patient allergic to: ";
    	
    	KieSession kieSession = kieContainer.newKieSession();


        kieSession.setGlobal("idPatient", record.getPatient().getId());
        
        if (!record.getMedicaments().isEmpty()){
        	for (Medicament m: record.getMedicaments()){
        		kieSession.insert(m);
        		if (!m.getIngridients().isEmpty()){
        			for(Ingridient i: m.getIngridients()){
                		kieSession.insert(i);
        			}
        		}
        	}
        }

        List<MedicamentAllergy> medAll = medicamentAllergyService.findByPatientId(record.getPatient().getId());
        
        if (!medAll.isEmpty()){
        	for (MedicamentAllergy ma: medAll){System.out.println("U sesiju ubacujem alergiju lijek " + ma.getMedicamentName() + " a pacijent: " + ma.getPatientId());
        		kieSession.insert(ma);
        	}
        }
        
        List<IngridientAllergy> ingrAll = ingridientAllergyService.findByPatient(record.getPatient().getId());
        
        if (!ingrAll.isEmpty()){
        	for (IngridientAllergy ia: ingrAll){System.out.println("U sesiju ubacujem alergiju na sastojak " + ia.getIngridientName() + " a pacijent: " + ia.getPatientId());
        		kieSession.insert(ia);
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
        }else{
        	Diagnose diagnose = new Diagnose();
        	diagnose.setDate(new Date());
        	diagnose.setIllnessName(record.getIllness().getName());
        	diagnose.setPatientId(record.getPatient().getId());
        	User doctor = userService.getById(record.getDoctor().getId());
        	diagnose.setDoctorId(record.getDoctor().getId());
        	diagnose.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
        	Patient patient = patientService.findOne(record.getPatient().getId());
        	diagnose.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        	
        	diagnose = diagnoseService.addDiagnose(diagnose);
        	
        	if (!symptoms.isEmpty()){
        		for (Symptom s: symptoms){
        			DiagnoseSymptom ds = new DiagnoseSymptom();
            		ds.setDiagnoseId(diagnose.getId());
            		ds.setSymptomTerm(s.getTerm().toString());
            		ds = diagnoseSymptomService.addDiagnoseSymtpom(ds);
        		}
        		
        	}
        	
        	if (!record.getMedicaments().isEmpty()){
        		for (Medicament m: record.getMedicaments()){
        			DiagnoseMedicament dm = new DiagnoseMedicament();
        			dm.setDiagnoseId(diagnose.getId());
        			dm.setMedicamentId(m.getId());
        			dm.setMedicamentName(m.getName());
        			dm.setMedicamentCategory(m.getCategory().toString());
        			dm.setPatientId(record.getPatient().getId());
        			dm.setDate(diagnose.getDate());
        			dm.setDoctorId(diagnose.getDoctorId());
        			dm.setIllnessName(diagnose.getIllnessName());
        			dm = diagnoseMedicamentService.addDiagnoseMedicament(dm);
        		}
        	}
        }
        
        
    	
    	
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

    public Illness addIllness(Illness illness){
    	return illnessRepository.save(illness);
    }
    
    public void deleteIllness(Illness illness){
    	illnessRepository.delete(illness);
    }
    
    public Illness findById(Long id){
    	return illnessRepository.findById(id);
    }
    
    public Illness findByName(String name){
    	return illnessRepository.findByName(name);
    }
    
    public ArrayList<Illness> findAll(){
    	return illnessRepository.findAll();
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
