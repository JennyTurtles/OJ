package edu.dhu.dao.impl;

import edu.dhu.dao.StudentTrainInfoDaoI;
import edu.dhu.model.Solution;
import edu.dhu.model.StudentTrainCatDetail;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentTrainInfoDao")
public class StudentTrainInfoDaoImpl extends BaseDaoImpl<StudentTrainCatDetail> implements StudentTrainInfoDaoI{
	@Override
	public List<StudentTrainCatDetail> getClassTrainScore(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "";
		if (classId > 0) // 大于0，查询单个班级
			hql = "from StudentTrainCatDetail s where s.examId="
					+ examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId="
					+ classId + " ) order by userId asc";
		else
			// 等于0，查询所有班级
			hql = "from StudentTrainCatDetail s where s.examId="
					+ examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId in (select e.classId from Examclasses e where e.examId="
					+ examId + ")) order by userId asc";
		List<StudentTrainCatDetail> scoreList = this.find(hql);
		return scoreList;
	}
	
	@Override
	public void deleteSubmit(int userId, int examId, int catId,float score, float points, String status) {
		String hql = "update StudentTrainCatDetail set score=score-:score,points=points-:points, where examId=:examId and userId=:userId and catId=:catId";
		Query q = this.getCurrentSession().createQuery(hql);
		if (score < 0)
			score = 0;
		if (points < 0)
			points = 0;
		q.setParameter("score", score);
		q.setParameter("examId", examId);
		q.setParameter("userId", userId);
		q.setParameter("catId", catId);
		q.setParameter("points", points);
		q.executeUpdate();
	}

	@Override
	public StudentTrainCatDetail getUserTrainScore(int userId, int examId, int catId) {
		String hql = "from StudentTrainCatDetail s where s.examId="+examId+" and userId="+userId+" and catId="+catId;
		StudentTrainCatDetail trainScore = this.get(hql);
		return trainScore;
	}

	@Override
	public void updateTrainScore(StudentTrainCatDetail stc) {
		this.update(stc);
	}
	
	@Override
	public List<StudentTrainCatDetail> getStudentTrainInfoByUserIdAnExamId(int userId,
			int examId) {
		String hql = "from StudentTrainCatDetail where userId = " + userId
				+ " and examId = " + examId;
		List<StudentTrainCatDetail> studentTrainiInfoList = this.find(hql);
		if (studentTrainiInfoList.size() == 0) {
			return null;
		}
		return studentTrainiInfoList;
	}
	
	@Override
	public int editStudentTrainScore(Solution solution) {
		// TODO Auto-generated method stub
		String hql = "update StudentTrainCatDetail set score="+solution.getScore()+" where userId="+solution.getUserid()+" and examId="+solution.getExamId()+" and catId="+solution.getId();
		Query q = this.getCurrentSession().createQuery(hql);
//		q.setParameter("score", solution.getScore());
//		q.setParameter("userId", solution.getUserid());
//		q.setParameter("examId", solution.getExamId());
//		q.setParameter("catId", solution.getId());
		int num = q.executeUpdate(); // 现将finished设置为false
		return num;
	}
}
