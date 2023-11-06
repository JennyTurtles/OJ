package edu.dhu.exam.model;

import java.util.Date;

public class PMPaperExam implements java.io.Serializable {
	
	// 为了分页而封装到User中。 从前台穿参数到后台
		private int page;
		private int rows;
		private String flag;
		private String sort;
		private String order;
		private String ids;

		// paperexam的基本属性
		private Integer id;
		private String name;
		private Date start_time;
		private Date end_time;
		private Integer duration;
		private String description;
		private Integer problem_Num;
		private Date create_Time;
		private Date update_Time;
		
//		private boolean canGetHint;
//		private boolean partialScore;
//		private String language;
		private String teacherIds;
		private Integer teacherId;
//		private boolean submitOnlyAC;
//		private boolean allowChangeSeat;

		// 页面上其他需要的属性
		private Integer studentId;
		private String teacherName;
		private String status;
		private Date lastUpdateTime;
		private Date updateTime;
		private String loginIp;
		private Integer schoolId;


		
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getStart_time() {
			return start_time;
		}

		public void setStart_time(Date start_time) {
			this.start_time = start_time;
		}

		public Date getEnd_time() {
			return end_time;
		}

		public void setEnd_time(Date end_time) {
			this.end_time = end_time;
		}

		public Integer getDuration() {
			return duration;
		}

		public void setDuration(Integer duration) {
			this.duration = duration;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getProblem_Num() {
			return problem_Num;
		}

		public void setProblem_Num(Integer problem_Num) {
			this.problem_Num = problem_Num;
		}

		public Date getCreate_Time() {
			return create_Time;
		}

		public void setCreate_Time(Date create_Time) {
			this.create_Time = create_Time;
		}

		public Date getUpdate_Time() {
			return update_Time;
		}

		public void setUpdate_Time(Date update_Time) {
			this.update_Time = update_Time;
		}

		public Integer getTeacherId() {
			return teacherId;
		}

		public void setTeacherId(Integer teacherId) {
			this.teacherId = teacherId;
		}

		public String getTeacherIds() {
			return teacherIds;
		}

		public void setTeacherIds(String teacherIds) {
			this.teacherIds = teacherIds;
		}

		
		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public Date getLastUpdateTime() {
			return lastUpdateTime;
		}

		public void setLastUpdateTime(Date lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getRows() {
			return rows;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}

		public String getTeacherName() {
			return teacherName;
		}

		public void setTeacherName(String teacherName) {
			this.teacherName = teacherName;
		}

		public Integer getStudentId() {
			return studentId;
		}

		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getLoginIp() {
			return loginIp;
		}

		public void setLoginIp(String loginIp) {
			this.loginIp = loginIp;
		}

		public Integer getSchoolId() {
			return schoolId;
		}

		public void setSchoolId(Integer schoolId) {
			this.schoolId = schoolId;
		}
//		public boolean isSubmitOnlyAC() {
//		return submitOnlyAC;
//	}
//
//	public void setSubmitOnlyAC(boolean submitOnlyAC) {
//		this.submitOnlyAC = submitOnlyAC;
//	}
//
//	public boolean isCanGetHint() {
//		return canGetHint;
//	}
//
//	public void setCanGetHint(boolean canGetHint) {
//		this.canGetHint = canGetHint;
//	}
//
//	public boolean isPartialScore() {
//		return partialScore;
//	}
//
//	public void setPartialScore(boolean partialScore) {
//		this.partialScore = partialScore;
//	}
//
//	public String getLanguage() {
//		return language;
//	}
//
//	public void setLanguage(String language) {
//		this.language = language;
//	}


//		public boolean getAllowChangeSeat() {
//			return allowChangeSeat;
//		}
//
//		public void setAllowChangeSeat(boolean allowChangeSeat) {
//			this.allowChangeSeat = allowChangeSeat;
//		}


}
