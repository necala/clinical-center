package drools.spring.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ManyToAny;

@Entity
public class IngridientAllergy {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long ingridientId;

	private String ingridientName;
	
	private Long patientId;

	
	public IngridientAllergy() {
		super();
	}
	
	public IngridientAllergy(Long ingridient, Long patient, String ingridientName) {
		super();
		this.ingridientId = ingridient;
		this.ingridientName = ingridientName;
		this.patientId = patient;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getIngridientId() {
		return ingridientId;
	}

	public void setIngridientId(Long ingridientId) {
		this.ingridientId = ingridientId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getIngridientName() {
		return ingridientName;
	}

	public void setIngridientName(String ingridientName) {
		this.ingridientName = ingridientName;
	}

	
}
