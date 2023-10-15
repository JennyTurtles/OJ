package edu.dhu.dao;

import edu.dhu.model.Studentpaperexaminfo;

import java.util.List;

public interface StudentpaperexaminfoDaoI extends BaseDaoI<Studentpaperexaminfo>{
	
	public List<Studentpaperexaminfo> getAllStudentsScoreOrderByPaperExamID(int paperexamId); // 通过考试id获取所有学生的成绩
	
	public List<Studentpaperexaminfo> getStudentsScoreOrderByPaperExamID(int classId,int paperexamId); // 通过学生id和考试id获取学生的成绩
}
