package drools.spring.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
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
    
    
    public ArrayList<Illness> getOneIllness(ArrayList<Symptom> symptoms, Long patientId) {
    	KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kieSession = kbase.newKieSession();
		
		if (!symptoms.isEmpty()){
			symptoms = handleSymptoms(symptoms);
		}
		
		ArrayList<Illness> illnesses = findAll();
		addSymptomsAndIllnesses(kieSession, symptoms, patientId, illnesses);
		insertDiagnosesInSession(kieSession, patientId);

		
		Illness illnessCatFirst = new Illness();
		illnessCatFirst.setName("illnessFirstCat");
		
		kieSession.insert(illnessCatFirst);
		
		Illness illnessCatSecond = new Illness();
		illnessCatSecond.setName("illnessSecondCat");
		
		kieSession.insert(illnessCatSecond);
		
		
		Illness illnessCatThird = new Illness();
		illnessCatThird.setName("illnessThirdCat");

		kieSession.insert(illnessCatThird);

		kieSession.getAgenda().getAgendaGroup("set-symptom-num").setFocus();
		
		kieSession.fireAllRules();
		
		kieSession.getAgenda().getAgendaGroup("set-illness").setFocus();
		
		kieSession.fireAllRules();

		System.out.println(illnessCatFirst.getName());
		System.out.println(illnessCatSecond.getName());
		System.out.println(illnessCatThird.getName());
		
		
		ArrayList<Illness> foundIllnesses = new ArrayList<>();
		
		if (!illnessCatThird.getName().equals("illnessThirdCat")){
			foundIllnesses.add(findByName(illnessCatThird.getName()));
		}
		
		
		if (!illnessCatSecond.getName().equals("illnessSecondCat")){
			foundIllnesses.add(findByName(illnessCatSecond.getName()));
		}

		if (!illnessCatFirst.getName().equals("illnessFirstCat")){
			foundIllnesses.add(findByName(illnessCatFirst.getName()));
		}
		
		return foundIllnesses;

	}
    
    public ArrayList<Illness> getAllIllness(ArrayList<Symptom> symptoms, Long patientId) {
    	KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kieSession = kbase.newKieSession();

		if (!symptoms.isEmpty()){
			symptoms = handleSymptoms(symptoms);
		}
		
		ArrayList<Illness> illnesses = findAll();
		addSymptomsAndIllnesses(kieSession, symptoms, patientId, illnesses);
		insertDiagnosesInSession(kieSession, patientId);
		
		
		kieSession.getAgenda().getAgendaGroup("set-symptom-num").setFocus();
		
		kieSession.fireAllRules();

		
		ArrayList<Illness> new_illnesses = new ArrayList<>();
		
		for (Illness illness: illnesses){
			if (illness.getSymptomsFound() > 0){
				System.out.println("BOLEST: " + illness.getName() + " simptom prvi: " + illness.getSymptomTermsFound().get(0).toString());
				new_illnesses.add(illness);
			}
		}
		
		Collections.sort(new_illnesses, symptomsFoundComparator);
		
		return new_illnesses;

	}
    
   
	private void addSymptomsAndIllnesses(KieSession kieSession, ArrayList<Symptom> symptoms, Long patientId,
			ArrayList<Illness> illnesses) {

		kieSession.setGlobal("pId", patientId);
		
		if (!symptoms.isEmpty()){
			for (Symptom symptom : symptoms) {
				kieSession.insert(symptom);
			}
		}else{
			kieSession.insert(new Symptom());
		}
		
		
		for (Illness illness : illnesses) {
			illness.setSymptomsFound(0);
			illness.setSpecificSymptomsFound(0);
			illness.setSymptomTermsFound(new ArrayList<Symptom.Term>());
			if (!illness.getSymptoms().isEmpty()){
				for (Symptom s: illness.getSymptoms()){
					//System.out.println("Ubacujem term simptoma u sesiju: " + s.getTerm().toString());
					illness.getSymptomsTerms().add(s.getTerm());
				}
				//System.out.println("Ubacujem bolest u sesiju: " + illness.getName());
				kieSession.insert(illness);
			}else{
				//System.out.println("Ubacujem bolest u sesiju: " + illness.getName());
				System.out.println("ubacujem bolest " + illness.getName() + " kat: " + illness.getCategory().toString());
				kieSession.insert(illness);
			}
			
		}

	}

	public void insertDiagnosesInSession(KieSession kieSession, Long patientId){
    	ArrayList<Diagnose> diagnoses = diagnoseService.findByPatientId(patientId);
        
        if (!diagnoses.isEmpty()){
        	for (Diagnose d: diagnoses){
        		kieSession.insert(d);
        		//System.out.println("Ubacujem u sesiju dijagnozu: " + d.getIllnessName());
        		ArrayList<DiagnoseMedicament> diagnoseMedicaments = diagnoseMedicamentService.findByDiagnoseId(d.getId());
        		if (!diagnoseMedicaments.isEmpty()){
        			for (DiagnoseMedicament dm: diagnoseMedicaments){
        				if (dm.getMedicamentCategory() != null && dm.getMedicamentCategory().equals("ANTIBIOTICS")){
        					kieSession.insert(dm);
        					//System.out.println("Ubacujem u sesiju dijagnozu lijek: " + dm.getMedicamentName());
        				}
        			}
        		}
        		
        		ArrayList<DiagnoseSymptom> diagnoseSymptoms = diagnoseSymptomService.findByDiagnoseId(d.getId());
        		if (!diagnoseSymptoms.isEmpty()){
        			for (DiagnoseSymptom ds: diagnoseSymptoms){
        				if (ds.getSymptomTerm().equals("TEMP_OVER_38") || ds.getSymptomTerm().equals("TEMP_BETWEEN_40_AND_41") ||
        						ds.getSymptomTerm().equals("HIGH_PRESSURE")){
        					kieSession.insert(ds);
        					//System.out.println("Ubacujem u sesiju dijagnozu simptom: " + ds.getSymptomTerm());
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
    	
    	ArrayList<Symptom.Term> symptomTerms = (ArrayList<Symptom.Term>) record.getIllness().getSymptomTermsFound();
    	
    	String allergies = "Patient allergic to: ";
    	
    	KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kieSession = kbase.newKieSession();


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
        	
        	if (!symptomTerms.isEmpty()){
        		for (Symptom.Term s: symptomTerms){
        			DiagnoseSymptom ds = new DiagnoseSymptom();
            		ds.setDiagnoseId(diagnose.getId());
            		ds.setSymptomTerm(s.toString());
            		ds.setDate(diagnose.getDate());
            		ds.setPatientId(diagnose.getPatientId());
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
    	
    	KieServices ks = KieServices.Factory.get();
		KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
		kbconf.setOption(EventProcessingOption.STREAM);
		KieBase kbase = kieContainer.newKieBase(kbconf);

		KieSession kieSession = kbase.newKieSession();
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
   
    
    Comparator<Illness> symptomsFoundComparator = new Comparator<Illness>()
    {
        @Override
        public int compare(Illness i1, Illness i2)
        {
            return Integer.compare(i2.getSymptomsFound(), i1.getSymptomsFound());
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
