package edu.dhu.problem.service.impl;

import edu.dhu.exam.dao.ExamDaoI;
import edu.dhu.exam.dao.ItrainProblemCatDaoI;
import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.ItrainProbCatgory;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.global.model.Constant;
import edu.dhu.problem.dao.ExamproblemDaoI;
import edu.dhu.problem.dao.ProblemsDaoI;
import edu.dhu.problem.dao.SolutionDaoI;
import edu.dhu.problem.dao.StudentTrainProbDetailDaoI;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.GradeProblemServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("gradeProblemService")
@Transactional
public class GradeProblemServiceImpl implements GradeProblemServiceI {

	@Resource
	private ProblemsDaoI problemsDao;
	@Resource
	private ExamDaoI examDao;
	@Resource
	private ExamproblemDaoI examproblemDao;
	@Resource
	private StudentexamdetailDaoI studentexamdetailDao;
	@Resource
	private SolutionDaoI solutionDao;
	@Resource
	private StudentTrainProbDetailDaoI studentTrainProbDetailDao;
	@Resource
	private ItrainProblemCatDaoI itrainProblemCatDao;

	@Override
	public float gradeProblemBySolution(Solution solution) {
		// 计算分数
		float score = 0;
		// 获取status
		String status = solution.getStatus();
		// 获取userID
		int userId = solution.getUserid();
		// 获取exam ID
		int examId = solution.getExamId();
		// 获取problemID
		int problemId = solution.getProblemId();
		// 获取correctCaseIds
		String correctCaseIds = solution.getCorrectCaseIds();
		// 获取正确的id数目
		int correctCaseIdsNum = 0;
		// 排除默认的情况下正确的测试用例为－1的情况
		if (correctCaseIds != null && !correctCaseIds.equals("")
				&& !correctCaseIds.equals(Constant.DEFAULT_CORRECTCASEIDS)) {
			// 根据userid，examID，problemID在studentexamdetail查找记录
			Studentexamdetail studentexamdetail = studentexamdetailDao
					.getStatusByUserIDexamIDproblemId(userId, examId, problemId);
			// 根据problemId在problem表中获取problem记录
			Problems problem = problemsDao.findProblemById(problemId);
			// 获取该题目的scoreGrade
			String[] scoreGrade = problem.getScoreGrade().split(",");
			// 根据examID获取Exam，从而查看是否允许部分得分
			Exam exam = examDao.get(Exam.class, examId);
			// 获取是否允许部分得分
			boolean partialScore = exam.getPartialScore();
			// 根据examID，problemID在examproblems中获取该题的总分数
			Examproblems examproblems = examproblemDao
					.getExamproblemsByExamIdAndProblemId(examId, problemId);
			// 获取该题的总分数
			float problemScore = examproblems.getScore();
			// 获取该题的deadline时间
			Date deadline = examproblems.getDeadline();
			Date bestBefore = examproblems.getBestBefore();
			float scoreCoef = examproblems.getScoreCoef();

			// 不允许部分得分
			if (partialScore == false) {
				if (status.equals("AC")) {
					score = problemScore;
					// 将score保存到solution表
					solution.setScore(score);
					// 更新分数
					solutionDao.update(solution);
					return score;
				} else {
					return 0;
				}
			} else {
				// 获取正确的测试用例数量
				correctCaseIdsNum = correctCaseIds.substring(0,
						correctCaseIds.length() - 1).split(",").length;
				// 如果因为其它原因导致正确的测试用例的个数超出了该题分数scoreGrade的个数，则将分数赋值为最大分数
				if (correctCaseIdsNum > scoreGrade.length)
					correctCaseIdsNum = scoreGrade.length;

				if (correctCaseIdsNum != 0) {
					score = (float) (Float
							.parseFloat(scoreGrade[correctCaseIdsNum - 1]) * 0.01 * problemScore);
				} else {
					return 0;
				}

				// 获取hintCases
				String hintCases = studentexamdetail.getHintCases();
				// 分数减去求助的部分
				if (!hintCases.equals(new String("null hintCases"))) {
					score = (float) (score - hintCases.split(",").length
							* problemScore * 0.02);
				}
				// 获取题目的提交时间
				Date submitTime = solution.getSubmitTime();
				if (bestBefore != null && submitTime.after(bestBefore)) {
					score = (float) (score * scoreCoef * 0.01);
				}
				
				//查看题解
				if(studentexamdetail.getCommentClick() != null)
					score = (float) (score - problemScore * 0.1);
								
				// 将score保存到solution表
				solution.setScore(score);
				// 更新分数
				solutionDao.update(solution);
				return score;
			}
		}
		// correctCaseIs中为默认值
		else {
			score = 0;
		}
		return score;
	}

