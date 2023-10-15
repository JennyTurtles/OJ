package edu.dhu.pageModel;

public class PMPaperExamScore implements java.io.Serializable {
	

	private int id;
	private int problem_id;
	private int userId;
	private int times;
	private int type;
	private String content;
	private String studentNo;
	private String username;
	private String chineseName;
	private int classId;
	private String banji;
	private float score;
	private int solve;
	private int submit;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public int getProblem_id() {
		return problem_id;
	}

	public void setProblem_id(int problem_id) {
		this.problem_id = problem_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public String getBanji() {
		return banji;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getScore() {
		return score;
	}

	public void setSolve(int solve) {
		this.solve = solve;
	}

	public int getSolve() {
		return solve;
	}

	public void setSubmit(int submit) {
		this.submit = submit;
	}

	public int getSubmit() {
		return submit;
	}

	@Override
	public String toString() {
		return "PMPaperExamScore [id=" + id + ",  problem_id=" + problem_id + ", userId=" + userId
				+ ", times=" + times + ", type=" + type + ", content=" + content + ", studentNo=" + studentNo
				+ ", username=" + username + ", chineseName=" + chineseName + ", classId=" + classId + ", banji="
				+ banji + ", score=" + score + ", solve=" + solve + ", submit=" + submit + "]";
	}
	

}
