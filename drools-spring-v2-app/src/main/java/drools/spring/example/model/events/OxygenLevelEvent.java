package drools.spring.example.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("15m")
public class OxygenLevelEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date executionTime;
	private Long patientId;
	private Integer level;

	public OxygenLevelEvent() {
		super();
		this.executionTime = new Date();
	}

	public OxygenLevelEvent(Long patientId, Integer level) {
		super();
		this.executionTime = new Date();
		this.patientId = patientId;
		this.level = level;
	}



	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