	@Override
	public float gradeItrainProblemBySolution(Solution solution,int catId) {
		// 计算分数
		float score = 0;
		// 获取status
		String status = solution.getStatus();
		// 获取userID
		int userId = solution.getUserid();
		// 获取exam ID
		int examId = solution.getExamId();
		// 获取problemID
		int problemId = solution.getProblemId();
		// 获取correctCaseIds
		String correctCaseIds = solution.getCorrectCaseIds();
		// 获取正确的id数目
		int correctCaseIdsNum = 0;
		// 排除默认的情况下正确的测试用例为－1的情况
		if (correctCaseIds != null && !correctCaseIds.equals("")
						&& !correctCaseIds.equals(Constant.DEFAULT_CORRECTCASEIDS)) {
			// 根据userid，examID，catId,problemID在studenttrainprodetail查找记录
			/*Studentexamdetail studentexamdetail = studentexamdetailDao
							.getStatusByUserIDexamIDproblemId(userId, examId, problemId);*/
			StudentTrainProbDetail stpd = studentTrainProbDetailDao.getStudentTrainProbDetail(userId, examId, catId, problemId);
			// 根据problemId在problem表中获取problem记录
			Problems problem = problemsDao.findProblemById(problemId);
			// 获取该题目的scoreGrade
			String[] scoreGrade = problem.getScoreGrade().split(",");
			// 根据examID获取Exam，从而查看是否允许部分得分
			Exam exam = examDao.get(Exam.class, examId);
			// 获取是否允许部分得分
			boolean partialScore = exam.getPartialScore();
			// 根据examID，problemID在itrainprobcat中获取该题的总分数
					/*Examproblems examproblems = examproblemDao
							.getExamproblemsByExamIdAndProblemId(examId, problemId);*/
			ItrainProbCatgory ipc = itrainProblemCatDao.getItrainProbCatgory(examId, catId);
			// 设置该题的总分数为1
			float problemScore = 1;
			Date bestBefore = ipc.getBestBefore();
			double scoreCoef = 0.0;
			if(ipc.getScoreCoef() != null)
			  scoreCoef = ipc.getScoreCoef();

			// 不允许部分得分
			if (partialScore == false) {
				if (status.equals("AC")) {
					score =  problemScore;
					// 将score保存到solution表
					solution.setScore(score);
					// 更新分数
					solutionDao.update(solution);
					return score;
				} else {
					return 0;
				}
			} else {
				// 获取正确的测试用例数量
				correctCaseIdsNum = correctCaseIds.substring(0,
								correctCaseIds.length() - 1).split(",").length;
				// 如果因为其它原因导致正确的测试用例的个数超出了该题分数scoreGrade的个数，则将分数赋值为最大分数
				if (correctCaseIdsNum > scoreGrade.length)
					correctCaseIdsNum = scoreGrade.length;

					if (correctCaseIdsNum != 0) {
						score = (float) (Float
									.parseFloat(scoreGrade[correctCaseIdsNum - 1]) * 0.01 * problemScore);
					} else {
						return 0;
					}

					// 获取hintCases
					String hintCases = stpd.getHintCases();
					// 分数减去求助的部分
					if (!hintCases.equals(new String("null hintCases"))) {
							score = (float) (score - hintCases.split(",").length
									* problemScore * 0.02);
					}
					// 获取题目的提交时间
					Date submitTime = solution.getSubmitTime();
					if (bestBefore != null && submitTime.after(bestBefore)) {
						score = (float) (score * scoreCoef * 0.01);
					}
					
					
					//查看题解
					if(stpd.getCommentClick() != null)
						score = (float) (score - problemScore * 0.1);
					
					// 将score保存到solution表
					solution.setScore(score);
					// 更新分数
					solutionDao.update(solution);
					return score;
					}
				}
				// correctCaseIs中为默认值
				else {
					score = 0;
				}
				return score;
	}

