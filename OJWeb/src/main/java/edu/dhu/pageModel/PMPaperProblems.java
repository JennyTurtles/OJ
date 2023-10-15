package edu.dhu.pageModel;

import java.util.Date;

public class PMPaperProblems implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7847375974848488081L;
	
	// user的ID
	private int userId;

	// Paper_Problems的基本属性
	private Integer id;
	private Integer teacherId;
	private String chapterCode;
	private String type;
	private String content;
	private String choice_a;
	private String choice_b;
	private String choice_c;
	private String choice_d;
	private String answer;
	private String count;
	private String fill1;
	private String fill2;
	private String fill3;
	private String fill4;
	private String comment;
	private Date create_time;
	private Date update_time;
	
	// 页面上其他需要的属性
	private String keywords; // 用于查询时候使用的关键字
	private String sortContent; // 排序关键字
	private String sortType; // 排序方式，升序降序
	private String difficulty;
	private String courseCode;
	private String courseName;
	private String chapterName;
	private String teacherName;
	
	
	public PMPaperProblems() {
	}


	public PMPaperProblems(int userId, Integer id, String chapterCode, String type, String content, String choice_a,
			String choice_b, String choice_c, String choice_d, String answer, String count, String fill1, String fill2,
			String fill3, String fill4, String comment, Date create_time, Date update_time, String keywords,
			String sortContent, String sortType, String difficulty, Integer teacherId, String courseCode,String courseName,
			String chapterName,String teacherName) {
		super();
		this.userId = userId;
		this.id = id;
		this.chapterCode = chapterCode;
		this.type = type;
		this.content = content;
		this.choice_a = choice_a;
		this.choice_b = choice_b;
		this.choice_c = choice_c;
		this.choice_d = choice_d;
		this.answer = answer;
		this.count = count;
		this.fill1 = fill1;
		this.fill2 = fill2;
		this.fill3 = fill3;
		this.fill4 = fill4;
		this.comment = comment;
		this.create_time = create_time;
		this.update_time = update_time;
		this.keywords = keywords;
		this.sortContent = sortContent;
		this.sortType = sortType;
		this.difficulty = difficulty;
		this.teacherId = teacherId;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.chapterName = chapterName;
		this.teacherName = teacherName;
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


	public String getChapterCode() {
		return chapterCode;
	}


	public void setChapterCode(String chapterCode) {
		this.chapterCode = chapterCode;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getChoice_a() {
		return choice_a;
	}


	public void setChoice_a(String choice_a) {
		this.choice_a = choice_a;
	}


	public String getChoice_b() {
		return choice_b;
	}


	public void setChoice_b(String choice_b) {
		this.choice_b = choice_b;
	}


	public String getChoice_c() {
		return choice_c;
	}


	public void setChoice_c(String choice_c) {
		this.choice_c = choice_c;
	}


	public String getChoice_d() {
		return choice_d;
	}


	public void setChoice_d(String choice_d) {
		this.choice_d = choice_d;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public String getCount() {
		return count;
	}


	public void setCount(String count) {
		this.count = count;
	}


	


	public String getFill1() {
		return fill1;
	}


	public void setFill1(String fill1) {
		this.fill1 = fill1;
	}


	public String getFill2() {
		return fill2;
	}


	public void setFill2(String fill2) {
		this.fill2 = fill2;
	}


	public String getFill3() {
		return fill3;
	}


	public void setFill3(String fill3) {
		this.fill3 = fill3;
	}


	public String getFill4() {
		return fill4;
	}


	public void setFill4(String fill4) {
		this.fill4 = fill4;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Date getCreate_time() {
		return create_time;
	}


	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	public Date getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}


	public String getKeywords() {
		return keywords;
	}


	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	public String getSortContent() {
		return sortContent;
	}


	public void setSortContent(String sortContent) {
		this.sortContent = sortContent;
	}


	public String getSortType() {
		return sortType;
	}


	public void setSortType(String sortType) {
		this.sortType = sortType;
	}


	public String getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}


	public Integer getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}


	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
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


	
}
