package edu.dhu.dao;

import edu.dhu.model.Exam;
import edu.dhu.pageModel.PMExam;

import java.util.List;

public interface ExamDaoI extends BaseDaoI<Exam> {

	// 学生未登录的情况下，查询一共有多少条考试列表，返回考试的总数量
	public long getAllExamsNum();

	// 学生未登录的情况下，获取所有的考试列表信息，返回所有考试列表的集合
	public List<Exam> getAllExams();

	// 学生未登录的情况下，获取当前页的所有的考试列表信息，返回当前页所有考试列表的集合
	// page:表示第几个，rows：表示每页显示多少行
	public List<Exam> getExamsByPage(int page, int rows);

	// 根据学生id，查询该学生一共有多少条考试获信息，返回该学生的考试总数量
	public long getAllExamsNumByStuId(int id);

	// 根据学生id，获取该学生所有的考试列表信息，返回该学生所有考试列表的集合
	public List<Exam> getExamsByStuId(int id);

	// 根据学生id,获取该学生当前页的所有的考试列表信息，返回该学生当前页所有考试列表的集合
	// page:表示第几个，rows：表示每页显示多少行
	public List<Exam> getExamsByPageAndStuId(int id, int page, int rows);
	
	//教师的查看所有考试页面的
	public List<Exam> getAllTeacherExamsOrderByEndTime(int teacherId, String roleName);

	public List<Exam> getAllExamsOrderByEndTime(int teacherId,String roleName); // 获取所有考试，依据结束时间逆序排列
	
	public List<Exam> getAllExamBySchoolIdOrderByEndTime(int schoolId); // 根据学校id获取相应的考试，依据结束时间逆序排序

	public boolean updateExamproblemNum(int examId, int num); // 更新考试题目数量

	public List<Exam> getExamByProblemId(int problemId); // 获取有problemId的考试

	public void examAdd(PMExam pexam); // 添加考试

	public int updateExam(PMExam pexam); // 更新考试

	public List<Exam> getExamsNotInClass(int classId,int teacherId); // 获取班级未参加的考试

	public List<Exam> getExamsByTeacherId(int adminId); // 通过教师id获取教师参加的考试
	

	public int updateAllowCSbyexamId(PMExam pexam);
	
	//根据examId获取exam
	public Exam getExamByExamId(int examId);
}
