package edu.dhu.problem.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import edu.dhu.cache.ExamCacheManager;
import edu.dhu.cache.ExamproblemsCacheManager;
import edu.dhu.cache.ProblemsCachManager;
import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.ExamServiceI;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.RespBean;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.ExamproblemServiceI;
import edu.dhu.problem.service.ProblemsServiceI;
import edu.dhu.problem.service.SolutionServiceI;
import edu.dhu.problem.service.StudentTrainProbDetailServiceI;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/problems")
@Transactional
public class ProblemsController {

	private static final long serialVersionUID = -2988368111795658835L;
	@Resource
	private ProblemsServiceI problemsServiceI;
	@Resource
	private ExamServiceI examService;
//	private ProblemtestcasesDaoI problemtestcasesDao;
	private AdminusersDaoI adminuserDao;
	@Resource
	private ExamproblemServiceI examProblemService;
	@Resource
	private SolutionServiceI solutionService;
	@Resource
	private StudentTrainProbDetailServiceI studentTrainProbDetailService;
	@Resource
	private StudentexamdetailServiceI studentexamdetailService;
//	private ProblemCommentsServiceI problemCommentsService;
//
//	public ExamproblemServiceI getExamProblemService() {
//		return examProblemService;
//	}
//
//
//	public ProblemCommentsServiceI getProblemCommentsService() {
//		return problemCommentsService;
//	}
//
//	@Autowired
//	public void setProblemCommentsService(ProblemCommentsServiceI problemCommentsService) {
//		this.problemCommentsService = problemCommentsService;
//	}
//
//	@Autowired
//	public void setExamProblemService(ExamproblemServiceI examProblemService) {
//		this.examProblemService = examProblemService;
//	}
//
//	public ProblemsServiceI getProblemsServiceI() {
//		return problemsServiceI;
//	}
//
//	@Autowired
//	public void setProblemsServiceI(ProblemsServiceI problemsServiceI) {
//		this.problemsServiceI = problemsServiceI;
//	}
//
//	public ProblemtestcasesDaoI getProblemtestcasesDao() {
//		return problemtestcasesDao;
//	}
//
//	@Autowired
//	public void setProblemtestcasesDao(ProblemtestcasesDaoI problemtestcasesDao) {
//		this.problemtestcasesDao = problemtestcasesDao;
//	}

	public ExamServiceI getExamService() {
		return examService;
	}

//	@Autowired
//	public void setExamService(ExamServiceI examService) {
//		this.examService = examService;
//	}
//
//	@Autowired
//	public void setAdminuserDao(AdminusersDaoI adminuserDao) {
//		this.adminuserDao = adminuserDao;
//	}

	public AdminusersDaoI getAdminuserDao() {
		return adminuserDao;
	}

	// 获取题目列表

