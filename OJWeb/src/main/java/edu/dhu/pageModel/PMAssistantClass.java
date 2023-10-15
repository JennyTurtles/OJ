package edu.dhu.pageModel;

public class PMAssistantClass implements java.io.Serializable {
	private static final long serialVersionUID = 1538274574086L;
	
	private Integer id;
	private Integer assistantId;
	private Integer classId;

	public PMAssistantClass() {
	}

	public PMAssistantClass(Integer id, Integer assistantId, Integer classId) {
		this.id = id;
		this.assistantId = assistantId;
		this.classId = classId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssistantId() {
		return assistantId;
	}

	public void setAssistantId(Integer assistantId) {
		this.assistantId = assistantId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setExamId(Integer classId) {
		this.classId = classId;
	}

}
