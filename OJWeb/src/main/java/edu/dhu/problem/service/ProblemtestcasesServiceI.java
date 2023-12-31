package edu.dhu.problem.service;

import edu.dhu.problem.model.Problemtestcases;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProblemtestcasesServiceI {

	// 根据problemtestcasesId查找Problemtestcases
	public Problemtestcases getProblemtestcasesById(int problemtestcasesId);

	public List<Problemtestcases> getProblemtestcasesByProblemId(int problemId);
}
