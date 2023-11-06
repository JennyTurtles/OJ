package edu.dhu.problem.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "studenttrainprobdetail", catalog = "gdoj")
public class StudentTrainProbDetail implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7188512925576415449L;
	private Integer id;
	private Integer userId;
	private Integer examId;
	private Integer catId;
	private Integer problemId;
	private Integer solutionId;
	private Integer submit;
	private String status;
	private String hintCases;
	private float scoreCoef;
	private float points;
	private Integer elapsedTime;
	private boolean finished;
	private Date startTime;
	private Date lastSubmitTime;
	private Integer commentClick;
	
	public StudentTrainProbDetail() {
		
	}
	
	public StudentTrainProbDetail(Integer id, Integer userId, Integer examId, Integer catId, Integer problemId,
                                  Integer solutionId, Integer submit, String status, String hintCases, float scoreCoef, float points,
                                  Integer elapsedTime, boolean finished, Date startTime, Date lastSubmitTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.examId = examId;
		this.catId = catId;
		this.problemId = problemId;
		this.solutionId = solutionId;
		this.submit = submit;
		this.status = status;
		this.hintCases = hintCases;
		this.scoreCoef = scoreCoef;
		this.points = points;
		this.elapsedTime = elapsedTime;
		this.finished = finished;
		this.startTime = startTime;
		this.lastSubmitTime = lastSubmitTime;
	}


	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "examId", nullable = false)
	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "catId", nullable = false)
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	@Column(name = "problemId", nullable = false)
	public Integer getProblemId() {
		return problemId;
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
		return submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	@Column(name = "status", length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "hintCases", length = 100)
	public String getHintCases() {
		return hintCases;
	}

	public void setHintCases(String hintCases) {
		this.hintCases = hintCases;
	}

	@Column(name = "scoreCoef")
	public float getScoreCoef() {
		return scoreCoef;
	}

	public void setScoreCoef(float scoreCoef) {
		this.scoreCoef = scoreCoef;
	}

	@Column(name = "points")
	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	@Column(name = "elapsedTime")
	public Integer getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public boolean isFinished() {
		return finished;
	}

	@Column(name = "finished")
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Date getStartTime() {
		return startTime;
	}

	@Column(name = "startTime")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastSubmitTime() {
		return lastSubmitTime;
	}

	@Column(name = "lastSubmitTime")
	public void setLastSubmitTime(Date lastSubmitTime) {
		this.lastSubmitTime = lastSubmitTime;
	}

	public Integer getCommentClick() {
		return commentClick;
	}

	@Column(name = "commentClick")
	public void setCommentClick(Integer commentClick) {
		this.commentClick = commentClick;
	}
	
	
}
