package edu.dhu.model;

import javax.persistence.*;

@Entity
@Table(name = "assistantClass", catalog = "gdoj")
public class AssistantClass implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer assistantId;
	private Integer classId;

	public AssistantClass() {
	}

	public AssistantClass(Integer assistantId, Integer classId) {
		this.assistantId = assistantId;
		this.classId = classId;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "assistantId", nullable = false)
	public Integer getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(Integer assistantId) {
		this.assistantId = assistantId;
	}

	@Column(name = "classId", nullable = false)
	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

}
