package drools.spring.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MedicamentAllergy {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long medicamentId;

	private String medicamentName;
	
	private Long patientId;
	
	public MedicamentAllergy() {
		super();
	}

	public MedicamentAllergy(Long medicament, Long patient, String medicamentName) {
		super();
		this.medicamentId = medicament;
		this.medicamentName = medicamentName;
		this.patientId = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Long medicamentId) {
		this.medicamentId = medicamentId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getMedicamentName() {
		return medicamentName;
	}

	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}

	
	
}
