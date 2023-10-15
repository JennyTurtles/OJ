package edu.dhu.dao;

import edu.dhu.model.StudentAttendance;

import java.util.List;

public interface StudentAttendenceDaoI extends BaseDaoI<StudentAttendance>{

	public List<StudentAttendance> findStudentAttendanceById(int studentId,int classId,int ClassTimeId); // 通过班级id，查询考勤情况
	
}
