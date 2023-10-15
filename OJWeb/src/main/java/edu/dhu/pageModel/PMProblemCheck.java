package edu.dhu.pageModel;

public class PMProblemCheck implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2854582218695449095L;
	private String sourceCode;
	private String language;
	private float memoryLimit;
	private int timeLimit;
	private String[] testIn;
	private String[] testOut;

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public float getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(float memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String[] getTestIn() {
		return testIn;
	}

	public void setTestIn(String[] testIn) {
		this.testIn = testIn;
	}

	public String[] getTestOut() {
		return testOut;
	}

	public void setTestOut(String[] testOut) {
		this.testOut = testOut;
	}

}
