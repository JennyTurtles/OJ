package edu.dhu.pageModel;

import edu.dhu.model.Exam;

import java.util.List;

public class PMExamProblemInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1538274748391L;
	
	private List<PMProblems> problemsList;
	private Exam exam;

	public PMExamProblemInfo() {
	}

	public PMExamProblemInfo(List<PMProblems> problemsList, Exam exam) {

		this.problemsList = problemsList;
		this.exam = exam;
	}

	public List<PMProblems> getProblemsList() {
		return problemsList;
	}

	public void setProblemsList(List<PMProblems> problemsList) {
		this.problemsList = problemsList;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
