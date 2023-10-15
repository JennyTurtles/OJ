package edu.dhu.dao.impl;

import edu.dhu.dao.ProblemAddDaoI;
import edu.dhu.model.Problems;
import org.springframework.stereotype.Repository;

@Repository("problemAddDao")
public class ProblemAddDaoImpl extends BaseDaoImpl<Problems> implements
		ProblemAddDaoI {

	@Override
	public String addProblem(Problems problems) {
		try {
			this.save(problems);
			return null; // 返回信息null,表示添加成功，没有错误信息
		} catch (Exception e) {
			return e.toString();
		}
	}

}
