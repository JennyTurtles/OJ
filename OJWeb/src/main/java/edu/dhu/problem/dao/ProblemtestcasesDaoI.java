package edu.dhu.problem.dao;

import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.problem.model.Problemtestcases;

import java.util.List;

public interface ProblemtestcasesDaoI extends BaseDaoI<Problemtestcases> {

	// 根据problemtestcasesId查找Problemtestcases
	public Problemtestcases getProblemtestcasesById(int problemtestcasesId);

	public List<Problemtestcases> getProblemtestcasesByProblemId(int problemId);

	public void deleteTestCase(int id); // 删除测试用例

	public void updateTestcase(Problemtestcases p);

	public void deleteProblemTestcaseByProblemId(int problemId);

}
