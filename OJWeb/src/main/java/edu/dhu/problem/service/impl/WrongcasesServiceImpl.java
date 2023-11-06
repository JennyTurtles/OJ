package edu.dhu.problem.service.impl;

import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.Constant;
import edu.dhu.problem.dao.WrongcasesDaoI;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.ExamproblemServiceI;
import edu.dhu.problem.service.GradeProblemServiceI;
import edu.dhu.problem.service.SolutionServiceI;
import edu.dhu.problem.service.WrongcasesServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("wrongcasesService")
@Transactional
public class WrongcasesServiceImpl implements WrongcasesServiceI {

	private StudentexamdetailServiceI studentexamdetailService;
	private WrongcasesDaoI WrongcasesDao;
	private ExamproblemServiceI examproblemService;
	private SolutionServiceI solutionService;
//	private ProblemtestcasesServiceI problemtestcasesService;
	private GradeProblemServiceI gradeProblemService;

	public GradeProblemServiceI getGradeProblemService() {
		return gradeProblemService;
	}

	@Autowired
	public void setGradeProblemService(GradeProblemServiceI gradeProblemService) {
		this.gradeProblemService = gradeProblemService;
	}

	public SolutionServiceI getSolutionService() {
		return solutionService;
	}

	@Autowired
	public void setSolutionService(SolutionServiceI solutionService) {
		this.solutionService = solutionService;
	}

	public ExamproblemServiceI getExamproblemService() {
		return examproblemService;
	}

	@Autowired
	public void setExamproblemService(ExamproblemServiceI examproblemService) {
		this.examproblemService = examproblemService;
	}

	public WrongcasesDaoI getWrongcasesDao() {
		return WrongcasesDao;
	}

	@Autowired
	public void setWrongcasesDao(WrongcasesDaoI wrongcasesDao) {
		WrongcasesDao = wrongcasesDao;
	}

	public StudentexamdetailServiceI getStudentexamdetailService() {
		return studentexamdetailService;
	}

	@Autowired
	public void setStudentexamdetailService(
			StudentexamdetailServiceI studentexamdetailService) {
		this.studentexamdetailService = studentexamdetailService;
	}

	@Override
	public List<Wrongcases> getWrongcasesBySolutionID(int solutionId) {
		return WrongcasesDao.getWrongcasesBySolutionID(solutionId);
	}

	@Override
	public Wrongcases getWrongcasesBysolutionIdAndCaseId(int solutionId,
			int caseId) {
		return WrongcasesDao.getWrongcasesBysolutionIdAndCaseId(solutionId,
				caseId);
	}

	@Override
	public PMWrongAndCorrectIds WS_getAllWrongAndRightCases(int userId, int examId, int problemId) {
		return null;
	}

	@Override
	public Wrongcases getWrongcasesById(int wrongcasesId) {
		return WrongcasesDao.get(Wrongcases.class, wrongcasesId);
	}

