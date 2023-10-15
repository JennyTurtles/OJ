package edu.dhu.dao;

import edu.dhu.model.PaperExamclasses;

import java.util.List;

public interface PaperExamClassesDaoI extends BaseDaoI<PaperExamclasses> {
	
	public void addClassToPaperExam(int paperexamId, int classId); // 添加班级到考试中
	
	public void deleteClassPaperExam(int paperexamId, int classId); // 删除班级参加的考试或考试包含的班级
	
	public List<PaperExamclasses> getPaperExamclassesByClassId(int classId);
	
	public void addPaperExamToClass(int paperexamId, int classId); // 添加笔试考试到班级
}
