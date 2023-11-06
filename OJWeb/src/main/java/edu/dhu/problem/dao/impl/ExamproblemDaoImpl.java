package edu.dhu.problem.dao.impl;

import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.problem.dao.ExamproblemDaoI;
import edu.dhu.problem.model.Examproblems;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("examproblemDao")
public class ExamproblemDaoImpl extends BaseDaoImpl<Examproblems> implements
		ExamproblemDaoI {

	@Override
	public int getMaxDisplaySequenceByExamId(int examId) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select count(*) from  Examproblems where examId=:examId";
		params.put("examId", examId);
		return countInt(hql, params);
	}

	@Override
	public void SubDisplaySequence(int examId, int start, int end) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "update Examproblems set displaySequence=displaySequence-1 where examId=:examId and displaySequence>:start and displaySequence<=:end";
		params.put("examId", examId);
		params.put("start", start);
		params.put("end", end);
		executeHql(hql, params);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String updateTime = sdf.format(date);// 将当前时间格式化为需要的类型
		String examhql = "update Exam set updateTime='" + updateTime
				+ "' where id=" + examId;

		executeHql(examhql);
	}

	@Override
	public void AddDisplaySequence(int examId, int start, int end) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "update Examproblems set displaySequence=displaySequence+1 where examId=:examId and displaySequence>=:start and displaySequence<:end";
		params.put("examId", examId);
		params.put("start", start);
		params.put("end", end);
		executeHql(hql, params);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String updateTime = sdf.format(date);// 将当前时间格式化为需要的类型
		String examhql = "update Exam set updateTime='" + updateTime
				+ "' where id=" + examId;

		executeHql(examhql);
	}

	@Override
	public int getDisplaySequence(int examId, int problemId) {
		// TODO Auto-generated method stub
		String hql = "from Examproblems where examId=" + examId
				+ " and problemId=" + problemId;
		List<Examproblems> examproblemList = this.find(hql);
		if (examproblemList.size() > 0) {
			Examproblems examproblem = examproblemList.get(0);
			int displaySequence = examproblem.getDisplaySequence();
			return displaySequence;
		} else
			return 0;
	}

	@Override
	public List<Examproblems> getProblemsUndo(int userId, int examId) {
		// TODO Auto-generated method stub
		String hql = "from Examproblems e where not exists( select 1 from Studentexamdetail s where s.examId="
				+ examId
				+ " and s.userId="
				+ userId
				+ " and e.problemId=s.problemId) and e.examId="
				+ examId
				+ " order by e.displaySequence asc";
		return this.find(hql);
	}

	@Override
	public int getExamproblemNum(int examId) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select count(*) from Examproblems where examId=:examId";
		params.put("examId", examId);
		return countInt(hql, params);
	}

	@Override
	public Examproblems getExamproblemsByExamIdAndProblemId(int examId,
			int problemId) {
		String hql = "from Examproblems where examId = " + examId
				+ " and problemId = " + problemId;
		List<Examproblems> examproblemList = this.find(hql);
		if(examproblemList.size()!=0){
			return examproblemList.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public List<Examproblems> getDisplaySequenceByExamId(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Examproblems where examId=" + examId;
		List<Examproblems> examproblemList = this.find(hql);
		return examproblemList;
	}

	@Override
	public float getScoreByExamIdAndProblemId(int examId, int problemId) {
		return this.getExamproblemsByExamIdAndProblemId(examId, problemId)
				.getScore();
	}

	@Override
	public boolean alterScore(int id, float score, int examId) {
		// TODO Auto-generated method stub
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String updateTime = sdf.format(date);// 将当前时间格式化为需要的类型
		String hql = "update Examproblems set score=" + score + " where id="
				+ id;
		String examhql = "update Exam set updateTime='" + updateTime
				+ "' where id=" + examId;

		int result = this.executeHql(hql);
		int examresult = this.executeHql(examhql);
		if (result == 1 && examresult == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean alterDeadline(int id, Date deadline, Date updateTime,
			int examId) {
		// TODO Auto-generated method stub
		String hql = "update Examproblems set deadline=:deadline where id=:id";
		;
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("id", id);
		q.setParameter("deadline", deadline);
		int result = q.executeUpdate();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String nowTime = sdf.format(updateTime);// 将当前时间格式化为需要的类型
		String examhql = "update Exam set updateTime='" + nowTime
				+ "' where id=" + examId;

		int examresult = this.executeHql(examhql);

		if (result == 1 && examresult == 1)
			return true;
		else
			return false;
	}

	@Override
	public List<Examproblems> getExamproblemsListByExamId(int examId) {
		String hql = "from Examproblems where examId=" + examId;
		List<Examproblems> examproblemList = this.find(hql);
		return examproblemList;
	}

	@Override
	public boolean alterbestBeforeAndscoreCoef(Integer id, Date bestBefore,
			Date updateTime, float scoreCoef, Integer examId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String hql = "update Examproblems set bestBefore=:bestBefore,scoreCoef=:scoreCoef where id=:id";
		;
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("id", id);
		q.setParameter("bestBefore", bestBefore);
		q.setParameter("scoreCoef", scoreCoef);
		int result = q.executeUpdate();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String nowTime = sdf.format(updateTime);// 将当前时间格式化为需要的类型
		String examhql = "update Exam set updateTime='" + nowTime
				+ "' where id=" + examId;

		int examresult = this.executeHql(examhql);

		if (result == 1 && examresult == 1)
			return true;
		else
			return false;
	}

}
