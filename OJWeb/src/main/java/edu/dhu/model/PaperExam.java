package edu.dhu.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "paper_exam", catalog = "gdoj")
public class PaperExam implements java.io.Serializable{
	
	private Integer id;
	private String name;
	private Date start_time;
	private Date end_time;
	private Integer duration;
	private String description;
	private Integer problem_Num;
	private Date create_Time;
	private Date update_Time;
	
//	private boolean canGetHint;
//	private boolean partialScore;
//	private String language;
	private Integer teacherId;
//	private boolean submitOnlyAC;
//	private boolean allowChangeSeat;
	
	/** default constructor */
	public PaperExam() {
	}

	/** minimal constructor */
	public PaperExam(String name, Integer problem_Num, Integer teacherId) {
		this.name = name;
		this.problem_Num = problem_Num;
		this.teacherId = teacherId;
	}
	/** full constructor */
	public PaperExam(String name, Date start_time, Date end_time, String description,
			Integer problem_Num,Integer duration,Integer teacherId, 
			Date create_Time,Date update_Time) {
		this.name = name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.description = description;
		this.problem_Num = problem_Num;
		this.duration = duration;
		this.teacherId = teacherId;
		this.create_Time = create_Time;
		this.update_Time = update_Time;

	}
//	/** full constructor */
//	public PaperExam(String name, Date start_time, Date end_time, String description,
//			Integer problem_Num,Integer duration, boolean canGetHint, boolean partialScore,
//			String language, Integer teacherId, boolean submitOnlyAC,
//			Date create_Time,Date update_Time, boolean allowChangeSeat) {
//		this.name = name;
//		this.start_time = start_time;
//		this.end_time = end_time;
//		this.description = description;
//		this.problem_Num = problem_Num;
//		this.duration = duration;
////		this.canGetHint = canGetHint;
////		this.partialScore = partialScore;
////		this.language = language;
//		this.teacherId = teacherId;
////		this.submitOnlyAC = submitOnlyAC;
//		this.create_Time = create_Time;
//		this.update_Time = update_Time;
////		this.allowChangeSeat = allowChangeSeat;
//	}
	
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

		@Column(name = "name",   length = 32)
		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Column(name = "start_time", length = 19)
		public Date getStart_time() {
			return this.start_time;
		}

		public void setStart_time(Date start_time) {
			this.start_time = start_time;
		}

		@Column(name = "end_time",  length = 19)
		public Date getEnd_time() {
			return this.end_time;
		}

		public void setEnd_time(Date end_time) {
			this.end_time = end_time;
		}

		@Column(name = "description", length = 512)
		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Column(name = "duration")
		public Integer getduration() {
			return this.duration;
		}

		public void setduration(Integer duration) {
			this.duration = duration;
		}

		@Column(name = "problem_Num")
		public Integer getProblem_Num() {
			return this.problem_Num;
		}

		public void setProblem_Num(Integer problem_Num) {
			this.problem_Num = problem_Num;
		}
		
		
		public Date getCreate_Time() {
			return create_Time;
		}

		@Column(name = "create_Time" )
		public void setCreate_Time(Date create_Time) {
			this.create_Time = create_Time;
		}

		public Date getUpdate_Time() {
			return update_Time;
		}

		@Column(name = "update_Time")
		public void setUpdate_Time(Date update_Time) {
			this.update_Time = update_Time;
		}
		
//		
//		@Column(name = "canGetHint")
//		public boolean getCanGetHint() {
//			return this.canGetHint;
//		}
//
//		public void setCanGetHint(boolean canGetHint) {
//			this.canGetHint = canGetHint;
//		}
//
//		@Column(name = "partialScore")
//		public boolean getPartialScore() {
//			return this.partialScore;
//		}
//
//		public void setPartialScore(boolean partialScore) {
//			this.partialScore = partialScore;
//		}
//
//		@Column(name = "language", length = 100)
//		public String getLanguage() {
//			return this.language;
//		}
//
//		public void setLanguage(String language) {
//			this.language = language;
//		}
//
		@Column(name = "teacherId")
		public Integer getTeacherId() {
			return this.teacherId;
		}

		public void setTeacherId(Integer teacherId) {
			this.teacherId = teacherId;
		}

//		public boolean isSubmitOnlyAC() {
//			return submitOnlyAC;
//		}
//
//		@Column(name = "submitOnlyAC")
//		public void setSubmitOnlyAC(boolean submitOnlyAC) {
//			this.submitOnlyAC = submitOnlyAC;
//		}
//		
//		public boolean getAllowChangeSeat() {
//			return allowChangeSeat;
//		}
//
//		@Column(name = "allowChangeSeat")
//		public void setAllowChangeSeat(boolean allowChangeSeat) {
//			this.allowChangeSeat = allowChangeSeat;
//		}


}
