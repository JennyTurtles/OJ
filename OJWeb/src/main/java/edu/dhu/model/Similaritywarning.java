package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "similaritywarning", catalog = "gdoj")
public class Similaritywarning implements java.io.Serializable {

	private static final long serialVersionUID = -2775288180366127128L;

	private Integer id;
	private Integer solutionId;
	private Date warningTime;
	private boolean submited;
	private Integer eversubmit;

	public Similaritywarning() {
	}

	public Similaritywarning(Integer id, Integer solutionId, Date warningTime,
			boolean submited, Integer eversubmit) {
		this.id = id;
		this.solutionId = solutionId;
		this.warningTime = warningTime;
		this.submited = submited;
		this.eversubmit = eversubmit;
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

	@Column(name = "solutionId")
	public Integer getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name = "warningTime")
	public Date getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(Date warningTime) {
		this.warningTime = warningTime;
	}

	@Column(name = "submited")
	public boolean getSubmited() {
		return submited;
	}

	public void setSubmited(boolean submited) {
		this.submited = submited;
	}

	public void setEversubmit(Integer eversubmit) {
		this.eversubmit = eversubmit;
	}

	@Column(name = "eversubmit")
	public Integer getEversubmit() {
		return eversubmit;
	}

}