package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student_attendance", catalog = "gdoj")
public class StudentAttendance implements java.io.Serializable{
	
	private Integer id;
	private Integer user_id;
	private Integer class_id;
	private Integer class_times_id;
	private Integer status;
	private Date create_time;
	private Date update_time;
	// Constructors

	/** default constructor */
	public StudentAttendance() {
	}

	/** minimal constructor */
	public StudentAttendance(Integer id, Integer user_id, Integer class_id, Integer class_times_id, Integer status,
			Date create_time, Date update_time) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.class_id = class_id;
		this.class_times_id = class_times_id;
		this.status = status;
		this.create_time = create_time;
		this.update_time = update_time;
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

	@Column(name = "user_id", nullable = false)
	public Integer getUser_id() {
		return this.user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	@Column(name = "class_id", nullable = false)
	public Integer getClass_id() {
		return this.class_id;
	}

	
	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}
	
	@Column(name = "class_times_id", nullable = false)
	public Integer getClass_times_id() {
		return this.class_times_id;
	}

	public void setClass_times_id(Integer class_times_id) {
		this.class_times_id = class_times_id;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "create_time", nullable = false)
	public Date getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time", nullable = false)
	public Date getUpdate_time() {
		return this.update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
	
}
