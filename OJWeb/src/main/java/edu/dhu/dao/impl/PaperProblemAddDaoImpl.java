package edu.dhu.dao.impl;

import edu.dhu.dao.PaperProblemAddDaoI;
import edu.dhu.model.Paper_Problems;
import org.springframework.stereotype.Repository;

@Repository("paperProblemAddDao")
public class PaperProblemAddDaoImpl extends BaseDaoImpl<Paper_Problems> implements
PaperProblemAddDaoI {

	@Override
	public String addPaperProblem(Paper_Problems paperproblems) {
		try {
			this.save(paperproblems);
			return null; // 返回信息null,表示添加成功，没有错误信息
		} catch (Exception e) {
			return e.toString();
		}
	}
	
}
