package edu.dhu.problem.dao.impl;

import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.problem.dao.ItrainproblemDaoI;
import edu.dhu.problem.model.Itrainproblems;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("itrainproblemDao")
public class ItrainproblemDaoImpl extends BaseDaoImpl<Itrainproblems> implements ItrainproblemDaoI {

	@Override
	public List<Itrainproblems> getItrainProblem(Map<String, Object> params) {
		String hql = "select ip from Itrainproblems ip where ip.examId =:examId and ip.catId =:catId";
		return this.find(hql, params);
	}

	@Override
	public List<Map<String, Object>> findExitsProblemsByExamId(int examId) {
		String sql = "select problemId,catId,mandatory,duration from gdoj.itrainproblems where examId = " + examId;
		return this.findBySql(sql, null);
	}

	@Override
	public Itrainproblems getItrainPro(int examId, int problemId) {
		String hql = "select ip from Itrainproblems ip where examId =:examId and problemId =:problemId";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		params.put("problemId", problemId);
		
		return this.get(hql, params);
	}
	

	@Override
	public List<Itrainproblems> getProblemsByExamId(int examId) {
		String hql = "from Itrainproblems where examId=" + examId;
		List<Itrainproblems> trainproblemList = this.find(hql);
		return trainproblemList;
	}
	
}
