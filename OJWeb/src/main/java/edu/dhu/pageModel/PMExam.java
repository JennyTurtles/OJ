package edu.dhu.pageModel;

import java.util.Date;

public class PMExam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5643701670745565018L;
	// 为了分页而封装到User中。 从前台穿参数到后台
	private int page;
	private int rows;
	private String flag;
	private String sort;
	private String order;
	private String ids;

	// exam的基本属性
	private Integer id;
	private String name;
	private Date starttime;
	private Date endtime;
	private String description;
	private Integer problemNum;
	private boolean submitOnlyAC;
	private boolean canGetHint;
	private boolean partialScore;
	private String language;
	private Integer teacherId;
	private String teacherIds;
	private boolean allowChangeSeat;
	private String examType;


	// 页面上其他需要的属性
	private Integer studentId;
	private String teacherName;
	private String status;
	private Date lastUpdateTime;
	private Date updateTime;
	private String loginIp;
	private Integer schoolId;
	
	//参与该场考试的班级数量
	private Integer classNum;
	//参与该场考试的总人数
	private Integer stuNum;
	
	//该场考试创建人所创班级的的学生人数为0的班级ID
	private String noStuClassId;
	
	//该场考试创建人所创班级的的学生人数为0的班级名称
	private String noStuClassName;
	
	//2020.3.7 新增 学生查看成绩选项字段 by:zzb
	//	"System"智能显示
	//	"RankAndStatus"排名及题目状态
	//	"OnlyRank"完整排名
	//	"Top10"显示前10名
	//	"NoScorePage"不显示
	private String studentViewScore;
	private String type;
	
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

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getProblemNum() {
		return problemNum;
	}

	public void setProblemNum(Integer problemNum) {
		this.problemNum = problemNum;
	}

	public boolean isSubmitOnlyAC() {
		return submitOnlyAC;
	}

	public void setSubmitOnlyAC(boolean submitOnlyAC) {
		this.submitOnlyAC = submitOnlyAC;
	}

	public boolean isCanGetHint() {
		return canGetHint;
	}

	public void setCanGetHint(boolean canGetHint) {
		this.canGetHint = canGetHint;
	}

	public boolean isPartialScore() {
		return partialScore;
	}

	public void setPartialScore(boolean partialScore) {
		this.partialScore = partialScore;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
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

	public String getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(String teacherIds) {
		this.teacherIds = teacherIds;
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

	public boolean getAllowChangeSeat() {
		return allowChangeSeat;
	}

	public void setAllowChangeSeat(boolean allowChangeSeat) {
		this.allowChangeSeat = allowChangeSeat;
	}

	public String getStudentViewScore() {
		return studentViewScore;
	}

	public void setStudentViewScore(String studentViewScore) {
		this.studentViewScore = studentViewScore;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Integer getStuNum() {
		return stuNum;
	}

	public void setStuNum(Integer stuNum) {
		this.stuNum = stuNum;
	}

	public String getNoStuClassId() {
		return noStuClassId;
	}

	public void setNoStuClassId(String noStuClassId) {
		this.noStuClassId = noStuClassId;
	}

	public String getNoStuClassName() {
		return noStuClassName;
	}

	public void setNoStuClassName(String noStuClassName) {
		this.noStuClassName = noStuClassName;
	}
	
	
}
