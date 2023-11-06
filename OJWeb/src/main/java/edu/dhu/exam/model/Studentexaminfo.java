package edu.dhu.exam.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Studentexaminfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "studentexaminfo", catalog = "gdoj")
public class Studentexaminfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4654077708941593793L;
	private Integer id;
	private Integer userId;
	private Integer examId;
	private Integer solved;
	private Integer submit;
	private float score;
	@Column(name = "`rank`")
	private Integer rank;
	private Date submitTime;
	private Integer elapsedTime;
	private String loginIp;
	private Date firstloginTime;
	private String loginUUID;
	private Integer currentCat;
	private Integer currentProb;
	private String message;

	// Constructors

	/** default constructor */
	public Studentexaminfo() {
	}

	/** minimal constructor */
	public Studentexaminfo(Integer userId, Integer examId) {
		this.userId = userId;
		this.examId = examId;
	}

	/** full constructor */
	public Studentexaminfo(Integer id, Integer userId, Integer examId, Integer solved, Integer submit, float score,
                           Integer rank, Date submitTime, Integer elapsedTime, String loginIp, Date firstloginTime, String loginUUID,
                           Integer currentCat, Integer currentProb, String message) {
		super();
		this.id = id;
		this.userId = userId;
		this.examId = examId;
		this.solved = solved;
		this.submit = submit;
		this.score = score;
		this.rank = rank;
		this.submitTime = submitTime;
		this.elapsedTime = elapsedTime;
		this.loginIp = loginIp;
		this.firstloginTime = firstloginTime;
		this.loginUUID = loginUUID;
		this.currentCat = currentCat;
		this.currentProb = currentProb;
		this.message = message;
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

	@Column(name = "solved")
	public Integer getSolved() {
		return this.solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	@Column(name = "submit")
	public Integer getSubmit() {
		return this.submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Column(name = "`rank`")
	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "submitTime", length = 19)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getElapsedTime() {
		return elapsedTime;
	}

	@Column(name = "elapsedTime")
	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	@Column(name = "loginIp")
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getFirstloginTime() {
		return firstloginTime;
	}

	@Column(name = "firstloginTime")
	public void setFirstloginTime(Date firstloginTime) {
		this.firstloginTime = firstloginTime;
	}

	public String getLoginUUID() {
		return loginUUID;
	}

	@Column(name = "loginUUID")
	public void setLoginUUID(String loginUUID) {
		this.loginUUID = loginUUID;
	}

	public Integer getCurrentCat() {
		return currentCat;
	}

	@Column(name = "currentCat")
	public void setCurrentCat(Integer currentCat) {
		this.currentCat = currentCat;
	}

	public Integer getCurrentProb() {
		return currentProb;
	}

	@Column(name = "currentProb")
	public void setCurrentProb(Integer currentProb) {
		this.currentProb = currentProb;
	}

	public String getMessage() {
		return message;
	}
	
	@Column(name = "message")
	public void setMessage(String message) {
		this.message = message;
	}

	
}