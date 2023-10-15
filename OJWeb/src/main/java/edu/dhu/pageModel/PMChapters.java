package edu.dhu.pageModel;

public class PMChapters implements java.io.Serializable {
	private static final long serialVersionUID = 1538274629905L;
	
	// chapter基本属性
	private int id;
	private String code;
	private String name;
	private int level;
	private String chapterName;

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
