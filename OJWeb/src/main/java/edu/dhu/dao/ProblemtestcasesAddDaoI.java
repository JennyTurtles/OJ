package edu.dhu.dao;

import edu.dhu.model.Problemtestcases;

public interface ProblemtestcasesAddDaoI extends BaseDaoI<Problemtestcases> {

	// 增加测试用例
	public String problemtestcasesAdd(Problemtestcases problemtestcases);
}
