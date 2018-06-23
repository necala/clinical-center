package drools.spring.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DiagnoseSymptom {


	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    private Long diagnoseId;
    private String symptomTerm;
    private Long patientId;
    private Date date;
    
	public DiagnoseSymptom() {
		super();
	}

	public DiagnoseSymptom(Long diagnoseId, String symptomTerm) {
		super();
		this.diagnoseId = diagnoseId;
		this.symptomTerm = symptomTerm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDiagnoseId() {
		return diagnoseId;
	}

	public void setDiagnoseId(Long diagnoseId) {
		this.diagnoseId = diagnoseId;
	}

	public String getSymptomTerm() {
		return symptomTerm;
	}

	public void setSymptomTerm(String symptomTerm) {
		this.symptomTerm = symptomTerm;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
	
}
