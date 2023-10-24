package edu.dhu.exam.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Exam entity. @author MyEclipse Persistence Tools
 */
@Entity
@Data
@Table(name = "exam", catalog = "gdoj")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exam implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 6817642372609057372L;
	private Integer id;
	private String name;
	private Date starttime;
	private Date endtime;
	private Long leftTime;
	private String description;
	private Integer problemNum;
	private boolean canGetHint;
	private boolean partialScore;
	private String language;
	private Integer teacherId;
	private boolean submitOnlyAC;
	private Date updateTime;
	private boolean allowChangeSeat;
	private String type;

	//2020.3.7 新增 学生查看成绩选项字段 by:zzb
	//	"System"智能显示
	//	"RankAndStatus"排名及题目状态
	//	"OnlyRank"完整排名
	//	"Top10"显示前10名
	//	"NoScorePage"不显示
	private String studentViewScore;

	// Constructors

	/** default constructor */
	public Exam() {
	}
	public Exam(Long endTimestamp) {
		this.leftTime = endTimestamp;
	}
	/** minimal constructor */
	public Exam(String name, Integer problemNum, Integer teacherId) {
		this.name = name;
		this.problemNum = problemNum;
		this.teacherId = teacherId;
	}

	/** full constructor */
	public Exam(String name, Date starttime, Date endtime, String description, Integer problemNum, boolean canGetHint,
				boolean partialScore, String language, Integer teacherId, boolean submitOnlyAC, Date updateTime,
				boolean allowChangeSeat, String studentViewScore, String type) {
		this.name = name;
		this.starttime = starttime;
		this.endtime = endtime;
		this.description = description;
		this.problemNum = problemNum;
		this.canGetHint = canGetHint;
		this.partialScore = partialScore;
		this.language = language;
		this.teacherId = teacherId;
		this.submitOnlyAC = submitOnlyAC;
		this.updateTime = updateTime;
		this.allowChangeSeat = allowChangeSeat;
		this.studentViewScore = studentViewScore;
		this.type = type;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "starttime", length = 19)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@Column(name = "endtime", length = 19)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Column(name = "description", length = 1000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "problemNum", nullable = false)
	public Integer getProblemNum() {
		return this.problemNum;
	}

	public void setProblemNum(Integer problemNum) {
		this.problemNum = problemNum;
	}

	@Column(name = "canGetHint")
	public boolean getCanGetHint() {
		return this.canGetHint;
	}

	public void setCanGetHint(boolean canGetHint) {
		this.canGetHint = canGetHint;
	}

	@Column(name = "partialScore")
	public boolean getPartialScore() {
		return this.partialScore;
	}

	public void setPartialScore(boolean partialScore) {
		this.partialScore = partialScore;
	}

	@Column(name = "language", length = 100)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "teacherId", nullable = false)
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public boolean isSubmitOnlyAC() {
		return submitOnlyAC;
	}

	@Column(name = "submitOnlyAC")
	public void setSubmitOnlyAC(boolean submitOnlyAC) {
		this.submitOnlyAC = submitOnlyAC;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Column(name = "updateTime")
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean getAllowChangeSeat() {
		return allowChangeSeat;
	}

	@Column(name = "allowChangeSeat")
	public void setAllowChangeSeat(boolean allowChangeSeat) {
		this.allowChangeSeat = allowChangeSeat;
	}

	public String getStudentViewScore() {
		return studentViewScore;
	}
	
	@Column(name = "studentViewScore")
	public void setStudentViewScore(String studentViewScore) {
		this.studentViewScore = studentViewScore;
	}

	public String getType() {
		return type;
	}

	@Column(name = "type")
	public void setType(String type) {
		this.type = type;
	}
	
	

}