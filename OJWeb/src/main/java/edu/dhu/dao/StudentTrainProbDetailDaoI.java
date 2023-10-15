package edu.dhu.dao;

import edu.dhu.model.StudentTrainProbDetail;

import java.util.List;
import java.util.Map;

public interface StudentTrainProbDetailDaoI  extends BaseDaoI<StudentTrainProbDetail>{
	//获取该考试分类题目做题记录
	public List<Map<String,Object>> getStudentTrainProRecord(int userId,int examId);
	//根据userId+examId+catId+problemId获取该题目信息状态
	public StudentTrainProbDetail getStudentTrainProbDetail(int userId,int examId,int catId,int problemId);
	
	public StudentTrainProbDetail getStatusByUserIdAndExamIdAndProId(int userId,int examId,int proId);
	
	//更新StudentTrainProbDetail
	public void updateStudentTrainProbDetail(StudentTrainProbDetail stpd);
	
	//获取该场考试已有的题目
	public List<Map<String,Object>> getDrawProblems(int userId,int examId,int catId);
}
