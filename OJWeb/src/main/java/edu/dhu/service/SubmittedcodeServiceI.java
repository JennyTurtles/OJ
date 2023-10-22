//package edu.dhu.service;
//
//import edu.dhu.model.*;
//import edu.dhu.pageModel.Json;
//
//import java.util.Date;
//import java.util.List;
//
//public interface SubmittedcodeServiceI {
//
//	// 根据solutionId和problemId查找记录
//	public Submittedcode getSubmittedcodeBySolutionIdAndProblemId(
//			int solutionId, int problemId);
//
//	// 根据userID，examID，problemID，提交最新的solution
//	// public boolean submitThisProblem(int userId,int examId,int
//	// problemId,String status);
//
//	// 根据Solution,Studentexamdetail提交最新的solution
//	public boolean submitThisProblem(Solution solu,
//			Studentexamdetail stuexamdetail);
//
//	// 保存提交代码的相关操作
//	public Json submitCode(Problems problem, Solution solution,
//			Studentexamdetail studentexamdetail, Date now, Date startTime,
//			Date endTime);
//
//	// 服务器保存提交代码的相关操作
//	public Solution WS_submitCode(Problems problem, Solution solution,
//			Studentexamdetail studentexamdetail, List<Wrongcases> wrongcases,
//			Date now, Date startTime, Date endTime);
//
//	/*
//	 * //服务器更新客户端提交本题操作 public boolean WS_submitThisProblem(Solution solu,
//	 * Studentexamdetail stuexamdetail);
//	 */
//
//	public Solution WS_updateResult(Problems problem, Solution solution, List<Wrongcases> wrongcases);
//
//	//客户端智能训练提交代码操作
//	public Solution WS_ItrainsubmitCode(Problems problem, Solution solution,
//			StudentTrainProbDetail stpd, List<Wrongcases> wrongcases,
//			Date now, Date startTime, Date endTime);
//
//	//web端智能训练保存提交代码操作
//	public Json itrainSubmitCode(Problems problem, Solution solution,
//			StudentTrainProbDetail stpd, Date now, Date startTime,
//				Date endTime);
//
//	// 根据Solution,StudentTrainProbDetail提交最新的solution
//	//public boolean itrainSubmitThisProblem(Solution solu,StudentTrainProbDetail stpd,String continueTrain);
//	public String itrainSubmitThisProblem(Solution solu,StudentTrainProbDetail stpd,String continueTrain);
//}
