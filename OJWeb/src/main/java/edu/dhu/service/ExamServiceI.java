package edu.dhu.service;

import edu.dhu.model.Exam;
import edu.dhu.pageModel.DataGrid;
import edu.dhu.pageModel.Json;
import edu.dhu.pageModel.PMExam;

import java.util.List;

public interface ExamServiceI {

	// 根据学生id查询所有考试列表和数量
	public DataGrid dataGrid(PMExam pMExam);

	public List<PMExam> getExamsByUserId(int userId);

	// 根据examID和userID判断该场考试该学生是否能够参加
	public boolean checkExamByUserIdAndExamId(int examId, int userId);

	// 根据examId获取本场考试的相关信息
	public Exam getExamById(int examId);

	public List<PMExam> getAllExamsOrderByEndTime(int teacherID,String roleName); // 获取所有的考试
	
//	public List<PMExam> getAllExamsOrderByEndTime(int teacherID,String roleName); // 获取所有的考试

	public List<Exam> getExamByProblemId(int problemId);

	public void examAdd(PMExam pexam); // 添加考试

	public int updateExam(PMExam pexam); // 更新考试

	public List<PMExam> getExamsNotInClass(int classId,int teacherId); // 获取班级没有参加的考试

	public List<PMExam> getExamsByTeacherId(int adminId); // 通过教师id获取教师参加的考试

	public List<PMExam> getAllExamsOrderByEndTime(PMExam pMExam,String roleName);

	public int updateAllowCSbyexamId(PMExam pMExam);
	
	public Json  getStudentRank(int userId,int examId);
	//根据examId获取exam
	public Exam getExamByExamId(int examId);
}
