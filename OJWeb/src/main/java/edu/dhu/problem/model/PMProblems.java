package edu.dhu.problem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PMProblems implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 640900540276659223L;
	// exam的id
	private Integer examId;
	// user的ID
	private int userId;

	// Problems的基本属性
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
	private String courseCode;
	private boolean checkSimilarity;
	private float similarityThreshold;
	private Integer solved;
	private Integer submit;
	private float ratio;
	private Integer teacherId;
	private String source;
	private String courseName;
	// 该题的分数
	public float problemScore;
	// 学生是否提交过本题
	private boolean problemIsSubmit;

	// 页面上其他需要的属性
	private Integer displaySequence; // 题目在试卷中显示的顺序
	private String status; // 题目的提交状态

	private String chapterName; // 增加chapterName属性
	private String teacherName; // 添加教师名熟悉
	private String keywords; // 用于查询时候使用的关键字
	private String sortContent; // 排序关键字
	private String sortType; // 排序方式，升序降序
	private List<String> testcaseId;
	private List<String> input;
	private List<String> output;
	private Date updateTime;
	private float scoreCoef;
	private Date bestBefore;
	private Date deadline;
	// 题目的类别
	private String category;
	//题目所属的其他分类
	private String otherCategory;
	//排除的分类題目
	private String excludeCategory;
	//属于题目分类名称
	private String belong;
	//题目第一分类
	private int firstClassificationId;
	private String firstClassification;
	//题目第二分类
	private int secondClassificationId;
	private String secondClassification;
	
	//做题估计时间
	private int duration;
	
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public PMProblems() {
	}

	public PMProblems(Integer examId, int userId, Integer id, String title, String description, float memoryLimit,
                      float timeLimit, String inputRequirement, String outputRequirement, String sampleInput, String sampleOuput,
                      String author, String difficulty, String sourceCode, String srcCodeLanguage, String scoreGrade,
                      String chapterCode, String courseCode, boolean checkSimilarity, float similarityThreshold, Integer solved,
                      Integer submit, float ratio, Integer teacherId, String source, float problemScore, boolean problemIsSubmit,
                      Integer displaySequence, String status, String courseName, String chapterName, String teacherName,
                      String keywords, List<String> testcaseId, List<String> input, List<String> output, Date updateTime,
                      float scoreCoef, Date bestBefore) {
		this.examId = examId;
		this.userId = userId;
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
		this.chapterCode = chapterCode;
		this.courseCode = courseCode;
		this.checkSimilarity = checkSimilarity;
		this.similarityThreshold = similarityThreshold;
		this.solved = solved;
		this.submit = submit;
		this.ratio = ratio;
		this.teacherId = teacherId;
		this.source = source;
		this.problemScore = problemScore;
		this.problemIsSubmit = problemIsSubmit;
		this.displaySequence = displaySequence;
		this.status = status;
		this.courseName = courseName;
		this.chapterName = chapterName;
		this.teacherName = teacherName;
		this.keywords = keywords;
		this.testcaseId = testcaseId;
		this.input = input;
		this.output = output;
		this.updateTime = updateTime;
		this.scoreCoef = scoreCoef;
		this.bestBefore = bestBefore;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public float getProblemScore() {
		return problemScore;
	}

	public void setProblemScore(float problemScore) {
		this.problemScore = problemScore;
	}

	public boolean isProblemIsSubmit() {
		return problemIsSubmit;
	}

	public void setProblemIsSubmit(boolean problemIsSubmit) {
		this.problemIsSubmit = problemIsSubmit;
	}

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List<String> getTestcaseId() {
		return testcaseId;
	}

	public void setSortContent(String sortContent) {
		this.sortContent = sortContent;
	}

	public String getSortContent() {
		return sortContent;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortType() {
		return sortType;
	}

	public void setTestcaseId(List<String> testcaseId) {
		this.testcaseId = testcaseId;
	}

	public List<String> getInput() {
		return input;
	}

	public void setInput(List<String> input) {
		this.input = input;
	}

	public List<String> getOutput() {
		return output;
	}

	public void setOutput(List<String> output) {
		this.output = output;
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

	public String getOtherCategory() {
		return otherCategory;
	}

	public void setOtherCategory(String otherCategory) {
		this.otherCategory = otherCategory;
	}

	public String getExcludeCategory() {
		return excludeCategory;
	}

	public void setExcludeCategory(String excludeCategory) {
		this.excludeCategory = excludeCategory;
	}

	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	public String getFirstClassification() {
		return firstClassification;
	}

	public void setFirstClassification(String firstClassification) {
		this.firstClassification = firstClassification;
	}

	public String getSecondClassification() {
		return secondClassification;
	}

	public void setSecondClassification(String secondClassification) {
		this.secondClassification = secondClassification;
	}

	public int getFirstClassificationId() {
		return firstClassificationId;
	}

	public void setFirstClassificationId(int firstClassificationId) {
		this.firstClassificationId = firstClassificationId;
	}

	public int getSecondClassificationId() {
		return secondClassificationId;
	}

	public void setSecondClassificationId(int secondClassificationId) {
		this.secondClassificationId = secondClassificationId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
