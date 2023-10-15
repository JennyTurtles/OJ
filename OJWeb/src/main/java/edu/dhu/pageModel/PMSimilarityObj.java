package edu.dhu.pageModel;

public class PMSimilarityObj implements java.io.Serializable {
	private static final long serialVersionUID = 1538275085986L;

	// 是否超过相似度阈值
	private boolean overSimilarity;
	// 相似度值为多少
	private float similarityValue;
	// 自己的solutionID为多少
	private int selfSolutionId;
	// 另一个用户的solutionID为多少
	private int otherSolutionId;
	// 检查的题目名称
	private String problemName;

	public PMSimilarityObj() {
	}

	public PMSimilarityObj(boolean overSimilarity, float similarityValue,
			int selfSolutionId, int otherSolutionId, String problemName) {
		this.overSimilarity = overSimilarity;
		this.similarityValue = similarityValue;
		this.selfSolutionId = selfSolutionId;
		this.otherSolutionId = otherSolutionId;
		this.problemName = problemName;
	}

	public boolean isOverSimilarity() {
		return overSimilarity;
	}

	public void setOverSimilarity(boolean overSimilarity) {
		this.overSimilarity = overSimilarity;
	}

	public float getSimilarityValue() {
		return similarityValue;
	}

	public void setSimilarityValue(float similarityValue) {
		this.similarityValue = similarityValue;
	}

	public int getSelfSolutionId() {
		return selfSolutionId;
	}

	public void setSelfSolutionId(int selfSolutionId) {
		this.selfSolutionId = selfSolutionId;
	}

	public int getOtherSolutionId() {
		return otherSolutionId;
	}

	public void setOtherSolutionId(int otherSolutionId) {
		this.otherSolutionId = otherSolutionId;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

}
