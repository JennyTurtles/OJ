package edu.dhu.dao.impl;

import edu.dhu.dao.WrongcasesDaoI;
import edu.dhu.model.Wrongcases;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("wrongcasesDao")
public class WrongcasesDaoImpl extends BaseDaoImpl<Wrongcases> implements
		WrongcasesDaoI {

	@Override
	public List<Wrongcases> getWrongcasesBySolutionID(int solutionId) {
		String hql = "from Wrongcases where solutionId = " + solutionId;
		List<Wrongcases> wrongcasesList = this.find(hql);
		return wrongcasesList;
	}

	@Override
	public Wrongcases getWrongcasesBysolutionIdAndCaseId(int solutionId,
			int caseId) {
		String hql = "from wrongcases where solutionId = " + solutionId
				+ " and caseId = " + caseId;
		Wrongcases wrongcases = this.get(hql);
		return wrongcases;
	}

	@Override
	public boolean alterWrongcasesBysolutionIdAndCaseId(Wrongcases wcs) {
		String hql = "update Wrongcases set output=" + "'"+wcs.getOutput()+"'"
				+ " where solutionId = " + wcs.getSolutionId() + " "
				+ "and caseId = " + wcs.getCaseId();
		int result = this.executeHql(hql);

		if (result == 1)
			return true;
		else
			return false;
	}

}
