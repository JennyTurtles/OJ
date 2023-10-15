package edu.dhu.pageModel;

import java.util.Date;

public class PMProblem implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4134197269188738739L;
	// Page
	private int page;
	private int rows;
	private String flag;
	private String sort;
	private String order;
	private String ids;
	// Field
	private Integer id;
	private String title;
	private String description;
	private float memoryLimit;
	private float timeLimit;
	private String inputRequirement;
	private String outputRequirement;
	private String sampleInput;
	private String sampleOuput;
	private String author;
	private String difficulty;
	private String sourceCode;
	private String srcCodeLanguage;
	private String scoreGrade;
	private String chapterCode;
	private String courseName;
	private String chapterName;
	private boolean checkSimilarity;
	private float similarityThreshold;
	private Integer solved;
	private Integer submit;
	private float ratio;
	private Integer teacherId;
	private String teacherName;
	private String source;
	private Date updateTime;
	
	//题目的类别
	private String category;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(float memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public float getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(float timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getInputRequirement() {
		return inputRequirement;
	}

	public void setInputRequirement(String inputRequirement) {
		this.inputRequirement = inputRequirement;
	}

	public String getOutputRequirement() {
		return outputRequirement;
	}

	public void setOutputRequirement(String outputRequirement) {
		this.outputRequirement = outputRequirement;
	}

	public String getSampleInput() {
		return sampleInput;
	}

	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	public String getSampleOuput() {
		return sampleOuput;
	}

	public void setSampleOuput(String sampleOuput) {
		this.sampleOuput = sampleOuput;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getSrcCodeLanguage() {
		return srcCodeLanguage;
	}

	public void setSrcCodeLanguage(String srcCodeLanguage) {
		this.srcCodeLanguage = srcCodeLanguage;
	}

	public String getScoreGrade() {
		return scoreGrade;
	}

	public void setScoreGrade(String scoreGrade) {
		this.scoreGrade = scoreGrade;
	}

	public boolean isCheckSimilarity() {
		return checkSimilarity;
	}

	public void setCheckSimilarity(boolean checkSimilarity) {
		this.checkSimilarity = checkSimilarity;
	}

	public float getSimilarityThreshold() {
		return similarityThreshold;
	}

	public void setSimilarityThreshold(float similarityThreshold) {
		this.similarityThreshold = similarityThreshold;
	}

	public Integer getSolved() {
		return solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	public Integer getSubmit() {
		return submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public String getChapterCode() {
		return chapterCode;
	}

	public void setChapterCode(String chapterCode) {
		this.chapterCode = chapterCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
