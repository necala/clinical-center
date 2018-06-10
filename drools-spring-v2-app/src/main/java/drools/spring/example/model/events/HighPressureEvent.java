package drools.spring.example.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import drools.spring.example.model.Symptom;
import drools.spring.example.model.Symptom.Term;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("183d")
public class HighPressureEvent implements Serializable {
private static final long serialVersionUID = 1L;
	
	private Date executionTime;
    private String patientId;
    private Term symptomTerm;
    
	public HighPressureEvent() {
		super();
		this.executionTime = new Date();
		this.symptomTerm = Symptom.Term.HIGH_PRESSURE;
	}

	public HighPressureEvent(String patientId) {
		super();
		this.executionTime = new Date();
		this.symptomTerm = Symptom.Term.HIGH_PRESSURE;
		this.patientId = patientId;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Term getSymptomTerm() {
		return symptomTerm;
	}

	public void setSymptomTerm(Term symptomTerm) {
		this.symptomTerm = symptomTerm;
	}
	
	@Override
	public String toString() {
		return "Date: " + this.executionTime + " : symptom: " + this.symptomTerm.toString() + " patient ID: " + this.patientId;
	}
	
	
    
    
}
