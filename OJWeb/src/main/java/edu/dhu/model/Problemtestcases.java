package edu.dhu.model;

import javax.persistence.*;

/**
 * Problemtestcases entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "problemtestcases", catalog = "gdoj")
public class Problemtestcases implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1045069276456042621L;
	private Integer id;
	private Integer problemId;
	private String input;
	private String output;

	// Constructors

	/** default constructor */
	public Problemtestcases() {
	}

	/** minimal constructor */
	public Problemtestcases(Integer problemId) {
		this.problemId = problemId;
	}

	/** full constructor */
	public Problemtestcases(Integer problemId, String input, String output) {
		this.problemId = problemId;
		this.input = input;
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

	@Column(name = "problemId", nullable = false)
	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "input", length = 8000)
	public String getInput() {
		return this.input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Column(name = "output", length = 8000)
	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}