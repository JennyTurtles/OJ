package edu.dhu.dao.impl;

import edu.dhu.dao.StudentTrainDetailDaoI;
import edu.dhu.model.StudentTrainProbDetail;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentTrainDetailDao")
public class StudentTrainDetailDaoImpl extends BaseDaoImpl<StudentTrainProbDetail> implements StudentTrainDetailDaoI {
	@Override
	public List<StudentTrainProbDetail> getClassStudentTrainDetail(int examId, int classId) {
		String hql = "";
		if (classId > 0) // 大于0查询单个班级
			hql = "from StudentTrainProbDetail s where s.examId=" + examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId=" + classId
					+ ") and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
					+ ") order by userId asc";
		else
			// 等于0，查询所有班级
			hql = "from StudentTrainProbDetail s where s.examId=" + examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId in (select e.classId from Examclasses e where e.examId="
					+ examId + ")) and s.problemId in (select e.problemId from Examproblems e where e.examId=" + examId
					+ ") order by userId asc";
		List<StudentTrainProbDetail> detailList = this.find(hql);
		return detailList;
	}
	
	@Override
	public List<Object[]> getSubmitedDetailByExamId(int examId) {
		// TODO Auto-generated method stub
		String hql = "select id,solutionId from StudentTrainProbDetail where examId="
				+ examId + " and finished=true and solutionId is not null";
		List<Object[]> solutionList = this.relationalQuery(hql);
		return solutionList;
	}
	
	@Override
	public StudentTrainProbDetail getTrainDetailBySolutionId(int solutionId) {
		String hql = "from StudentTrainProbDetail where solutionId=" + solutionId;
		List<StudentTrainProbDetail> list = this.find(hql);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else
			return null;
	}
	
	@Override
	public StudentTrainProbDetail deleteFinishedBySolutionId(int solutionId,
			int reason) {
		String hql = "from StudentTrainProbDetail where solutionId=:solutionId"; // 获取信息准备减分数用
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("solutionId", solutionId);
		List<StudentTrainProbDetail> list = q.list();
		hql = "update StudentTrainProbDetail set finished=:finished,points=0,status=:status,scoreCoef=0 where solutionId=:solutionId";
		q = this.getCurrentSession().createQuery(hql);
		q.setParameter("finished", false);
		q.setParameter("solutionId", solutionId);
		if (reason == 1) // 1表示抄袭
			q.setParameter("status", "COPY");
		else
			q.setParameter("status", "REDO");
		q.executeUpdate(); // 现将finished设置为false
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public List<Object[]> getStuProbFinishNumberByEamId(int examId) {
		String hql = "select userId, count(*) as finishednumber from StudentTrainProbDetail where examId="
				+ examId + " and finished=true and solutionId is not null GROUP BY userId";
		List<Object[]> finishedNumberList = this.relationalQuery(hql);
		return finishedNumberList;
	}
	
}
