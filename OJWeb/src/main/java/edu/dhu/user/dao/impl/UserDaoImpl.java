package edu.dhu.user.dao.impl;

import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.user.dao.UserDaoI;
import edu.dhu.user.model.PMUser;
import edu.dhu.user.model.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<Users> implements UserDaoI {

	@Override
	public List<Users> getAllStudents() {
		// TODO Auto-generated method stub
		String hql = "from Users order by studentNo asc";
		List<Users> users = this.find(hql);
		return users;
	}

	@Override
	public List<Users> getUsersByExamId(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Users u where u.id in (select c.userId from Classstudents c where c.classId in (select e.classId "
				+ "from Examclasses e where e.examId="
				+ examId
				+ ")) order by u.studentNo asc";
		List<Users> users = this.find(hql);
		return users;
	}
	@Override
	public List<Users> getUsersByExamIdAndTeacherId(int examId,int teacherId) {
		// TODO Auto-generated method stub
		String hql = "from Users u where u.id in (select c.userId from Classstudents c where c.classId in (select e.classId "
				+ "from Examclasses e where e.examId="
				+ examId+"and e.classId in(select cs.id from Classes cs where cs.teacherId="
				+ teacherId
						+ ")"
				+ ")) order by u.studentNo asc";
		List<Users> users = this.find(hql);
		return users;
	}

	@Override
	public List<Users> getUsersByClassId(int classId) {
		// TODO Auto-generated method stub
		String hql = "from Users u where u.id in (select c.userId from Classstudents c where c.classId="
				+ classId + " ) order by u.studentNo asc";
		return this.find(hql);
	}

	@Override
	public Users getUserByStudentNo(String studentNo) {
		String hql = "from Users  where studentNo= " + studentNo + "";
		return this.get(hql);
	}

	@Override
	public List<Users> findStudentsByCondition(String studentNo,
			String chineseName, String stu_userName) {

		String hql = "from Users u where (u.studentNo like '%" + studentNo
				+ "%' and u.chineseName like '%" + chineseName
				+ "%' and u.username like '%" + stu_userName + "%') "
				+ "order by u.id asc";

		List<Users> users = this.find(hql);

		return users;
	}

	@Override
	public Users getUserByUsername(String username) {
		String hql = "from Users  where username= '" + username + "'";
		return this.get(hql);
	}

	@Override
	public List<Users> findStudentsByCondition(PMUser user) {
		// TODO Auto-generated method stub
		String hql = "from Users u where u.schoolId=" + user.getSchoolId()
				+ " and (u.studentNo like '%" + user.getStudentNo()
				+ "%' and u.chineseName like '%" + user.getChineseName()
				+ "%' and u.username like '%" + user.getStu_userName() + "%') "
				+ "order by u.id asc";

		List<Users> users = this.find(hql);
		return users;
	}

	@Override
	public Users getUserByUserId(int userId) {
		String hql = "from Users  where id= '" + userId + "'";
		return this.get(hql);
	}


}
