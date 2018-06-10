package drools.spring.example.model.events;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("14d")
public class IllnessWithHighTempEvent {

private static final long serialVersionUID = 1L;
	
	private Date executionTime;
    private String patientId;
    private String illnessName;
    
    public IllnessWithHighTempEvent() {
		super();
		this.executionTime = new Date();
	}

	public IllnessWithHighTempEvent(String patientId, String illnessName) {
		super();
		this.executionTime = new Date();
		this.patientId = patientId;
		this.illnessName = illnessName;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getIllnessName() {
		return illnessName;
	}

	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}

	@Override
	public String toString() {
		return "Date: " + this.executionTime + " : illness name: " + this.illnessName + " patient ID: " + this.patientId;
	}
	
}
