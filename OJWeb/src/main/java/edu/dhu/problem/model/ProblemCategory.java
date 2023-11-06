package edu.dhu.problem.model;

import javax.persistence.*;

@Entity
@Table(name = "problemcategory", catalog = "gdoj")
public class ProblemCategory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2477135372917349556L;
	
	private Integer id;
	private String name;
	//父类id，没有则为0
	private Integer parentId;
	//描述信息
	private String description;
	//题目数量
	private Integer problemNum;
	
	
	
	public ProblemCategory() {
		
	}
	
	
	public ProblemCategory(String name, Integer parentId, String description, Integer problemNum) {
		super();
		this.name = name;
		this.parentId = parentId;
		this.description = description;
		this.problemNum = problemNum;
	}


	public ProblemCategory(Integer id, String name, Integer parentId, String description, Integer problemNum) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.description = description;
		this.problemNum = problemNum;
	}


	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
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


	@Column(name = "parentId", nullable = false)
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "description", length = 1000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "problemNum", nullable = false)
	public Integer getProblemNum() {
		return problemNum;
	}
	public void setProblemNum(Integer problemNum) {
		this.problemNum = problemNum;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
