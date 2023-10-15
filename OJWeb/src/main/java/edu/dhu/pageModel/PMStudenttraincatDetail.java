package edu.dhu.pageModel;

import java.util.Date;

public class PMStudenttraincatDetail implements java.io.Serializable {

	private static final long serialVersionUID = -445797511628679085L;
	
	private Integer id;
	private Integer userId;
	private Integer examId;
	private Integer catId;
	private Integer submit;
	private float points;
	private float score;
	private Integer elapsedTime;
	private boolean finished;
	private Integer currentProb;
	private String probSequence;
	private String probFinished;
	private Integer lastProb;
	private String studentNo;
	private String chineseName;
	private String banji;
	private Integer teacherId;
	private Integer classId;
	private Date passTime;
	private String passTimeFrom;
	private String passTimeTo;

	
	public PMStudenttraincatDetail() {
		
	}


	public PMStudenttraincatDetail(Integer userId, Integer examId, Integer catId) {
		super();
		this.userId = userId;
		this.examId = examId;
		this.catId = catId;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getExamId() {
		return examId;
	}


	public void setExamId(Integer examId) {
		this.examId = examId;
	}


	public Integer getCatId() {
		return catId;
	}


	public void setCatId(Integer catId) {
		this.catId = catId;
	}


	public Integer getSubmit() {
		return submit;
	}


	public void setSubmit(Integer submit) {
		this.submit = submit;
	}


	public float getPoints() {
		return points;
	}


	public void setPoints(float points) {
		this.points = points;
	}


	public float getScore() {
		return score;
	}


	public void setScore(float score) {
		this.score = score;
	}


	public Integer getElapsedTime() {
		return elapsedTime;
	}


	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}


	public boolean isFinished() {
		return finished;
	}


	public void setFinished(boolean finished) {
		this.finished = finished;
	}


	public Integer getCurrentProb() {
		return currentProb;
	}


	public void setCurrentProb(Integer currentProb) {
		this.currentProb = currentProb;
	}


	public String getProbSequence() {
		return probSequence;
	}


	public void setProbSequence(String probSequence) {
		this.probSequence = probSequence;
	}


	public String getProbFinished() {
		return probFinished;
	}


	public void setProbFinished(String probFinished) {
		this.probFinished = probFinished;
	}


	public Integer getLastProb() {
		return lastProb;
	}


	public void setLastProb(Integer lastProb) {
		this.lastProb = lastProb;
	}


	public String getStudentNo() {
		return studentNo;
	}


	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}


	public String getChineseName() {
		return chineseName;
	}


	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}


	public String getBanji() {
		return banji;
	}


	public void setBanji(String banji) {
		this.banji = banji;
	}


	public Integer getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}


	public Integer getClassId() {
		return classId;
	}


	public void setClassId(Integer classId) {
		this.classId = classId;
	}


	public Date getPassTime() {
		return passTime;
	}


	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}


	public String getPassTimeFrom() {
		return passTimeFrom;
	}


	public void setPassTimeFrom(String passTimeFrom) {
		this.passTimeFrom = passTimeFrom;
	}


	public String getPassTimeTo() {
		return passTimeTo;
	}


	public void setPassTimeTo(String passTimeTo) {
		this.passTimeTo = passTimeTo;
	}


	
	
	
}
