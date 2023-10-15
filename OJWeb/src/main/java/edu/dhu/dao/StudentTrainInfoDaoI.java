package edu.dhu.dao;

import edu.dhu.model.Solution;
import edu.dhu.model.StudentTrainCatDetail;

import java.util.List;

//by zzb
public interface StudentTrainInfoDaoI {
	public List<StudentTrainCatDetail> getClassTrainScore(int examId, int classId);
	
	public void deleteSubmit(int userId, int examId, int catId,float score,float points, String status); // 撤销提交后更新iNfo表
	//获取单个user的信息
	public StudentTrainCatDetail getUserTrainScore(int userId, int examId, int catId);
	
	public void updateTrainScore(StudentTrainCatDetail stc);
	
	// 根据userId和examId获取Studentexaminfo记录
	public List<StudentTrainCatDetail> getStudentTrainInfoByUserIdAnExamId(int userId,
			int examId);
	
	public int editStudentTrainScore(Solution solution);
}