	@Override
	public PMWrongAndCorrect WS_getHint(int userId, int examId, int problemId,
										int caseId, boolean returnCaseInfo) {
		// 根据userID，examID，problemID在Studentexamdetail中查找获取studentexamdetail记录
		Studentexamdetail studentexamdetail = studentexamdetailService
				.getStatusByUserIDexamIDproblemId(userId, examId, problemId);

		Solution s = null;
		if (studentexamdetail.getSolutionId() != null) {
			s = solutionService.getSolutionById(studentexamdetail
					.getSolutionId());
		} else {
			// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
			s = solutionService
					.getLastSolutionByUserIdExamIdProblemIdAndStatus(userId,
							examId, problemId, studentexamdetail.getStatus());
		}

		// 根据examID，problemID在examproblems中获取该题的总分数
		Examproblems examproblems = examproblemService
				.getExamproblemsByExamIdAndProblemId(examId, problemId);
		// 获取该题的总分数
		float problemScore = examproblems.getScore();
		// 获取hintCases
		String hintCases = studentexamdetail.getHintCases();
		// 获取提示的测试用例ID
		int theTestCaseId = caseId;
		// 获取分数
		float score = s.getScore();
		// 该学生查看 的测试用例是不是新的
		boolean flag = true;
		String hintCasesString;

		if (hintCases.equals(new String(Constant.DEFAULT_HINTCASE))) {// 从来没查看过
			hintCasesString = "";
			// score = gradeProblemService.gradeProblemBySolution(s);
			/*
			 * studentexamdetail.setHintCases("" + theTestCaseId); // 重新计算得分
			 * score = (float) (score - problemScore * 0.02);
			 * studentexamdetail.setScore(score);
			 * studentexamdetailService.updateStudentexamdetail
			 * (studentexamdetail);
			 */
		} else {
			// 如果请求的测试用例ID在studentExamDetail.hintCases里不存在
			String[] hintCasesArr = hintCases.split(",");

			for (int i = 0; i < hintCasesArr.length; i++) {
				if (hintCasesArr[i].equals(new String("" + theTestCaseId))) {
					flag = false;
				}
			}
			hintCasesString = hintCases + ",";
		}
		if (flag == true) {
			// 如果不存在，则将该测试用例ID加入到studentExamDetail.hintCases字段中，并更新hintCases字段
			studentexamdetail.setHintCases(hintCasesString + theTestCaseId);
			// 重新计算得分
			score = gradeProblemService.gradeProblemBySolution(s);
			studentexamdetail.setScore(score);
			studentexamdetailService.updateStudentexamdetail(studentexamdetail);
		}
		// 保存分数
		// s.setScore(score);
		// solutionService.updateSolution(s);

		PMWrongAndCorrect pMWrongAndCorrect = new PMWrongAndCorrect();
		pMWrongAndCorrect.setScore(score);
		pMWrongAndCorrect.setSolutionId(studentexamdetail.getSolutionId());
		/*
		 * if (returnCaseInfo) { // 获取正确和错误的输出 Problemtestcases problemtestcases
		 * = problemtestcasesService .getProblemtestcasesById(caseId);
		 * Wrongcases wrongcases =
		 * getWrongcasesBysolutionIdAndCaseId(studentexamdetail
		 * .getSolutionId(),caseId);
		 * 
		 * 
		 * if (problemtestcases != null) {
		 * pMWrongAndCorrect.setProblemtestcases(problemtestcases); } if
		 * (wrongcases != null) { pMWrongAndCorrect.setWrongcases(wrongcases); }
		 * }
		 */

		return pMWrongAndCorrect;
	}

//	@Override
//	public PMWrongAndCorrectIds WS_getAllWrongAndRightCases(int userId,
//															int examId, int problemId) {
//		// TODO Auto-generated method stub
//		// 根据userID，examID，problemID在studentexamdetail表中查找该条记录
//		Studentexamdetail studentexamdetail = studentexamdetailService
//				.getStatusByUserIDexamIDproblemId(userId, examId, problemId);
//		// 返回前台的json数据
//		PMWrongAndCorrectIds wrongAndCorrectIds = new PMWrongAndCorrectIds();
//		// 如果没有不存在记录
//		if (studentexamdetail != null) {
//			Solution s = null;
//			if (studentexamdetail.getSolutionId() != null) {
//				s = solutionService.getSolutionById(studentexamdetail
//						.getSolutionId());
//			} else {
//				// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
//				s = solutionService
//						.getLastSolutionByUserIdExamIdProblemIdAndStatus(
//								userId, examId, problemId,
//								studentexamdetail.getStatus());
//			}
//			float score;
//			// 如果solution不为空,则代表用户提交了该题
//			if (s != null) {
//				if (s.getScore() > 0) {
//					score = s.getScore();
//				} else {
//					// 获取该题的所得的分数情况
//					score = gradeProblemService.gradeProblemBySolution(s);
//				}
//				// 根据solutionID查询该题所有的正确的测试用例
//				String[] correctCaseIds = solutionService.getCorrectCaseIds(s
//						.getId());
//				// 根据solutionID查询该题所有的错误的测试用例
//				List<Wrongcases> wrongcases = getWrongcasesBySolutionID(s
//						.getId());
//
//				wrongAndCorrectIds.setHintCases(studentexamdetail
//						.getHintCases());
//				wrongAndCorrectIds.setElapsedTime(studentexamdetail
//						.getElapsedTime());
//				wrongAndCorrectIds.setFinished(studentexamdetail.isFinished());
//				wrongAndCorrectIds.setExamId(examId);
//				wrongAndCorrectIds.setUserId(userId);
//				wrongAndCorrectIds.setProblemId(problemId);
//				wrongAndCorrectIds.setSolutionId(s.getId());
//				wrongAndCorrectIds.setCorrectCaseIds(correctCaseIds);
//				wrongAndCorrectIds.setWrongcases(wrongcases);
//				wrongAndCorrectIds.setStatus(studentexamdetail.getStatus());
//				wrongAndCorrectIds.setCode(s.getSourceCode());
//				wrongAndCorrectIds.setSubmit(studentexamdetail.getSubmit());
//				// 如果分数出来了，则设置分数，返回页面
//				if (score >= 0) {
//					wrongAndCorrectIds.setScore(score);
//				}
//				// 设置提交次数
//
//				WrongAndCorrectCasesAction wrongaction = new WrongAndCorrectCasesAction();
//				// 设置remark
//				if (s.getRemark() != null
//						&& !s.getRemark().equals(new String(""))) {
//					wrongAndCorrectIds
//							.setRemark(wrongaction
//									.switchStatusToRemark(studentexamdetail
//											.getStatus())
//									+ "\n具体信息如下:\n" + s.getRemark());
//				} else {
//					wrongAndCorrectIds
//							.setRemark(wrongaction
//									.switchStatusToRemark(studentexamdetail
//											.getStatus()));
//				}
//
//			} else {
//				wrongAndCorrectIds = null;
//				/*
//				 * wrongAndCorrectIds.setStatus("");
//				 * wrongAndCorrectIds.setSubmit(0);
//				 * wrongAndCorrectIds.setScore(-1);
//				 * wrongAndCorrectIds.setCorrectCaseIds(null);
//				 * wrongAndCorrectIds.setWrongcases(null);
//				 * wrongAndCorrectIds.setRemark("");
//				 */
//			}
//		} else {
//			wrongAndCorrectIds = null;
//			/*
//			 * wrongAndCorrectIds.setStatus("");
//			 * wrongAndCorrectIds.setSubmit(0); wrongAndCorrectIds.setScore(-1);
//			 * wrongAndCorrectIds.setCorrectCaseIds(null);
//			 * wrongAndCorrectIds.setWrongcases(null);
//			 * wrongAndCorrectIds.setRemark("");
//			 */
//		}
//		return wrongAndCorrectIds;
//	}

	@Override
	public boolean alterWrongcasesBysolutionIdAndCaseId(Wrongcases wcs) {
		// TODO Auto-generated method stub
		return WrongcasesDao.alterWrongcasesBysolutionIdAndCaseId(wcs);
	}
}
