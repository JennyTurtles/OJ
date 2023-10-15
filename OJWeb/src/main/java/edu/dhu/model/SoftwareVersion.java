package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "softwareversion", catalog = "gdoj")
public class SoftwareVersion implements java.io.Serializable{

	private static final long serialVersionUID = -5632879541256802669L;
	private Integer id;
	private String versionName;
	private String description;
	private String location;
	private Date lastModifiedTime;
	private String ex1;
	private Double ex2;
	
	public SoftwareVersion(){
		super();
		this.id = null;
		this.versionName = null;
		this.description = null;
		this.location = null;
		this.lastModifiedTime = null;
		this.ex1 = null;
		this.ex2 = null;
	}
	
	public SoftwareVersion(Integer id, String versionName, String description,
			String location) {
		super();
		this.id = id;
		this.versionName = versionName;
		this.description = description;
		this.location = location;
	}
	
	public SoftwareVersion(Integer id, String versionName, String description,
			String location, Date lastModifiedTime, String ex1, Double ex2) {
		super();
		this.id = id;
		this.versionName = versionName;
		this.description = description;
		this.location = location;
		this.lastModifiedTime = lastModifiedTime;
		this.ex1 = ex1;
		this.ex2 = ex2;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "versionName", nullable = false, length = 255)
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	@Column(name="description", nullable = false, length = 4095)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="location", nullable = false, length = 511)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "lastModifiedTime")
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	@Column(name = "ex1", length = 255)
	public String getEx1() {
		return ex1;
	}

	public void setEx1(String ex1) {
		this.ex1 = ex1;
	}

	@Column(name = "ex2")
	public Double getEx2() {
		return ex2;
	}

	public void setEx2(Double ex2) {
		this.ex2 = ex2;
	}

	
}
