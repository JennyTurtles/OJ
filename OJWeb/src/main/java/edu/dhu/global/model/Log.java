package edu.dhu.global.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Log entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "log", catalog = "gdoj")
public class Log implements java.io.Serializable {

	private static final long serialVersionUID = -4577366061004276665L;
	private Integer id;
	private Date optime;
	private String type;
	private String userType;
	private Integer userId;
	private String abstractContent;
	private String content;

	public Log() {
	}

	public Log(Integer id, Date optime, String type, String userType,
               Integer userId, String abstractContent, String content) {
		this.id = id;
		this.optime = optime;
		this.type = type;
		this.userType = userType;
		this.userId = userId;
		this.abstractContent = abstractContent;
		this.content = content;
	}

	public Log(Integer id, Date optime, String type) {
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

	@Column(name = "userType", nullable = true, length = 10)
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name = "userId", nullable = true)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "abstractContent", nullable = true, length = 500)
	public String getAbstractContent() {
		return this.abstractContent;
	}

	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}

	@Column(name = "content", nullable = true, length = 8000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
