package edu.dhu.dao;

import edu.dhu.model.Wrongcases;

import java.util.List;

public interface WrongcasesDaoI extends BaseDaoI<Wrongcases> {
	// 根据solution ID获取所有的错误测试用例
	public List<Wrongcases> getWrongcasesBySolutionID(int solutionId);

	// public boolean saveWrongcases(Wrongcases wrongcases);

	public Wrongcases getWrongcasesBysolutionIdAndCaseId(int solutionId,
			int caseId);

	public boolean alterWrongcasesBysolutionIdAndCaseId(Wrongcases wcs);
}
