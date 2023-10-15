package edu.dhu.pageModel;

import java.util.Date;

/*
 * 聊天信息，记录了消息的发送时间，发送对象，接收对象等信息
 * */
public class PMChatMessage implements java.io.Serializable {
	private static final long serialVersionUID = 1538274682893L;
	
	private Integer id;
	private String sendUserId;
	private String sendUserName;
	private String receiveUserId;
	private String receiveUserName;
	private String msg;
	private Date time;
	private Integer examId;
	private boolean popup;
	private boolean isRead;

	public PMChatMessage() {

	}

	public PMChatMessage(String sendUserId, String receiveUserId, String msg,
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

	public Integer getId() {
		return id;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSendUSerName() {
		return sendUserName;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveUSerName() {
		return receiveUserName;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public boolean getPopup() {
		return popup;
	}

	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean getIsRead() {
		return isRead;
	}
}
