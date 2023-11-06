package edu.dhu.problem.model;

public class PMProblemsStatus implements java.io.Serializable {
	private static final long serialVersionUID = 1538274953194L;

	// 用户ID
	private int userId;
	// 考试ID
	private int examId;
	// 题目ID数组
	private int[] problemIdArr;
	// 保存对应的题目状态
	private String[] statusArr;
	// 保存对应的题目是否完成
	private boolean[] finishedArr;

	public PMProblemsStatus() {
	}

	public PMProblemsStatus(int userId, int examId, int[] problemIdArr,
                            String[] statusArr, boolean[] finishedArr) {
		this.userId = userId;
		this.examId = examId;
		this.problemIdArr = problemIdArr;
		this.statusArr = statusArr;
		this.finishedArr = finishedArr;
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

	public int[] getProblemIdArr() {
		return problemIdArr;
	}

	public void setProblemIdArr(int[] problemIdArr) {
		this.problemIdArr = problemIdArr;
	}

	public String[] getStatusArr() {
		return statusArr;
	}

	public void setStatusArr(String[] statusArr) {
		this.statusArr = statusArr;
	}

	public boolean[] getFinishedArr() {
		return finishedArr;
	}

	public void setFinishedArr(boolean[] finishedArr) {
		this.finishedArr = finishedArr;
	}

}
