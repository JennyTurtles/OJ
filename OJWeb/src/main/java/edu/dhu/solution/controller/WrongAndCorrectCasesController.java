package edu.dhu.solution.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.Constant;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.RespBean;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wrongAndRightCases")
@Transactional
public class WrongAndCorrectCasesController {

	@Resource
	private SolutionServiceI solutionService;
	@Resource
	private StudentexamdetailServiceI studentexamdetailService;
	@Resource
	private GradeProblemServiceI gradeProblemService;
	@Resource
	private WrongcasesServiceI wrongcasesService;
	@Resource
	private ExamproblemServiceI examproblemService;
	@Resource
	private ProblemtestcasesServiceI problemtestcasesService;
//	private ExamServiceI examService;
//	private StudentTrainProbDetailServiceI studentTrainProbDetailService;
//	private ItrainProblemCatServiceI itrainProblemCatService;
	private int catId;
//	// 根据examID查看是否允许获取提示
//	public void canGetHint() {
//		// 返回前台的json数据
//		Json j = new Json();
//		// 根据examId查找Exam
//		Exam exam = examService.getExamById(pMWrongAndCorrectIds.getExamId());
//		if (exam.getCanGetHint()) {
//			j.setSuccess(true);
//			j.setMsg("该场考试允许获取提示");
//		} else {
//			j.setSuccess(false);
//			j.setMsg("该场考试不允许获取提示");
//		}
//		super.writeJson(j);
//	}
//
//	// 根据wrongcasesId 查找错误输出和对应的Wrongcases
//	// 根据problemtestcasesId查找对应的Problemtestcases
//
	@PostMapping("/getHint")
	public RespBean getHint(@RequestBody PMWrongAndCorrectIds pMWrongAndCorrectIds, HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		pMWrongAndCorrectIds.setUserId(Integer.parseInt(userId));
		// 根据userID，examID，problemID在Studentexamdetail中查找获取studentexamdetail记录
		Studentexamdetail studentexamdetail = studentexamdetailService
				.getStatusByUserIDexamIDproblemId(
						pMWrongAndCorrectIds.getUserId(),
						pMWrongAndCorrectIds.getExamId(),
						pMWrongAndCorrectIds.getProblemId());

		Solution s = null;
		if (studentexamdetail.getSolutionId() != null) {
			s = solutionService.getSolutionById(studentexamdetail
					.getSolutionId());
		} else {
			// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
			s = solutionService
					.getLastSolutionByUserIdExamIdProblemIdAndStatus(
							pMWrongAndCorrectIds.getUserId(),
							pMWrongAndCorrectIds.getExamId(),
							pMWrongAndCorrectIds.getProblemId(),
							studentexamdetail.getStatus());
		}

		// 根据examID，problemID在examproblems中获取该题的总分数
		Examproblems examproblems = examproblemService
				.getExamproblemsByExamIdAndProblemId(
						pMWrongAndCorrectIds.getExamId(),
						pMWrongAndCorrectIds.getProblemId());
		// 获取该题的总分数
		float problemScore = examproblems.getScore();
		// 获取hintCases
		String hintCases = studentexamdetail.getHintCases();
		// 获取提示的测试用例ID
		int theTestCaseId = pMWrongAndCorrectIds.getProblemtestcasesId();
		// 获取分数
		float score = s.getScore();
		// 该学生没有请求过测试用例
		if (hintCases.equals(new String(Constant.DEFAULT_HINTCASE))) {
			studentexamdetail.setHintCases("" + theTestCaseId);
			// 重新计算得分
			score = (float) (score - problemScore * 0.02);

			//查看题解
			if(studentexamdetail.getCommentClick() != null)
				score = (float) (score - problemScore * 0.1);

			if(score<0)
				score=0;
			studentexamdetail.setScore(score);
			studentexamdetailService.updateStudentexamdetail(studentexamdetail);
		} else {
			// 如果请求的测试用例ID在studentExamDetail.hintCases里不存在
			String[] hintCasesArr = hintCases.split(",");
			boolean flag = false;
			for (int i = 0; i < hintCasesArr.length; i++) {
				if (hintCasesArr[i].equals(new String("" + theTestCaseId))) {
					flag = true;
				}
			}
			if (flag == false) {
				// 如果不存在，则将该测试用例ID加入到studentExamDetail.hintCases字段中，并更新hintCases字段
				studentexamdetail.setHintCases(hintCases + "," + theTestCaseId);
				// 重新计算得分
				score = (float) (score - problemScore * 0.02);

				//查看题解
				if(studentexamdetail.getCommentClick() != null)
					score = (float) (score - problemScore * 0.1);

				if(score<0)
					score=0;
				studentexamdetail.setScore(score);
				studentexamdetailService
						.updateStudentexamdetail(studentexamdetail);
			}
		}
		// 保存分数
		if(score<0)
			score=0;
		s.setScore(score);
		solutionService.updateSolution(s);
		// 获取正确和错误的输出
		Problemtestcases problemtestcases = problemtestcasesService
				.getProblemtestcasesById(pMWrongAndCorrectIds
						.getProblemtestcasesId());
		Wrongcases wrongcases = wrongcasesService
				.getWrongcasesById(pMWrongAndCorrectIds.getWrongcasesId());

		PMWrongAndCorrect pMWrongAndCorrect = new PMWrongAndCorrect();
		if (problemtestcases != null && wrongcases != null) {
			pMWrongAndCorrect.setProblemtestcases(problemtestcases);
			pMWrongAndCorrect.setWrongcases(wrongcases);
			pMWrongAndCorrect.setScore(score);
			return RespBean.ok("获取正确的测试用成功",pMWrongAndCorrect);
		} else {
			return RespBean.ok("获取正确的测试用失败");
		}
	}

