package edu.dhu.dao.impl;

import edu.dhu.dao.ExamClassesDaoI;
import edu.dhu.model.Examclasses;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("examclassesDao")
public class ExamclassesDaoImpl extends BaseDaoImpl<Examclasses> implements
		ExamClassesDaoI {

	@Override
	public List<Examclasses> getExamclassesByClassId(int classId) {
		// TODO Auto-generated method stub
		String hql = "from Examclasses where classId=" + classId;
		List<Examclasses> examList = this.find(hql);
		return examList;
	}

	@Override
	public List<Examclasses> getExamclassesByExamId(int examId) {
		// TODO Auto-generated method stub
		String hql = "from Examclasses where examId=" + examId;
		List<Examclasses> classList = this.find(hql);
		return classList;
	}

	@Override
	public void addExamToClass(int examId, int classId) {
		// TODO Auto-generated method stub
		Examclasses examClass = new Examclasses();
		examClass.setClassId(classId);
		examClass.setExamId(examId);
		this.save(examClass);
	}

	@Override
	public void addClassToExam(int examId, int classId) {
		// TODO Auto-generated method stub
		Examclasses examClass = new Examclasses();
		examClass.setClassId(classId);
		examClass.setExamId(examId);
		this.save(examClass);
	}

	@Override
	public void deleteClassExam(int examId, int classId) {
		// TODO Auto-generated method stub
		String hql = "delete from Examclasses where examId=" + examId
				+ " and classId=" + classId;
		this.executeHql(hql);
	}

}
