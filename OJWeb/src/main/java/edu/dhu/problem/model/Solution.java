package edu.dhu.problem.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Solution entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "solution", catalog = "gdoj")
public class Solution implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5413191255477527289L;
	private Integer id;
	private Integer examId;
	private Integer problemId;
	private Integer userid;
	private Date submitTime;
	private String language;
	private String sourceCode;
	private Integer codelength;
	private String status;
	private String correctCaseIds;
	private float similarity;
	private Integer similarId;
	private float score;
	private String remark;

	// Constructors

	/** default constructor */
	public Solution() {
	}

	public Solution(Integer id, Integer examId, Integer problemId,
                    Integer userid, Date submitTime, String language,
                    String sourceCode, Integer codelength, String status,
                    String correctCaseIds, float similarity, Integer similarId,
                    float score, String remark) {
		this.id = id;
		this.examId = examId;
		this.problemId = problemId;
		this.userid = userid;
		this.submitTime = submitTime;
		this.language = language;
		this.sourceCode = sourceCode;
		this.codelength = codelength;
		this.status = status;
		this.correctCaseIds = correctCaseIds;
		this.similarity = similarity;
		this.similarId = similarId;
		this.score = score;
		this.remark = remark;
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

	@Column(name = "examId")
	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "problemId")
	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "submitTime", length = 19)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "language", length = 20)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "sourceCode", length = 8000)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Column(name = "codelength")
	public Integer getCodelength() {
		return this.codelength;
	}

	public void setCodelength(Integer codelength) {
		this.codelength = codelength;
	}

	@Column(name = "status", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "correctCaseIds", length = 500)
	public String getCorrectCaseIds() {
		return this.correctCaseIds;
	}

	public void setCorrectCaseIds(String correctCaseIds) {
		this.correctCaseIds = correctCaseIds;
	}

	@Column(name = "similarity", precision = 12, scale = 0)
	public float getSimilarity() {
		return this.similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	@Column(name = "similarId")
	public Integer getSimilarId() {
		return this.similarId;
	}

	public void setSimilarId(Integer similarId) {
		this.similarId = similarId;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Column(name = "remark", length = 8000)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}