	@PostMapping("/getProblemsList")
	public RespBean getProblemsList(@RequestBody PMProblems pMProblems, HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String role = decodeToken.getRole();
		String userId = decodeToken.getUserId();
		// 用户已经登录
		// 如果session里的用户是teacher或者admin，则根据传入的参数userid获取该学生的考试题目列表
		if (!role.equals("student")) {
			userId = String.valueOf(pMProblems.getUserId());
		}
		// 根据userid和examid判断该学生是否能参加这场考试，如果不能，则返回error
		if (!examService.checkExamByUserIdAndExamId(pMProblems.getExamId(), Integer.parseInt(userId))) {
			return RespBean.error("您无法参加该场考试");
		}
		// !此处存在魔改，将firstLogin作为false传入
		// 根据exam ID和user ID获取题目列表信息
		List<PMProblems> problemsList = problemsServiceI.findAllProblemsByExamId(pMProblems.getExamId(), Integer.parseInt(userId),
				false);
		// 根据examID查询该场考试的信息,先从缓冲中获取该场考试的信息
		ExamCacheManager examCacheManager = ExamCacheManager.getInstance();
		Exam exam = (Exam) examCacheManager.getObject("theExamById" + pMProblems.getExamId());
		System.out.println(exam);
		if (exam == null) {
			exam = examService.getExamById(pMProblems.getExamId());
			examCacheManager.putObject("theExamById" + pMProblems.getExamId(), exam);
			System.out.println(exam);
		}
		// 返回前段页面的对象
		PMExamProblemInfo pMExamProblemInfo = new PMExamProblemInfo();
		pMExamProblemInfo.setProblemsList(problemsList);
		pMExamProblemInfo.setExam(exam);
		return RespBean.ok("查询所有考试题目成功", pMExamProblemInfo);
		/*
		 * // 首先需要验证现在是否是考试时间范围内 Date now = new Date(); // 根据examId获取exam
		 * Exam exam = examService.getExamById(pMProblems.getExamId()); Date
		 * startTime = exam.getStarttime(); Date endTime =
		 * exam.getEndtime(); if(now.getTime() >= startTime.getTime() &&
		 * now.getTime() <= endTime.getTime()) { int userId =
		 * sessionInfo.getUserId(); // 根据exam ID和user ID获取题目列表信息
		 * List<PMProblems> problems =
		 * problemsServiceI.findAllProblemsByExamId(pMProblems.getExamId(),
		 * userId); logger.info("查询所有考试题目成功"); j.setSuccess(true);
		 * j.setMsg("查询所有考试题目成功"); j.setObj(problems); super.writeJson(j); }
		 * else { logger.info("考试时间没到，无法查看考试题目。"); j.setSuccess(false);
		 * j.setMsg("考试时间没到，无法查看考试题目。"); super.writeJson(j); }
		 */

	}

//	// 根据题目ID获取题目信息
//	public void getProblemById() {
//		// 返回前台的json数据
//		Json j = new Json();
//		ProblemsCachManager problemsCachManager = ProblemsCachManager.getInstance();
//		PMProblemInfo problem = (PMProblemInfo) problemsCachManager.getObject("problemId" + pMProblems.getId());
//		System.out.println(problem);
//		if (problem == null) {
//			problem = problemsServiceI.findProblemInfoById(pMProblems.getId());
//			problemsCachManager.putObject("problemId" + pMProblems.getId(), problem);
//			System.out.println(problem);
//		}
//
//		if (problem != null) {
//			logger.info("查询题目ID:" + pMProblems.getId() + "的题目成功");
//			j.setSuccess(true);
//			j.setMsg("查询题目ID:" + pMProblems.getId() + "的题目成功");
//			j.setObj(problem);
//			super.writeJson(j);
//		} else {
//			logger.info("查询题目ID:" + pMProblems.getId() + "的题目失败");
//			j.setSuccess(false);
//			j.setMsg("查询题目ID:" + pMProblems.getId() + "的题目失败");
//			super.writeJson(j);
//		}
//	}
//
	// 根据题目ID获取题目信息
	@PostMapping("/getProblemByIdAndExamId")
	public RespBean getProblemByIdAndExamId(@RequestBody PMProblems pMProblems) {
		ProblemsCachManager problemsCachManager = ProblemsCachManager.getInstance();
		PMProblemInfo problem = (PMProblemInfo) problemsCachManager.getObject("problemId" + pMProblems.getId());
		if (problem == null) {
			problem = problemsServiceI.findProblemInfoById(pMProblems.getId());
			problemsCachManager.putObject("problemId" + pMProblems.getId(), problem);
		}
		ExamproblemsCacheManager examProblemsCachManager = ExamproblemsCacheManager.getInstance();
		Examproblems examProblem = (Examproblems) examProblemsCachManager
				.getObject("examProblemId_" + pMProblems.getExamId() + "_" + pMProblems.getId());
		if (examProblem == null) {
			examProblem = examProblemService.getExamproblemsByExamIdAndProblemId(pMProblems.getExamId(),
					pMProblems.getId());
			examProblemsCachManager.putObject("examProblemId_" + pMProblems.getExamId() + "_" + pMProblems.getId(),
					examProblem);
		}
		if (problem != null) {
			if (examProblem != null) {
				problem.setBestBefore(examProblem.getBestBefore());
				problem.setScoreCoef(examProblem.getScoreCoef());
				problem.setDeadline(examProblem.getDeadline());
			}
			problem.setSourceCode(null);
			return RespBean.ok("查询题目ID:" + pMProblems.getId() + "的题目成功", problem);
		} else {
			return RespBean.error("查询题目ID:" + pMProblems.getId() + "的题目失败");
		}
	}

//	// 查找所有问题
//	public void findProblemsByCondition() {
//		// 查询时使用的查询条件
//		String keywords = pMProblems.getKeywords();
//		String courseCode = pMProblems.getCourseCode();
//		String chapterCode = pMProblems.getChapterCode();
//		String source = pMProblems.getSource();
//		String difficulty = pMProblems.getDifficulty();
//		int teacherId = pMProblems.getTeacherId();
//		String sortContent = pMProblems.getSortContent(); // 排序内容，排序方式
//		String sortType = pMProblems.getSortType();
//		//
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMProblems> problems = problemsServiceI.findProblemsByCondition(keywords, courseCode, chapterCode,
//					source, difficulty, teacherId, sortContent, sortType);
//			logger.info("查询所有考试题目成功");
//			j.setSuccess(true);
//			j.setMsg("查询所有考试题目成功");
//			j.setObj(problems);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	// 根据分类id查找已经分类的问题
//	public void findProblemsByCategory() {
//		String category = pMProblems.getCategory();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json j = new Json();
//		if (sessionInfo != null) {
//			if(Integer.parseInt(category)==-1){
//				logger.info("二级分类不能为空！");
//				j.setSuccess(false);
//				j.setMsg("二级分类不能为空！");
//				super.writeJson(j);
//			}else{
//				List<PMProblems> problems = problemsServiceI.findProblemsByCategory(category);
//				logger.info("查询所有考试题目成功");
//				j.setSuccess(true);
//				j.setMsg("查询所有考试题目成功");
//				j.setObj(problems);
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	// 查找所有问题
//	public void findExcludeProblemsByCondition() {
//		// 查询时使用的查询条件
//		String keywords = pMProblems.getKeywords();
//		String courseCode = pMProblems.getCourseCode();
//		String chapterCode = pMProblems.getChapterCode();
//		String source = pMProblems.getSource();
//		String difficulty = pMProblems.getDifficulty();
//
//		String excludeCategory = pMProblems.getExcludeCategory();
//
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMProblems> problems = problemsServiceI.findExcludeProblemsByCondition(keywords, courseCode,
//					chapterCode, source, difficulty, excludeCategory);
//			logger.info("查询所有考试题目成功");
//			j.setSuccess(true);
//			j.setMsg("查询所有考试题目成功");
//			j.setObj(problems);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	public void findProblemBelowCategoryById() {
//		int id = pMProblems.getId(); // 试题的id
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json json = new Json();
//		if (sessionInfo != null) {
//			Problems p = problemsServiceI.findProblemById(id);
//			String categoryIds = p.getCategory();
//			PMProblems problems = problemsServiceI.findProblemBelowCategoryById(categoryIds);
//
//			logger.info("查询题目所属的考试成功");
//			json.setSuccess(true);
//			json.setObj(problems);
//			json.setMsg("查询题目所属的考试成功");
//			super.writeJson(json);
//		} else {
//			json.setSuccess(false);
//			json.setMsg("请先登录。");
//			super.writeJson(json);
//		}
//	}
//
//	public void viewProblemDetailInformationById() // 查看问题的详细信息
//	{
//		int id = pMProblems.getId(); // 试题的id
//		int examId = pMProblems.getExamId();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json j = new Json();
//		if (sessionInfo != null) {
//			PMProblems problem = problemsServiceI.viewProblemDetailInformationById(id, examId);
//			logger.info("查询题目信息成功");
//			j.setSuccess(true);
//			j.setMsg("查询题目信息成功");
//			j.setObj(problem);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	public void editProblem() // 编辑问题
//	{
//		List<String> inputList = pMProblems.getInput();
//		List<String> outputList = pMProblems.getOutput();
//		List<String> idList = pMProblems.getTestcaseId();
//		String str1[] = inputList.get(0).split("\n<分隔符>\n");
//		String str2[] = outputList.get(0).split("\n<分隔符>\n");
//		String str3[] = idList.get(0).split("\n<分隔符>\n");
//		List<String> temp1 = new ArrayList<String>();
//		List<String> temp2 = new ArrayList<String>();
//		List<String> temp3 = new ArrayList<String>();
//		for (int i = 0; i < str1.length; i++) {
//			temp1.add(str1[i]);
//			temp2.add(str2[i]);
//			temp3.add(str3[i]);
//		}
//		pMProblems.setInput(temp1);
//		pMProblems.setOutput(temp2);
//		pMProblems.setTestcaseId(temp3);
//		Date updateTime = new Date();
//		pMProblems.setUpdateTime(updateTime);
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json json = new Json();
//		if (sessionInfo != null) {
//			int id = pMProblems.getId();
//			problemsServiceI.editProblem(pMProblems); // 将问题信息添加到problems表中
//			List<String> inputs = pMProblems.getInput();
//			List<String> outputs = pMProblems.getOutput(); // 从页面得到的测试用例与数据库中的进行比对
//			List<String> ids = pMProblems.getTestcaseId();
//			for (int i = 0; i < inputs.size(); i++) {
//				if (Integer.parseInt(ids.get(i)) > 0) // 如果id大于0，则表示当前的测试用例是原有的测试用例
//				{
//					Problemtestcases c = new Problemtestcases();
//					c.setId(Integer.parseInt(ids.get(i)));
//					c.setInput(inputs.get(i));
//					c.setOutput(outputs.get(i));
//					c.setProblemId(id);
//					problemtestcasesDao.updateTestcase(c); // 更新测试用例
//				}
//			}
//			List<Problemtestcases> testcases = problemtestcasesDao.getProblemtestcasesByProblemId(id); // 从数据库中读取到的测试用例
//			int dexist[] = new int[testcases.size()]; // 从页面上传的测试用例是否存在当前用例，1表示存在，0表示不存在
//			int nexist[] = new int[inputs.size()]; // 记录页面测试用例
//			for (int i = 0; i < testcases.size(); i++)
//				dexist[i] = 0; // 表示不存在
//			for (int i = 0; i < inputs.size(); i++)
//				nexist[i] = 0;
//			for (int i = 0; i < inputs.size(); i++) {
//				String input = inputs.get(i);
//				String output = outputs.get(i);
//				for (int j = 0; j < testcases.size(); j++) {
//					if (testcases.get(j).getInput().equals(input) && testcases.get(j).getOutput().equals(output)
//							&& dexist[j] == 0) // 用例与数据库中一个用例相同
//					{
//						nexist[i] = 1;
//						dexist[j] = 1;
//						break;
//					}
//				}
//			}
//			// dexist[j]=0表示该测试用例已经没用，应该将其删除
//			for (int i = 0; i < testcases.size(); i++) {
//				if (dexist[i] == 0) {
//					int caseId = testcases.get(i).getId();
//					problemtestcasesDao.deleteTestCase(caseId); // 删除该测试用例
//					// System.out.println("testcases:"+i);
//				}
//			}
//			for (int i = 0; i < inputs.size(); i++) {
//				if (nexist[i] == 0) // 表明该测试用例是新添加的，要将该测试用例添加到数据库中
//				{
//					Problemtestcases testcase = new Problemtestcases();
//					testcase.setInput(inputs.get(i));
//					testcase.setOutput(outputs.get(i));
//					testcase.setProblemId(id);
//					problemtestcasesDao.save(testcase);
//				}
//			}
//
//			// 清空缓存
//			ProblemsCachManager problemsCachManager = ProblemsCachManager.getInstance();
//			problemsCachManager.removeAllObject();
//			WSProblemsCachManager wsproblemsCachManager = WSProblemsCachManager.getInstance();
//			wsproblemsCachManager.removeAllObject();
//
//			logger.info("修改题目信息成功");
//			json.setSuccess(true);
//			json.setMsg("修改题目信息成功");
//			// json.setObj(pMProblems);
//			super.writeJson(json);
//		} else {
//			json.setSuccess(false);
//			json.setMsg("请先登录。");
//			super.writeJson(json);
//		}
//
//	}
//
//	public void deleteProblem() {
//		int id = pMProblems.getId(); // 试题的id
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json json = new Json();
//		if (sessionInfo != null) {
//
//			List<Exam> examList = examService.getExamByProblemId(id);
//			if (examList.size() > 0) {
//				String msg = "该题已被以下考试收录，请将其从考试中删除后再删除题目:<br>";
//				for (int i = 0; i < examList.size(); i++) {
//					Exam exam = examList.get(i);
//					msg = msg + exam.getName() + "<br>";
//				}
//				logger.info("删除题目失败");
//				json.setSuccess(false);
//				json.setMsg(msg);
//				super.writeJson(json);
//			} else {
//				problemtestcasesDao.deleteProblemTestcaseByProblemId(id); // 删除测试用例
//				Problems problem = problemsServiceI.findProblemById(id);
//				String description = problem.getDescription();
//				String inputRuqirement = problem.getInputRequirement();
//				String outputRuqirement = problem.getOutputRequirement();
//				problemsServiceI.deleteProblem(id); // 删除题目
//				deleteImageAndFile(description); // 删除描述输入输出说明中的图片和文件
//				deleteImageAndFile(inputRuqirement);
//				deleteImageAndFile(outputRuqirement);
//				problemCommentsService.delProblemCommentByProId(id);//删除该题目相关题解
//				logger.info("删除题目成功");
//				json.setSuccess(true);
//				json.setMsg("删除题目成功");
//				json.setObj(id);
//				super.writeJson(json);
//			}
//		} else {
//			json.setSuccess(false);
//			json.setMsg("请先登录。");
//			super.writeJson(json);
//		}
//	}
//
//	public void getProblemBelowExam() {
//		int id = pMProblems.getId(); // 试题的id
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json json = new Json();
//		if (sessionInfo != null) {
//
//			List<Exam> examList = examService.getExamByProblemId(id);
//			List<PMExam> list = new ArrayList<PMExam>();
//			for (int i = 0; i < examList.size(); i++) {
//				Exam exam = examList.get(i);
//				PMExam e = new PMExam();
//				String name = exam.getName();
//				e.setId(exam.getId());
//				e.setName(name);
//				Integer teacherId = exam.getTeacherId();
//				e.setTeacherId(teacherId);
//				if (teacherId != null) {
//					Adminusers adminuser = adminuserDao.get(Adminusers.class, teacherId.intValue());
//					String teacherName = adminuser.getName();
//					e.setTeacherName(teacherName);
//				}
//				list.add(e);
//			}
//			logger.info("查询题目所属的考试成功");
//			json.setSuccess(true);
//			json.setObj(list);
//			json.setMsg("查询题目所属的考试成功");
//			super.writeJson(json);
//		} else {
//			json.setSuccess(false);
//			json.setMsg("请先登录。");
//			super.writeJson(json);
//		}
//	}
//
//	public void deleteImageAndFile(String source) // 删除题目后删除图片和文件
//	{
//		String str = source;
//		while (str.indexOf("<img") >= 0) // 删除图片
//		{
//			int x = str.indexOf("<img");
//			int y;
//			for (y = x; y < str.length(); y++) {
//				if (str.toCharArray()[y] == '>') // 找到第一个">"退出循环
//				{
//					break;
//				}
//			}
//			String img = str.substring(x, y);
//			int m = img.indexOf("src=\"");
//			img = img.substring(m + 5, img.length());
//			int n = img.indexOf("\"");
//			img = img.substring(0, n); // 获得img的src
//			ServletContext servletContext = ServletActionContext.getServletContext();
//			File dir = new File(servletContext.getRealPath("/"));
//			File file = new File(dir.getParent() + img);
//			if (file.exists() == true) {
//				file.delete();
//			}
//			String str1 = str.substring(0, x);
//			String str2 = str.substring(y + 1, str.length());
//			str = str1 + str2;
//		}
//		str = source;
//		while (str.indexOf("<a") >= 0) // 删除图片
//		{
//			int x = str.indexOf("<a");
//			int y;
//			for (y = x; y < str.length(); y++) {
//				if (str.toCharArray()[y] == '>') // 找到第一个">"退出循环
//				{
//					break;
//				}
//			}
//			String temp = str.substring(x, y);
//			int m = temp.indexOf("href=\"");
//			temp = temp.substring(m + 6, temp.length());
//			int n = temp.indexOf("\"");
//			temp = temp.substring(0, n); // 获得img的src
//			ServletContext servletContext = ServletActionContext.getServletContext();
//			File dir = new File(servletContext.getRealPath("/"));
//			File file = new File(dir.getParent() + temp);
//			if (file.exists() == true) {
//				file.delete();
//			}
//			String str1 = str.substring(0, x);
//			String str2 = str.substring(y + 1, str.length());
//			str = str1 + str2;
//		}
//	}
//
//	// 向题库中添加题目分类
//	public void addProblemClassification() {
//		// 返回前台的json数据
//		Json j = new Json();
//		String res = problemsServiceI.addProblemClassification(pMProblems.getId(), pMProblems.getCategory(),pMProblems.getDuration());
//		if (res == null) // 没有错误信息
//		{
//			logger.info("题目设置分类成功。");
//			j.setSuccess(true);
//			j.setMsg("题目设置分类成功");
//			super.writeJson(j);
//		} else {
//			logger.info("题目设置分类失败。");
//			j.setSuccess(false);
//			j.setMsg("题目设置分类失败!<br>" + res);
//			super.writeJson(j);
//		}
//	}
//
//	// 根据题目id查找该题目的所有分类
//	public void findProblemClassifications() {
//		int problemId = pMProblems.getId();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMProblems> problems = problemsServiceI.findProblemClassifications(problemId);
//			logger.info("查询题目的所有类别成功");
//			j.setSuccess(true);
//			j.setMsg("查询题目的所有类别成功");
//			j.setObj(problems);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("查询题目的所有类别失败");
//			super.writeJson(j);
//		}
//	}
//
//	// 根据题目id查找该题目的所有分类
//	public void findProblemDuration() {
//		int problemId = pMProblems.getId();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		// 返回前台的json数据
//		Json j = new Json();
//		if (sessionInfo != null) {
//			int duration = problemsServiceI.findProblemDuration(problemId);
////			logger.info("查询题目的所有类别成功");
//			j.setSuccess(true);
////			j.setMsg("查询题目的所有类别成功");
//			j.setObj(duration);
//			super.writeJson(j);
//		} else {
//			j.setSuccess(false);
////			j.setMsg("查询题目的所有类别失败");
//			super.writeJson(j);
//		}
//	}
//
//
//	// 删除题目的分类
//	public void deleteProblemClassification() {
//		// 返回前台的json数据
//		Json j = new Json();
//
//		String res = problemsServiceI.deleteProblemClassification(pMProblems.getId(),pMProblems.getSecondClassification());
//		if (res == null) // 没有错误信息
//		{
//			logger.info("题目删除分类成功");
//			j.setSuccess(true);
//			j.setMsg("题目删除分类成功");
//			super.writeJson(j);
//		} else {
//			logger.info("题目删除分类失败。");
//			j.setSuccess(false);
//			j.setMsg("题目删除分类失败!<br>" + res);
//			super.writeJson(j);
//		}
//	}
//
//	//获取studenttrainprobdetail提交状态
//	public void getTrianProbdetailStatus() {
//		Json j = new Json();
//
//		StudentTrainProbDetail stpd = studentTrainProbDetailService.getStatusByUserIdAndExamIdAndProId(
//				pMProblems.getUserId(), pMProblems.getExamId(), pMProblems.getId());
//
//		if(stpd != null){
//			j.setMsg("获取提交本题状态成功！");
//			j.setSuccess(true);
//			j.setObj(stpd);
//		}else{
//			j.setSuccess(false);
//			j.setMsg("获取提交本题状态失败！");
//		}
//		super.writeJson(j);
//	}

	// 判断是否能够点击提交本题按钮
	@PostMapping("/isSubmitThisProblem")
	public RespBean isSubmitThisProblem(@RequestBody PMWrongAndCorrectIds pMWrongAndCorrectIds, HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		// 根据userId,examId,problemId在solution中查找最新的solution
		Solution solution = solutionService
				.getLastSolutionByUserIdExamIdProblemId(
						Integer.parseInt(userId),
						pMWrongAndCorrectIds.getExamId(),
						pMWrongAndCorrectIds.getProblemId());
		if (solution == null) {
			return RespBean.error("本题没有提交过代码，不可以提交本题。");
		} else {
			//智能训练页面做题判断是否提交
			// !暂时不知道catId的作用
			int catId = 0;
			if(catId != 0){
				StudentTrainProbDetail stpd = studentTrainProbDetailService
						.getStudentTrainProbDetail(Integer.parseInt(userId), pMWrongAndCorrectIds.getExamId(),
								catId, pMWrongAndCorrectIds.getProblemId());
				if(stpd.isFinished()){
					return RespBean.error("不能重复提交");
				}else{
					return RespBean.ok("没有提交过本题，可以提交");
				}
			}else{
				// 根据userid，examID，problemID在studentexamtail表中查找
				Studentexamdetail studentexamdetail = studentexamdetailService
						.getStatusByUserIDexamIDproblemId(
								Integer.parseInt(userId),
								pMWrongAndCorrectIds.getExamId(),
								pMWrongAndCorrectIds.getProblemId());
				if (studentexamdetail.isFinished()) {
					return RespBean.error("不能重复提交");
				} else {
					return RespBean.ok("没有提交过本题，可以提交");
				}
			}
		}
	}

	// 该函数会被前端每隔0.5秒轮询调用一次查询结果，猜测当statusArr不为QUEUE时，前端调用getAllWrongAndRightCases
	@PostMapping("/getProblemsStatusByIds")
	public RespBean getProblemsStatusByIds(@RequestBody PMProblemsStatus pMProblemsStatus,HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String userId = decodeToken.getUserId();
		pMProblemsStatus.setUserId(Integer.parseInt(userId));
		PMProblemsStatus problemsStatus = studentexamdetailService
				.getProblemsStatusArrByIds(pMProblemsStatus);
		if (problemsStatus != null) {
			return RespBean.ok("根据ID数组查找状态成功。", problemsStatus);
		} else {
			return RespBean.error("根据ID数组查找状态失败。");
		}
	}
}
