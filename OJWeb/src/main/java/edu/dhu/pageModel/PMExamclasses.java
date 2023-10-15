package edu.dhu.pageModel;

public class PMExamclasses implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6196677015794382128L;
	private int id;
	private int classId;
	private int examId;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getClassId() {
		return classId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getExamId() {
		return examId;
	}

}
