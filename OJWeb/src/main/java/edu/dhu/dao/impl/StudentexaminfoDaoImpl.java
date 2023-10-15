package edu.dhu.dao.impl;

import edu.dhu.dao.StudentexaminfoDaoI;
import edu.dhu.model.Studentexaminfo;
import edu.dhu.model.Users;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentexaminfoDao")
public class StudentexaminfoDaoImpl extends BaseDaoImpl<Studentexaminfo>
		implements StudentexaminfoDaoI {

	@Override
	public Studentexaminfo getStudentexaminfoByUserIdAnExamId(int userId,
			int examId) {
		String hql = "from Studentexaminfo where userId = " + userId
				+ " and examId = " + examId;
		List<Studentexaminfo> studentexaminfoList = this.find(hql);
		if (studentexaminfoList.size() == 0) {
			return null;
		}
		return studentexaminfoList.get(0);
	}

	@Override
	public void updateStudentexaminfo(Studentexaminfo studentexaminfo) {
		this.update(studentexaminfo);
	}

	@Override
	public List<Studentexaminfo> getClassExamScore(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "";
		if (classId > 0) // 大于0，查询单个班级
			hql = "from Studentexaminfo s where s.examId="
					+ examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId="
					+ classId + " ) order by userId asc";
		else
			// 等于0，查询所有班级
			hql = "from Studentexaminfo s where s.examId="
					+ examId
					+ " and s.userId in (select c.userId from Classstudents c where c.classId in (select e.classId from Examclasses e where e.examId="
					+ examId + ")) order by userId asc";
		List<Studentexaminfo> scoreList = this.find(hql);
		return scoreList;
	}

	@Override
	public void deleteSubmit(int userId, int examId, float score, String status) {
		// TODO Auto-generated method stub
		if (status.equals("AC")) {
			String hql = "update Studentexaminfo set score=score-:score,solved=solved-1 where examId=:examId and userId=:userId";
			Query q = this.getCurrentSession().createQuery(hql);
			if (score < 0)
				score = 0;
			q.setParameter("score", score);
			q.setParameter("examId", examId);
			q.setParameter("userId", userId);
			q.executeUpdate();
		} else {
			String hql = "update Studentexaminfo set score=score-:score where examId=:examId and userId=:userId";
			Query q = this.getCurrentSession().createQuery(hql);
			if (score < 0)
				score = 0;
			q.setParameter("score", score);
			q.setParameter("examId", examId);
			q.setParameter("userId", userId);
			q.executeUpdate();
			System.out.println("ok");
		}

	}

	@Override
	public int updateStudentScore(int userId, int examId, float score) {
		// TODO Auto-generated method stub
		String hql = "update Studentexaminfo set score=:score where examId=:examId and userId=:userId";
		Query q = this.getCurrentSession().createQuery(hql);

		q.setParameter("score", score);
		q.setParameter("examId", examId);
		q.setParameter("userId", userId);
		int num = q.executeUpdate();
		return num;
	}

	@Override
	public void setUuidAndIp(int examId, int userId) {
		// TODO Auto-generated method stub
		String hql = "update Studentexaminfo set loginIp=:loginIp,loginUUID=:loginUUID where examId=:examId and userId=:userId";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("loginIp", null);
		q.setParameter("loginUUID", null);
		q.setParameter("examId", examId);
		q.setParameter("userId", userId);
		q.executeUpdate();
	}

	@Override
	public String getStuByExamIdAndIp(int examId, String ip) {
		String sql = "select * from users where "
				+ "id=(select userId from studentexaminfo where examId="+examId+" and loginIp='"+ip+"')";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.addEntity(Users.class);
		List<Users> userList = query.list(); 
		if(userList.size()==0){
			return null;
		}else{
			return userList.get(0).getChineseName();
		}
	}

}
