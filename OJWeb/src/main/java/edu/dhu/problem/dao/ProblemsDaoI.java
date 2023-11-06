package edu.dhu.problem.dao;

import edu.dhu.problem.model.PMProblems;
import edu.dhu.problem.model.Problems;

import java.util.List;

public interface ProblemsDaoI {

	// 根据exam id 查询所有的题目信息
	public List<PMProblems> findAllProblemsByExamId(int examId);

	// 根据problem id查询题目信息
	public Problems findProblemById(int problemId);

	// 更新problems
	public void updateProblems(Problems problems);

	// 按条件查找所有的题目
	public List<PMProblems> findProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty, int teacherId, String sortContent, String sortType);

	// 查找所有的问题
	public List<PMProblems> findExcludeProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty,  String excludeCategory);

	// 根据分类Id查找所有的问题
	public List<PMProblems> findProblemsByCategory(String category);

	// 修改题目的信息
	public void editProblem(PMProblems problem);

	public List<Problems> findExamProblems(int examId); // 查询考试的题目
	
	public List<Problems> findTrainProblems(int examId); // 查询Train的题目

	public void deleteProblem(int id);

	public List<Problems> findProblemByteacherId(int teacherId);
	
	//为题目设置分类
	public String addProblemClassification(int id, String category,int duration);
	
	//查询该题目所有的题目分类
	public String findProblemClassifications(int problemId);
	//查询该题目的做题预估时间
	public int findProblemDuration(int problemId);
	//删除题目分类
	public String deleteProblemClassification(Integer id, String secondClassification);

}
