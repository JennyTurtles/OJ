package edu.dhu.service;

import edu.dhu.model.Paper_Problems;
import edu.dhu.pageModel.PMPaperProblems;

import java.util.List;

public interface PaperProblemsServiceI {
	
	// 查找所有题目
	public List<PMPaperProblems> findProblemsByCondition(String keywords, String courseCode, String chapterCode,
			 int teacherId, String sortContent, String sortType);

	public void editPaperProblem(PMPaperProblems pMpaperproblems);
	
	public void editBlankQuestion(PMPaperProblems pMpaperproblems);
	
	// 根据paperproblem id查询题目信息
		public Paper_Problems findPaperProblemById(int paperproblemId);

		public void deletePaperProblem(int id);

		// 查看选择题的详细信息
		public PMPaperProblems viewProblemDetailInformationById(int id);
		
		
		// 查看填空题的详细信息
		public PMPaperProblems viewBlankDetailInformationById(int id);
		

		//根据用户Id获取表中课程章节缺失的题目
		public List<PMPaperProblems> findChoiceQuestionById(int teacherId);
		
		//根据用户Id获取表中课程章节缺失的题目
		public List<PMPaperProblems> findBlankQuestionById(int teacherId);

}

