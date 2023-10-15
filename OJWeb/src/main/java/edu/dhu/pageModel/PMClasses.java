package edu.dhu.pageModel;

import java.util.Date;

public class PMClasses implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5372127226595718778L;
	private Integer id;
	private String name;
	private Integer teacherId;
	private String teacherName;
	private Integer studentNum;
	private Date createTime;
	private String inviteCode;

	private String weektime;
	private Integer advance;
	private Integer late;
	private Integer reject;
	private Date first_week_monday;
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
	public void setWeektime(String weektime) {
		this.weektime = weektime;
	}

	public String getWeektime() {
		return weektime;
	}
	
	

	public void setAdvance(Integer advance) {
		this.advance = advance;
	}
	
	public Integer getAdvance() {
		return advance;
	}


	public void setLate(Integer late) {
		this.late = late;
	}

	public Integer getLate() {
		return late;
	}
	

	public void setFirst_week_monday(Date first_week_monday) {
		this.first_week_monday = first_week_monday;
	}

	public Date getFirst_week_monday() {
		return first_week_monday;
	}

	public void setReject(Integer reject) {
		this.reject = reject;
	}
	
	public Integer getReject() {
		return reject;
	}
	
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

}
