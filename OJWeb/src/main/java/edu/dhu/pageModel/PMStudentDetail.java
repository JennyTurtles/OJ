package edu.dhu.pageModel;

import java.util.Date;

public class PMStudentDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7341091262413535148L;
	private  Integer userId;
	private Integer problemId;
	private Integer submitNum;
	public Integer getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(Integer submitNum) {
		this.submitNum = submitNum;
	}
	private float score;
	private float dateScore;
	public float getDateScore() {
		return dateScore;
	}
	public void setDateScore(float dateScore) {
		this.dateScore = dateScore;
	}
	private Date  submitTime;
	
	public  PMStudentDetail() {
		
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProblemId() {
		return problemId;
	}
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}  
}
