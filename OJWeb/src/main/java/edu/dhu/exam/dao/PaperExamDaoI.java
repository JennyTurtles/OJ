package edu.dhu.exam.dao;


import edu.dhu.exam.model.PMPaperExam;
import edu.dhu.exam.model.PaperExam;

import java.util.List;


public interface PaperExamDaoI extends BaseDaoI<PaperExam>{
	public void paperexamAdd(PMPaperExam pMPaperExam); // 添加笔试考试
	
	public List<PaperExam> getAllPaperExamsOrderByEndTime(int teacherId,String roleName); // 获取所有考试，依据结束时间逆序排列
	
	public List<PaperExam> getAllPaperExamBySchoolIdOrderByEndTime(int schoolId); // 根据学校id获取相应的考试，依据结束时间逆序排序
	
	public int updatePaperExam(PMPaperExam pMPaperExam); // 更新笔试考试
	
	public boolean updateExamproblemNum(int paperexamId, int num); // 更新笔试考试题目数量
	
	public List<PaperExam> getPaperExamByProblemId(int paperproblemId); // 获取有problemId的考试
	
	public List<PaperExam> getExamByPaperProblemId(int paperproblemId);//根据paperproblemId查询该题是否被考试收录
	
	public List<PaperExam> getPaperExamsNotInClass(int classId,int teacherId); // 获取班级未参加的笔试考试
	


}
