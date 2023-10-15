package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Examproblems entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper_exam_problems", catalog = "gdoj")
public class PaperExamproblems implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2582164109594535407L;
	private Integer id;
	private Integer examId;
	private Integer problemId;
	private float score;
	private Integer displaySequence;
	private Date createTime;
	private Date updateTime;

	// Constructors

	/** default constructor */
	public PaperExamproblems() {
	}

	/** minimal constructor */
	public PaperExamproblems(Integer examId, Integer problemId) {
		this.examId = examId;
		this.problemId = problemId;
	}

	/** full constructor */
	public PaperExamproblems(Integer examId, Integer problemId, float score,
			Integer displaySequence,Date createTime,
			Date updateTime) {
		this.examId = examId;
		this.problemId = problemId;
		this.score = score;
		this.displaySequence = displaySequence;
		this.updateTime = updateTime;
		this.createTime = createTime;
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

	@Column(name = "exam_id", nullable = false)
	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "problem_id", nullable = false)
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

	@Column(name = "display_sequence")
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


	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}