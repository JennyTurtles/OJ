package edu.dhu.problem.model;

import javax.persistence.*;

/**
 * Chapters entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chapters", catalog = "gdoj")
public class Chapters implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 9190703839845116503L;
	private Integer id;
	private String code;
	private String name;
	private Integer level;

	// Constructors

	/** default constructor */
	public Chapters() {
	}

	/** full constructor */
	public Chapters(String name) {
		this.name = name;
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

	@Column(name = "code", nullable = false, length = 5)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "level", nullable = false, length = 1)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}