package edu.dhu.model;

import javax.persistence.*;

/**
 * Questions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "questions", catalog = "gdoj")
public class Questions {

	// Fields
	private Integer id;
	private String question;

	// Constructors

	/** default constructor */
	public Questions() {
	}

	public Questions(Integer id, String question) {
		this.id = id;
		this.question = question;
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

	@Column(name = "question", length = 50)
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
