package edu.dhu.service;

import edu.dhu.model.Problems;
import edu.dhu.pageModel.PMProblemInfo;
import edu.dhu.pageModel.PMProblems;

import java.util.List;

public interface ProblemsServiceI {

	// 根据examId 和用户Id 查询所有的题目信息
	public List<PMProblems> findAllProblemsByExamId(int examId, int userId, boolean firstLogin);

	// 根据problem id查询题目信息
	public Problems findProblemById(int problemId);

	// 更新Problems
	public void updateProblems(Problems problems);

	// 根据problemID查询题目信息，其中包含章节的中文名字
	public PMProblemInfo findProblemInfoById(int problemId);

	// 查找所有的问题
	public List<PMProblems> findProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty, int teacherId, String sortContent, String sortType);

	// 根据分类Id查找所有的问题
	public List<PMProblems> findProblemsByCategory(String category);

	// 根据分类条件查找所有的问题
	public List<PMProblems> findExcludeProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty,  String excludeCategory);
	
	//查找题目所属的第一分类
	public PMProblems findProblemBelowCategoryById(String categoryIds);
	// 查看问题详细信息
	public PMProblems viewProblemDetailInformationById(int id, int examId);

	// 修改题目信息
	public void editProblem(PMProblems problem);

	public void deleteProblem(int id);

	public PMProblemInfo findProblemInfoByIdAndExamId(int id, int examId);
	//为题目设置题目分类
	public String addProblemClassification(int id, String category,int duration);
	//查询该题目所有的题目分类
	public List<PMProblems> findProblemClassifications(int problemId);
	//查询该题目的做题预估时间
	public int findProblemDuration(int problemId);
	//删除题目分类
	public String deleteProblemClassification(Integer id, String secondClassification);

}
