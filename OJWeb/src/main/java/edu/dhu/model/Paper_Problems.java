package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Problems entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper_problems", catalog = "gdoj")
public class Paper_Problems implements java.io.Serializable{
	
	

	private static final long serialVersionUID = 7498437921585562289L;
	
	
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
	
	// Constructors

			/** default constructor */
	public Paper_Problems() {
	}
	
	/** minimal constructor */
	public Paper_Problems(Integer teacherId) {
		this.teacherId = teacherId;
	}
	
	
	
	/** full constructor */
	public Paper_Problems( Integer teacherId,String chapterCode, String type, String content, String choice_a, String choice_b,
			String choice_c, String choice_d, String answer, String count, String fill1, String fill2, String fill3,
			String fill4, String comment, Date create_time, Date update_time) {
		super();
		this.teacherId = teacherId;
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
	}



	// Property accessors
		    @Id
		    @GeneratedValue
		    @Column(name = "id", unique = true, nullable = false)
			public Integer getId() {
				return id;
			}

			public void setId(Integer id) {
				this.id = id;
			}

			
			@Column(name = "teacherId", nullable = false)
			public Integer getTeacherId() {
				return teacherId;
			}

			public void setTeacherId(Integer teacherId) {
				this.teacherId = teacherId;
			}

			@Column(name = "chapterCode")
			public String getChapterCode() {
				return chapterCode;
			}

			public void setChapterCode(String chapterCode) {
				this.chapterCode = chapterCode;
			}
			
			@Column(name = "type", length = 100)
			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			@Column(name = "content", length = 65535)
			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			@Column(name = "choice_a", length = 655)
			public String getChoice_a() {
				return choice_a;
			}

			public void setChoice_a(String choice_a) {
				this.choice_a = choice_a;
			}

			@Column(name = "choice_b", length = 655)
			public String getChoice_b() {
				return choice_b;
			}

			public void setChoice_b(String choice_b) {
				this.choice_b = choice_b;
			}

			@Column(name = "choice_c", length = 655)
			public String getChoice_c() {
				return choice_c;
			}

			public void setChoice_c(String choice_c) {
				this.choice_c = choice_c;
			}

			@Column(name = "choice_d", length = 655)
			public String getChoice_d() {
				return choice_d;
			}

			public void setChoice_d(String choice_d) {
				this.choice_d = choice_d;
			}

			@Column(name = "answer", length = 100)
			public String getAnswer() {
				return answer;
			}

			public void setAnswer(String answer) {
				this.answer = answer;
			}

			@Column(name = "count", length = 100)
			public String getCount() {
				return count;
			}

			public void setCount(String count) {
				this.count = count;
			}

			@Column(name = "fill1", length = 655)
			public String getFill1() {
				return fill1;
			}

			public void setFill1(String fill1) {
				this.fill1 = fill1;
			}

			@Column(name = "fill2", length = 655)
			public String getFill2() {
				return fill2;
			}

			public void setFill2(String fill2) {
				this.fill2 = fill2;
			}

			@Column(name = "fill3", length = 655)
			public String getFill3() {
				return fill3;
			}

			public void setFill3(String fill3) {
				this.fill3 = fill3;
			}

			@Column(name = "fill4", length = 655)
			public String getFill4() {
				return fill4;
			}

			public void setFill4(String fill4) {
				this.fill4 = fill4;
			}

			@Column(name = "comment", length = 32654)
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
	

}
