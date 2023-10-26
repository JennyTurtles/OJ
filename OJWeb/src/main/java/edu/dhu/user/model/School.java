package edu.dhu.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(name = "school", catalog = "gdoj")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class School {

	private Integer id;
	private String name;

	public School() {
	}

	public School(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
