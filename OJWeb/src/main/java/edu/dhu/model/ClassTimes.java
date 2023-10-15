package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Classes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "class_times", catalog = "gdoj")
public class ClassTimes implements java.io.Serializable {

	// Fields

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

	// Constructors

	/** default constructor */
	public ClassTimes() {
	}

	/** full constructor */
	public ClassTimes(Integer class_id, Date class_date, Date start_time, Date end_time, Integer advance, Integer late,
			Integer reject, Integer attend_count, Integer leave_count, Integer absence_count, Date create_time,
			Date update_time) {
		this.class_id = class_id;
		this.class_date = class_date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.advance = advance;
		this.late = late;
		this.reject = reject;
		this.attend_count = attend_count;
		this.leave_count = leave_count;
		this.absence_count = absence_count;
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
	
	@Column(name = "class_id",  nullable = false)
	public Integer getClass_id() {
		return this.class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	@Column(name = "class_date",  nullable = false)
	public Date getClass_date() {
		return this.class_date;
	}

	public void setClass_date(Date class_date) {
		this.class_date = class_date;
	}

	@Column(name = "start_time",  nullable = false)
	public Date getStart_time() {
		return this.start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	@Column(name = "end_time",  nullable = false)
	public Date getEnd_time() {
		return this.end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	@Column(name = "advance",  nullable = false)
	public Integer getAdvance() {
		return this.advance;
	}

	public void setAdvance(Integer advance) {
		this.advance = advance;
	}

	@Column(name = "late",  nullable = false)
	public Integer getLate() {
		return this.late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}

	@Column(name = "reject",  nullable = false)
	public Integer getReject() {
		return this.reject;
	}

	public void setReject(Integer reject) {
		this.reject = reject;
	}

	@Column(name = "attend_count",  nullable = false)
	public Integer getAttend_count() {
		return this.attend_count;
	}

	public void setAttend_count(Integer attend_count) {
		this.attend_count = attend_count;
	}

	@Column(name = "leave_count",  nullable = false)
	public Integer getLeave_count() {
		return this.leave_count;
	}

	public void setLeave_count(Integer leave_count) {
		this.leave_count = leave_count;
	}

	@Column(name = "absence_count",  nullable = false)
	public Integer getAbsence_count() {
		return this.absence_count;
	}

	public void setAbsence_count(Integer absence_count) {
		this.absence_count = absence_count;
	}

	@Column(name = "create_time",  nullable = false)
	public Date getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time",  nullable = false)
	public Date getUpdate_time() {
		return this.update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	

}