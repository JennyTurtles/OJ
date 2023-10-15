package edu.dhu.pageModel;

import java.util.List;

public class PMExamScore2 implements java.io.Serializable {
	// 用于存放acm格式的考试成绩页面显示对象
	// 该格式中每个对象最多10道题
	//
	private static final long serialVersionUID = 1538274347532L;
	
	private int id;
	private int rank;
	private int userId;
	private String studentNo;
	private String chineseName;
	private String banji;
	private float score;
	private int solve;
	private int submit;
	private List<String> problemSubInfo; // 存放呢提交次数/所用时间的字符串
	private List solvedproblem;
	private List<String> problemScores; // 提交的solution得分
	private List<String> problemStatus; // 提交的solution状态

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public String getBanji() {
		return banji;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getScore() {
		return score;
	}

	public void setSolve(int solve) {
		this.solve = solve;
	}

	public int getSolve() {
		return solve;
	}

	public void setSubmit(int submit) {
		this.submit = submit;
	}

	public int getSubmit() {
		return submit;
	}

	public void setProblemSubInfo(List<String> problemSubInfo) {
		this.problemSubInfo = problemSubInfo;
	}

	public List<String> getProblemSubInfo() {
		return problemSubInfo;
	}

	public void setSolvedproblem(List solvedproblem) {
		this.solvedproblem = solvedproblem;
	}

	public List getSolvedproblem() {
		return solvedproblem;
	}

	public void setProblemScores(List<String> problemScores) {
		this.problemScores = problemScores;
	}

	public List<String> getProblemScores() {
		return problemScores;
	}

	public void setProblemStatus(List<String> problemStatus) {
		this.problemStatus = problemStatus;
	}

	public List<String> getProblemStatus() {
		return problemStatus;
	}

}
