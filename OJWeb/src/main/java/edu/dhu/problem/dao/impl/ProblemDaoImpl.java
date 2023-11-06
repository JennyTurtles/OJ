package edu.dhu.problem.dao.impl;


import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.problem.dao.ProblemDaoI;
import edu.dhu.problem.model.Problems;
import org.springframework.stereotype.Repository;

@Repository("problemDao")
public class ProblemDaoImpl extends BaseDaoImpl<Problems> implements
		ProblemDaoI {
}