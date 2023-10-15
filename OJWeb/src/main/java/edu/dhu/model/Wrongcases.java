package edu.dhu.model;

import javax.persistence.*;

/**
 * Wrongcases entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "wrongcases", catalog = "gdoj")
public class Wrongcases implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5175657714930673172L;
	private Integer id;
	private Integer solutionId;
	private Integer caseId;
	private String output;

	// Constructors

	/** default constructor */
	public Wrongcases() {
	}

	/** full constructor */
	public Wrongcases(Integer solutionId, Integer caseId, String output) {
		this.solutionId = solutionId;
		this.caseId = caseId;
		this.output = output;
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

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name = "caseId")
	public Integer getCaseId() {
		return this.caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	@Column(name = "output", length = 8000)
	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}