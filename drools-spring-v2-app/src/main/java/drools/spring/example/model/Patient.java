package drools.spring.example.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Nevena Lazarevic
 *
 */
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String patientId;
	private Ingridient allergicIngridient;
	private Medicament allergicMedicament;
	
	public Patient() {
		super();
	}

	public Patient(String firstName, String lastName,
			Ingridient allergicIngridient, Medicament allergicMedicamnt) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.allergicIngridient = allergicIngridient;
		this.allergicMedicament = allergicMedicamnt;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Ingridient getAllergicIngridients() {
		return allergicIngridient;
	}

	public void setAllergicIngridients(Ingridient allergicIngridients) {
		this.allergicIngridient = allergicIngridients;
	}

	public Medicament getAllergicMedicaments() {
		return allergicMedicament;
	}

	public void setAllergicMedicaments(Medicament allergicMedicaments) {
		this.allergicMedicament = allergicMedicaments;
	}
	
}
