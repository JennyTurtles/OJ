package edu.dhu.pageModel;

public class PMGradeProblem implements java.io.Serializable {
	private static final long serialVersionUID = 1538274855589L;
	
	private int userId;
	private int examId;
	private int problemId;
	private String status;

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

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
