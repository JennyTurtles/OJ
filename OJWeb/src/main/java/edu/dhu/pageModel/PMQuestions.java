package edu.dhu.pageModel;

public class PMQuestions implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4916578918781258048L;
	private Integer id;
	private String question;

	public PMQuestions() {
	}

	public PMQuestions(Integer id, String question) {
		this.id = id;
		this.question = question;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
