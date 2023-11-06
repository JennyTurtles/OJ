package edu.dhu.problem.service;

import edu.dhu.problem.model.PMSimilarityObj;
import edu.dhu.solution.model.Submittedcode;

import java.util.List;

public interface CheckSimilarityServiceI {

	// 根据solutionID，sourceCode，problemID获取包含超过相似度值，以及与哪一个solution相似的对象
	public PMSimilarityObj checkSimilarityById(int solutionId,
											   String sourceCode, int problemId);

	public List<Submittedcode> getSubmittedListByProblemIdAndSchoolId(
			int problemId, int schoolId);
}
