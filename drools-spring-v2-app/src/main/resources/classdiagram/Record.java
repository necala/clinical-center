package drools.spring.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Record implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private Illness illness;
	private Date date;
	
	private Patient patient;
	private User doctor;
	private List<Medicament> medicaments = new ArrayList<>();
	
	public Record() {
		super();
		this.date = new Date();
	}

	public Record(Illness illness) {
		super();
		this.illness = illness;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public Illness getIllness() {
		return illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
