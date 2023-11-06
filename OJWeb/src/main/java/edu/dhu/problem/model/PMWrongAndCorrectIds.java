package edu.dhu.problem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PMWrongAndCorrectIds implements java.io.Serializable {
	private static final long serialVersionUID = 1538275279606L;

	private String submitType;
	private int userId;
	private int examId;
	private int problemId;
	private int solutionId;
	private int wrongcasesId;
	private int problemtestcasesId;
	// 该题的运行状态
	private String status;
	// 该题的提交次数
	private int submit;
	// 该题的所得分数
	private float score;
	// 正确案例的ID数组
	private String[] correctCaseIds;
	// 错误案例的list列表
	private List<Wrongcases> Wrongcases;
	// 该题的详细信息
	private String remark;

	private boolean finished;

	private int elapsedTime;

	private String hintCases;

	// 该题代码
	private String code;
	// 本题提交时间
	private Date submitTime;

	public PMWrongAndCorrectIds() {
	}

	public PMWrongAndCorrectIds(String submitType, int userId, int examId,
								int problemId, int solutionId, int wrongcasesId,
								int problemtestcasesId, String status, int submit, float score,
								String[] correctCaseIds, List<Wrongcases> wrongcases,
								String remark, boolean finished, int elapsedTime, String hintCases,
								String code, Date submitTime) {
		super();
		this.submitType = submitType;
		this.userId = userId;
		this.examId = examId;
		this.problemId = problemId;
		this.solutionId = solutionId;
		this.wrongcasesId = wrongcasesId;
		this.problemtestcasesId = problemtestcasesId;
		this.status = status;
		this.submit = submit;
		this.score = score;
		this.correctCaseIds = correctCaseIds;
		Wrongcases = wrongcases;
		this.remark = remark;
		this.finished = finished;
		this.elapsedTime = elapsedTime;
		this.hintCases = hintCases;
		this.code = code;
		this.submitTime = submitTime;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getHintCases() {
		return hintCases;
	}

	public void setHintCases(String hintCases) {
		this.hintCases = hintCases;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getExamId() {
		return examId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public int getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	public int getWrongcasesId() {
		return wrongcasesId;
	}

	public void setWrongcasesId(int wrongcasesId) {
		this.wrongcasesId = wrongcasesId;
	}

	public int getProblemtestcasesId() {
		return problemtestcasesId;
	}

	public void setProblemtestcasesId(int problemtestcasesId) {
		this.problemtestcasesId = problemtestcasesId;
	}

	public String[] getCorrectCaseIds() {
		return correctCaseIds;
	}

	public void setCorrectCaseIds(String[] correctCaseIds) {
		this.correctCaseIds = correctCaseIds;
	}

	public List<Wrongcases> getWrongcases() {
		return Wrongcases;
	}

	public void setWrongcases(List<Wrongcases> wrongcases) {
		Wrongcases = wrongcases;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getSubmit() {
		return submit;
	}

	public void setSubmit(int submit) {
		this.submit = submit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

}
