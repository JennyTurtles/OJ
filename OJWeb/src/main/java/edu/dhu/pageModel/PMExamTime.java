package edu.dhu.pageModel;

public class PMExamTime implements java.io.Serializable {
	private static final long serialVersionUID = 1538274816239L;
	
	private int examId;
	private long day;
	private long hour;
	private long minute;
	private long second;

	public PMExamTime() {
	}

	public PMExamTime(int examId, long day, long hour, long minute, long second) {
		this.examId = examId;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public long getDay() {
		return day;
	}

	public void setDay(long day) {
		this.day = day;
	}

	public long getHour() {
		return hour;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}

	public long getMinute() {
		return minute;
	}

	public void setMinute(long minute) {
		this.minute = minute;
	}

	public long getSecond() {
		return second;
	}

	public void setSecond(long second) {
		this.second = second;
	}

}
