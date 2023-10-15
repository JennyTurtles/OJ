package edu.dhu.dao;

import edu.dhu.model.Classstudents;

import java.util.List;

public interface ClassstudentsDaoI extends BaseDaoI<Classstudents> {

	public boolean insertClassStudent(int userId, int classId);

	public boolean findClassStudentByUserId(int userId, int classId); // 查看该同学是否已在该课程中

	public int getClassStudentsNum(int classId); // 获取班级的学生人数

	public List<Classstudents> getClassStudentsByExamId(int examId); // 获取考试的学生
	
	public List<Classstudents> getClassStudentsByClassId(int classId);

}
