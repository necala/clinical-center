package drools.spring.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DiagnoseMedicament {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    private Long diagnoseId;
    private Long medicamentId;
    private String medicamentName;
    private String medicamentCategory;
    private Long patientId;
    private Date date;
    private Long doctorId;
    private String illnessName;
    
	public DiagnoseMedicament() {
		super();
	}


	public DiagnoseMedicament(Long diagnoseId, Long medicamentId, String medicamentName, String category) {
		super();
		this.diagnoseId = diagnoseId;
		this.medicamentId = medicamentId;
		this.medicamentName = medicamentName;
		this.medicamentCategory = category;
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


	public Long getMedicamentId() {
		return medicamentId;
	}


	public void setMedicamentId(Long medicamentId) {
		this.medicamentId = medicamentId;
	}


	public String getMedicamentName() {
		return medicamentName;
	}


	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}


	public String getMedicamentCategory() {
		return medicamentCategory;
	}


	public void setMedicamentCategory(String medicamentCategory) {
		this.medicamentCategory = medicamentCategory;
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


	public Long getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}


	public String getIllnessName() {
		return illnessName;
	}


	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}

	
	
}
