package edu.dhu.model;

import javax.persistence.*;

/**
 * Studentexamdetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "studentexamdetail", catalog = "gdoj")
public class Studentexamdetail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2333371750426586719L;
	private Integer id;
	private Integer userId;
	private Integer examId;
	private Integer problemId;
	private Integer solutionId;
	private Integer submit;
	private String status;
	private String hintCases;
	private float score;
	private boolean finished;
	private Integer elapsedTime;
	private Integer commentClick;

	// Constructors

	/** default constructor */
	public Studentexamdetail() {
	}

	/** minimal constructor */
	public Studentexamdetail(Integer userId, Integer examId, Integer problemId,
			Integer solutionId) {
		this.userId = userId;
		this.examId = examId;
		this.problemId = problemId;
		this.solutionId = solutionId;
	}

	public Studentexamdetail(Integer id, Integer userId, Integer examId,
			Integer problemId, Integer solutionId, Integer submit,
			String status, String hintCases, float score, boolean finished,
			Integer elapsedTime) {
		this.id = id;
		this.userId = userId;
		this.examId = examId;
		this.problemId = problemId;
		this.solutionId = solutionId;
		this.submit = submit;
		this.status = status;
		this.hintCases = hintCases;
		this.score = score;
		this.finished = finished;
		this.elapsedTime = elapsedTime;

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

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	@Column(name = "solutionId")
	public Integer getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name = "submit")
	public Integer getSubmit() {
		return this.submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	@Column(name = "status", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "hintCases", length = 100)
	public String getHintCases() {
		return this.hintCases;
	}

	public void setHintCases(String hintCases) {
		this.hintCases = hintCases;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public boolean isFinished() {
		return finished;
	}

	@Column(name = "finished")
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Integer getElapsedTime() {
		return elapsedTime;
	}

	@Column(name = "elapsedTime")
	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Integer getCommentClick() {
		return commentClick;
	}

	@Column(name = "commentClick")
	public void setCommentClick(Integer commentClick) {
		this.commentClick = commentClick;
	}
	
	
	
}