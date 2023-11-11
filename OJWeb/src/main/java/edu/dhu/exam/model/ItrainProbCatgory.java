package edu.dhu.exam.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "itrainprobcat")
public class ItrainProbCatgory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6132846556023607637L;
	
	private Integer id;
	
	private Integer examId;
	
	// 二级类型id
	private Integer catId;
	// 题目数量
	private Integer problemNum;
	// 题目数量下限
	private Integer lowerLimit;
	// 题目数量上限
	private Integer upperLimit;
	// 分数
	private Double score;
	// 最佳完成时间
	private Date bestBefore;
	// 打折系数
	private Double scoreCoef;
	// 截止时间
	private Date deadline;
	// 更新时间
	private Date updateTime;
	//页面X坐标
	private Integer rowX;
	//页面Y坐标
	private Integer colY;
	
	
	public ItrainProbCatgory() {
	}

	public ItrainProbCatgory(Integer id, Integer examId, Integer catId, Integer problemNum, Integer lowerLimit,
                             Integer upperLimit, Double score, Date bestBefore, Double scoreCoef, Date deadline, Date updateTime,
                             Integer rowX, Integer colY) {
		super();
		this.id = id;
		this.examId = examId;
		this.catId = catId;
		this.problemNum = problemNum;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.score = score;
		this.bestBefore = bestBefore;
		this.scoreCoef = scoreCoef;
		this.deadline = deadline;
		this.updateTime = updateTime;
		this.rowX = rowX;
		this.colY = colY;
	}


	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "examId", nullable = false)
	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	@Column(name = "catId", nullable = false)
	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	@Column(name = "problemNum", nullable = false)
	public Integer getProblemNum() {
		return problemNum;
	}

	public void setProblemNum(Integer problemNum) {
		this.problemNum = problemNum;
	}
	
	@Column(name = "lowerLimit", nullable = false)
	public Integer getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Integer lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	@Column(name = "upperLimit", nullable = false)
	public Integer getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Integer upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	@Column(name = "score", precision = 12, scale = 0)
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	@Column(name = "bestBefore", length = 19)
	public Date getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}
	
	@Column(name = "scoreCoef")
	public Double getScoreCoef() {
		return scoreCoef;
	}

	public void setScoreCoef(Double scoreCoef) {
		this.scoreCoef = scoreCoef;
	}
	
	@Column(name = "deadline", length = 19)
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@Column(name = "updateTime", length = 19)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "rowX")
	public Integer getRowX() {
		return rowX;
	}


	public void setRowX(Integer rowX) {
		this.rowX = rowX;
	}

	@Column(name = "colY")
	public Integer getColY() {
		return colY;
	}

	public void setColY(Integer colY) {
		this.colY = colY;
	}
	
	
	
}
