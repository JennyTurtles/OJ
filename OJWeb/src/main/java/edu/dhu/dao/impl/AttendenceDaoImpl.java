package edu.dhu.dao.impl;

import edu.dhu.dao.AttendenceDaoI;
import edu.dhu.model.ClassTimes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("attendenceDao")
public class AttendenceDaoImpl extends BaseDaoImpl<ClassTimes> implements AttendenceDaoI{

	
	@Override
	public List<ClassTimes> getAttendanceByClassId(int classId) {
		// TODO Auto-generated method stub
		String hql = "from ClassTimes where class_id=" + classId;
		List<ClassTimes> attendenceList = this.find(hql);
		return attendenceList;
	}
	
	
	
}
