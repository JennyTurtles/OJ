package edu.dhu.dao.impl;

import edu.dhu.dao.StudentAttendenceDaoI;
import edu.dhu.model.StudentAttendance;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentattendenceDao")
public class StudentAttendenceDaoImpl extends BaseDaoImpl<StudentAttendance> implements StudentAttendenceDaoI{

	@Override
	public List<StudentAttendance> findStudentAttendanceById(int studentId,int classId,int ClassTimeId) {
		// TODO Auto-generated method stub
		String hql = "from StudentAttendance where user_id=" + studentId
				+ " and class_id=" + classId
				+ " and class_times_id=" + ClassTimeId;
		List<StudentAttendance> stuattendance = this.find(hql);
		return stuattendance;	
	}
}
