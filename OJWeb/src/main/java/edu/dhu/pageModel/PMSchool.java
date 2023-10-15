package edu.dhu.pageModel;

public class PMSchool implements java.io.Serializable {
	private static final long serialVersionUID = 1538275049944L;

	private Integer id;
	private String name;

	private String oldSchoolName;
	private String operate;
	private String teacherName;
	private String email;
	private String reason;

	public PMSchool() {
	}

	public PMSchool(Integer id, String name, String oldSchoolName,
			String operate, String teacherName, String email, String reason) {
		this.id = id;
		this.name = name;
		this.oldSchoolName = oldSchoolName;
		this.operate = operate;
		this.teacherName = teacherName;
		this.email = email;
		this.reason = reason;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldSchoolName() {
		return oldSchoolName;
	}

	public void setOldSchoolName(String oldSchoolName) {
		this.oldSchoolName = oldSchoolName;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
