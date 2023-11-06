package edu.dhu.exam.dao.impl;

import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.model.PMStudentDetail;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.problem.model.PMProblemsStatus;
import edu.dhu.problem.model.Solution;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("studentexamdetailDao")
public class StudentexamdetailDaoImpl extends BaseDaoImpl<Studentexamdetail>
		implements StudentexamdetailDaoI {

	@Override
	public List<Studentexamdetail> getStudentexamdetailSubmit(int userId,
			int examId) {
		String hql = "from Studentexamdetail where userId=" + userId
				+ " and examId=" + examId + " and score>0";
		List<Studentexamdetail> detailList = this.find(hql);
		return detailList;
	}

	@Override
	public List<Studentexamdetail> getStudentexamdetailDoing(int userId,
			int examId) {
		String hql = "from Studentexamdetail where userId=" + userId
				+ " and examId=" + examId + " and score=0";
		List<Studentexamdetail> detailList = this.find(hql);
		return detailList;
	}

	@Override
	public Studentexamdetail getStatusByUserIDexamIDproblemId(int userId,
			int examId, int problemId) {
		String hql = "from Studentexamdetail where userId=" + userId
				+ " and examId=" + examId + " and problemId=" + problemId;
		List<Studentexamdetail> detailList = this.find(hql);
		if (detailList.isEmpty())
			return null;
		return detailList.get(0);
	}

	@Override
	public void updateStudentexamdetail(Studentexamdetail studentexamdetail) {
		this.update(studentexamdetail);
	}

	@Override
	public List<Studentexamdetail> getAllStudentexamdetailListByUserIdAndExamId(
			int userId, int examId) {
		String hql = "from Studentexamdetail where userId=" + userId
				+ " and examId=" + examId + "order by problemId asc";
		List<Studentexamdetail> detailList = this.find(hql);
		if (detailList == null || detailList.isEmpty())
			return null;
		return detailList;
	}

	@Override
	public List<Studentexamdetail> getClassStudentexamdetail(int examId,
			int classId) {
		String hql = "";
		if (classId > 0) // 大于0查询单个班级
			hql = "from Studentexamdetail s where s.examId="
					+ examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId="
					+ classId
					+ ") and s.problemId in (select e.problemId from Examproblems e where e.examId="
					+ examId + ") order by userId asc";
		else
			// 等于0，查询所有班级
			hql = "from Studentexamdetail s where s.examId="
					+ examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId in (select e.classId from Examclasses e where e.examId="
					+ examId
					+ ")) and s.problemId in (select e.problemId from Examproblems e where e.examId="
					+ examId + ") order by userId asc";
		List<Studentexamdetail> detailList = this.find(hql);
		return detailList;
	}

	@Override
	public PMProblemsStatus getProblemsStatusArrByIds(
			PMProblemsStatus pMProblemsStatus) {
		PMProblemsStatus problemsStatus = pMProblemsStatus;
		for (int i = 0; i < pMProblemsStatus.getProblemIdArr().length; i++) {
			String hql = "from Studentexamdetail s where s.examId = "
					+ pMProblemsStatus.getExamId() + " and s.userId = "
					+ pMProblemsStatus.getUserId() + " and problemId = "
					+ pMProblemsStatus.getProblemIdArr()[i];
			List<Studentexamdetail> studentexamdetailList = this.find(hql);
			if (studentexamdetailList != null
					&& studentexamdetailList.size() == 1) {
				// 设置题目状态
				problemsStatus.getStatusArr()[i] = studentexamdetailList.get(0)
						.getStatus();
				// 设置Finish字段
				problemsStatus.getFinishedArr()[i] = studentexamdetailList.get(
						0).isFinished();
			} else {
				return null;
			}
		}
		return problemsStatus;
	}

	@Override
	public Studentexamdetail deleteFinishedBySolutionId(int solutionId,
			int reason) {
		// TODO Auto-generated method stub

		String hql = "from Studentexamdetail where solutionId=:solutionId"; // 获取信息准备减分数用
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("solutionId", solutionId);
		List<Studentexamdetail> list = q.list();
		hql = "update Studentexamdetail set finished=:finished,score=0,status=:status where solutionId=:solutionId";
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
	public List<Object[]> getSubmitedDetailByExamId(int examId) {
		// TODO Auto-generated method stub
		String hql = "select id,solutionId from Studentexamdetail where examId="
				+ examId + " and finished=true and solutionId is not null";
		List<Object[]> solutionList = this.relationalQuery(hql);
		return solutionList;
	}

	@Override
	public Studentexamdetail getDetailBySolutionId(int solutionId) {
		// TODO Auto-generated method stub
		String hql = "from Studentexamdetail where solutionId=" + solutionId;
		List<Studentexamdetail> list = this.find(hql);
		if (list != null && list.size() >= 1) {
			return list.get(0);
		} else
			return null;
	}

	@Override
	public int editStudentScore(Solution solution) {
		// TODO Auto-generated method stub
		String hql = "update Studentexamdetail set score=:score where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("score", solution.getScore());
		q.setParameter("id", solution.getId());
		int num = q.executeUpdate(); // 现将finished设置为false
		return num;
	}

	@Override
	public int editStudentDetailFinished(Solution solution) {
		// TODO Auto-generated method stub
		String hql = "update Studentexamdetail set finished=:finished where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("finished", true);
		q.setParameter("id", solution.getId());
		int num = q.executeUpdate(); // 现将finished设置为false
		return num;
	}

	@Override
	public List<PMStudentDetail> getStudentexamdetailsWithSubmittime(int examId) {
		String hql="SELECT sd.userId,sd.problemId,sd.submit,sd.score,s.submitTime FROM Studentexamdetail  sd ,Solution s   where sd.examId ="+examId+" and finished=1 and sd.solutionId =s.id";
		List<Object[]> list	= this.getCurrentSession().createQuery(hql).list();;
		List<PMStudentDetail> result=new ArrayList<>();
	   PMStudentDetail  pmStudentDetail;
	   for (int i = 0; i < list.size(); i++) {
		   Object[] object=list.get(i);
		   pmStudentDetail =new PMStudentDetail();
		pmStudentDetail.setUserId((int)object[0]);
		pmStudentDetail.setProblemId((int)object[1]);
		pmStudentDetail.setSubmitNum((int)object[2]);
		pmStudentDetail.setScore((float)object[3]);
		pmStudentDetail.setSubmitTime((Date)object[4]);
        result.add(pmStudentDetail)	;	
	}
			
		
		return result;
	}
}
