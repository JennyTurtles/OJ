package edu.dhu.solution.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import edu.dhu.exam.dao.ItrainProblemCatDaoI;
import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.dao.StudentexaminfoDaoI;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.ExamServiceI;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.Constant;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.Log;
import edu.dhu.global.model.RespBean;
import edu.dhu.global.service.LogServiceI;
import edu.dhu.problem.dao.ExamproblemDaoI;
import edu.dhu.problem.dao.ProblemCategoryDaoI;
import edu.dhu.problem.model.Examproblems;
import edu.dhu.problem.model.PMWrongAndCorrectIds;
import edu.dhu.problem.model.Problems;
import edu.dhu.problem.model.Solution;
import edu.dhu.problem.service.ExamproblemServiceI;
import edu.dhu.problem.service.ProblemsServiceI;
import edu.dhu.problem.service.SolutionServiceI;
import edu.dhu.solution.service.SubmittedcodeServiceI;
import edu.dhu.user.dao.UserDaoI;
import edu.dhu.user.model.Json;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Transactional
@RestController
@RequestMapping("/solution")
public class SolutionController {
	// 记录日志
//	private static final String root_ZIP = ServletActionContext.getServletContext().getRealPath("/") + "OJtemp\\";
	int pageSize, nowPage;
	String displaySequence;
	String studentNo, name, banji, searchTime, url, targeName;
	float similarity;
	int reason = 0; // 撤销提交的原因
	boolean isCopy = true;
	boolean isLast; // 是否要查询最后提交
	private InputStream fileInput;
	private String fileName;
	private UserDaoI userDao;
	private ExamproblemDaoI examproblemDao;
//	private ClassesDaoI classesDao;
//	private ClassstudentsDaoI classstudentsDao;
	private SolutionServiceI solutionService;
	@Resource
	private StudentexamdetailServiceI studentexamdetailService;
	private ExamServiceI examService;
	private ProblemsServiceI problemsService;
	@Resource
	private LogServiceI logService;
	private SubmittedcodeServiceI submittedcodeService;
	private ExamproblemServiceI examproblemService;
	private ProblemsServiceI problemsServiceI;
	private StudentexaminfoDaoI studentexaminfoDao;
	private StudentexamdetailDaoI studentexamdetailDao;
//	private StudentTrainInfoDaoI studentTrainInfoDaoI;
//	private StudentTrainDetailDaoI studentTrainDetailDao;
	private ItrainProblemCatDaoI itrainProblemCatDao;
	private ProblemCategoryDaoI problemCategoryDao;
//	private ClassesServiceI classesServiceI;
//	private RedisServiceI redisService;
	private static Lock reentrantLock = new ReentrantLock();// 锁对象
//	private StudentTrainProbDetailServiceI studentTrainProbDetailService;
	private int catId;
//	private ItrainProblemCatServiceI itrainProblemCatService;
//	private ItrainproblemDaoI itrainproblemDao;
//
//	public RedisServiceI getRedisService() {
//		return redisService;
//	}
//
//	@Autowired
//	public void setRedisService(RedisServiceI redisService) {
//		this.redisService = redisService;
//	}
//
//	@Autowired
//	public void setClassesServiceI(ClassesServiceI classesServiceI) {
//		this.classesServiceI = classesServiceI;
//	}
//
//	public ClassesServiceI getClassesServiceI() {
//		return classesServiceI;
//	}
//
//	public ClassesDaoI getClassesDao() {
//		return classesDao;
//	}
//
//	@Autowired
//	public void setClassesDao(ClassesDaoI classesDao) {
//		this.classesDao = classesDao;
//	}
//
//	public ClassstudentsDaoI getClassstudentsDao() {
//		return classstudentsDao;
//	}
//
//	@Autowired
//	public void setClassstudentsDao(ClassstudentsDaoI classstudentsDao) {
//		this.classstudentsDao = classstudentsDao;
//	}

	public StudentexamdetailDaoI getStudentexamdetailDao() {
		return studentexamdetailDao;
	}

	@Autowired
	public void setStudentexamdetailDao(StudentexamdetailDaoI studentexamdetailDao) {
		this.studentexamdetailDao = studentexamdetailDao;
	}

	// 给给个学生分配一个锁，让学生可以并行提交代码，但是每个学生只能线性提交代码
	private final static HashMap<Integer, ReentrantLock> lockMap = new HashMap<>();

	public StudentexaminfoDaoI getStudentexaminfoDao() {
		return studentexaminfoDao;
	}

	@Autowired
	public void setStudentexaminfoDao(StudentexaminfoDaoI studentexaminfoDao) {
		this.studentexaminfoDao = studentexaminfoDao;
	}

	public ProblemsServiceI getProblemsServiceI() {
		return problemsServiceI;
	}

	@Autowired
	public void setProblemsServiceI(ProblemsServiceI problemsServiceI) {
		this.problemsServiceI = problemsServiceI;
	}

	public ExamproblemServiceI getExamproblemService() {
		return examproblemService;
	}

	@Autowired
	public void setExamproblemService(ExamproblemServiceI examproblemService) {
		this.examproblemService = examproblemService;
	}

//	public StudentTrainInfoDaoI getStudentTrainInfoDaoI() {
//		return studentTrainInfoDaoI;
//	}
//
//	@Autowired
//	public void setStudentTrainInfoDaoI(StudentTrainInfoDaoI studentTrainInfoDaoI) {
//		this.studentTrainInfoDaoI = studentTrainInfoDaoI;
//	}

	public ItrainProblemCatDaoI getItrainProblemCatDao() {
		return itrainProblemCatDao;
	}

//	public StudentTrainDetailDaoI getStudentTrainDetailDao() {
//		return studentTrainDetailDao;
//	}
//
//	@Autowired
//	public void setStudentTrainDetailDao(StudentTrainDetailDaoI studentTrainDetailDao) {
//		this.studentTrainDetailDao = studentTrainDetailDao;
//	}

	@Autowired
	public void setItrainProblemCatDao(ItrainProblemCatDaoI itrainProblemCatDao) {
		this.itrainProblemCatDao = itrainProblemCatDao;
	}

	public ProblemCategoryDaoI getProblemCategoryDao() {
		return problemCategoryDao;
	}

	@Autowired
	public void setProblemCategoryDao(ProblemCategoryDaoI problemCategoryDao) {
		this.problemCategoryDao = problemCategoryDao;
	}

	public InputStream getFileInput() {
		return fileInput;
	}