	// 获取学生提交的该题的所有错误的测试用例ID和正确的测试用例ID
	@PostMapping("/getAllWrongAndRightCases")
	public RespBean getAllWrongAndRightCases(@RequestBody PMWrongAndCorrectIds pMWrongAndCorrectIds, HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		return solutionService.getAllWrongAndRightCases(
				Integer.parseInt(userId), pMWrongAndCorrectIds.getExamId(),pMWrongAndCorrectIds.getProblemId());
	}

//	public void getWrongTestCaseBySolutionId() // 通过solutionId获取错误测试用例
//	{
//		int solutionId = pMWrongAndCorrectIds.getSolutionId();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			j.setSuccess(true);
//			List<Wrongcases> caseList = wrongcasesService
//					.getWrongcasesBySolutionID(solutionId);
//			j.setObj(caseList);
//			j.setMsg("获取错误测试用例成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getProblemTestCasesById() // 根据id获取测试用例
//	{
//		int problemId = pMWrongAndCorrectIds.getProblemId();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<Problemtestcases> testCases = problemtestcasesService
//					.getProblemtestcasesByProblemId(problemId);
//			j.setSuccess(true);
//			j.setObj(testCases);
//			j.setMsg("获取测试用例成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}

//
//	public void getItrainAllWrongAndRightCases(){
//		Json j = new Json();
//		//获取studentTrainProbDetai记录
//		StudentTrainProbDetail stpd = studentTrainProbDetailService.getStatusByUserIdAndExamIdAndProId(
//				pMWrongAndCorrectIds.getUserId(), pMWrongAndCorrectIds.getExamId(), pMWrongAndCorrectIds.getProblemId());
//
//		if(stpd != null){
//			Solution s = null;
//			if (stpd.getSolutionId() != null) {
//				s = solutionService.getSolutionById(stpd.getSolutionId());
//			} else {
//				// 根据userId,examId,problemId以及StudenttrainProbdetail的status在solution中查找ID值最大的solution
//				s = solutionService.getLastSolutionByUserIdExamIdProblemIdAndStatus(pMWrongAndCorrectIds.getUserId(),
//								pMWrongAndCorrectIds.getExamId(),pMWrongAndCorrectIds.getProblemId(),stpd.getStatus());
//			}
//			float score;
//			// 如果solution不为空,则代表用户提交了该题
//			if (s != null) {
//				if (s.getScore() > 0) {
//					score = s.getScore();
//				} else {
//					// 获取该题的所得的分数情况
//					score = gradeProblemService.gradeItrainProblemBySolution(s, catId);
//				}
//				// 根据solutionID查询该题所有的正确的测试用例
//				String[] correctCaseIds = solutionService.getCorrectCaseIds(s
//						.getId());
//				// 根据solutionID查询该题所有的错误的测试用例
//				List<Wrongcases> wrongcases = wrongcasesService
//						.getWrongcasesBySolutionID(s.getId());
//
//				PMWrongAndCorrectIds wrongAndCorrectIds = new PMWrongAndCorrectIds();
//				wrongAndCorrectIds.setExamId(pMWrongAndCorrectIds.getExamId());
//				wrongAndCorrectIds.setUserId(pMWrongAndCorrectIds.getUserId());
//				wrongAndCorrectIds.setProblemId(pMWrongAndCorrectIds
//						.getProblemId());
//				wrongAndCorrectIds.setSolutionId(s.getId());
//				wrongAndCorrectIds.setCorrectCaseIds(correctCaseIds);
//				wrongAndCorrectIds.setWrongcases(wrongcases);
//				wrongAndCorrectIds.setStatus(stpd.getStatus());
//				// 如果分数出来了，则设置分数，返回页面
//				if (score >= 0) {
//					wrongAndCorrectIds.setScore(score);
//				}
//				// 设置提交次数
//				wrongAndCorrectIds.setSubmit(stpd.getSubmit());
//				// 设置remark
//				if (s.getRemark() != null
//						&& !s.getRemark().equals(new String(""))) {
//					wrongAndCorrectIds
//							.setRemark(switchStatusToRemark(stpd
//									.getStatus())
//									+ "\n具体信息如下:\n"
//									+ s.getRemark());
//				} else {
//					wrongAndCorrectIds
//							.setRemark(switchStatusToRemark(stpd
//									.getStatus()));
//				}
//
//				j.setSuccess(true);
//				j.setObj(wrongAndCorrectIds);
//				j.setMsg("查询所有的正确和错误的测试用例成功");
//				super.writeJson(j);
//			} else{
//				j.setSuccess(true);
//				j.setMsg("正确和错误的测试用例为空");
//				super.writeJson(j);
//			}
//
//		}else{
//			j.setSuccess(true);
//			j.setMsg("正确和错误的测试用例为空");
//			super.writeJson(j);
//		}
//
//	}
//
//	public void getItrainHint(){
//		// 返回前台的json数据
//		Json j = new Json();
//		//获取studentTrainProbDetai记录
//		StudentTrainProbDetail stpd = studentTrainProbDetailService.getStatusByUserIdAndExamIdAndProId(
//				pMWrongAndCorrectIds.getUserId(), pMWrongAndCorrectIds.getExamId(), pMWrongAndCorrectIds.getProblemId());
//
//		Solution s = null;
//		if (stpd.getSolutionId() != null) {
//			s = solutionService.getSolutionById(stpd.getSolutionId());
//		} else {
//			// 根据userId,examId,problemId以及StudenttrainProbdetail的status在solution中查找ID值最大的solution
//			s = solutionService.getLastSolutionByUserIdExamIdProblemIdAndStatus(pMWrongAndCorrectIds.getUserId(),
//							pMWrongAndCorrectIds.getExamId(),pMWrongAndCorrectIds.getProblemId(),stpd.getStatus());
//		}
//
//		ItrainProbCatgory ipc = itrainProblemCatService.getItrainProCategoryByExamIdAndCatId(pMWrongAndCorrectIds.getExamId(), catId);
//		// 获取该题的总分数
//		double sumScore = ipc.getScore();
//		float problemScore = (float) sumScore;
//		// 获取hintCases
//		String hintCases = stpd.getHintCases();
//		// 获取提示的测试用例ID
//		int theTestCaseId = pMWrongAndCorrectIds.getProblemtestcasesId();
//		// 获取分数
//		float score = s.getScore();
//		// 该学生没有请求过测试用例
//		if (hintCases.equals(new String(Constant.DEFAULT_HINTCASE))) {
//			stpd.setHintCases("" + theTestCaseId);
//			// 重新计算得分
//			score = (float) (score - problemScore * 0.02);
//
//			//查看题解
//			if(stpd.getCommentClick() != null)
//				score = (float) (score - problemScore * 0.1);
//
//			if(score<0)
//				score=0;
//				stpd.setScoreCoef(score);
//				studentTrainProbDetailService.updateStudentTrainProbDetail(stpd);
//		} else {
//			// 如果请求的测试用例ID在studentExamDetail.hintCases里不存在
//			String[] hintCasesArr = hintCases.split(",");
//			boolean flag = false;
//			for (int i = 0; i < hintCasesArr.length; i++) {
//				if (hintCasesArr[i].equals(new String("" + theTestCaseId))) {
//					flag = true;
//				}
//			}
//			if (flag == false) {
//				// 如果不存在，则将该测试用例ID加入到studentExamDetail.hintCases字段中，并更新hintCases字段
//				stpd.setHintCases(hintCases + "," + theTestCaseId);
//				// 重新计算得分
//				score = (float) (score - problemScore * 0.02);
//
//				//查看题解
//				if(stpd.getCommentClick() != null)
//					score = (float) (score - problemScore * 0.1);
//
//				if(score<0)
//					score=0;
//				stpd.setScoreCoef(score);
//				studentTrainProbDetailService.updateStudentTrainProbDetail(stpd);
//			}
//		}
//		// 保存分数
//		if(score<0)
//			score=0;
//		s.setScore(score);
//		solutionService.updateSolution(s);
//		// 获取正确和错误的输出
//		Problemtestcases problemtestcases = problemtestcasesService.getProblemtestcasesById(pMWrongAndCorrectIds.getProblemtestcasesId());
//		Wrongcases wrongcases = wrongcasesService.getWrongcasesById(pMWrongAndCorrectIds.getWrongcasesId());
//
//		PMWrongAndCorrect pMWrongAndCorrect = new PMWrongAndCorrect();
//		if (problemtestcases != null && wrongcases != null) {
//			pMWrongAndCorrect.setProblemtestcases(problemtestcases);
//			pMWrongAndCorrect.setWrongcases(wrongcases);
//			pMWrongAndCorrect.setScore(score);
//			logger.info("获取wrongcasesId" + wrongcases.getId()
//							+ ";problemtestcasesId为" + problemtestcases.getId()
//							+ "获取正确的测试用成功");
//			j.setSuccess(true);
//			j.setObj(pMWrongAndCorrect);
//			j.setMsg("获取正确的测试用成功");
//			super.writeJson(j);
//		} else {
//			logger.info("获取wrongcasesId" + wrongcases.getId()
//							+ ";problemtestcasesId为" + problemtestcases.getId()
//							+ "获取正确的测试用成功");
//			j.setSuccess(true);
//			j.setObj(null);
//			j.setMsg("获取正确的测试用失败");
//			super.writeJson(j);
//		}
//
//	}
//
//
}
