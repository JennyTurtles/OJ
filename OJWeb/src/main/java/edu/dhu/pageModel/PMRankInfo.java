package edu.dhu.pageModel;

import java.util.List;

public class PMRankInfo {

	private boolean isMoreClass;// 是否多个班级参与考试
	private Integer classRankNo;
	private float score;
	private float dateScore;
	private Integer examRankNo;
	private Integer dateClassRankNo;
	private Integer dateExamRankNo;
	private Integer classSize;
	private Integer examJoinerSize;
	private List<PMExamScore> classRankList;
	private List<PMExamScore> examRankList;
	private List<PMExamScore> dateClassRankList;
	private List<PMExamScore> dateExamRankList;
	public PMRankInfo(){
		
		
	}
	public PMRankInfo(boolean isMoreClass, Integer classRankNo, Integer examRankNo, Integer dateClassRankNo,
			Integer dateExamRankNo,float score, List<PMExamScore> classRankList, List<PMExamScore> examRankList,
			List<PMExamScore> dateClassRankList, List<PMExamScore> dateExamRankList) {
		this.classRankNo = classRankNo;
		this.examRankNo = examRankNo;
		this.dateClassRankNo = dateClassRankNo;
		this.dateExamRankNo = dateExamRankNo;
		this.classRankList = classRankList;
		this.examRankList = examRankList;
		this.dateClassRankList = dateClassRankList;
		this.dateExamRankList = dateExamRankList;
		this.isMoreClass = isMoreClass;
		this.score=score;
	}


	public float getScore() {
		return score;
	}


	public void setScore(float score) {
		this.score = score;
	}


	public boolean isMoreClass() {
		return isMoreClass;
	}

	public void setMoreClass(boolean isMoreClass) {
		this.isMoreClass = isMoreClass;
	}

	public Integer getClassRankNum() {
		return classRankNo;
	}

	public void setClassRankNum(Integer classRankNum) {
		this.classRankNo = classRankNum;
	}

	public Integer getExamRankNum() {
		return examRankNo;
	}

	public void setExamRankNum(Integer examRankNum) {
		this.examRankNo = examRankNum;
	}

	public Integer getDateClassRankNum() {
		return dateClassRankNo;
	}

	public void setDateClassRankNum(Integer dateClassRankNum) {
		this.dateClassRankNo = dateClassRankNum;
	}

	public Integer getDateExamRankNum() {
		return dateExamRankNo;
	}

	public void setDateExamRankNum(Integer dateExamRankNum) {
		this.dateExamRankNo = dateExamRankNum;
	}

	public List<PMExamScore> getClassRankList() {
		return classRankList;
	}

	public void setClassRankList(List<PMExamScore> classRankList) {
		this.classRankList = classRankList;
	}

	public List<PMExamScore> getExamRankList() {
		return examRankList;
	}

	public void setExamRankList(List<PMExamScore> examRankList) {
		this.examRankList = examRankList;
	}

	public List<PMExamScore> getDateClassRankList() {
		return dateClassRankList;
	}

	public void setDateClassRankList(List<PMExamScore> dateClassRankList) {
		this.dateClassRankList = dateClassRankList;
	}

	public List<PMExamScore> getDateExamRankList() {
		return dateExamRankList;
	}

	public void setDateExamRankList(List<PMExamScore> dateExamRankList) {
		this.dateExamRankList = dateExamRankList;
	}


	public Integer getClassSize() {
		return classSize;
	}
	public void setClassSize(Integer classSize) {
		this.classSize = classSize;
	}
	public Integer getExamJoinerSize() {
		return examJoinerSize;
	}

	public void setExamJoinerSize(Integer examJoinerSize) {
		this.examJoinerSize = examJoinerSize;
	}
	
	public float getDateScore() {
		return dateScore;
	}
	public void setDateScore(float dateScore) {
		this.dateScore = dateScore;
	}
}
