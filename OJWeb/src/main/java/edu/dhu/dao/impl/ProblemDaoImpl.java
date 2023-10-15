package edu.dhu.dao.impl;

import edu.dhu.dao.ProblemDaoI;
import edu.dhu.model.Problems;
import org.springframework.stereotype.Repository;

@Repository("problemDao")
public class ProblemDaoImpl extends BaseDaoImpl<Problems> implements
		ProblemDaoI {
}