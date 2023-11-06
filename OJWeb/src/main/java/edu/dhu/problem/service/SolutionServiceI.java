package edu.dhu.problem.service;


import edu.dhu.exam.model.ExamStudent;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.problem.model.*;
import edu.dhu.user.model.Json;

import java.util.List;
import java.util.Map;

public interface SolutionServiceI {
	// 用户提交代码
	public boolean submitCode(Solution solution);

	// 根据solutionId获取Solution
	public Solution getSolutionById(int solutionId);

	// 根据solutionID获取整条记录
	public String[] getCorrectCaseIds(int solutionId);

	// 根据userID,examID,problemID查找solutionID最大的记录
	public Solution getLastSolutionByUserIdExamIdProblemId(int userId,
			int examId, int problemId);
    
	// 根据userID,examID,problemID以及status查找solutionID最大的记录
	public Solution getLastSolutionByUserIdExamIdProblemIdAndStatus(int userId,
			int examId, int problemId, String status);

	// 更新Solution
	public void updateSolution(Solution solution);

	public List<PMSolution> getExamSubmitSolution(boolean isLast, int examId, int teacherId,
												  int nowPage, int pageSize, String displaySequence,
												  String studentNo, String name, String banji, float similarity,
												  String searchTime); // 查看考试的solution
	
	public List<PMSolution> getTrainSubmitSolution(boolean isLast, int examId,int teacherId,
			int nowPage, int pageSize,
			String studentNo, String name, String banji, float similarity,
			String searchTime); // 查看Train的solution

	public long getExamSubmitSolutionCount(boolean isLast, int examId,int teacherId,
			String displaySequence, String studentNo, String name,
			String banji, float similarity, String searchTime); // 获取考试的solution总数
	
	public long getTrainSubmitSolutionCount(boolean isLast, int examId,int teacherId,String role,
			String studentNo, String name,
			String banji, float similarity, String searchTime); // 获取Train的solution总数

	public List<PMSolution> exportExamSearchSolution(boolean isLast,
			int examId, String displaySequence, String studentNo, String name,
			String banji, float similarity, String searchTime);
	
	public List<Solution> exportExamCode(int examId); //获取所有班级的考试的代码

	public List<Solution> exportExamCode(int examId, int teacherId); //获取考试的   老师自己班级学生的代码
	
	public List<Solution> exportTrainCode(int examId); //获取所有班级的训练的代码
	
	public List<Solution> exportTrainCode(int examId,int teacherId); //获取教师自己班级的训练的代码

	public List<Solution> exportClassExamCode(int examId, int classId); // 导出本班考试的最后代码
	
	public List<Solution> exportClassTrainCode(int examId, int classId); // 导出本班训练的最后代码
	
	public List<Solution> exportExamLastCode(int examId);//获取考试的，最后提交的 所有班级的代码
	
	public List<Solution> exportExamLastCode(int examId,int teacherId);//获取考试的，最后提交的  老师自己班级的代码
	
	public List<Solution> exportTrainLastCode(int examId);//获取训练的,最后提交的  所有班级的代码
	
	public List<Solution> exportTrainLastCode(int examId,int teacherId);//获取训练的,最后提交的 教师自己的班级的代码
	
	public List<ExamStudent> getExamStudentInfo(int examId, int classId);

	public List<Solution> exportClassExamLastCode(int examId, int classId);
	
	public List<Solution> exportClassTrainLastCode(int examId, int classId);
	
	public List<PMSolution> exportExamLastCode(boolean isLast, int examId, String displaySequence, 
			String studentNo, String name, String banji, float similarity, String searchTime);
	public Solution getSourceCode(int id);

	public List<PMSolution> getCopyList(int examId, int teacherId,String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
	
	public List<PMSolution> getTrainCopyList(int examId, int teacherId,
			String studentNo, String name, String banji, float similarity,
			String searchTime);

	public boolean deleteSubmitBySolutionId(int solutionId, int reason,
			boolean isCopy, String remark); // 撤销提交
	
	public boolean deleteTrainSubmitBySolutionId(int solutionId, int reason,
			boolean isCopy, String remark); // 撤销提交

	public boolean isSubmited(int solutionId); // 查看solution在similarityWarning中是否为submited
	
	public boolean isTrainSubmited(int solutionId);
	
	public Json submitThisProblem(Studentexamdetail studentexamdetail,
								  PMWrongAndCorrectIds pMWrongAndCorrectIds, Json j,
								  boolean isOverSimilarity);
//
	public Json WS_submitThisProblem(Studentexamdetail studentexamdetail,
			PMSubmitProblemInfo WS_solution);

	public List<Solution> getSolutionsByNumber(int number); // 查找状态为WAIT的solutions

	public int editStudentScore(Solution solution);

	public Json WS_ItrainSubmitThisProblem(StudentTrainProbDetail stpd, PMSubmitProblemInfo WS_solution, String continueTrain);

//	public Json submitItrainThisProblem(StudentTrainProbDetail stpd,
//			PMWrongAndCorrectIds pMWrongAndCorrectIds, Json j,
//			boolean isOverSimilarity,String continueTrain);
	
	
	public List<Map<String,Object>> getExamSubmitSolutionInfo(boolean isLast, int examId,int teacherId,
			int nowPage, int pageSize, String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
	
	public List<Map<String,Object>> getCopyListInfo(int examId, int teacherId,String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
	
	public List<Map<String,Object>> getTrainSubmitSolutionInfo(boolean isLast, int examId,int teacherId,
			String role,int nowPage, int pageSize,
			String studentNo, String name, String banji, float similarity,
			String searchTime); // 查看Train的solution

	public List<Map<String,Object>> getTrainCopyListInfo(int examId, int teacherId, String role,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
}
