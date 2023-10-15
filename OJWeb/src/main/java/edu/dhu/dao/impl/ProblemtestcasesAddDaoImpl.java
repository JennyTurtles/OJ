package edu.dhu.dao.impl;

import edu.dhu.dao.ProblemtestcasesAddDaoI;
import edu.dhu.model.Problemtestcases;
import org.springframework.stereotype.Repository;

@Repository("problemtestcasesAddDao")
public class ProblemtestcasesAddDaoImpl extends BaseDaoImpl<Problemtestcases>
		implements ProblemtestcasesAddDaoI {

	@Override
	public String problemtestcasesAdd(Problemtestcases problemtestcases) {
		try {
			this.save(problemtestcases);
			return null;
		} catch (Exception e) {
			return e.toString();
		}

	}

}
