package edu.dhu.dao;


import edu.dhu.model.Paper_Problems;

public interface PaperProblemAddDaoI extends BaseDaoI<Paper_Problems>{

	
	// 向题库中增加一道题目
		public String addPaperProblem(Paper_Problems paperproblems);
	

}
