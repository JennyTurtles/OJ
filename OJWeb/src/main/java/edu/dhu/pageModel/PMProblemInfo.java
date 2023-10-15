package edu.dhu.pageModel;

import java.util.Date;

public class PMProblemInfo implements java.io.Serializable {

	private static final long serialVersionUID = -6413865769945242482L;

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
	private Integer chapterId;
	private boolean checkSimilarity;
	private float similarityThreshold;
	private Integer solved;
	private Integer submit;
	private float ratio;
	private Integer teacherId;
	private String source;
	private Date updateTime;
	private float scoreCoef;
	private Date bestBefore;
	// 章节的中文名字
	private String chapterName;
	private Date deadline;

	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public PMProblemInfo() {
	}

	public PMProblemInfo(Integer id, String title, String description,
			float memoryLimit, float timeLimit, String inputRequirement,
			String outputRequirement, String sampleInput, String sampleOuput,
			String author, String difficulty, String sourceCode,
			String srcCodeLanguage, String scoreGrade, Integer chapterId,
			boolean checkSimilarity, float similarityThreshold, Integer solved,
			Integer submit, float ratio, Integer teacherId, String source,
			String chapterName, Date updateTime, float scoreCoef,
			Date bestBefore) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.memoryLimit = memoryLimit;
		this.timeLimit = timeLimit;
		this.inputRequirement = inputRequirement;
		this.outputRequirement = outputRequirement;
		this.sampleInput = sampleInput;
		this.sampleOuput = sampleOuput;
		this.author = author;
		this.difficulty = difficulty;
		this.sourceCode = sourceCode;
		this.srcCodeLanguage = srcCodeLanguage;
		this.scoreGrade = scoreGrade;
		this.chapterId = chapterId;
		this.checkSimilarity = checkSimilarity;
		this.similarityThreshold = similarityThreshold;
		this.solved = solved;
		this.submit = submit;
		this.ratio = ratio;
		this.teacherId = teacherId;
		this.source = source;
		this.chapterName = chapterName;
		this.updateTime = updateTime;
		this.scoreCoef = scoreCoef;
		this.bestBefore = bestBefore;
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

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
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

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public float getScoreCoef() {
		return scoreCoef;
	}

	public void setScoreCoef(float scoreCoef) {
		this.scoreCoef = scoreCoef;
	}

	public Date getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}

}
