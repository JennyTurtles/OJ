package edu.dhu.problem.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Examproblems entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "examproblems", catalog = "gdoj")
public class Examproblems implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2582164109594535407L;
	private Integer id;
	private Integer examId;
	private Integer problemId;
	private float score;
	//截止日期
	private Date deadline;
	//打折系数
	private float scoreCoef;
	//最佳日期
	private Date bestBefore;
	private Integer displaySequence;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public Examproblems() {
	}

	/** minimal constructor */
	public Examproblems(Integer examId, Integer problemId) {
		this.examId = examId;
		this.problemId = problemId;
	}

	/** full constructor */
	public Examproblems(Integer examId, Integer problemId, float score,
                        Date bestBefore, float scoreCoef, Integer displaySequence,
                        Date updateTime) {
		this.examId = examId;
		this.problemId = problemId;
		this.score = score;
		this.bestBefore = bestBefore;
		this.scoreCoef = scoreCoef;
		this.displaySequence = displaySequence;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "examId", nullable = false)
	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "problemId", nullable = false)
	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Column(name = "deadline", length = 19)
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public float getScoreCoef() {
		return scoreCoef;
	}

	public void setScoreCoef(float scoreCoef) {
		this.scoreCoef = scoreCoef;
	}

	public Date getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}

	@Column(name = "displaySequence")
	public Integer getDisplaySequence() {
		return this.displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}