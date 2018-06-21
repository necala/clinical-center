package drools.spring.example.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Illness implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public enum Category {
        FIRST, SECOND, THIRD
    };
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	private String name;
	private Category category;
	@OneToMany(mappedBy="illness", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Symptom> symptoms = new ArrayList<Symptom>();
	
	@Transient
	private Integer symptomsFound;
	
	@Transient
	private Integer specificSymptomsFound;
	
	@Transient
	private List<Symptom.Term> symptomsTerms = new ArrayList<>();
	
	@Transient
	private List<Symptom.Term> symptomTermsFound = new ArrayList<>();
	
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

	public Integer getSymptomsFound() {
		return symptomsFound;
	}

	public void setSymptomsFound(Integer symptomsFound) {
		this.symptomsFound = symptomsFound;
	}

	public Integer getSpecificSymptomsFound() {
		return specificSymptomsFound;
	}

	public void setSpecificSymptomsFound(Integer specificSymptomsFound) {
		this.specificSymptomsFound = specificSymptomsFound;
	}

	public List<Symptom.Term> getSymptomsTerms() {
		return symptomsTerms;
	}

	public void setSymptomsTerms(List<Symptom.Term> symptomsTerms) {
		this.symptomsTerms = symptomsTerms;
	}

	public List<Symptom.Term> getSymptomTermsFound() {
		return symptomTermsFound;
	}

	public void setSymptomTermsFound(List<Symptom.Term> symptomTermsFound) {
		this.symptomTermsFound = symptomTermsFound;
	}
	
	
	
}
