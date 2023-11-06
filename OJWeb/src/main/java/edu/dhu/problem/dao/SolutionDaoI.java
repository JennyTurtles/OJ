package edu.dhu.problem.dao;


import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.problem.model.Solution;

import java.util.List;
import java.util.Map;

public interface SolutionDaoI extends BaseDaoI<Solution> {

	// 用户提交代码
	public boolean submitCode(Solution solution);

	// 根据userID,examID,problemID查找solutionID最大的记录
	public Solution getLastSolutionByUserIdExamIdProblemId(int userId,
			int examId, int problemId);

	// 根据userID,examID,problemID以及status查找solutionID最大的记录
	public Solution getLastSolutionByUserIdExamIdProblemIdAndStatus(int userId,
			int examId, int problemId, String status);

	public List<Solution> getExamSubmitSolution(boolean isLast, int examId,int teacherId,
			int nowPage, int pageSize, String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime); // 查看考试的solution
	
	public List<Solution> getTrainSubmitSolution(boolean isLast, int examId,int teacherId,
			int nowPage, int pageSize, 
			String studentNo, String name, String banji, float similarity,
			String searchTime); // 查看Train的solution

	public long getExamSolutionCount(boolean isLast, int examId,int teacherId,
			String displaySequence, String studentNo, String name,
			String banji, float similarity, String searchTime); // 考试的solution数
	
	public long getTrainSolutionCount(boolean isLast, int examId,int teacherId,String role,
		    String studentNo, String name,
			String banji, float similarity, String searchTime); // Train的solution数

	public List<Solution> exportExamSearchSolution(boolean isLast, int examId,
			String displaySequence, String studentNo, String name,
			String banji, float similarity, String searchTime); // 获得筛选的考试solution

	public List<Solution> exportExamCode(int examId); // 导出考试的所有solution的code
	
	public List<Solution> exportExamCode(int examId,int teacherId); // 导出考试的教师自己班级的solution的code
	
	public List<Solution> exportTrainCode(int examId); // 导出训练的所有solution的code
	
	public List<Solution> exportTrainCode(int examId,int teacherId); // 导出训练的教师自己班级的solution的code

	public List<Solution> exportClassExamCode(int examId, int classId); // 导出本班考试的代码
	
	public List<Solution> exportClassTrainCode(int examId, int classId); // 导出本班训练的代码

	public List<Solution> exportExamLastCode(int examId); // 导出考试的，最后提交的，所有solution最后的code
	
	public List<Solution> exportExamLastCode(int examId,int teacherId); // 导出考试的，最后提交的，教师自己班级的solution
	
	public List<Solution> exportTrainLastCode(int examId); // 导出训练的，最后提交的，所有solution最后的code
	
	public List<Solution> exportTrainLastCode(int examId,int teacherId); // 导出训练的，最后提交的，教师自己班级的solution最后的code
	
	public List<Object[]> getExamStudentInfo(int examId,int classId); //获取本场考试考生以及班级信息

	public List<Solution> exportClassExamLastCode(int examId, int classId); // 导出班级考试的所有solution最后的code
	
	public List<Solution> exportClassTrainLastCode(int examId, int classId); // 导出班级训练的所有solution最后的code

	public Solution getSourceCode(int id);

	public List<Object[]> getCopyList(int examId, int teacherId,String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime);// 查看抄袭情况
	
	public List<Object[]> getTrainCopyList(int examId, int teacherId,
			String studentNo, String name, String banji, float similarity,
			String searchTime);// 查看Train抄袭情况

	public List<Solution> getSimilarityObject(int examId); // 获取考试的similarityId指定的Solution

	public List<Object[]> getExamLastSolutionStatus(int examId); // 获取考试试题最后的solution状态

	public void deleteSubmit(int id, int reason, String remark); // 当用户撤销提交后更改数据

	public List<Solution> getSolutionsByNumber(int number); // 查找状态为WAIT的solutions

	public int editStudentScore(Solution solution);
	
	public List<Map<String,Object>> getExamSubmitSolutionInfo(boolean isLast, int examId,int teacherId,
			int nowPage, int pageSize, String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
	
	public List<Map<String,Object>> getCopyListInfo(int examId, int teacherId,String displaySequence,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
	
	
	public List<Map<String,Object>> getTrainSubmitSolutionInfo(boolean isLast, int examId,int teacherId,
			String role,int nowPage, int pageSize, String studentNo, String name, String banji, float similarity,
			String searchTime);
	
	public List<Map<String,Object>> getTrainCopyListInfo(int examId, int teacherId,String role,
			String studentNo, String name, String banji, float similarity,
			String searchTime);
	
}
