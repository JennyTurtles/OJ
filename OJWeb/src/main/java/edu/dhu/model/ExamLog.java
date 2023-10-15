package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "examlog", catalog = "gdoj")
public class ExamLog implements java.io.Serializable{
	private static final long serialVersionUID = -4577366061004276665L;
	private Integer id;
	private Date optime;
	private String type;
	private Integer userId;
	private String content;
	
	public ExamLog() {
		
	}

	public ExamLog(Integer id, Date optime, String type, Integer userId, String content) {
		super();
		this.id = id;
		this.optime = optime;
		this.type = type;
		this.userId = userId;
		this.content = content;
	}

	public ExamLog(Integer id, Date optime, String type) {
		super();
		this.id = id;
		this.optime = optime;
		this.type = type;
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
	
	@Column(name = "optime", length = 19)
	public Date getOptime() {
		return this.optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Column(name = "type", nullable = false, length = 30)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "userId", nullable = true)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "content", nullable = true, length = 8000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
