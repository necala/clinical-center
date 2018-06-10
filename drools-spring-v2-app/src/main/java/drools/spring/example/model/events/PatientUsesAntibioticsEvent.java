package drools.spring.example.model.events;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import drools.spring.example.model.Medicament.Category;
import drools.spring.example.model.Medicament;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("21d")
public class PatientUsesAntibioticsEvent {

private static final long serialVersionUID = 1L;
	
	private Date executionTime;
    private String patientId;
    private String illnessName;
    private Category medicamentCategory;
    
	public PatientUsesAntibioticsEvent() {
		super();
		this.executionTime = new Date();
		this.medicamentCategory = Medicament.Category.ANTIBIOTICS;
	}

	public PatientUsesAntibioticsEvent(String patientId, String illnessName) {
		super();
		this.executionTime = new Date();
		this.patientId = patientId;
		this.illnessName = illnessName;
		this.medicamentCategory = Medicament.Category.ANTIBIOTICS;
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

	public String getIllnessName() {
		return illnessName;
	}

	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}

	
	
	public Category getMedicamentCategory() {
		return medicamentCategory;
	}

	public void setMedicamentCategory(Category medicamentCategory) {
		this.medicamentCategory = medicamentCategory;
	}

	@Override
	public String toString() {
		return "Date: " + this.executionTime + " : illness name: " + this.illnessName + " patient ID: " + this.patientId +
				" medicament category: " + this.medicamentCategory.toString();
	}
	
}
