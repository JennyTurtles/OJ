package edu.dhu.dao;

import edu.dhu.model.ClassTimes;

import java.util.List;

public interface AttendenceDaoI extends BaseDaoI<ClassTimes>{

	public List<ClassTimes> getAttendanceByClassId(int classId); // 通过班级id，查询考勤情况
	
}
