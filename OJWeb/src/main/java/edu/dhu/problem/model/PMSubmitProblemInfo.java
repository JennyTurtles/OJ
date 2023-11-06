package edu.dhu.problem.model;

import java.util.Date;

public class PMSubmitProblemInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1538275169095L;
	
	private Integer id;
	private Integer examId;
	private Integer problemId;
	private Date submitTime;
	private boolean isOverSimilarity;// 判断是否抄袭
	private boolean ifSubmit;// 用来判断抄袭后是否真的坚持提交
	private float similarity;
	private Integer similarId;
	private float score;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getProblemId() {
		return problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public boolean isOverSimilarity() {
		return isOverSimilarity;
	}

	public void setOverSimilarity(boolean isOverSimilarity) {
		this.isOverSimilarity = isOverSimilarity;
	}

	public boolean isIfSubmit() {
		return ifSubmit;
	}

	public void setIfSubmit(boolean ifSubmit) {
		this.ifSubmit = ifSubmit;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public Integer getSimilarId() {
		return similarId;
	}

	public void setSimilarId(Integer similarId) {
		this.similarId = similarId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
