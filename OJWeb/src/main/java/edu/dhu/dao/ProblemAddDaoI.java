package edu.dhu.dao;

import edu.dhu.model.Problems;

public interface ProblemAddDaoI extends BaseDaoI<Problems> {

	// 向题库中增加一道题目
	public String addProblem(Problems problems);
}
