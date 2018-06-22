package drools.spring.example.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Timestamp;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("10s")
public class HeartBeatEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date executionTime;
	private Long patientId;
	public HeartBeatEvent() {
		super();
		this.executionTime = new Date();
	}


	public HeartBeatEvent(Long patientId) {
		super();
		this.patientId = patientId;
		this.executionTime = new Date();
	}

	

	public Long getPatientId() {
		return patientId;
	}


	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}


	public Date getExecutionTime() {
		return executionTime;
	}


	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}


}
