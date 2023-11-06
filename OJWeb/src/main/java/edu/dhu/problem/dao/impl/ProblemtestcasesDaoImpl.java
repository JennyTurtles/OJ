package edu.dhu.problem.dao.impl;

import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.problem.dao.ProblemtestcasesDaoI;
import edu.dhu.problem.model.Problemtestcases;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("problemtestcasesDao")
public class ProblemtestcasesDaoImpl extends BaseDaoImpl<Problemtestcases>
		implements ProblemtestcasesDaoI {

	@Override
	public Problemtestcases getProblemtestcasesById(int problemtestcasesId) {
		return this.get(Problemtestcases.class, problemtestcasesId);
	}

	@Override
	public List<Problemtestcases> getProblemtestcasesByProblemId(int problemId) {
		// TODO Auto-generated method stub
		String hql = "from Problemtestcases where problemId="+problemId;
		List<Problemtestcases> p = this.find(hql);
		return p;
	}

	@Override
	public void deleteTestCase(int id) {
		// TODO Auto-generated method stub
		String hql = "delete from Problemtestcases where id=" + id;
		this.executeHql(hql);
	}

	@Override
	public void updateTestcase(Problemtestcases p) {
		// TODO Auto-generated method stub
		int id = p.getId();
		String input = p.getInput();
		String output = p.getOutput();
		int problemId = p.getProblemId();
		String hql = "update Problemtestcases set input=:input,output=:output where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("input", input);
		q.setParameter("output", output);
		q.setParameter("id", id);
		q.executeUpdate();
	}

	@Override
	public void deleteProblemTestcaseByProblemId(int problemId) {
		// TODO Auto-generated method stub
		String hql = "delete from Problemtestcases where problemId="
				+ problemId;
		this.executeHql(hql);
	}

}
