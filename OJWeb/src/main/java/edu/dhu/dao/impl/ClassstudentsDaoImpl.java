package edu.dhu.dao.impl;

import edu.dhu.dao.ClassstudentsDaoI;
import edu.dhu.model.Classstudents;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("classstudentsDao")
public class ClassstudentsDaoImpl extends BaseDaoImpl<Classstudents> implements
		ClassstudentsDaoI {

	@Override
	public boolean insertClassStudent(int userId, int classId) {
		// TODO Auto-generated method stub
		Classstudents student = new Classstudents();
		student.setClassId(classId);
		student.setUserId(userId);
		this.save(student);
		return true;
	}

	@Override
	public boolean findClassStudentByUserId(int userId, int classId) {
		// TODO Auto-generated method stub
		String hql = "from Classstudents where userId=" + userId
				+ " and classId=" + classId;
		List<Classstudents> classstudents = this.find(hql);
		if (classstudents.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public int getClassStudentsNum(int classId) { // 获取班级学生的总数
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select count(*) from Classstudents where classId=:classId";
		params.put("classId", classId);
		return countInt(hql, params);
	}

	@Override
	public List<Classstudents> getClassStudentsByExamId(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Classstudents c where c.classId in (select e.classId from Examclasses e where examId="
				+ examId + ")";
		List<Classstudents> classstudents = this.find(hql);
		return classstudents;
	}

	@Override
	public List<Classstudents> getClassStudentsByClassId(int classId) {
		// TODO Auto-generated method stub
		String hql = "from Classstudents where classId=" + classId;
		List<Classstudents> classstudents = this.find(hql);
		return classstudents;
	}
	
	

}
