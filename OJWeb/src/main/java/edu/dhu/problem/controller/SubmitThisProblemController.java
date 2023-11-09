package edu.dhu.problem.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.ExamServiceI;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.Log;
import edu.dhu.global.model.RespBean;
import edu.dhu.global.service.LogServiceI;
import edu.dhu.global.service.RedisServiceI;
import edu.dhu.problem.model.PMWrongAndCorrectIds;
import edu.dhu.problem.service.ExamproblemServiceI;
import edu.dhu.problem.service.ProblemsServiceI;
import edu.dhu.problem.service.SolutionServiceI;
import edu.dhu.solution.service.SubmittedcodeServiceI;
import edu.dhu.user.model.Json;
import edu.dhu.user.service.UserServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/submitThisProblem")
@Transactional
public class SubmitThisProblemController {

	private static final long serialVersionUID = -2185834336935466790L;

	@Resource
	RedisServiceI redisService;
	@Resource
	private SolutionServiceI solutionService;
	@Resource
	private StudentexamdetailServiceI studentexamdetailService;
//	private SimilaritywarningServiceI similaritywarningService;
//	private StudentexamdetailServiceI studentexamdetailService;
//	private ExamproblemServiceI examproblemService;
	@Resource
	private LogServiceI logService;
//	private RedisServiceI redisService;
//	private StudentTrainProbDetailServiceI studentTrainProbDetailService;
//	private int catId;
//	private String continueTrain;

