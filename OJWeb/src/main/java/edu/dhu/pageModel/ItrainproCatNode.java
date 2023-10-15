package edu.dhu.pageModel;

public class ItrainproCatNode implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8981123181762321892L;
	
	//结点名称
	private String name;
	
	//结点ID(二级分类id)
	private int id;
	
	//结点在页面的X坐标
	private double x;
	
	//结点在页面Y坐标
	private double y;

	
	public ItrainproCatNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ItrainproCatNode(String name, int id, double x, double y) {
		super();
		this.name = name;
		this.id = id;
		this.x = x;
		this.y = y;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
		
	
}
