package edu.dhu.dao;

import edu.dhu.model.Submittedcode;

import java.util.List;

public interface SubmittedcodeDaoI extends BaseDaoI<Submittedcode> {
	// 保存一条记录
	public boolean saveSubmittedcode(Submittedcode submittedcode);

	// 根据problemID在submittedcode中查找所有的记录
	public List<Submittedcode> getAllSubmittedcodeByProblemId(int solutionId,
			int problemId);

	// 根据solutionId和problemId查找记录
	public Submittedcode getSubmittedcodeBySolutionIdAndProblemId(
			int solutionId, int problemId);

	// 根据problemID获取提交的题目列表
	public List<Submittedcode> getSubmittedListByProblemId(int problemId);

	public void deleteCodeBySolutionId(int solutionId); // 通过solutionId删除

	public List<Submittedcode> getSubmittedListByProblemIdAndSchoolId(
			int problemId, int schoolId);

}
