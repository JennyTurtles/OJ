package edu.dhu.model;

import javax.persistence.*;

@Entity
@Table(name = "school", catalog = "gdoj")
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
