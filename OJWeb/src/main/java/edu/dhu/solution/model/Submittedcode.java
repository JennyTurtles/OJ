package edu.dhu.solution.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Submittedcode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "submittedcode")
public class Submittedcode implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6232326943824293403L;
	private Integer id;
	private Integer solutionId;
	private Integer problemId;
	private String processedCode1;
	private String processedCode2;
	private Integer schoolId;
	private Date time;

	// Constructors

	/** default constructor */
	public Submittedcode() {
	}

	/** full constructor */
	public Submittedcode(Integer solutionId, Integer problemId,
                         String processedCode1, String processedCode2, Integer schoolId,
                         Date time) {
		this.solutionId = solutionId;
		this.problemId = problemId;
		this.processedCode1 = processedCode1;
		this.processedCode2 = processedCode2;
		this.schoolId = schoolId;
		this.time = time;
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

	@Column(name = "solutionId")
	public Integer getSolutionId() {
		return this.solutionId;
	}

	@Column(name = "schoolId")
	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name = "time")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name = "problemId")
	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "processedCode1", length = 8000)
	public String getProcessedCode1() {
		return this.processedCode1;
	}

	public void setProcessedCode1(String processedCode1) {
		this.processedCode1 = processedCode1;
	}

	@Column(name = "processedCode2", length = 8000)
	public String getProcessedCode2() {
		return this.processedCode2;
	}

	public void setProcessedCode2(String processedCode2) {
		this.processedCode2 = processedCode2;
	}

}