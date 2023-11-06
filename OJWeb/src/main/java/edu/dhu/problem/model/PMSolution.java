package edu.dhu.problem.model;

import java.util.Date;

public class PMSolution implements java.io.Serializable{
	private static final long serialVersionUID = 1538275128389L;
	
	private Integer id;
	private Integer examId;
	private Integer problemId;
	private Integer userid;
	private Date submitTime;
	private String language;
	private String sourceCode;
	private Integer codelength;
	private String status;
	private String correctCaseIds;
	private float similarity;
	private Integer similarId;
	private float score;
	private String studentNo;
	private String chineseName;
	private String banji;
	private int displaySequence;
	private String title;
	private float similarityThreshold;
	private long size;
	private boolean flag;// 用来判断某个学生是否属于某个老师的班级

	private float scoreTotal;
	private Double proCatscore;
	private float studentAllScore;// 用来存储学生该考试的总分

	// 添加的，用于显示抄袭
	private boolean submited;
	private Integer eversubmit;
	private String studentNo2;
	private String chineseName2;
	private String examName;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getExamId() {
		return this.examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getProblemId() {
		return this.problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getCodelength() {
		return this.codelength;
	}

	public void setCodelength(Integer codelength) {
		this.codelength = codelength;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCorrectCaseIds() {
		return this.correctCaseIds;
	}

	public void setCorrectCaseIds(String correctCaseIds) {
		this.correctCaseIds = correctCaseIds;
	}

	public float getSimilarity() {
		return this.similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public Integer getSimilarId() {
		return this.similarId;
	}

	public void setSimilarId(Integer similarId) {
		this.similarId = similarId;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentNo() {
		return studentNo;
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

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getSimilarityThreshold() {
		return similarityThreshold;
	}

	public void setSimilarityThreshold(float similarityThreshold) {
		this.similarityThreshold = similarityThreshold;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	public void setSubmited(boolean submited) {
		this.submited = submited;
	}

	public boolean getSubmited() {
		return submited;
	}

	public void setStudentNo2(String studentNo2) {
		this.studentNo2 = studentNo2;
	}

	public String getStudentNo2() {
		return studentNo2;
	}

	public String getChineseName2() {
		return chineseName2;
	}

	public void setChineseName2(String chineseName2) {
		this.chineseName2 = chineseName2;
	}

	public void setEversubmit(Integer eversubmit) {
		this.eversubmit = eversubmit;
	}

	public Integer getEversubmit() {
		return eversubmit;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public float getScoreTotal() {
		return scoreTotal;
	}

	public void setScoreTotal(float scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public float getStudentAllScore() {
		return studentAllScore;
	}

	public void setStudentAllScore(float studentAllScore) {
		this.studentAllScore = studentAllScore;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Double getProCatscore() {
		return proCatscore;
	}

	public void setProCatscore(Double proCatscore) {
		this.proCatscore = proCatscore;
	}

}
