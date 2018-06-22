package drools.spring.example.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("12h")
public class UrinalSumEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date executionTime;
	private Long patientId;
	private Integer amount;
	
	public UrinalSumEvent() {
		super();
		this.executionTime = new Date();
	}

	public UrinalSumEvent(Long patientId, Integer amount) {
		super();
		this.executionTime = new Date();
		this.patientId = patientId;
		this.amount = amount;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	
	

}
