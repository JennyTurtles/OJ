package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chatMessage", catalog = "gdoj")
public class ChatMessage implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sendUserId;
	private String receiveUserId;
	private String msg;
	private Date time;
	private Integer examId;
	private boolean popup;
	private boolean isRead;

	public ChatMessage() {

	}

	public ChatMessage(String sendUserId, String receiveUserId, String msg,
			Date time, Integer examId, boolean popup, boolean isRead) {
		this.sendUserId = sendUserId;
		this.receiveUserId = receiveUserId;
		this.msg = msg;
		this.time = time;
		this.examId = examId;
		this.popup = popup;
		this.isRead = isRead;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	@Column(name = "sendUserId", unique = false, nullable = false)
	public String getSendUserId() {
		return sendUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	@Column(name = "receiveUserId", unique = false)
	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "msg", nullable = true, length = 300)
	public String getMsg() {
		return msg;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "time", length = 19)
	public Date getTime() {
		return time;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "examId", nullable = false)
	public Integer getExamId() {
		return examId;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	@Column(name = "popup", nullable = false)
	public boolean getPopup() {
		return popup;
	}

	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Column(name = "isRead", nullable = false)
	public boolean getIsRead() {
		return isRead;
	}

}
