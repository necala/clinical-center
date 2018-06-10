package drools.spring.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Illness implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum Category {
        FIRST, SECOND, THIRD
    };
    
    private Long id;
	private String name;
	private Category category;
	private List<Symptom> symptoms;
	private Patient patient;
	
	public Illness() {
		this.symptoms = new ArrayList<Symptom>();
		this.name = "";
	}
	
	public Illness(String name, Category category) {
		this.name = name;
		this.category = category;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}
