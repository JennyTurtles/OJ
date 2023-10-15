package edu.dhu.pageModel;

import java.util.Date;

public class PMPaperExamproblem implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 477484886463674418L;
	// Page
	private int page;
	private int rows;
	private String flag;
	private String sort;
	private String order;
	private String ids;

	// Field
	private Integer id;
	private Integer paperexamId;
	private Integer paperproblemId;
	private float score;
	private Integer displaySequence;

	// Other
	private String title;
	private String description;
	private String difficulty;
	private String courseCode;
	private String chapterCode;
	private String source;
	private String keywords;
	private String sortContent;
	private String sortType;
	private int teacherId;
	private Date deadline;
	private float scoreCoef;
	private Date bestBefore;
	private String strDeadline;
	private String strBestBefore;
	private String eliminateExams;
	private Integer newExamId;// 克隆用

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

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
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

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	private String chapterName;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
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

	public Integer getPaperexamId() {
		return paperexamId;
	}

	public void setPaperexamId(Integer paperexamId) {
		this.paperexamId = paperexamId;
	}

	public Integer getPaperproblemId() {
		return paperproblemId;
	}

	public void setPaperproblemId(Integer paperproblemId) {
		this.paperproblemId = paperproblemId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getKeywords() {
		return keywords;
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
		return this.sortType;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setStrDeadline(String strDeadline) {
		this.strDeadline = strDeadline;
	}

	public String getStrDeadline() {
		return strDeadline;
	}

	public void setEliminateExams(String eliminateExams) {
		this.eliminateExams = eliminateExams;
	}

	public String getEliminateExams() {
		return eliminateExams;
	}

	public String getChapterCode() {
		return chapterCode;
	}

	public void setChapterCode(String chapterCode) {
		this.chapterCode = chapterCode;
	}

	public String getStrBestBefore() {
		return strBestBefore;
	}

	public void setStrBestBefore(String strBestBefore) {
		this.strBestBefore = strBestBefore;
	}

	public Integer getNewExamId() {
		return newExamId;
	}

	public void setNewExamId(Integer newExamId) {
		this.newExamId = newExamId;
	}

}
