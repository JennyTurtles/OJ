package edu.dhu.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * SimilaritywarningId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class SimilaritywarningId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7783990134042422472L;
	private Integer solutionId;
	private Date warningTime;

	// Constructors

	/** default constructor */
	public SimilaritywarningId() {
	}

	/** minimal constructor */
	public SimilaritywarningId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	/** full constructor */
	public SimilaritywarningId(Integer solutionId, Date warningTime) {
		this.solutionId = solutionId;
		this.warningTime = warningTime;
	}

	// Property accessors

	@Column(name = "solutionId", nullable = false)
	public Integer getSolutionId() {
		return this.solutionId;
	}

	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}

	@Column(name = "warningTime", length = 19)
	public Date getWarningTime() {
		return this.warningTime;
	}

	public void setWarningTime(Date warningTime) {
		this.warningTime = warningTime;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SimilaritywarningId))
			return false;
		SimilaritywarningId castOther = (SimilaritywarningId) other;

		return ((this.getSolutionId() == castOther.getSolutionId()) || (this
				.getSolutionId() != null && castOther.getSolutionId() != null && this
				.getSolutionId().equals(castOther.getSolutionId())))
				&& ((this.getWarningTime() == castOther.getWarningTime()) || (this
						.getWarningTime() != null
						&& castOther.getWarningTime() != null && this
						.getWarningTime().equals(castOther.getWarningTime())));
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSolutionId() == null ? 0 : this.getSolutionId()
						.hashCode());
		result = 37
				* result
				+ (getWarningTime() == null ? 0 : this.getWarningTime()
						.hashCode());
		return result;
	}

}