package edu.dhu.dao;

import edu.dhu.model.StudentTrainProbDetail;

import java.util.List;

//by zzb
public interface StudentTrainDetailDaoI extends BaseDaoI<StudentTrainProbDetail>{
	// 获取考试的所有提交信息
	public List<StudentTrainProbDetail> getClassStudentTrainDetail(int examId, int classId);
	
	public List<Object[]> getSubmitedDetailByExamId(int examId); // 获取考试所有提交的detail
	
	public StudentTrainProbDetail getTrainDetailBySolutionId(int solutionId);
	
	public StudentTrainProbDetail deleteFinishedBySolutionId(int solutionId,
			int reason); // 撤销detail表的finished
	public List<Object[]> getStuProbFinishNumberByEamId(int examId);
}
