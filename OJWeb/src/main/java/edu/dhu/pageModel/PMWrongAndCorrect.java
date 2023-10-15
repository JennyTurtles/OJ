package edu.dhu.pageModel;

import edu.dhu.model.Problemtestcases;
import edu.dhu.model.Wrongcases;

public class PMWrongAndCorrect implements java.io.Serializable {
	private static final long serialVersionUID = 1538275244203L;
	
	private Problemtestcases problemtestcases;
	private Wrongcases wrongcases;
	private float score;

	private boolean caseInfo;
	private int solutionId;

	public PMWrongAndCorrect() {
		super();
	}

	public PMWrongAndCorrect(Problemtestcases problemtestcases,
			Wrongcases wrongcases, float score, boolean caseInfo, int solutionId) {

		this.problemtestcases = problemtestcases;
		this.wrongcases = wrongcases;
		this.score = score;
		this.caseInfo = caseInfo;
		this.solutionId = solutionId;
	}

	public int getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	public boolean isCaseInfo() {
		return caseInfo;
	}

	public void setCaseInfo(boolean caseInfo) {
		this.caseInfo = caseInfo;
	}

	public Problemtestcases getProblemtestcases() {
		return problemtestcases;
	}

	public void setProblemtestcases(Problemtestcases problemtestcases) {
		this.problemtestcases = problemtestcases;
	}

	public Wrongcases getWrongcases() {
		return wrongcases;
	}

	public void setWrongcases(Wrongcases wrongcases) {
		this.wrongcases = wrongcases;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
