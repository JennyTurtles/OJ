package edu.dhu.dao;

import edu.dhu.model.Examclasses;

import java.util.List;

public interface ExamClassesDaoI extends BaseDaoI<Examclasses> {

	public List<Examclasses> getExamclassesByClassId(int classId);

	public List<Examclasses> getExamclassesByExamId(int examId);

	public void addExamToClass(int examId, int classId); // 添加考试到班级

	public void addClassToExam(int examId, int classId); // 添加班级到考试中

	public void deleteClassExam(int examId, int classId); // 删除班级参加的考试或考试包含的班级

}
