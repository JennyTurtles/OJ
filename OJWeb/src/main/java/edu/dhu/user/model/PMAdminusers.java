package edu.dhu.user.model;

public class PMAdminusers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1523485661753750121L;
	private int id;
	private String account;
	private String password;
	private String name;
	private String role;
	private String Email;
	private String examIds;
	private int teacherId;
	private int schoolId;
	private int active;
	private String classIds;
	private String uuid;
	private String schoolname;
	private String question;
	private String answer;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setEmail(String Email) {
		this.Email = Email;

	}

	public String getEmail() {
		return Email;
	}

	public void setExamIds(String examIds) {
		this.examIds = examIds;
	}

	public String getExamIds() {
		return examIds;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}

	public String getClassIds() {
		return classIds;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
