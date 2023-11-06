package edu.dhu.problem.service.impl;

import edu.dhu.problem.dao.ProblemtestcasesDaoI;
import edu.dhu.problem.model.Problemtestcases;
import edu.dhu.problem.service.ProblemtestcasesServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("problemtestcasesService")
@Transactional
public class ProblemtestcasesServiceImpl implements ProblemtestcasesServiceI {

	private ProblemtestcasesDaoI problemtestcasesDao;

	public ProblemtestcasesDaoI getProblemtestcasesDao() {
		return problemtestcasesDao;
	}

	@Autowired
	public void setProblemtestcasesDao(ProblemtestcasesDaoI problemtestcasesDao) {
		this.problemtestcasesDao = problemtestcasesDao;
	}

	@Override
	public Problemtestcases getProblemtestcasesById(int problemtestcasesId) {
		return problemtestcasesDao.getProblemtestcasesById(problemtestcasesId);
	}

	@Override
	public List<Problemtestcases> getProblemtestcasesByProblemId(int problemId) {
		return problemtestcasesDao.getProblemtestcasesByProblemId(problemId);
	}

}
