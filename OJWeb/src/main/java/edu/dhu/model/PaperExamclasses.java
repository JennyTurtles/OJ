package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Examclasses entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper_exam_class", catalog = "gdoj")
public class PaperExamclasses implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8097055874544999699L;
	private Integer id;
	private Integer paperexamId;
	private Integer classId;
	private Date createTime;
	private Date updateTime;
	// Constructors

	/** default constructor */
	public PaperExamclasses() {
	}

	/** full constructor */
	public PaperExamclasses(Integer paperexamId, Integer classId) {
		this.paperexamId = paperexamId;
		this.classId = classId;
	}
	
	/** full constructor */
	public PaperExamclasses(Integer paperexamId, Integer classId,Date createTime,
			Date updateTime) {
		this.paperexamId = paperexamId;
		this.classId = classId;
		this.updateTime = updateTime;
		this.createTime = createTime;
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

	@Column(name = "paper_Exam_id", nullable = false)
	public Integer getPaperexamId() {
		return this.paperexamId;
	}

	public void setPaperexamId(Integer paperexamId) {
		this.paperexamId = paperexamId;
	}


	@Column(name = "class_id", nullable = false)
	public Integer getClassId() {
		return this.classId;
	}

	
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	
	@Column(name = "update_time", nullable = false)
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "create_time", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}