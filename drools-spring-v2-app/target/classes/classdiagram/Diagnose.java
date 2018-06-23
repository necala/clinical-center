package drools.spring.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Diagnose {
	
private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
    private String illnessName;
    private Date date;
    private Long patientId;
    private Long doctorId;
    private String patientName;
    private String doctorName;
    
    
	public Diagnose() {
		super();
	}
	
	
	public Diagnose(String illnessName, Long patientId, Long doctorId) {
		super();
		this.illnessName = illnessName;
		this.date = new Date();
		this.patientId = patientId;
		this.doctorId = doctorId;
	}
	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getIllnessName() {
		return illnessName;
	}

	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}


	public Long getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}


	public String getPatientName() {
		return patientName;
	}


	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	
    
}