	public void setFileInput(InputStream fileInput) {

		this.fileInput = fileInput;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setDisplaySequence(String displaySequence) {
		this.displaySequence = displaySequence;
	}

	public String getDisplaySequence() {
		return displaySequence;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTargeName() {
		return targeName;
	}

	public void setTargeName(String targeName) {
		this.targeName = targeName;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public String getBanji() {
		return banji;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setReason(int reason) {
		this.reason = reason;
	}

	public int getReason() {
		return reason;
	}

	public void setIsCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public boolean getIsCopy() {
		return isCopy;
	}

	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}

	public String getSearchTime() {
		return searchTime;
	}

	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean getIsLast() {
		return isLast;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

//	@Override
//	public Solution getModel() {
//		return solution;
//	}

	public SolutionServiceI getSolutionService() {
		return solutionService;
	}

	@Autowired
	public void setSolutionService(SolutionServiceI solutionService) {
		this.solutionService = solutionService;
	}

	public StudentexamdetailServiceI getStudentexamdetailService() {
		return studentexamdetailService;
	}

	@Autowired
	public void setStudentexamdetailService(StudentexamdetailServiceI studentexamdetailService) {
		this.studentexamdetailService = studentexamdetailService;
	}

	public SubmittedcodeServiceI getSubmittedcodeService() {
		return submittedcodeService;
	}

	@Autowired
	public void setSubmittedcodeService(SubmittedcodeServiceI submittedcodeService) {
		this.submittedcodeService = submittedcodeService;
	}

	public ExamServiceI getExamService() {
		return examService;
	}

	@Autowired
	public void setExamService(ExamServiceI examService) {
		this.examService = examService;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setExamproblemDao(ExamproblemDaoI examproblemDao) {
		this.examproblemDao = examproblemDao;
	}

	public ExamproblemDaoI getExamproblemDao() {
		return examproblemDao;
	}

	@Autowired
	public void setLogService(LogServiceI logService) {
		this.logService = logService;
	}

	public LogServiceI getLogService() {
		return logService;
	}

	public ProblemsServiceI getProblemsService() {
		return problemsService;
	}

	@Autowired
	public void setProblemsService(ProblemsServiceI problemsService) {
		this.problemsService = problemsService;
	}

//	public StudentTrainProbDetailServiceI getStudentTrainProbDetailService() {
//		return studentTrainProbDetailService;
//	}
//
//	@Autowired
//	public void setStudentTrainProbDetailService(StudentTrainProbDetailServiceI studentTrainProbDetailService) {
//		this.studentTrainProbDetailService = studentTrainProbDetailService;
//	}

//	public ItrainProblemCatServiceI getItrainProblemCatService() {
//		return itrainProblemCatService;
//	}
//
//	@Autowired
//	public void setItrainProblemCatService(ItrainProblemCatServiceI itrainProblemCatService) {
//		this.itrainProblemCatService = itrainProblemCatService;
//	}

//	public ItrainproblemDaoI getItrainproblemDao() {
//		return itrainproblemDao;
//	}
//
//	@Autowired
//	public void setItrainproblemDao(ItrainproblemDaoI itrainproblemDao) {
//		this.itrainproblemDao = itrainproblemDao;
//	}

	/*
	 * 先判断LockMap中当前userid是否存在，若不存在则赋值加锁，存在就加上锁否则空的话直接赋值，加锁最后遍历找到userid对应的锁进行释放
	 */
//	public synchronized Lock getUserLock() {
//		if (lockMap.containsKey(solution.getUserid())) {
//			return lockMap.get(solution.getUserid());
//		} else {
//			ReentrantLock lock = new ReentrantLock();
//			lockMap.put(solution.getUserid(), lock);
//			return lockMap.get(solution.getUserid());
//		}
//	}

	// 根据题目ID提交代码
	@PostMapping("/submitCodeById")
	public RespBean submitCodeById(@RequestBody Solution solution, HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		String role = decodeToken.getRole();
		solution.setUserid(Integer.parseInt(userId));
		if (solution.getSourceCode().length() > 64000) {
			return RespBean.error("代码长度不能超过64000个字符");
		}
		reentrantLock.lock();
		try {
			// 设置消耗的时间elapsedTime
			Date now = new Date();

			// 设置提交时间
			solution.setSubmitTime(new Date());
			// 获取考试开始时间和结束时间
			Exam exam = examService.getExamById(solution.getExamId());

			Date startTime = exam.getStarttime();
			Date endTime = exam.getEndtime();
			// 如果考试开始之前提交代码
			if (now.getTime() < startTime.getTime()) {
				return RespBean.error("考试还没开始，无法提交代码！");
			}
			// 如果考试结束之后提交代码
			else if (now.getTime() > endTime.getTime() && role.equals("student")) {
				return RespBean.error("考试已经结束，无法再提交代码！");
			} else {
				// 根据problemID查找Problems
				Problems problem = problemsService.findProblemById(solution.getProblemId());
				// 根据userId+examId+problemId到studentExamDetail表查找
				Studentexamdetail studentexamdetail = studentexamdetailService.getStatusByUserIDexamIDproblemId(
						Integer.parseInt(userId), solution.getExamId(), solution.getProblemId());

				// 提交本题之后再次提交代码
				if (studentexamdetail != null && studentexamdetail.isFinished()) {
					return RespBean.error("已经过提交本题，不能再提交代码！");
				}
				Examproblems examproblems = examproblemDao.getExamproblemsByExamIdAndProblemId(solution.getExamId(),
						solution.getProblemId());
				Date deadline = examproblems.getDeadline();
				Date submitTime = solution.getSubmitTime();
				if (deadline != null && submitTime.after(deadline) && role.equals("student")) {
					return RespBean.error("提交时间已经晚于本题截止时间不能提交代码");
				}
				// 更新Problems表的submit字段，增加1
				if (problem.getSubmit() == null) {
					problem.setSubmit(1);
				} else {
					problem.setSubmit(problem.getSubmit() + 1);
				}
				// 设置status字段为已提交状态
				solution.setStatus(Constant.CODE_WAIT);
				// 设置remark默认值
				solution.setRemark(Constant.DEFAULT_REMARK);
				// 设置正确的测试用例ids字断为默认值－1
				solution.setCorrectCaseIds(Constant.DEFAULT_CORRECTCASEIDS);

				Json json = submittedcodeService.submitCode(problem, solution, studentexamdetail, now, startTime,
						endTime);
				if (json.isSuccess()){
					return RespBean.ok(json.getMsg());
				}else{
					return RespBean.error(json.getMsg());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String sOut = "";
			StackTraceElement[] trace = e.getStackTrace();
			for (StackTraceElement s : trace) {
				sOut += "\tat " + s + "\r\n";
			}
			// 异常信息最大记录19000个字符，数据库该字段最大为20K
			int count = sOut.length() > 19000 ? 19000 : sOut.length();
			sOut = sOut.substring(0, count - 1);
			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e.getLocalizedMessage().length();
			String localMessage = "";
			if (e.getLocalizedMessage() != null) {
				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
			}
			Log log = new Log();
			log.setType("代码提交");
			log.setOptime(new Date());
			log.setUserId(solution.getUserid());
			log.setUserType("student");
			log.setContent(sOut);
			log.setAbstractContent("学生id:" + userId + "考试id:" + solution.getExamId() + "题目id:"
					+ solution.getProblemId() + "\n" + localMessage);
			logService.WriteLog(log,new Integer(userId),role);
			return RespBean.error("服务器内部发生错误，请报告管理员。");
		} finally {
			reentrantLock.unlock();
			// redisService.unLock(key);
			// System.out.println(solution.getUserid() +
			// "解锁了！！！！！！！！！！！！！！！！！！！！！" + Calendar.getInstance().getTime());
		}
	}
	@PostMapping("/getLastSolution")
	public RespBean getLastSolution(@RequestBody PMWrongAndCorrectIds pMWrongAndCorrectIds,
									HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		Solution solution = solutionService
				.getLastSolutionByUserIdExamIdProblemId(
						Integer.parseInt(userId),
						pMWrongAndCorrectIds.getExamId(),
						pMWrongAndCorrectIds.getProblemId());
		if (solution != null) {
			return RespBean.ok("获取之前的代码成功", solution);
		} else {
			return RespBean.error("本题之前没有提交过");
		}
	}

//
//	public void getExamStudentByNowTeacherId() // 查看本场考试中当前登录教师的学生
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMClasses> classsList = classesServiceI.findClassInExam(solution.getExamId());
//			// 如果当前登录者不是管理员，则删除该场考试中不是当前登录教师的班级
//			if (sessionInfo.getTeacherId() != 4) {
//				Iterator<PMClasses> it = classsList.iterator();
//				while (it.hasNext()) {
//					PMClasses nt = it.next();
//					if (nt.getTeacherId() != sessionInfo.getTeacherId()) {
//						it.remove();
//					}
//				}
//			}
//			List<PMUser> pmustudent = new ArrayList<PMUser>();
//			for (int i = 0; i < classsList.size(); i++) {
//				List<PMUser> pmu = classesServiceI.findClassStudentsById(classsList.get(i).getId());
//				for (int k = 0; k < pmu.size(); k++) {
//					Studentexaminfo sei = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(pmu.get(k).getId(),
//							solution.getExamId());
//					if (sei != null) {
//						pmu.get(k).setScore(sei.getScore());
//						pmu.get(k).setFirstLoginTime(sei.getFirstloginTime());
//					}
//				}
//				pmustudent.addAll(pmu);
//
//			}
//			j.setSuccess(true);
//			j.setObj(pmustudent);
//			j.setMsg("获取学生信息成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamStudentByTeacherId() // 查看本场考试本班的学生
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMClasses> classsList = classesServiceI.findClassInExam(solution.getExamId());
//			List<PMUser> pmustudent = new ArrayList<PMUser>();
//			int teacherId = solution.getId();// 将teacherId作为id传入
//			for (int i = 0; i < classsList.size(); i++) {
//				if (classsList.get(i).getTeacherId() == teacherId) {
//					List<PMUser> pmu = classesServiceI.findClassStudentsById(classsList.get(i).getId());
//					for (int k = 0; k < pmu.size(); k++) {
//						Studentexaminfo sei = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(pmu.get(k).getId(),
//								solution.getExamId());
//						if (sei != null) {
//							pmu.get(k).setScore(sei.getScore());
//							pmu.get(k).setFirstLoginTime(sei.getFirstloginTime());
//						}
//					}
//					pmustudent.addAll(pmu);
//				}
//			}
//			j.setSuccess(true);
//			j.setObj(pmustudent);
//			j.setMsg("获取学生信息成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamStudentByexamId() //
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMClasses> classsList = classesServiceI.findClassInExam(solution.getExamId());
//			List<PMUser> pmustudent = new ArrayList<PMUser>();
//			// 如果当前登录者不是管理员 则删除该场考试中不是当前登录教师的班级
//			if (sessionInfo.getTeacherId() != 4) {
//				Iterator<PMClasses> it = classsList.iterator();
//				while (it.hasNext()) {
//					PMClasses nt = it.next();
//					if (nt.getTeacherId() != sessionInfo.getTeacherId()) {
//						it.remove();
//					}
//				}
//			}
//			for (int i = 0; i < classsList.size(); i++) {
//				List<PMUser> pmu = classesServiceI.findClassStudentsById(classsList.get(i).getId());
//				for (int k = 0; k < pmu.size(); k++) {
//					Studentexaminfo sei = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(pmu.get(k).getId(),
//							solution.getExamId());
//					if (sei != null) {
//						pmu.get(k).setScore(sei.getScore());
//						pmu.get(k).setFirstLoginTime(sei.getFirstloginTime());
//					}
//				}
//				pmustudent.addAll(pmu);
//			}
//			j.setSuccess(true);
//			j.setObj(pmustudent);
//			j.setMsg("获取学生信息成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamStudentByexamIdClassId() //
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMClasses> classsList = classesServiceI.findClassInExam(solution.getExamId());
//			// 如果当前登录者不是管理员 则删除该场考试中不是当前登录教师的班级
//			if (sessionInfo.getTeacherId() != 4) {
//				Iterator<PMClasses> it = classsList.iterator();
//				while (it.hasNext()) {
//					PMClasses nt = it.next();
//					if (nt.getTeacherId() != sessionInfo.getTeacherId()) {
//						it.remove();
//					}
//				}
//			}
//			List<PMUser> pmustudent = new ArrayList<PMUser>();
//
//			for (int i = 0; i < classsList.size(); i++) {
//				if (classsList.get(i).getId() == solution.getId()) {
//					List<PMUser> pmu = classesServiceI.findClassStudentsById(classsList.get(i).getId());
//					for (int k = 0; k < pmu.size(); k++) {
//						Studentexaminfo sei = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(pmu.get(k).getId(),
//								solution.getExamId());
//						if (sei != null) {
//							pmu.get(k).setScore(sei.getScore());
//							pmu.get(k).setFirstLoginTime(sei.getFirstloginTime());
//						}
//					}
//					pmustudent.addAll(pmu);
//				}
//			}
//			j.setSuccess(true);
//			j.setObj(pmustudent);
//			j.setMsg("获取学生信息成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamSubmitSolution() // 查看考试哦的提交情况
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			/*
//			 * List<PMSolution> pList =
//			 * solutionService.getExamSubmitSolution(isLast,
//			 * solution.getExamId(), sessionInfo.getTeacherId(),nowPage,
//			 * pageSize, displaySequence, studentNo, name, banji,
//			 * solution.getSimilarity(), searchTime);
//			 */
//
//			List<Map<String, Object>> pList = solutionService.getExamSubmitSolutionInfo(isLast, solution.getExamId(),
//					sessionInfo.getTeacherId(), nowPage, pageSize, displaySequence, studentNo, name, banji,
//					solution.getSimilarity(), searchTime);
//
//			Exam exam = examService.getExamById(solution.getExamId());
//			// pList.get(0).setExamName(exam.getName());
//			if (pList.size() > 0)
//				pList.get(0).put("examName", exam.getName());
//			else {
//				Map<String, Object> temp = new HashMap<String, Object>();
//				temp.put("examName", exam.getName());
//				pList.add(temp);
//			}
//			j.setSuccess(true);
//			j.setObj(pList);
//			j.setMsg("获取提交状况成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getTrainSubmitSolution() // 查看Train的提交情况
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			/*
//			 * List<PMSolution> pList =
//			 * solutionService.getTrainSubmitSolution(isLast,
//			 * solution.getExamId(), sessionInfo.getTeacherId(),nowPage,
//			 * pageSize, studentNo, name, banji, solution.getSimilarity(),
//			 * searchTime);
//			 */
//
//			List<Map<String, Object>> pList = solutionService.getTrainSubmitSolutionInfo(isLast, solution.getExamId(),
//					sessionInfo.getTeacherId(), sessionInfo.getRoleNames(), nowPage, pageSize, studentNo, name, banji,
//					solution.getSimilarity(), searchTime);
//
//			Exam exam = examService.getExamById(solution.getExamId());
//			// pList.get(0).setExamName(exam.getName());
//			if (pList.size() > 0)
//				pList.get(0).put("examName", exam.getName());
//			else {
//				Map<String, Object> temp = new HashMap<String, Object>();
//				temp.put("examName", exam.getName());
//				pList.add(temp);
//			}
//			j.setSuccess(true);
//			j.setObj(pList);
//			j.setMsg("获取提交状况成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void editStudentScore() // 查看考试的提交情况
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int num = studentexamdetailService.editStudentScore(solution);
//			List<Studentexamdetail> stuexamDetailList = studentexamdetailService
//					.getAllStudentexamdetailListByUserIdAndExamId(solution.getUserid(), solution.getExamId());
//			float stuAllScore = 0;
//			for (int i = 0; i < stuexamDetailList.size(); i++) {
//				stuAllScore += stuexamDetailList.get(i).getScore();
//			}
//			int num2 = studentexaminfoDao.updateStudentScore(solution.getUserid(), solution.getExamId(), stuAllScore);
//			if (num == 1 && num2 == 1) {
//				j.setSuccess(true);
//				j.setObj(stuAllScore);
//				j.setMsg("修改成绩成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setObj(-1);
//				j.setMsg("修改成绩失败");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void editStudentTrainScore() // 修改智能训练的单个类别的成绩
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int num = studentTrainInfoDaoI.editStudentTrainScore(solution);
//			List<StudentTrainCatDetail> studentTrainInfoList = studentTrainInfoDaoI
//					.getStudentTrainInfoByUserIdAnExamId(solution.getUserid(), solution.getExamId());
//
//			float stuAllScore = 0;
//			for (int i = 0; i < studentTrainInfoList.size(); i++) {
//				stuAllScore += studentTrainInfoList.get(i).getScore();
//			}
//			int num2 = studentexaminfoDao.updateStudentScore(solution.getUserid(), solution.getExamId(), stuAllScore);
//			if (num == 1 && num2 == 1) {
//				j.setSuccess(true);
//				j.setObj(stuAllScore);
//				j.setMsg("修改成绩成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setObj(-1);
//				j.setMsg("修改成绩失败");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void editStudentDetailFinished() {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			boolean isOverSimilarity = false;
//			Studentexamdetail stuexamd = studentexamdetailDao.get(Studentexamdetail.class, solution.getId());
//			// Studentexamdetail为空说明还没有裁判过，则提示用户
//
//			PMWrongAndCorrectIds pMWrongAndCorrectIds = new PMWrongAndCorrectIds();
//			pMWrongAndCorrectIds.setUserId(stuexamd.getUserId());
//			pMWrongAndCorrectIds.setExamId(stuexamd.getExamId());
//			pMWrongAndCorrectIds.setProblemId(stuexamd.getProblemId());
//			pMWrongAndCorrectIds.setSubmitType(solution.getStatus());
//			Json json = solutionService.submitThisProblem(stuexamd, pMWrongAndCorrectIds, j, isOverSimilarity);
//			j.setSuccess(json.isSuccess());
//			j.setMsg(json.getMsg());
//			super.writeJson(j);
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void editStudentexaminfoScore() {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int num = studentexaminfoDao.updateStudentScore(solution.getUserid(), solution.getExamId(),
//					solution.getScore());
//			if (num == 1) {
//				j.setSuccess(true);
//				j.setMsg("修改学生总分成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setObj(-1);
//				j.setMsg("修改学生总分失败");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamSubmitSolutionScore() // 查看考试得分数
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMSolution> newList = new ArrayList<PMSolution>();
//
//			Studentexaminfo stuexamInfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(solution.getUserid(),
//					solution.getExamId());
//			if (stuexamInfo != null) {
//
//				List<Examproblems> pList = examproblemService.getProblemByExamId(solution.getExamId());// 获取到了problemId和总分还有题号
//				for (int i = 0; i < pList.size(); i++) {
//					Problems pro = problemsServiceI.findProblemById(pList.get(i).getProblemId());
//					Solution pms = solutionService.getLastSolutionByUserIdExamIdProblemId(solution.getUserid(),
//							solution.getExamId(), pList.get(i).getProblemId());
//					Studentexamdetail stuexamdetail = studentexamdetailService.getStatusByUserIDexamIDproblemId(
//							solution.getUserid(), solution.getExamId(), pList.get(i).getProblemId());
//
//					PMSolution p = new PMSolution();
//					p.setDisplaySequence(pList.get(i).getDisplaySequence());
//					p.setScoreTotal(pList.get(i).getScore());
//					p.setTitle(pro.getTitle());
//					p.setProblemId(pList.get(i).getProblemId());
//					p.setExamId(solution.getExamId());
//
//					p.setStudentAllScore(stuexamInfo.getScore());
//
//					if (pms != null) {
//						p.setScore(stuexamdetail.getScore());
//						p.setId(stuexamdetail.getId());
//						p.setSubmited(stuexamdetail.isFinished());
//					} else {
//						p.setScore(0);
//						p.setId(0);
//						p.setSubmited(false);
//						p.setFlag(true);
//					}
//					newList.add(p);
//				}
//
//				j.setSuccess(true);
//				j.setObj(newList);
//				j.setMsg("获取学生分数成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("查询无记录");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getTrainSubmitSolutionScore() // 查看Train得分数
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMSolution> newList = new ArrayList<PMSolution>();
//			List<StudentTrainCatDetail> studentTrainInfoList = studentTrainInfoDaoI
//					.getStudentTrainInfoByUserIdAnExamId(solution.getUserid(), solution.getExamId());
//			if (studentTrainInfoList != null) {
//				List<ItrainProbCatgory> pList = itrainProblemCatDao.getCatagoryListByExamId(solution.getExamId());
//				// 该examId下的每个类别
//				for (int i = 0; i < pList.size(); i++) {
//					ProblemCategory pcat = problemCategoryDao.findProblemCategoryById(pList.get(i).getCatId());
//					String titlleName = pcat.getName();
//					if (pcat.getParentId() != 0) {
//						ProblemCategory pcatParent = problemCategoryDao.findProblemCategoryById(pcat.getParentId());
//						titlleName = pcatParent.getName() + "—>" + titlleName;
//					}
//
//					PMSolution p = new PMSolution();
//					// 这里存入的是分类的id
//					p.setDisplaySequence(pList.get(i).getCatId());
//					// 分类的名字
//					p.setTitle(titlleName);
//					p.setProCatscore(pList.get(i).getScore());
//					// 默认为每个类别的成绩设置为0 如果该学生做了此分类，则下面覆盖成绩
//					p.setScore(0);
//					// 默认没有完成 如果该学生做了此分类，则下面进行覆盖
//					p.setSubmited(false);
//					// 查找studentTrainInfoList是否存在该分类信息 重新设置成绩
//					for (int k = 0; k < studentTrainInfoList.size(); k++) {
//						if (pList.get(i).getCatId().equals(studentTrainInfoList.get(k).getCatId())) {
//							p.setScore(studentTrainInfoList.get(k).getScore());
//							p.setSubmited(studentTrainInfoList.get(k).isFinished());
//
//						}
//					}
//					newList.add(p);
//				}
//				j.setSuccess(true);
//				j.setObj(newList);
//				j.setMsg("获取学生分数成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("查询无记录");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamUserName() // 查看考试得分数
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			PMSolution pms = new PMSolution();
//			Users users = userDao.get(Users.class, solution.getUserid());
//			Exam exam = examService.getExamById(solution.getExamId());
//			if (users != null && exam != null) {
//				pms.setChineseName(users.getChineseName());
//				pms.setExamName(exam.getName());
//				j.setSuccess(true);
//				j.setObj(pms);
//				j.setMsg("获取学生考试名称成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("考试信息或者学生信息不存在");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getExamSubmitSolutionCount() // 查看一场考试的solution总数
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			j.setSuccess(true);
//			long size = solutionService.getExamSubmitSolutionCount(isLast, solution.getExamId(),
//					sessionInfo.getTeacherId(), displaySequence, studentNo, name, banji, solution.getSimilarity(),
//					searchTime);
//			j.setObj(size);
//			j.setMsg("获取考试提交总数成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getTrainSubmitSolutionCount() // 查看一场考试的solution总数
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			j.setSuccess(true);
//			long size = solutionService.getTrainSubmitSolutionCount(isLast, solution.getExamId(),
//					sessionInfo.getTeacherId(), sessionInfo.getRoleNames(), studentNo, name, banji,
//					solution.getSimilarity(), searchTime);
//			j.setObj(size);
//			j.setMsg("获取考试提交总数成功");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getSourceCoude() {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int id = solution.getId();
//			Solution s = solutionService.getSourceCode(id);
//			if (s != null) {
//				j.setSuccess(true);
//				j.setObj(s.getSourceCode());
//				j.setMsg("获取源代码成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("获取源代码成功");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//
//	}
//
//	public String exportExamSearchSolution() {
//		try {
//			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
//			displaySequence = new String(displaySequence.getBytes("ISO-8859-1"), "UTF-8");
//			banji = new String(banji.getBytes("ISO-8859-1"), "UTF-8");
//			studentNo = new String(studentNo.getBytes("ISO-8859-1"), "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMSolution> pList = solutionService.exportExamSearchSolution(isLast, solution.getExamId(),
//					displaySequence, studentNo, name, banji, solution.getSimilarity(), searchTime);
//			HSSFWorkbook wb = new HSSFWorkbook(); // 导出到excel文件中
//			HSSFSheet sheet = wb.createSheet("表一");
//			HSSFRow row = sheet.createRow(0);
//			HSSFCellStyle style = wb.createCellStyle();
//			style.setAlignment(CellStyle.ALIGN_CENTER);
//			HSSFCell cell = row.createCell((short) 0);
//			cell.setCellValue("序号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 1);
//			cell.setCellValue("提交时间");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 2);
//			cell.setCellValue("学号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 3);
//			cell.setCellValue("姓名");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 4);
//			cell.setCellValue("班级");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 5);
//			cell.setCellValue("题号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 6);
//			cell.setCellValue("标题");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 7);
//			cell.setCellValue("状态");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 8);
//			cell.setCellValue("分数");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 9);
//			cell.setCellValue("相似度");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 10);
//			cell.setCellValue("相似度阈");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 11);
//			cell.setCellValue("语言");
//			cell.setCellStyle(style);
//			for (int i = 0; i < pList.size(); i++) {
//				row = sheet.createRow(i + 1);
//				PMSolution s = pList.get(i);
//				row.createCell((short) 0).setCellValue((i + 1));
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String date = dateFormat.format(s.getSubmitTime());
//				row.createCell((short) 1).setCellValue(date);
//				row.createCell((short) 2).setCellValue(s.getStudentNo());
//				row.createCell((short) 3).setCellValue(s.getChineseName());
//				row.createCell((short) 4).setCellValue(s.getBanji());
//				row.createCell((short) 5).setCellValue(s.getDisplaySequence());
//				row.createCell((short) 6).setCellValue(s.getTitle());
//				row.createCell((short) 7).setCellValue(s.getStatus());
//				row.createCell((short) 8).setCellValue(s.getScore());
//				if (s.getSimilarity() != -1)
//					row.createCell((short) 9).setCellValue(s.getSimilarity());
//				if (s.getSimilarityThreshold() != -1)
//					row.createCell((short) 10).setCellValue(s.getSimilarityThreshold());
//				row.createCell((short) 11).setCellValue(s.getLanguage());
//			}
//			try {
//				String userId = String.valueOf(sessionInfo.getTeacherId());
//				File dir = new File("C:\\OJtemp\\" + userId + "\\");
//				if (!dir.exists() && !dir.isDirectory()) {
//					dir.mkdirs(); // 创建用户目录
//				}
//				fileName = "exportExamSearchSolution.xls";
//				FileOutputStream fout = new FileOutputStream("C:\\OJtemp\\" + userId + "\\" + fileName);
//				wb.write(fout);
//				fout.close();
//				File file = new File("C:\\OJtemp\\" + userId + "\\" + fileName);
//				fileInput = new FileInputStream(file);
//				return "exportExamSearchSolution";
//			} catch (Exception e) {
//
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//		try {
//			fileInput.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//
//		}
//		return null;
//	}
//
//	public void exportExamCode() throws RuntimeException, Exception {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			// 组合教师id_时间戳分割多教师文件夹
//			String roleName = sessionInfo.getRoleNames();
//			String tearcherId = String.valueOf(sessionInfo.getTeacherId());
//			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//			String dateNow = df.format(Calendar.getInstance().getTime());
//			String tearcherFileId = tearcherId + "_" + dateNow;
//
//			List<Solution> solutionList = new ArrayList<Solution>();
//			List<ExamStudent> eaxmStudentList = new ArrayList<ExamStudent>();
//			String codeType = "exportExamCode";// TODO用来标记文件夹，本次导出的类型
//			int classId = Integer.parseInt(banji);
//			int examId = solution.getExamId();
//			if (classId != 0) {
//				// codeType="exportExamLastCode";
//				if (isLast) {
//					solutionList = solutionService.exportClassExamLastCode(examId, classId);
//				} else {
//					// 获取考试所有班级的考试数据
//					solutionList = solutionService.exportClassExamCode(examId, classId);
//				}
//			} else {
//				if (isLast) {
//					//如果是教师，则导出教师自己的班级
//					if(roleName.equals("teacher")){
//						solutionList = solutionService.exportExamLastCode(examId,sessionInfo.getTeacherId());
//					}else{// 导出所有班级的代码
//						solutionList = solutionService.exportExamLastCode(examId);
//					}
//				} else {
//					if(roleName.equals("teacher")){ //如果是教师，则导出教师自己的班级
//						solutionList = solutionService.exportExamCode(examId,sessionInfo.getTeacherId());
//					}else{
//						solutionList = solutionService.exportExamCode(examId);
//					}
//				}
//			}
//			eaxmStudentList = solutionService.getExamStudentInfo(examId, classId);
//			// 根据userId建立学生信息字典
//			HashMap<Integer, ExamStudent> ExamStudentsMap = new HashMap<Integer, ExamStudent>();
//			for (ExamStudent examstudent : eaxmStudentList) {
//				ExamStudentsMap.put(examstudent.getId(), examstudent);
//			}
//			// 获取这场考试中所有题目对应的DisplaySequence（题目在考试中的编号）
//			List<Examproblems> examproblemList = examproblemDao.getDisplaySequenceByExamId(examId);
//			HashMap<Integer, Integer> examproblemMap = new HashMap<Integer, Integer>();
//			for (int i = 0; i < examproblemList.size(); i++) {
//				examproblemMap.put(examproblemList.get(i).getProblemId(), examproblemList.get(i).getDisplaySequence());
//			}
//			int displaySequence;
//			int userId;
//			String dirPath = null;// 文件路径
//			ExamStudent examStudent;
//			// 新建hashmap
//			HashMap<String, HashMap<String, HashMap<String, String>>> classNameMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
//			for (Solution solution : solutionList) {
//				userId = solution.getUserid();
//				examStudent = ExamStudentsMap.get(userId);
//				if (examStudent != null) {
//					String className = examStudent.getClassName();
//					String studentStuAndName = examStudent.getStudentNo() + "_" + examStudent.getStudentName();
//
//					// 文件路径
//					dirPath = tearcherFileId + "\\" + codeType + "\\" + className + "\\" + studentStuAndName;
//					// 替代非法字符\ / : * ? " < > |
//					dirPath = dirPath.replace("/", "_").replace(":", "_").replace("*", "_").replace("?", "_")
//							.replace("\"", "_").replace("<", "_").replace(">", "_").replace("|", "_").replace(" ", "_");
//					dirPath = root_ZIP + dirPath;
//					// 文件
//					if (examproblemMap.containsKey(solution.getProblemId())) {
//						displaySequence = examproblemMap.get(solution.getProblemId());
//						String submitTime = df.format(solution.getSubmitTime());
//						String fileString = null;
//
//						if (solution.getLanguage().equals("C++")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + submitTime + "_"
//									+ solution.getStatus() + ".cpp";
//						} else if (solution.getLanguage().equals("C")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + submitTime + "_"
//									+ solution.getStatus() + ".c";
//						} else if (solution.getLanguage().equals("Java")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + submitTime + "_"
//									+ solution.getStatus() + ".java";
//						} else if (solution.getLanguage().equals("Python")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + submitTime + "_"
//									+ solution.getStatus() + ".py";
//						}
//						// 如果classNameMap中包含有该班级的名字
//						if (classNameMap.containsKey(className)) {
//							// 如果该班级中包含该学生的userId
//							if (classNameMap.get(className).containsKey(studentStuAndName)) {
//								// 将该学生的题目提交情况路径存入该学生的提交情况Map中
//								classNameMap.get(className).get(studentStuAndName).put(fileString,
//										solution.getSourceCode());
//							} else { // 创建该学生的Map
//								classNameMap.get(className).put(studentStuAndName, new HashMap<String, String>());
//								classNameMap.get(className).get(studentStuAndName).put(fileString,
//										solution.getSourceCode());
//							}
//						} else {
//							// 创建该班级的Map
//							classNameMap.put(className, new HashMap<String, HashMap<String, String>>());
//							classNameMap.get(className).put(studentStuAndName, new HashMap<String, String>());
//							classNameMap.get(className).get(studentStuAndName).put(fileString,
//									solution.getSourceCode());
//						}
//					}
//				}
//			}
//			for (Map.Entry<String, HashMap<String, HashMap<String, String>>> classentry : classNameMap.entrySet()) {
//				// 创建该班级的文件夹
//				String classPath = tearcherFileId + "\\" + codeType + "\\" + classentry.getKey();
//				// // 替代非法字符\ / : * ? " < > |
//				classPath = classPath.replace("/", "_").replace(":", "_").replace("*", "_").replace("?", "_")
//						.replace("\"", "_").replace("<", "_").replace(">", "_").replace("|", "_").replace(" ", "_");
//				classPath = root_ZIP + classPath;
//				File file_class = new File(classPath);
//				if (!file_class.exists()) {
//					file_class.mkdirs();
//				}
//				for (Map.Entry<String, HashMap<String, String>> userentry : classentry.getValue().entrySet()) {
//					String userPath = userentry.getKey();
//					userPath = userPath.replace("/", "_").replace(":", "_").replace("*", "_").replace("?", "_")
//							.replace("\"", "_").replace("<", "_").replace(">", "_").replace("|", "_").replace(" ", "_");
//					userPath = classPath + "\\" + userPath;
//					File file_user = new File(userPath);
//					if (!file_user.exists()) {
//						file_user.mkdirs();
//					}
//					for (Map.Entry<String, String> solutionentry : userentry.getValue().entrySet()) {
//						try {
//							File file_solution = new File(solutionentry.getKey());
//							FileOutputStream out = new FileOutputStream(file_solution);
//							out.write(solutionentry.getValue().getBytes());
//							out.close();
//						} catch (Exception e) {
//							e.printStackTrace();
//							j.setSuccess(false);
//							j.setMsg("系统内部发生错误,请联系系统管理员");
//							super.writeJson(j);
//						}
//
//					}
//				}
//
//			}
//			// 文件整体压缩
//			String sourceFolder = root_ZIP + tearcherFileId + "\\" + codeType + "\\";
//			String zipFilePath = root_ZIP + tearcherFileId + "\\" + codeType + ".7z";
//			// CHZipUtils.zip(sourceFolder, zipFilePath);
//			try {
//				System.out.println("开始压缩文件-----------");
//				long startTime = System.currentTimeMillis();
//				FileOutputStream fos = new FileOutputStream(new File(zipFilePath));
//				ZipUtilsZZB2.toZip(new File(sourceFolder), fos,true);
////				ZipUtilZZB.Compress7z(sourceFolder, zipFilePath);
//				long endTime= System.currentTimeMillis();
//				System.out.println("压缩文件结束-----------共耗时"+(endTime-startTime)/1000+"秒");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			j.setSuccess(true);
//			j.setMsg(tearcherFileId);
//			super.writeJson(j);
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void exportTrainCode() throws RuntimeException, Exception {
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			// 组合教师id_时间戳分割多教师文件夹
//			String roleName = sessionInfo.getRoleNames();
//			String tearcherId = String.valueOf(sessionInfo.getTeacherId());
//			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//			String dateNow = df.format(Calendar.getInstance().getTime());
//			String tearcherFileId = tearcherId + "_" + dateNow;
//
//			List<Solution> solutionList = new ArrayList<Solution>();
//			List<ExamStudent> eaxmStudentList = new ArrayList<ExamStudent>();
//			String codeType = "exportExamCode";// TODO用来标记文件夹，本次导出的类型
//			int classId = Integer.parseInt(banji);
//			int examId = solution.getExamId();
//			if (classId != 0) {
//				// codeType="exportExamLastCode";
//				if (isLast) {
//					solutionList = solutionService.exportClassTrainLastCode(examId, classId);
//				} else {
//					// 获取考试所有班级的考试数据
//					solutionList = solutionService.exportClassTrainCode(examId, classId);
//				}
//			} else {
//				if (isLast) {
//					//如果是教师，则导出教师自己的班级
//					if(roleName.equals("teacher")){
//						solutionList = solutionService.exportTrainLastCode(examId,sessionInfo.getTeacherId());
//					}else{
//						solutionList = solutionService.exportTrainLastCode(examId);
//					}
//				} else {
//					if(roleName.equals("teacher")){ //如果是教师，则导出教师自己的班级
//						solutionList = solutionService.exportTrainCode(examId,sessionInfo.getTeacherId());
//					}else{
//						solutionList = solutionService.exportTrainCode(examId);
//					}
//
//				}
//			}
//			eaxmStudentList = solutionService.getExamStudentInfo(examId, classId);
//			// 根据userId建立学生信息字典
//			HashMap<Integer, ExamStudent> ExamStudentsMap = new HashMap<Integer, ExamStudent>();
//			for (ExamStudent examstudent : eaxmStudentList) {
//				ExamStudentsMap.put(examstudent.getId(), examstudent);
//			}
//
//			// 获取这场训练中所有题目对应的DisplaySequence（题目在考试中的编号）
//			// List<Examproblems> examproblemList =
//			// examproblemDao.getDisplaySequenceByExamId(examId);
//
//			List<Itrainproblems> trainproblemList = itrainproblemDao.getProblemsByExamId(examId);
//
//			HashMap<Integer, Integer> examproblemMap = new HashMap<Integer, Integer>();
//			for (int i = 0; i < trainproblemList.size(); i++) {
//				examproblemMap.put(trainproblemList.get(i).getProblemId(), trainproblemList.get(i).getCatId());
//			}
//			// 分类号
//			int displaySequence;
//
//			int userId;
//
//			String dirPath = null;// 文件路径
//			ExamStudent examStudent;
//			int i = 0;
//			// 新建hashmap
//			HashMap<String, HashMap<String, HashMap<String, String>>> classNameMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
//
//			for (Solution solution : solutionList) {
//				userId = solution.getUserid();
//				examStudent = ExamStudentsMap.get(userId);
//				if (examStudent != null) {
//					String className = examStudent.getClassName();
//					String studentStuAndName = examStudent.getStudentNo() + "_" + examStudent.getStudentName();
//
//					// 文件路径
//					dirPath = tearcherFileId + "\\" + codeType + "\\" + className + "\\" + studentStuAndName;
//					// 替代非法字符\ / : * ? " < > |
//					dirPath = dirPath.replace("/", "_").replace(":", "_").replace("*", "_").replace("?", "_")
//							.replace("\"", "_").replace("<", "_").replace(">", "_").replace("|", "_").replace(" ", "_");
//					dirPath = root_ZIP + dirPath;
//					// 判断是否存在
//					if (examproblemMap.containsKey(solution.getProblemId())) {
//						displaySequence = examproblemMap.get(solution.getProblemId());
//						String submitTime = df.format(solution.getSubmitTime());
//						String fileString = null;
//
//						if (solution.getLanguage().equals("C++")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + solution.getProblemId() + "_"
//									+ submitTime + "_" + solution.getStatus() + ".cpp";
//						} else if (solution.getLanguage().equals("C")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + solution.getProblemId() + "_"
//									+ submitTime + "_" + solution.getStatus() + ".c";
//						} else if (solution.getLanguage().equals("Java")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + solution.getProblemId() + "_"
//									+ submitTime + "_" + solution.getStatus() + ".java";
//						} else if (solution.getLanguage().equals("Python")) {
//							fileString = dirPath + "\\" + displaySequence + "_" + solution.getProblemId() + "_"
//									+ submitTime + "_" + solution.getStatus() + ".py";
//						}
//						// 如果classNameMap中包含有该班级的名字
//						if (classNameMap.containsKey(className)) {
//							// 如果该班级中包含该学生的userId
//							if (classNameMap.get(className).containsKey(studentStuAndName)) {
//								// 将该学生的题目提交情况路径存入该学生的提交情况Map中
//								classNameMap.get(className).get(studentStuAndName).put(fileString,
//										solution.getSourceCode());
//							} else { // 创建该学生的Map
//								classNameMap.get(className).put(studentStuAndName, new HashMap<String, String>());
//								classNameMap.get(className).get(studentStuAndName).put(fileString,
//										solution.getSourceCode());
//							}
//						} else {
//							// 创建该班级的Map
//							classNameMap.put(className, new HashMap<String, HashMap<String, String>>());
//							classNameMap.get(className).put(studentStuAndName, new HashMap<String, String>());
//							classNameMap.get(className).get(studentStuAndName).put(fileString,
//									solution.getSourceCode());
//						}
//					}
//				}
//
//			}
//
//			for (Map.Entry<String, HashMap<String, HashMap<String, String>>> classentry : classNameMap.entrySet()) {
//				// 创建该班级的文件夹
//				String classPath = tearcherFileId + "\\" + codeType + "\\" + classentry.getKey();
//				// // 替代非法字符\ / : * ? " < > |
//				classPath = classPath.replace("/", "_").replace(":", "_").replace("*", "_").replace("?", "_")
//						.replace("\"", "_").replace("<", "_").replace(">", "_").replace("|", "_").replace(" ", "_");
//				classPath = root_ZIP + classPath;
//				File file_class = new File(classPath);
//				if (!file_class.exists()) {
//					file_class.mkdirs();
//				}
//				for (Map.Entry<String, HashMap<String, String>> userentry : classentry.getValue().entrySet()) {
//					String userPath = userentry.getKey();
//					userPath = userPath.replace("/", "_").replace(":", "_").replace("*", "_").replace("?", "_")
//							.replace("\"", "_").replace("<", "_").replace(">", "_").replace("|", "_").replace(" ", "_");
//					userPath = classPath + "\\" + userPath;
//					File file_user = new File(userPath);
//					if (!file_user.exists()) {
//						file_user.mkdirs();
//					}
//					for (Map.Entry<String, String> solutionentry : userentry.getValue().entrySet()) {
//						try {
//							File file_solution = new File(solutionentry.getKey());
//							FileOutputStream out = new FileOutputStream(file_solution);
//							out.write(solutionentry.getValue().getBytes());
//							out.close();
//						} catch (Exception e) {
//							e.printStackTrace();
//							j.setSuccess(false);
//							j.setMsg("系统内部发生错误,请联系系统管理员");
//							super.writeJson(j);
//						}
//
//					}
//				}
//
//			}
//
//			// 文件整体压缩
//			String sourceFolder = root_ZIP + tearcherFileId + "\\" + codeType + "\\";
//			String zipFilePath = root_ZIP + tearcherFileId + "\\" + codeType + ".7z";
//
//			// CHZipUtils.zip(sourceFolder, zipFilePath);
//
//			try {
//				FileOutputStream fos = new FileOutputStream(new File(zipFilePath));
//				ZipUtilsZZB2.toZip(new File(sourceFolder), fos,true);
////				ZipUtilZZB.Compress7z(sourceFolder, zipFilePath);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			j.setSuccess(true);
//			j.setMsg(tearcherFileId);
//			super.writeJson(j);
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getCopy() // 获取抄袭的搜索结果
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int examId = solution.getExamId();
//			/*
//			 * List<PMSolution> obj = solutionService.getCopyList(examId,
//			 * sessionInfo.getTeacherId(),displaySequence, studentNo, name,
//			 * banji, solution.getSimilarity(), searchTime);
//			 */
//
//			List<Map<String, Object>> obj = solutionService.getCopyListInfo(examId, sessionInfo.getTeacherId(),
//					displaySequence, studentNo, name, banji, solution.getSimilarity(), searchTime);
//
//			if (obj != null) {
//				j.setSuccess(true);
//				j.setObj(obj);
//				j.setMsg("获取抄袭情况成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("获取抄袭情况成功");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//
//	}
//
//	public void getTrainCopy() // 获取抄袭的搜索结果
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int examId = solution.getExamId();
//			/*
//			 * List<PMSolution> obj = solutionService.getTrainCopyList(examId,
//			 * sessionInfo.getTeacherId(),studentNo, name, banji,
//			 * solution.getSimilarity(), searchTime);
//			 */
//
//			List<Map<String, Object>> obj = solutionService.getTrainCopyListInfo(examId, sessionInfo.getTeacherId(),
//					sessionInfo.getRoleNames(), studentNo, name, banji, solution.getSimilarity(), searchTime);
//
//			if (obj != null) {
//				j.setSuccess(true);
//				j.setObj(obj);
//				j.setMsg("获取抄袭情况成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("获取抄袭情况成功");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//
//	}
//
//	public String exportSearchCopy() // 导出查看抄袭情况的搜索结果
//	{
//		try {
//			name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
//			displaySequence = new String(displaySequence.getBytes("ISO-8859-1"), "UTF-8");
//			banji = new String(banji.getBytes("ISO-8859-1"), "UTF-8");
//			studentNo = new String(studentNo.getBytes("ISO-8859-1"), "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMSolution> pList = solutionService.getCopyList(solution.getExamId(), sessionInfo.getTeacherId(),
//					displaySequence, studentNo, name, banji, solution.getSimilarity(), searchTime);
//			HSSFWorkbook wb = new HSSFWorkbook(); // 导出到excel文件中
//			HSSFSheet sheet = wb.createSheet("表一");
//			HSSFRow row = sheet.createRow(0);
//			HSSFCellStyle style = wb.createCellStyle();
//			style.setAlignment(CellStyle.ALIGN_CENTER);
//			HSSFCell cell = row.createCell((short) 0);
//			cell.setCellValue("序号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 1);
//			cell.setCellValue("提交时间");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 2);
//			cell.setCellValue("题号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 3);
//			cell.setCellValue("标题");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 4);
//			cell.setCellValue("学号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 5);
//			cell.setCellValue("姓名");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 6);
//			cell.setCellValue("班级");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 7);
//			cell.setCellValue("对象学号");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 8);
//			cell.setCellValue("对象姓名");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 9);
//			cell.setCellValue("相似度");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 10);
//			cell.setCellValue("相似度阈");
//			cell.setCellStyle(style);
//			cell = row.createCell((short) 11);
//			cell.setCellValue("是否提交");
//			cell.setCellStyle(style);
//			for (int i = 0; i < pList.size(); i++) {
//				row = sheet.createRow(i + 1);
//				PMSolution s = pList.get(i);
//				row.createCell((short) 0).setCellValue((i + 1));
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				String date = dateFormat.format(s.getSubmitTime());
//				row.createCell((short) 1).setCellValue(date);
//				row.createCell((short) 2).setCellValue(s.getDisplaySequence());
//				row.createCell((short) 3).setCellValue(s.getTitle());
//				row.createCell((short) 4).setCellValue(s.getStudentNo());
//				row.createCell((short) 5).setCellValue(s.getChineseName());
//				row.createCell((short) 6).setCellValue(s.getBanji());
//				row.createCell((short) 7).setCellValue(s.getStudentNo2());
//				row.createCell((short) 8).setCellValue(s.getChineseName2());
//				if (s.getSimilarity() != -1)
//					row.createCell((short) 9).setCellValue(s.getSimilarity());
//				if (s.getSimilarityThreshold() != -1)
//					row.createCell((short) 10).setCellValue(s.getSimilarityThreshold());
//				if (s.getSubmited() == false)
//					row.createCell((short) 11).setCellValue("");
//				else
//					row.createCell((short) 11).setCellValue("是");
//				if (s.getEversubmit() != null && s.getEversubmit() == 1)
//					row.createCell((short) 11).setCellValue("曾经");
//			}
//			try {
//				String userId = String.valueOf(sessionInfo.getTeacherId());
//				File dir = new File("C:\\OJtemp\\" + userId + "\\");
//				if (!dir.exists() && !dir.isDirectory()) {
//					dir.mkdirs(); // 创建用户目录
//				}
//				fileName = "exportSearchCopy.xls";
//				FileOutputStream fout = new FileOutputStream("C:\\OJtemp\\" + userId + "\\" + fileName);
//				wb.write(fout);
//				fout.close();
//				File file = new File("C:\\OJtemp\\" + userId + "\\" + fileName);
//				fileInput = new FileInputStream(file);
//				return "exportSearchCopy";
//			} catch (Exception e) {
//
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//		try {
//			fileInput.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//
//		}
//		return null;
//	}
//
//	// 对外暴露接口
//	public void deleteTearchFile() {
//		delFolder(root_ZIP + targeName);
//	}
//
//	public void deleteSubmit() // 撤销提交
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			boolean result = solutionService.deleteSubmitBySolutionId(solution.getId(), reason, isCopy,
//					solution.getRemark());
//			if (result == true) {
//				j.setSuccess(true);
//				j.setMsg("撤销成功!");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(true);
//				j.setMsg("撤销失败!");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void deleteTrainSubmit() // 撤销Train提交
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			boolean result = solutionService.deleteTrainSubmitBySolutionId(solution.getId(), reason, isCopy,
//					solution.getRemark());
//			if (result == true) {
//				j.setSuccess(true);
//				j.setMsg("撤销成功!");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(true);
//				j.setMsg("撤销失败!");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getSolutionDetail() // 获取提交的详细信息
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMSolution> list = new ArrayList<PMSolution>();
//			Solution s1 = solutionService.getSolutionById(solution.getId());
//			PMSolution p1 = new PMSolution();
//			p1.setSourceCode(s1.getSourceCode());
//			p1.setSimilarity(s1.getSimilarity());
//			int userId = s1.getUserid();
//			Users user = userDao.get(Users.class, userId);
//			if (user != null) {
//				p1.setBanji(user.getBanji());
//				p1.setChineseName(user.getChineseName());
//				p1.setStudentNo(user.getStudentNo());
//			}
//			Solution s2 = solutionService.getSolutionById(solution.getSimilarId());
//			PMSolution p2 = new PMSolution();
//			p2.setSourceCode(s2.getSourceCode());
//			p2.setSimilarity(s2.getSimilarity());
//			userId = s2.getUserid();
//			user = userDao.get(Users.class, userId);
//			if (user != null) {
//				p2.setBanji(user.getBanji());
//				p2.setChineseName(user.getChineseName());
//				p2.setStudentNo(user.getStudentNo());
//			}
//			list.add(p1);
//			list.add(p2);
//			j.setSuccess(true);
//			j.setObj(list);
//			j.setMsg("获取提交相似度信息成功!");
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void isSubmited() // 查看solution在similarityWarning中是否为submited
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			boolean result = solutionService.isSubmited(solution.getId());
//			j.setSuccess(true);
//			j.setObj(result);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void isTrainSubmited() // 查看solution在similarityWarning中是否为submited
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			boolean result = solutionService.isTrainSubmited(solution.getId());
//			j.setSuccess(true);
//			j.setObj(result);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	public void getSolutionById() // 通过id获取solution
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			Solution result = solutionService.getSolutionById(solution.getId().intValue());
//			j.setSuccess(true);
//			j.setObj(result);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录!");
//			super.writeJson(j);
//		}
//	}
//
//	// 删除文件夹
//	public static void delFolder(String folderPath) {
//		try {
//			delAllFile(folderPath); // 删除完里面所有内容
//			String filePath = folderPath;
//			filePath = filePath.toString();
//			File myFilePath = new File(filePath);
//			myFilePath.delete(); // 删除空文件夹
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	// 删除指定文件夹下所有文件
//	public static boolean delAllFile(String path) {
//		boolean flag = false;
//		File file = new File(path);
//		if (!file.exists()) {
//			return flag;
//		}
//		if (!file.isDirectory()) {
//			return flag;
//		}
//		String[] tempList = file.list();
//		File temp = null;
//		for (int i = 0; i < tempList.length; i++) {
//			if (path.endsWith(File.separator)) {
//				temp = new File(path + tempList[i]);
//			} else {
//				temp = new File(path + File.separator + tempList[i]);
//			}
//			if (temp.isFile()) {
//				temp.delete();
//			}
//			if (temp.isDirectory()) {
//				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
//				flag = true;
//			}
//		}
//		return flag;
//	}
//
//	public void downloadZipFile() {
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("application/7z");
//		String fullFileName = root_ZIP + url + "\\exportExamCode.7z";
//		InputStream in = null;
//		OutputStream out = null;
//		try {
//			String filename = new String(root_ZIP.getBytes("UTF-8"), "ISO-8859-1");
//			in = new FileInputStream(fullFileName);
//			response.setHeader("Content-Disposition", "attachment;filename=exportExamCode.7z");
//			response.setContentLength(in.available());
//			out = response.getOutputStream();
//			int b;
//			while ((b = in.read()) != -1) {
//				out.write(b);
//			}
//
//			in.close();
//			out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (in != null)
//				try {
//					in.close();
//				} catch (Exception e2) {
//				}
//			if (out != null)
//				try {
//					out.close();
//				} catch (Exception e3) {
//				}
//		} finally {
//			delFolder(root_ZIP + url);
//		}
//
//	}
//
//	// 智能训练模块根据题目id提交代码
//	public void itrainSubmitCodeById() {
//		Json j = new Json();
//		// 如果session断掉了
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		if (sessionInfo == null) {
//			j.setSuccess(false);
//			j.setMsg("必须先登录才能提交本题。");
//			super.writeJson(j);
//			return;
//		}
//		if (solution.getSourceCode().length() > 64000) {
//			j.setSuccess(false);
//			j.setMsg("代码长度不能超过64000个字符");
//			super.writeJson(j);
//			return;
//		}
//
//		reentrantLock.lock();
//
//		try {
//			Date now = new Date();
//			// 设置提交时间
//			solution.setSubmitTime(new Date());
//			// 获取考试开始时间和结束时间
//			Exam exam = examService.getExamById(solution.getExamId());
//
//			Date startTime = exam.getStarttime();
//			Date endTime = exam.getEndtime();
//			// 如果考试开始之前提交代码
//			if (now.getTime() < startTime.getTime()) {
//				j.setSuccess(false);
//				j.setMsg("练习还没开始，无法提交代码！");
//				super.writeJson(j);
//			}
//			// 如果考试结束提交代码
//			else if (now.getTime() > endTime.getTime() && sessionInfo.getRoleNames().equals("student")) {
//				j.setSuccess(false);
//				j.setMsg("练习已经结束，无法再提交代码！");
//				super.writeJson(j);
//			} else {
//				// 根据problemID查找Problems
//				Problems problem = problemsService.findProblemById(solution.getProblemId());
//				// 根据userId+examId+catId+problemId到studentTrainProbDetail表查找
//				StudentTrainProbDetail stpd = studentTrainProbDetailService.getStudentTrainProbDetail(
//						solution.getUserid(), solution.getExamId(), catId, solution.getProblemId());
//				// 提交本题之后再次提交代码
//				if (stpd != null && stpd.isFinished()) {
//					j.setSuccess(false);
//					j.setMsg("已经提交过本题，不能再提交代码！");
//					super.writeJson(j);
//					return;
//				}
//				Date deadline = itrainProblemCatService.getDeadline(solution.getExamId(), catId);
//				Date submitTime = solution.getSubmitTime();
//				if (deadline != null && submitTime.after(deadline) && sessionInfo.getRoleNames().equals("student")) {
//					j.setSuccess(false);
//					j.setMsg("提交时间已经晚于本关截止时间不能提交代码");
//					super.writeJson(j);
//					return;
//				}
//				// 更新Problems表的submit字段，增加1
//				if (problem.getSubmit() == null) {
//					problem.setSubmit(1);
//				} else {
//					problem.setSubmit(problem.getSubmit() + 1);
//				}
//				// 设置status字段为已提交状态
//				solution.setStatus(Constant.CODE_WAIT);
//				// 设置remark默认值
//				solution.setRemark(Constant.DEFAULT_REMARK);
//				// 设置正确的测试用例ids字断为默认值－1
//				solution.setCorrectCaseIds(Constant.DEFAULT_CORRECTCASEIDS);
//
//				Json json = submittedcodeService.itrainSubmitCode(problem, solution, stpd, now, startTime, endTime);
//				j.setMsg(json.getMsg());
//				j.setSuccess(json.isSuccess());
//				super.writeJson(j);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			String sOut = "";
//			StackTraceElement[] trace = e.getStackTrace();
//			for (StackTraceElement s : trace) {
//				sOut += "\tat " + s + "\r\n";
//			}
//			// 异常信息最大记录19000个字符，数据库该字段最大为20K
//			int count = sOut.length() > 19000 ? 19000 : sOut.length();
//			sOut = sOut.substring(0, count - 1);
//			int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e.getLocalizedMessage().length();
//			String localMessage = "";
//			if (e.getLocalizedMessage() != null) {
//				localMessage = e.getLocalizedMessage().substring(0, leng - 1);
//			}
//			Log log = new Log();
//			log.setType("代码提交");
//			log.setOptime(new Date());
//			log.setUserId(solution.getUserid());
//			log.setUserType("student");
//			log.setContent(sOut);
//			log.setAbstractContent("学生id:" + solution.getUserid() + "考试id:" + solution.getExamId() + "题目id:"
//					+ solution.getProblemId() + "\n" + localMessage);
//			logService.WriteLog(log);
//
//			// 返回前台的json数据
//			j.setSuccess(false);
//			j.setMsg("服务器内部发生错误，请报告管理员。");
//			super.writeJson(j);
//		} finally {
//			reentrantLock.unlock();
//		}
//
//	}

}
