package edu.dhu.dao;

import edu.dhu.model.Paper_Problems;
import edu.dhu.pageModel.PMPaperProblems;

import java.util.List;

public interface PaperProblemsDaoI extends BaseDaoI<Paper_Problems>{
	// 查找所有题目
	public List<PMPaperProblems> findProblemsByCondition(String keywords, String courseCode, String chapterCode,
			 int teacherId, String sortContent, String sortType);
	
	public  List<Paper_Problems> findPaperProblemByteacherId(int teacherId) ;

	public void editPaperProblem(PMPaperProblems paperproblem);
	
	public void editBlankQuestion(PMPaperProblems paperproblem);
	

	// 根据paperproblem id查询题目信息
	public Paper_Problems findPaperProblemById(int paperproblemId);
	
	public void deletePaperProblem(int id);

	public Paper_Problems findProblemById(int id);

	/* public Paper_Problems get(Class<Paper_Problems> c, int id); */

	public Paper_Problems get(String hql);

	public boolean findQuestionByContentId(String content, int teacherId); // 查看该题目是否已在题库

	public boolean insertBlankQuestion(PMPaperProblems p);

	//保存到表中
	public boolean insertChoiceQuestion(PMPaperProblems p);

	
	//根据用户Id获取表中课程章节缺失的题目
	public List<PMPaperProblems> findBlankQuestionById(int teacherId);

	//根据用户Id获取表中课程章节缺失的题目
	public List<PMPaperProblems> findChoiceQuestionById(int teacherId);
	
	//根据学生的学号和考试id获取学生做的题目信息
	public List<Paper_Problems>  findPaperProblemByStuNo(int paperexamId,int StuNo);  




}

