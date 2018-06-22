package drools.spring.example.model;

import java.io.Serializable;

public class MonitoringIssue implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public enum Issue {
        OXYGEN_ISSUE, ACCELERATED_HEARTBEAT, URGENT_DIALYSIS
    };
	
	private Long patientId;
	
	private Issue issue;

	public MonitoringIssue() {
		super();
	}

	public MonitoringIssue(Long patientId, Issue issue) {
		super();
		this.patientId = patientId;
		this.issue = issue;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
}
