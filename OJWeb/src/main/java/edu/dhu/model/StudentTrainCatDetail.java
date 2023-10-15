package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "studenttraincatdetail", catalog = "gdoj")
public class StudentTrainCatDetail implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7821366937345549447L;
	private Integer id;
	private Integer userId;
	private Integer examId;
	private Integer catId;
	private Integer submit;
	private float points;
	private float score;
	private Integer elapsedTime;
	private boolean finished;
	private Integer currentProb;
	private String probSequence;
	private String probFinished;
	private Integer lastProb;
	private Date passTime;
	
	
	public StudentTrainCatDetail() {
		
	}


	

	public StudentTrainCatDetail(Integer id, Integer userId, Integer examId, Integer catId, Integer submit,
			float points, float score, Integer elapsedTime, boolean finished, Integer currentProb, String probSequence,
			String probFinished, Integer lastProb) {
		super();
		this.id = id;
		this.userId = userId;
		this.examId = examId;
		this.catId = catId;
		this.submit = submit;
		this.points = points;
		this.score = score;
		this.elapsedTime = elapsedTime;
		this.finished = finished;
		this.currentProb = currentProb;
		this.probSequence = probSequence;
		this.probFinished = probFinished;
		this.lastProb = lastProb;
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

	@Column(name = "submit")
	public Integer getSubmit() {
		return submit;
	}


	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	@Column(name = "points")
	public float getPoints() {
		return points;
	}


	public void setPoints(float points) {
		this.points = points;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public float getScore() {
		return score;
	}


	public void setScore(float score) {
		this.score = score;
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


	@Column(name = "CurrentProb")
	public Integer getCurrentProb() {
		return currentProb;
	}


	public void setCurrentProb(Integer currentProb) {
		this.currentProb = currentProb;
	}

	@Column(name = "ProbSequence")
	public String getProbSequence() {
		return probSequence;
	}


	public void setProbSequence(String probSequence) {
		this.probSequence = probSequence;
	}

	@Column(name = "ProbFinished")
	public String getProbFinished() {
		return probFinished;
	}


	public void setProbFinished(String probFinished) {
		this.probFinished = probFinished;
	}

	@Column(name = "lastProb")
	public Integer getLastProb() {
		return lastProb;
	}


	public void setLastProb(Integer lastProb) {
		this.lastProb = lastProb;
	}


	@Column(name = "passTime")
	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	
	
}
