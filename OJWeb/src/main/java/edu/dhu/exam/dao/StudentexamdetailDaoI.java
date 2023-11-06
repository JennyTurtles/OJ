package edu.dhu.exam.dao;

import edu.dhu.exam.model.PMStudentDetail;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.problem.model.PMProblemsStatus;
import edu.dhu.problem.model.Solution;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentexamdetailDao")
public interface StudentexamdetailDaoI extends BaseDaoI<Studentexamdetail> {

	public List<Studentexamdetail> getStudentexamdetailSubmit(int userId,
			int examId); // 通过用户id和考试id获取已提交的考试细节

	public List<Studentexamdetail> getStudentexamdetailDoing(int userId,
			int examId); // 获取正在做的题

	// 根据userID，examID获取该学生所有的Studentexamdetail列表
	public List<Studentexamdetail> getAllStudentexamdetailListByUserIdAndExamId(
			int userId, int examId);

	// 根据userID，examId,problemId获取当前用户提交的题目
	public Studentexamdetail getStatusByUserIDexamIDproblemId(int userId,
			int examId, int problemId);

	// update 更新Studentexamdetail
	public void updateStudentexamdetail(Studentexamdetail studentexamdetail);

	// 获取考试的所有提交信息
	public List<Studentexamdetail> getClassStudentexamdetail(int examId,
			int classId);

	// 根据problemID的数组以及examID,userId查找题目的状态数组
	public PMProblemsStatus getProblemsStatusArrByIds(
			PMProblemsStatus pMProblemsStatus);

	public Studentexamdetail deleteFinishedBySolutionId(int solutionId,
			int reason); // 撤销detail表的finished

	public List<Object[]> getSubmitedDetailByExamId(int examId); // 获取考试所有提交的detail

	public Studentexamdetail getDetailBySolutionId(int solutionId);

	public int editStudentScore(Solution solution);

	public int editStudentDetailFinished(Solution solution);
	public List<PMStudentDetail> getStudentexamdetailsWithSubmittime(int examId);

}
