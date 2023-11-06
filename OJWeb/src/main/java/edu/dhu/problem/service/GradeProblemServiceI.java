package edu.dhu.problem.service;



import edu.dhu.problem.model.Solution;
import edu.dhu.problem.model.StudentTrainCatDetail;
import edu.dhu.problem.model.StudentTrainProbDetail;

import java.util.Date;

public interface GradeProblemServiceI {

	// 根据最新提交的solution进行打分
	public float gradeProblemBySolution(Solution solution);
	
	//智能训练题目根据最新提交的solution进行打分
	public float gradeItrainProblemBySolution(Solution solution,int catId);
	
	//计算智能训练题目积分points
	public float calculateItrainProblemPoints(StudentTrainProbDetail stpd, Date submitTime);
	
	//计算智能训练模块类别分数score
	public float calculateItrianCategoryScore(StudentTrainCatDetail stcd);
}
