package edu.dhu.exam.model;

import javax.persistence.*;

/**
 * Examclasses entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "examclasses", catalog = "gdoj")
public class Examclasses implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8097055874544999699L;
	private Integer id;
	private Integer examId;
	private Integer classId;

	// Constructors

	/** default constructor */
	public Examclasses() {
	}

	/** full constructor */
	public Examclasses(Integer examId, Integer classId) {
		this.examId = examId;
		this.classId = classId;
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

	@Column(name = "classId", nullable = false)
	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

}