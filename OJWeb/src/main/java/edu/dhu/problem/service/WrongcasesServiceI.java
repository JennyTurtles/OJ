package edu.dhu.problem.service;

import edu.dhu.problem.model.PMWrongAndCorrect;
import edu.dhu.problem.model.PMWrongAndCorrectIds;
import edu.dhu.problem.model.Wrongcases;

import java.util.List;

public interface WrongcasesServiceI {
	// 根据solution ID获取所有的错误用例
	public List<Wrongcases> getWrongcasesBySolutionID(int solutionId);

	// 根据wrongcasesId获取Wrongcases
	public Wrongcases getWrongcasesById(int wrongcasesId);

	// 客户端查看测试数据
	public PMWrongAndCorrect WS_getHint(int userId, int examId, int problemId,
										int caseId, boolean caseInfo);

	public Wrongcases getWrongcasesBysolutionIdAndCaseId(int solutionId,
			int caseId);

	// 客户端获取最新状态
	public PMWrongAndCorrectIds WS_getAllWrongAndRightCases(int userId,
															int examId, int problemId);

	// 更改wrongcases
	public boolean alterWrongcasesBysolutionIdAndCaseId(Wrongcases wcs); // 更改考试题目的分数
}
