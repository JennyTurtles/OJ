package edu.dhu.pageModel;

import java.util.Date;

public class PMClassTimes implements java.io.Serializable {
	/**
	 * 
	 */
	private Integer id;
	private Integer class_id;
	private Date class_date;
	private Date start_time;
	private Date end_time;
	private Integer advance;
	private Integer late;
	private Integer reject;
	private Integer attend_count;
	private Integer leave_count;
	private Integer absence_count;
	private Date create_time;
	private Date update_time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getClass_id() {
		return class_id;
	}
	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}
	public Date getClass_date() {
		return class_date;
	}
	public void setClass_date(Date class_date) {
		this.class_date = class_date;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Integer getAdvance() {
		return advance;
	}
	public void setAdvance(Integer advance) {
		this.advance = advance;
	}
	public Integer getLate() {
		return late;
	}
	public void setLate(Integer late) {
		this.late = late;
	}
	public Integer getReject() {
		return reject;
	}
	public void setReject(Integer reject) {
		this.reject = reject;
	}
	public Integer getAttend_count() {
		return attend_count;
	}
	public void setAttend_count(Integer attend_count) {
		this.attend_count = attend_count;
	}
	public Integer getLeave_count() {
		return leave_count;
	}
	public void setLeave_count(Integer leave_count) {
		this.leave_count = leave_count;
	}
	public Integer getAbsence_count() {
		return absence_count;
	}
	public void setAbsence_count(Integer absence_count) {
		this.absence_count = absence_count;
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
