package edu.dhu.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PMExam implements java.io.Serializable {

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

}
