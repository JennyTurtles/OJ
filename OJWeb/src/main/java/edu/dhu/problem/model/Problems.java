package edu.dhu.problem.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Problems entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "problems", catalog = "gdoj")
public class Problems implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103453821354424508L;
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
	private Date updateTime;
	//题目的类别
	private String category;
	//做题估计时间
	private int duration;
	// Constructors

	/** default constructor */
	public Problems() {
	}

	/** minimal constructor */
	public Problems(Integer teacherId) {
		this.teacherId = teacherId;
	}

	/** full constructor */
	public Problems(String title, String description, float memoryLimit, float timeLimit, String inputRequirement,
                    String outputRequirement, String sampleInput, String sampleOuput, String author, String difficulty,
                    String sourceCode, String srcCodeLanguage, String scoreGrade, String chapterCode, boolean checkSimilarity,
                    float similarityThreshold, Integer solved, Integer submit, float ratio, Integer teacherId, String source,
                    Date updateTime, String category, int duration) {
		super();
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
		this.checkSimilarity = checkSimilarity;
		this.similarityThreshold = similarityThreshold;
		this.solved = solved;
		this.submit = submit;
		this.ratio = ratio;
		this.teacherId = teacherId;
		this.source = source;
		this.updateTime = updateTime;
		this.category = category;
		this.duration = duration;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "memory_limit", precision = 12, scale = 0)
	public float getMemoryLimit() {
		return this.memoryLimit;
	}

	public void setMemoryLimit(float memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Column(name = "time_limit", precision = 12, scale = 0)
	public float getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(float timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Column(name = "inputRequirement", length = 3000)
	public String getInputRequirement() {
		return this.inputRequirement;
	}

	public void setInputRequirement(String inputRequirement) {
		this.inputRequirement = inputRequirement;
	}

	@Column(name = "outputRequirement", length = 3000)
	public String getOutputRequirement() {
		return this.outputRequirement;
	}

	public void setOutputRequirement(String outputRequirement) {
		this.outputRequirement = outputRequirement;
	}

	@Column(name = "sample_input", length = 1000)
	public String getSampleInput() {
		return this.sampleInput;
	}

	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	@Column(name = "sample_ouput", length = 1000)
	public String getSampleOuput() {
		return this.sampleOuput;
	}

	public void setSampleOuput(String sampleOuput) {
		this.sampleOuput = sampleOuput;
	}

	@Column(name = "author", length = 20)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "difficulty", length = 10)
	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Column(name = "sourceCode", length = 8000)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Column(name = "srcCodeLanguage", length = 45)
	public String getSrcCodeLanguage() {
		return this.srcCodeLanguage;
	}

	public void setSrcCodeLanguage(String srcCodeLanguage) {
		this.srcCodeLanguage = srcCodeLanguage;
	}

	@Column(name = "scoreGrade", length = 1000)
	public String getScoreGrade() {
		return this.scoreGrade;
	}

	public void setScoreGrade(String scoreGrade) {
		this.scoreGrade = scoreGrade;
	}

	@Column(name = "chapterCode")
	public String getchapterCode() {
		return this.chapterCode;
	}

	public void setchapterCode(String chapterCode) {
		this.chapterCode = chapterCode;
	}

	@Column(name = "checkSimilarity")
	public boolean getCheckSimilarity() {
		return this.checkSimilarity;
	}

	public void setCheckSimilarity(boolean checkSimilarity) {
		this.checkSimilarity = checkSimilarity;
	}

	@Column(name = "similarityThreshold", precision = 12, scale = 0)
	public float getSimilarityThreshold() {
		return this.similarityThreshold;
	}

	public void setSimilarityThreshold(float similarityThreshold) {
		this.similarityThreshold = similarityThreshold;
	}

	@Column(name = "solved")
	public Integer getSolved() {
		return this.solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	@Column(name = "submit")
	public Integer getSubmit() {
		return this.submit;
	}

	public void setSubmit(Integer submit) {
		this.submit = submit;
	}

	@Column(name = "ratio", precision = 12, scale = 0)
	public float getRatio() {
		return this.ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	@Column(name = "teacherId", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "source", length = 50)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "category", length = 100)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "duration")
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	

}