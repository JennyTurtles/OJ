package edu.dhu.dao;

import edu.dhu.model.Studentexaminfo;

import java.util.List;

public interface StudentexaminfoDaoI extends BaseDaoI<Studentexaminfo> {

	public List<Studentexaminfo> getClassExamScore(int examId, int classId); // 获取班级成绩

	// 根据userId和examId获取Studentexaminfo记录
	public Studentexaminfo getStudentexaminfoByUserIdAnExamId(int userId,
			int examId);

	// 更新studentexaminfo
	public void updateStudentexaminfo(Studentexaminfo studentexaminfo);
	
	//将学生的考试的uuid和ip地址置为空
	public void setUuidAndIp(int examId,int userId);

	public void deleteSubmit(int userId, int examId, float score, String status); // 撤销提交后更新iNfo表
	
	public int updateStudentScore(int userId, int examId, float score);
	//查询此前ip的登录者
	public String getStuByExamIdAndIp(int examId,String ip);

}