	@Override
	public float calculateItrainProblemPoints(StudentTrainProbDetail stpd, Date submitTime) {
		return 0;
	}

//	@Override
//	public float calculateItrainProblemPoints(StudentTrainProbDetail stpd, Date submitTime) {
//		Date startTime = stpd.getStartTime();
//		ItrainProbCatgory ipc = itrainProblemCatDao.getItrainProbCatgory(stpd.getExamId(), stpd.getCatId());
//		//做题时间(min)
//		float duration = (submitTime.getTime() - startTime.getTime())/(1000*60);
//		//获取本题平均做题时间
//		Itrainproblems ip = itrainproblemDao.getItrainPro(stpd.getExamId(), stpd.getProblemId());
//		float average = ip.getDuration();
//		//本次提交次数
//		float submit = stpd.getSubmit();
//		//查看测试数据的个数
//		float viewNum;
//		if(stpd.getHintCases().equals("null hintCases"))
//			viewNum = 0;
//		else
//			viewNum = stpd.getHintCases().split(",").length;
//
//
//		//数据替换
//		if(duration <= 0.8 * average)
//			duration = (float) (0.8 * average);
//		if(duration >= 3 * average)
//			duration = 3 * average;
//
//		if(submit >= 5)
//			submit = 5;
//
//		if(viewNum >= 1)
//			viewNum = 1;
//
//		//数据归一化
//		float duration_g = (float) ((duration-0.8*average)/(3*average-0.8*average));
//		float submit_g = (submit-1) / 4;
//		float testcase_g = viewNum/1;
//
//		float lowerlimit = ipc.getLowerLimit();
//		float upperlimit = ipc.getUpperLimit();
//
//		float points = 1/lowerlimit-(duration_g*2+submit_g+testcase_g)/4*(1/lowerlimit-1/upperlimit);
//
//		//points保留5位小数，第6位往上升
//		points = (float) (Math.ceil(points*100000)/100000);
//
//		return points;
//	}

	@Override
	public float calculateItrianCategoryScore(StudentTrainCatDetail stcd) {
		ItrainProbCatgory ipc = itrainProblemCatDao.getItrainProbCatgory(stcd.getExamId(), stcd.getCatId());
		
		float points = stcd.getPoints();
		double temp =  ipc.getScore();
		float totalScore = (float) temp;
		
		if(points > 1)
			points = 1;
		
		float tempScore = points * totalScore;
		
		//已完成的题目
		String finishedIds = stcd.getProbFinished();
		List<Integer> probFinished = new ArrayList<Integer>();
		if(finishedIds != null){
			String[] finishedId = finishedIds.split(",");
			for(int i = 0;i < finishedId.length;i++)
				probFinished.add(Integer.valueOf(finishedId[i]));
		}
		
		//每道题的平均分
		float averageScore = 0;
		if(probFinished.size() != 0)
			averageScore = tempScore / probFinished.size();
		
		float score = 0;
		for(int i = 0;i < probFinished.size();i++)
		{
			StudentTrainProbDetail stpd = studentTrainProbDetailDao.getStudentTrainProbDetail(stcd.getUserId(), stcd.getExamId(), 
					stcd.getCatId(), probFinished.get(i));
			score += averageScore * stpd.getScoreCoef();
		}
		
		//获取未完成的题目数量
		int unprobFinished = 0;
		String extractIds = stcd.getProbSequence(); 
		if(extractIds != null)
			unprobFinished = extractIds.split(",").length - probFinished.size();
		
		//每道未完成的题扣本关的1%分数
		if(unprobFinished > 0)
			score = (float) (score - totalScore * 0.01 * unprobFinished);

		if(score < 0)
			score = 0;
		else{
			//将分数保留两位小数
			BigDecimal bg = new BigDecimal(score);
	        score = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		}
		
		return score;
	}


}
