package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Classes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "classes", catalog = "gdoj")
public class Classes implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4577366061004245861L;
	private Integer id;
	private String name;
	private Integer teacherId;
	private Integer studentNum;
	private Date createTime;
	private String inviteCode;
	private String weektime;
	private Integer advance;
	private Integer late;
	private Integer reject;
	private Date first_week_monday;

	// Constructors

	/** default constructor */
	public Classes() {
	}

	/** minimal constructor */
	public Classes(String name, Integer teacherId, Integer studentNum) {
		this.name = name;
		this.teacherId = teacherId;
		this.studentNum = studentNum;
	}

	/** full constructor */
	public Classes(String name, Integer teacherId, Integer studentNum,
			Date createTime,String inviteCode) {
		this.name = name;
		this.teacherId = teacherId;
		this.studentNum = studentNum;
		this.createTime = createTime;
		this.inviteCode=inviteCode;
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

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "teacherId", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "studentNum", nullable = false)
	public Integer getStudentNum() {
		return this.studentNum;
	}

	public void setStudentNum(Integer studentNum) {
		this.studentNum = studentNum;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "inviteCode", length = 10)
	public String getInviteCode() {
		return inviteCode;
	}
	@Column(name = "weektime", nullable = false)
	public String getWeektime() {
		return weektime;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public void setWeektime(String weektime) {
		this.weektime = weektime;
	}
	
	@Column(name = "advance", nullable = false)
	public Integer getAdvance() {
		return advance;
	}

	public void setAdvance(Integer advance) {
		this.advance = advance;
	}
	
	@Column(name = "late", nullable = false)
	public Integer getLate() {
		return late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}

	@Column(name = "first_week_monday")
	public Date getFirst_week_monday() {
		return first_week_monday;
	}

	public void setFirst_week_monday(Date first_week_monday) {
		this.first_week_monday = first_week_monday;
	}
	
	@Column(name = "reject")
	public Integer getReject() {
		return reject;
	}

	public void setReject(Integer reject) {
		this.reject = reject;
	}
	

}