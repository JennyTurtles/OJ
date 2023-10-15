package edu.dhu.model;

import javax.persistence.*;

@Entity
@Table(name = "itrainproblems", catalog = "gdoj")
public class Itrainproblems implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6333947357674877127L;
	

	private Integer id;
	
	private Integer examId;
	
	// 二级类型id
	private Integer catId;
	
	//题目的id
	private Integer problemId;

	// 是否为必选题
	private Integer mandatory;
	
	//做题时间
	private float duration;
	
	//提交人数
	private Integer commitPerNum;
	
	public Itrainproblems() {
		super();
	}

	public Itrainproblems(Integer id, Integer examId, Integer catId, Integer problemId, Integer mandatory,
			float duration, Integer commitPerNum) {
		super();
		this.id = id;
		this.examId = examId;
		this.catId = catId;
		this.problemId = problemId;
		this.mandatory = mandatory;
		this.duration = duration;
		this.commitPerNum = commitPerNum;
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

	@Column(name = "examId", nullable = false)
	public Integer getExamId() {
		return examId;
	}


	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "catId", nullable = false)
	public Integer getCatId() {
		return catId;
	}


	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	@Column(name = "problemId", nullable = false)
	public Integer getProblemId() {
		return problemId;
	}


	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "mandatory")
	public Integer getMandatory() {
		return mandatory;
	}

	public void setMandatory(Integer mandatory) {
		this.mandatory = mandatory;
	}

	@Column(name = "duration")
	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@Column(name = "commitPerNum")
	public Integer getCommitPerNum() {
		return commitPerNum;
	}


	public void setCommitPerNum(Integer commitPerNum) {
		this.commitPerNum = commitPerNum;
	}
	
	
}
