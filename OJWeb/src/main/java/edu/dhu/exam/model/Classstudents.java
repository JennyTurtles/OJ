package edu.dhu.exam.model;

import javax.persistence.*;

/**
 * Classstudents entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "classstudents", catalog = "gdoj")
public class Classstudents implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050828329203855066L;
	private Integer id;
	private Integer classId;
	private Integer userId;

	// Constructors

	/** default constructor */
	public Classstudents() {
	}

	/** full constructor */
	public Classstudents(Integer classId, Integer userId) {
		this.classId = classId;
		this.userId = userId;
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

	@Column(name = "classId", nullable = false)
	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}