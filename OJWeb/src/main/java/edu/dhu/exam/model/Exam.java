package edu.dhu.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam implements java.io.Serializable {

	private static final long serialVersionUID = 6817642372609057372L;
	private Integer id;
	private String name;
	private Date starttime;
	private Date endtime;
	private String endTimestamp;
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
	private String studentViewScore;

	public Exam(String name, Integer problemNum, Integer teacherId) {
		this.name = name;
		this.problemNum = problemNum;
		this.teacherId = teacherId;
	}

	public Exam(Long endTimestamp) {
		this.endTimestamp = String.valueOf(endTimestamp);
	}
}