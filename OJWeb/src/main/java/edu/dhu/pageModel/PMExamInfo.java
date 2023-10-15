package edu.dhu.pageModel;

import java.util.Date;

public class PMExamInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2237758222079468004L;
	private float scoreCoef;
	private Date bestBefore;
	private Date deadline;
	
	public float getScoreCoef() {
		return scoreCoef;
	}
	public void setScoreCoef(float scoreCoef) {
		this.scoreCoef = scoreCoef;
	}
	public Date getBestBefore() {
		return bestBefore;
	}
	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
