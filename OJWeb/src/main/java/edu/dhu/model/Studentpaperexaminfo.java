package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student_paper_exam_info", catalog = "gdoj")
public class Studentpaperexaminfo implements java.io.Serializable {

	private Integer id;
	private Integer user_id;
	private Integer exam_id;
	private Date enter_time;
	private Integer score;
	private Date create_time;
	private Date update_time;
	
	
	public Studentpaperexaminfo() {
		
	}


	public Studentpaperexaminfo(Integer id, Integer user_id, Integer exam_id, Date enter_time, Integer score,
			Date create_time, Date update_time) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.enter_time = enter_time;
		this.score = score;
		this.create_time = create_time;
		this.update_time = update_time;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Integer getUser_id() {
		return this.user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	@Column(name = "exam_id", nullable = false)
	public Integer getExam_id() {
		return this.exam_id;
	}


	public void setExam_id(Integer exam_id) {
		this.exam_id = exam_id;
	}

	@Column(name = "enter_time", nullable = false)
	public Date getEnter_time() {
		return this.enter_time;
	}


	public void setEnter_time(Date enter_time) {
		this.enter_time = enter_time;
	}

	@Column(name = "score", nullable = false)
	public Integer getScore() {
		return this.score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "create_time", nullable = false)
	public Date getCreate_time() {
		return this.create_time;
	}


	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "update_time", nullable = false)
	public Date getUpdate_time() {
		return this.update_time;
	}


	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	
}
