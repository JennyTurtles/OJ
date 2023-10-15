package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "problemComment", catalog = "gdoj")
public class ProblemComment implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8277053318108171770L;
	
	private Integer id;
	
	// 题解标题
	private String title;
	// 对应题目ID
	private Integer problemId;
	// 题解内容
	private String content;
	// 代码语言
	private String language;
	// 审核状态：wait, pass, reject
	private String reviewStatus;
	//学生ID
	private Integer studentId;
	// 教师ID
	private Integer teacherId;
	// 关键词(多关键词用逗号隔开)
	private String keywords;
	//发布时间
	private Date time;
	//题解纯文本内容
	private String textContent;
	
	public ProblemComment() {
		
	}

	public ProblemComment(Integer id, String title, Integer problemId, String content, String language,
			String reviewStatus, Integer studentId, Integer teacherId, String keywords, Date time, String textContent) {
		super();
		this.id = id;
		this.title = title;
		this.problemId = problemId;
		this.content = content;
		this.language = language;
		this.reviewStatus = reviewStatus;
		this.studentId = studentId;
		this.teacherId = teacherId;
		this.keywords = keywords;
		this.time = time;
		this.textContent = textContent;
	}


	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", length = 100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "problemId", nullable = false)
	public Integer getProblemId() {
		return problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	@Column(name = "content", length = 10000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "language", length = 45)
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "reviewStatus", length = 45)
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Column(name = "studentId")
	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Column(name = "teacherId")
	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "keywords", length = 100)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "time", length = 19)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "textContent", length = 10000)
	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
	
	
}
