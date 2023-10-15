package edu.dhu.pageModel;

public class PMExamCheck implements java.io.Serializable {
	private static final long serialVersionUID = 6381413733402100551L;

	private int userId;
	private int examId;

	public PMExamCheck() {
	}

	public PMExamCheck(int userId, int examId) {
		this.userId = userId;
		this.examId = examId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

}
