package edu.dhu.pageModel;

import java.util.List;

public class PMProblemTestCaseAdd implements java.io.Serializable {
	private static final long serialVersionUID = 1538274993197L;
	
	// Problem的字段
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
	private boolean checkSimilarity;
	private float similarityThreshold;
	private Integer solved;
	private Integer submit;
	private float ratio;
	private Integer teacherId;
	private String source;
	// 测试用例的输入和输出数组
	private List<String> input;
	private List<String> output;

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
}