	private static Lock lock = new ReentrantLock();// 锁对象
	// 根据examID，solutionID，problemID获取用户最新的solution
//	public void getLastSolution() {
//		Solution solution = solutionService
//				.getLastSolutionByUserIdExamIdProblemId(
//						pMWrongAndCorrectIds.getUserId(),
//						pMWrongAndCorrectIds.getExamId(),
//						pMWrongAndCorrectIds.getProblemId());
//		// 返回前台的json数据
//		Json j = new Json();
//		if (solution != null) {
//			j.setSuccess(true);
//			j.setObj(solution);
//			j.setMsg("获取之前的代码成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("本题之前没有提交过");
//			super.writeJson(j);
//		}
//	}
//
//	// 判断是否能够点击提交本题按钮
//	public void isSubmitThisProblem() {
//		// 返回前台的json数据
//		Json j = new Json();
//		// 根据userId,examId,problemId在solution中查找最新的solution
//		Solution solution = solutionService
//				.getLastSolutionByUserIdExamIdProblemId(
//						pMWrongAndCorrectIds.getUserId(),
//						pMWrongAndCorrectIds.getExamId(),
//						pMWrongAndCorrectIds.getProblemId());
//		if (solution == null) {
//			j.setSuccess(false);
//			j.setMsg("本题没有提交过代码，不可以提交本题。");
//			super.writeJson(j);
//		} else {
//			//智能训练页面做题判断是否提交
//			if(catId != 0){
//				StudentTrainProbDetail stpd = studentTrainProbDetailService
//						.getStudentTrainProbDetail(pMWrongAndCorrectIds.getUserId(), pMWrongAndCorrectIds.getExamId(),
//								catId, pMWrongAndCorrectIds.getProblemId());
//				if(stpd.isFinished()){
//					j.setSuccess(false);
//					j.setMsg("不能重复提交");
//					super.writeJson(j);
//				}else{
//					j.setSuccess(true);
//					j.setMsg("没有提交过本题，可以提交");
//					super.writeJson(j);
//				}
//			}else{
//				// 根据userid，examID，problemID在studentexamtail表中查找
//				Studentexamdetail studentexamdetail = studentexamdetailService
//						.getStatusByUserIDexamIDproblemId(
//								pMWrongAndCorrectIds.getUserId(),
//								pMWrongAndCorrectIds.getExamId(),
//								pMWrongAndCorrectIds.getProblemId());
//				if (studentexamdetail.isFinished()) {
//					j.setSuccess(false);
//					j.setMsg("不能重复提交");
//					super.writeJson(j);
//				} else {
//					j.setSuccess(true);
//					j.setMsg("没有提交过本题，可以提交");
//					super.writeJson(j);
//				}
//			}
//		}
//	}
	 //用户提交本题
	@PostMapping("")
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public RespBean submitThisProblem(@RequestBody PMWrongAndCorrectIds pMWrongAndCorrectIds, HttpServletRequest request) throws InterruptedException {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		pMWrongAndCorrectIds.setUserId(Integer.parseInt(userId));
		String key = "submitThisProblem_" + userId;
		System.out.printf("key: %s\n", key);
//		lock.lock();// 获得锁
		// 只使用redis分布式锁
		try {
			// 获取锁失败就结束
			if (!redisService.lock(key, 30)) {
//				lock.unlock();
				return RespBean.error("提交本题不能过快,请稍后重试");
			}
		} catch (Exception e) {
//			lock.unlock();
			return RespBean.error("服务器错误,请重试");
		}
		try {
			boolean isOverSimilarity = false;
			// 根据userID，examID，problemID在Studentexamdetail中查找获取studentexamdetail记录
			Studentexamdetail studentexamdetail = studentexamdetailService
					.getStatusByUserIDexamIDproblemId(
							pMWrongAndCorrectIds.getUserId(),
							pMWrongAndCorrectIds.getExamId(),
							pMWrongAndCorrectIds.getProblemId());
			// Studentexamdetail为空说明还没有裁判过，则提示用户
			if (studentexamdetail == null) {
				return RespBean.error("必须先提交代码才能提交本题。");
			}
			else if (studentexamdetail.isFinished()) {
				return RespBean.error("不能重复提交");
			} 
			else {
				Json j = new Json();
				Json json = solutionService.submitThisProblem(
						studentexamdetail, pMWrongAndCorrectIds, j,
						isOverSimilarity);
				if (json.isSuccess())
					return RespBean.ok(json.getMsg());
				else
					return RespBean.error(json.getMsg());
			}
			// }
		} catch (Exception e) {
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {
				sOut += "\tat " + s + "\r\n";
			}
			// 异常信息最大记录19000个字符，数据库该字段最大为20K
			int count = sOut.length() > 19000 ? 19000 : sOut.length();
			sOut = sOut.substring(0, count - 1);
			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e
					.getLocalizedMessage().length();
			String localMessage = "";
			if (e.getLocalizedMessage() != null) {
				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
			}
			Log log = new Log();
			log.setType("代码提交");
			log.setOptime(new Date());
			log.setUserId(pMWrongAndCorrectIds.getUserId());
			log.setUserType("student");
			log.setContent(sOut);
			log.setAbstractContent("学生id:" + pMWrongAndCorrectIds.getUserId()
					+ "考试id:" + pMWrongAndCorrectIds.getExamId() + "题目id:"
					+ pMWrongAndCorrectIds.getProblemId() + "\n" + localMessage);
			logService.WriteLog(log);
			return RespBean.error("服务器内部发生错误，请报告管理员。");
		} finally {
			redisService.unLock(key);
//			lock.unlock();// 释放锁
		}
	}
	
	
//	//智能训练提交本题
//	@Transactional(isolation=Isolation.SERIALIZABLE)
//	public void submitItrainThisProblem() {
//		String key=null;
//		// 返回前台的json数据
//		Json j = new Json();
//		// 如果session断掉了
//		Map<String, Object> session = ActionContext.getContext()
//				.getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		if (sessionInfo == null) {
//			j.setSuccess(false);
//			j.setMsg("必须先登录才能提交本题。");
//			super.writeJson(j);
//			return;
//		}
//		String userName=sessionInfo.getLoginName();
//		key = "submitThisProblem_" + userName;
//
//		lock.lock();// 获得锁
//		// redis分布式锁
//		int expire = 30; // 超时时间
//
//		try {
//			boolean isOverSimilarity = false;
//			StudentTrainProbDetail stpd = studentTrainProbDetailService.getStudentTrainProbDetail(pMWrongAndCorrectIds.getUserId(),
//					pMWrongAndCorrectIds.getExamId(),
//					catId, pMWrongAndCorrectIds.getProblemId());
//			if(stpd == null){
//				j.setSuccess(false);
//				j.setMsg("必须先提交代码才能提交本题。");
//				super.writeJson(j);
//			}else if(stpd.isFinished()){
//				j.setSuccess(false);
//				j.setMsg("不能重复提交");
//				super.writeJson(j);
//			}else{
//				Json json = solutionService.submitItrainThisProblem(
//						stpd, pMWrongAndCorrectIds, j,
//						isOverSimilarity,continueTrain);
//				j.setSuccess(json.isSuccess());
//				j.setMsg(json.getMsg());
//				j.setObj(json.getObj());
//				super.writeJson(j);
//			}
//		} catch (Exception e) {
//			String sOut = "";
//			StackTraceElement[] trace = e.getStackTrace();
//			for (StackTraceElement s : trace) {
//				sOut += "\tat " + s + "\r\n";
//			}
//			// 异常信息最大记录19000个字符，数据库该字段最大为20K
//			int count = sOut.length() > 19000 ? 19000 : sOut.length();
//			sOut = sOut.substring(0, count - 1);
//			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e
//					.getLocalizedMessage().length();
//			String localMessage = "";
//			if (e.getLocalizedMessage() != null) {
//				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
//			}
//			Log log = new Log();
//			log.setType("代码提交");
//			log.setOptime(new Date());
//			log.setUserId(pMWrongAndCorrectIds.getUserId());
//			log.setUserType("student");
//			log.setContent(sOut);
//			log.setAbstractContent("学生id:" + pMWrongAndCorrectIds.getUserId()
//					+ "考试id:" + pMWrongAndCorrectIds.getExamId() + "题目id:"
//					+ pMWrongAndCorrectIds.getProblemId() + "\n" + localMessage);
//			logService.WriteLog(log);
//
//			// 返回前台的json数据
//			j = new Json();
//			j.setSuccess(false);
//			j.setMsg("服务器内部发生错误，请报告管理员。");
//			super.writeJson(j);
//		}finally {
////			redisService.unLock(key);
//			lock.unlock();// 释放锁
//
//		}
//	}
}
