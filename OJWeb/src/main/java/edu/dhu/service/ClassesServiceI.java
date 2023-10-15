package edu.dhu.service;

import edu.dhu.model.ClassTimes;
import edu.dhu.model.Classes;
import edu.dhu.model.StudentAttendance;
import edu.dhu.pageModel.Json;
import edu.dhu.pageModel.PMClasses;
import edu.dhu.pageModel.PMUser;

import java.util.Date;
import java.util.List;

public interface ClassesServiceI {

	public List<Classes> findAllClass();

	public List<PMClasses> findClassesByCondition(int teacherId);

	// public List<PMClasses> findClassesByCondition(PMClasses pmclasses);

	public boolean editClassMessage(int id, String className, int teacherId,int advance, int late, int reject, String weektime, Date first_week_monday); // 修改班级信息

	public boolean deleteClass(int id); // 删除班级

	public List<PMUser> findClassStudentsById(int classId); // 通过id查询所有学生
	

	public boolean deleteClassStudentsByUserId(int classId, int userId);

	public void addClass(PMClasses pclass); // 添加班级

	public List<PMClasses> findClassInExam(int examId); // 获取参与考试的班级
	
	public List<PMClasses> findClassInPaperExam(int paperexamId); // 获取参与考试的班级

	public List<PMClasses> findClassNotInExam(int examId); // 获取未参加考试的班级

	public List<PMClasses> findClassNotInPaperExam(int paperexamId); // 获取未参加笔试考试的班级
	
	public Classes findClassById(int classId);

	public List<Classes> findNotEndClassByTeacherId(int teacherId); // 通过教师查找所属的班级，班级时间为一年以内

	public List<PMClasses> findClassesByCondition(PMClasses pmclasses);  //查找教师没有参加该考试的班级
	
	public Json takeOneClass(String inviteCode,int studentID);
	
	public List<ClassTimes> getAttendanceByClassId(int classId); // 通过班级id查询考勤情况
	
	// 根据studentId获取该学生考勤的相关信息
	public List<StudentAttendance> findStudentAttendanceById(int studentId,int classId,int ClassTimeId);
	
//	public List<ClassTimes> getClassStudentsByClassId(int classId); // 通过班级id查询班级所有学生
}
