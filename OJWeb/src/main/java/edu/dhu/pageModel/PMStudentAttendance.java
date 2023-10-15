package edu.dhu.pageModel;

import java.util.Date;

public class PMStudentAttendance implements java.io.Serializable {

	
	private Integer id;
	private String studentname;
	private Integer user_id;
	private Integer class_id;
	private Integer class_times_id;
	private Integer status;
	private Date create_time;
	private Date update_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getClass_id() {
		return class_id;
	}
	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}
	public Integer getClass_times_id() {
		return class_times_id;
	}
	public void setClass_times_id(Integer class_times_id) {
		this.class_times_id = class_times_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
}
