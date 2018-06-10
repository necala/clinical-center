package drools.spring.example.model;

import java.io.Serializable;


public class Symptom implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Term {
		RUNNY_NOSE, SORE_THROAT, HEADACHE, SNEEZE,
		COUGH, TEMP_OVER_38, SHIVER, PAIN_TO_EARS,
		TEMP_BETWEEN_40_AND_41, APPETITE_LOSS, 
		TIREDNESS, YELLOW_SECRETION_FROM_NOSE,
		EYE_SWELLING, 
		HIGH_PRESSURE, OFTEN_URINATION, WEIGHT_LOSS, 
		FATIGUE, NAUSEA_AND_VOMITTING,
		NOCTURIA, LEGS_AND_JOINTS_SWELLING,
		CHOKING, CHEST_PAIN, DIARRHEA,
		TEMPERATURE,
		SURGERY_RECOVERY,
		SUFFERED_FROM_COLD_OR_FEVER_LAST_60_DAYS,
		TEN_TIMES_HIGH_PRESSURE_IN_LAST_6_MONTHS,
		SUFFERES_FROM_HYPERTENSION_FOR_AT_LEAST_6_MONTHS,
		SUFFERES_FROM_DIABETES,
		SUFFERED_FROM_ILLNESS_WITH_SYMPTOM_HIGH_TEMPERATURE_LAST_2_WEEKS,
		SUFFERED_AND_USED_ANTIBIOTICS_LAST_3_WEEKS
		
    };
    
    
    private Long id;
    private Term term;
    
    private Double temperature;
    private String helper;
    private Boolean specific;
    
    
    
	public Symptom() {
		this.specific = false;
	}

	public Symptom(Term term) {
		this.term = term;
	}
	
	
	public Long getId() {
		return id;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
	}

	public Boolean getSpecific() {
		return specific;
	}

	public void setSpecific(Boolean specific) {
		this.specific = specific;
	}
    
	
}
