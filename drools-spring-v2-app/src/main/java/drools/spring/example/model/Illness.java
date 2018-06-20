package drools.spring.example.model;

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

}
