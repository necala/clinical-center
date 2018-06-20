package drools.spring.example.model;

public class Report {

	public enum Category {
        CHRONIC_ILLNES, ADDICT, WEAKENED_IMMUNITY
    };
	
    private Long id;
	private Patient patient;
	private Category category;
	private String helper;
	public Report() {
		super();
	}
	public Report(Patient patient, Category category, String helper) {
		super();
		this.patient = patient;
		this.category = category;
		this.helper = helper;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getHelper() {
		return helper;
	}
	public void setHelper(String helper) {
		this.helper = helper;
	}
	
	
}
