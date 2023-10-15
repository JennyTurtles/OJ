package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student_paper_exam_detail", catalog = "gdoj")
public class Studentpaperexamdetail implements java.io.Serializable {

	private Integer id;
	private Integer user_id;
	private Integer exam_id;
	private Integer problem_id;
	private String choice;
	private String fill1;
	private String fill2;
	private String fill3;
	private String fill4;
	private String accuracy;
	private Integer score;
	private Date create_time;
	private Date update_time;
	
	
	public Studentpaperexamdetail() {
		
	}


	public Studentpaperexamdetail(Integer id, Integer user_id, Integer exam_id, Integer problem_id, String choice,
			String fill1, String fill2, String fill3, String fill4, String accuracy, Integer score, Date create_time,
			Date update_time) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.problem_id = problem_id;
		this.choice = choice;
		this.fill1 = fill1;
		this.fill2 = fill2;
		this.fill3 = fill3;
		this.fill4 = fill4;
		this.accuracy = accuracy;
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

	
	
	
	@Column(name = "problem_id", nullable = false)
	public Integer getProblem_id() {
		return this.problem_id;
	}


	public void setProblem_id(Integer problem_id) {
		this.problem_id = problem_id;
	}

	@Column(name = "choice", nullable = false)
	public String getChoice() {
		return this.choice;
	}


	public void setChoice(String choice) {
		this.choice = choice;
	}

	@Column(name = "fill1", nullable = false)
	public String getFill1() {
		return this.fill1;
	}


	public void setFill1(String fill1) {
		this.fill1 = fill1;
	}

	@Column(name = "fill2", nullable = false)
	public String getFill2() {
		return this.fill2;
	}


	public void setFill2(String fill2) {
		this.fill2 = fill2;
	}

	@Column(name = "fill3", nullable = false)
	public String getFill3() {
		return this.fill3;
	}


	public void setFill3(String fill3) {
		this.fill3 = fill3;
	}

	@Column(name = "fill4", nullable = false)
	public String getFill4() {
		return this.fill4;
	}


	public void setFill4(String fill4) {
		this.fill4 = fill4;
	}

	@Column(name = "accuracy", nullable = false)
	public String getAccuracy() {
		return this.accuracy;
	}


	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
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
