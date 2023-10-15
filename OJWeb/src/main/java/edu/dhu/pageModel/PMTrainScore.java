package edu.dhu.pageModel;

public class PMTrainScore implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2058006805538170359L;
	
	private int id;
	private int userId;
	private String studentNo;
	private String chineseName;
	private String banji;
	private float score;
	private int rank;
	//完成的类别数
	private int solveCat;
	// 解题数
	private int solvePro;
	//已完成的类别
	private String finishedCat;
	
	//正在做
	private String doingCat;
	//未做
	private String undoCat;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public String getBanji() {
		return banji;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getScore() {
		return score;
	}

	public int getSolveCat() {
		return solveCat;
	}

	public void setSolveCat(int solveCat) {
		this.solveCat = solveCat;
	}

	public String getFinishedCat() {
		return finishedCat;
	}

	public void setFinishedCat(String finishedCat) {
		this.finishedCat = finishedCat;
	}


	public int getSolvePro() {
		return solvePro;
	}

	public void setSolvePro(int solvePro) {
		this.solvePro = solvePro;
	}

	public String getDoingCat() {
		return doingCat;
	}

	public void setDoingCat(String doingCat) {
		this.doingCat = doingCat;
	}

	public String getUndoCat() {
		return undoCat;
	}

	public void setUndoCat(String undoCat) {
		this.undoCat = undoCat;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
