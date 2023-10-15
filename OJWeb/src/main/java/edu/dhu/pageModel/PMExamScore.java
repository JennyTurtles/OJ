package edu.dhu.pageModel;

import java.util.List;

public class PMExamScore implements java.io.Serializable {
	
	private static final long serialVersionUID = 1538274175061L;

	private int id;
	private int rank;
	private int userId;
	private String studentNo;
	private String chineseName;
	private String banji;
	private float score;
	private int solve;
	private int submit;
	private List<String> submited;
	private List<String> doing;
	private List<String> undo;

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

	public void setSubmited(List<String> submited) {
		this.submited = submited;
	}

	public List<String> getSubmited() {
		return submited;
	}

	public void setDoing(List<String> doing) {
		this.doing = doing;
	}

	public List<String> getDoing() {
		return doing;
	}

	public void setUndo(List<String> undo) {
		this.undo = undo;
	}

	public List<String> getUndo() {
		return undo;
	}

}
