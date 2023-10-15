package edu.dhu.service;

import edu.dhu.model.Solution;
import edu.dhu.model.Studentexamdetail;
import edu.dhu.pageModel.PMProblemsStatus;

import java.util.List;

public interface StudentexamdetailServiceI {
	// 根据userID，examId,problemId获取当前用户提交的题目
	public Studentexamdetail getStatusByUserIDexamIDproblemId(int userId,
			int examId, int problemId);

	// 根据userID，examId获取当前用户提交的每一道题目
	public List<Studentexamdetail> getAllStudentexamdetailListByUserIdAndExamId(
			int userId, int examId);

	// 根据problemID的数组以及examID,userId查找题目的状态数组
	public PMProblemsStatus getProblemsStatusArrByIds(
			PMProblemsStatus pMProblemsStatus);

	// update 更新Studentexamdetail
	public void updateStudentexamdetail(Studentexamdetail studentexamdetail);

	// 保存Studentexamdetail
	public void saveStudentexamdetail(Studentexamdetail studentexamdetail);

	public int editStudentScore(Solution solution);

	public int editStudentDetailFinished(Solution solution);

}
