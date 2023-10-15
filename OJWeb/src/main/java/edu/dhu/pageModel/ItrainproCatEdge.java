package edu.dhu.pageModel;

public class ItrainproCatEdge implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8880784283320755008L;
	
	//边结点的名称
	private String name;
	
	//边结点起始结点id
	private int from;
	
	//边结点末尾结点id
	private int to;


	public ItrainproCatEdge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItrainproCatEdge(String name, int from, int to) {
		super();
		this.name = name;
		this.from = from;
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
	
}
