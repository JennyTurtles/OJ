package edu.dhu.dao.impl;

import edu.dhu.dao.PaperExamClassesDaoI;
import edu.dhu.model.PaperExamclasses;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository("paperexamclassesDao")
public class PaperExamclassesDaoImpl extends BaseDaoImpl<PaperExamclasses> implements
PaperExamClassesDaoI {
	
	@Override
	public List<PaperExamclasses> getPaperExamclassesByClassId(int classId) {
		// TODO Auto-generated method stub
		String hql = "from PaperExamclasses where classId=" + classId;
		List<PaperExamclasses> examList = this.find(hql);
		return examList;
	}
	
	@Override
	public void addClassToPaperExam(int paperexamId, int classId) {
		// TODO Auto-generated method stub
		PaperExamclasses examClass = new PaperExamclasses();
		Date createTime = new Date();
		Date updateTime = new Date();
		examClass.setClassId(classId);
		examClass.setPaperexamId(paperexamId);
		examClass.setCreateTime(createTime);
		examClass.setUpdateTime(updateTime);
		this.save(examClass);
	}
	
	@Override
	public void deleteClassPaperExam(int paperexamId, int classId) {
		// TODO Auto-generated method stub
		String hql = "delete from PaperExamclasses where paperexamId=" + paperexamId
				+ " and classId=" + classId;
		this.executeHql(hql);
	}
	
	@Override
	public void addPaperExamToClass(int paperexamId, int classId) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		PaperExamclasses paperexamClass = new PaperExamclasses();
		paperexamClass.setClassId(classId);
		paperexamClass.setPaperexamId(paperexamId);
		paperexamClass.setCreateTime(time);
		paperexamClass.setUpdateTime(time);
		this.save(paperexamClass);
	}
}
