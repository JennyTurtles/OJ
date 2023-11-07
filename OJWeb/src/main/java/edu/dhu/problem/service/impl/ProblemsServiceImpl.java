package edu.dhu.problem.service.impl;

import edu.dhu.cache.ExamproblemsCacheManager;
import edu.dhu.cache.ProblemsCachManager;
import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.problem.dao.ChapterDaoI;
import edu.dhu.problem.dao.ProblemCategoryDaoI;
import edu.dhu.problem.dao.ProblemsDaoI;
import edu.dhu.problem.model.*;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.problem.service.ProblemsServiceI;
import edu.dhu.user.model.PMAdminusers;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service("problemsService")
@Transactional
public class ProblemsServiceImpl implements ProblemsServiceI {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private ProblemCategoryDaoI problemCategoryDao;

	public ProblemCategoryDaoI getProblemCategoryDao() {
		return problemCategoryDao;
	}

	@Autowired
	public void setProblemCategoryDao(ProblemCategoryDaoI problemCategoryDao) {
		this.problemCategoryDao = problemCategoryDao;
	}

	private ProblemsDaoI problemsDao;
	private StudentexamdetailDaoI studentexamdetailDao;
//	private ProblemtestcasesDaoI problemtestcasesDao;
	private ChapterDaoI chapterDao;
//	private ExamproblemDaoI examproblemDao;
	private AdminusersDaoI adminuserDao;

//	private SolutionDaoI solutionDao;

//	public SolutionDaoI getSolutionDao() {
//		return solutionDao;
//	}
//
//	@Autowired
//	public void setSolutionDao(SolutionDaoI solutionDao) {
//		this.solutionDao = solutionDao;
//	}

	public ProblemsDaoI getProblemsDao() {
		return problemsDao;
	}

	@Autowired
	public void setProblemsDao(ProblemsDaoI problemsDao) {
		this.problemsDao = problemsDao;
	}

	public StudentexamdetailDaoI getStudentexamdetailDao() {
		return studentexamdetailDao;
	}

	@Autowired
	public void setStudentexamdetailDao(StudentexamdetailDaoI studentexamdetailDao) {
		this.studentexamdetailDao = studentexamdetailDao;
	}

//	@Autowired
//	public void setProblemtestcasesDao(ProblemtestcasesDaoI problemtestcasesDao) {
//		this.problemtestcasesDao = problemtestcasesDao;
//	}
//
//	public ProblemtestcasesDaoI getProblemtestcasesDao() {
//		return problemtestcasesDao;
//	}

	@Autowired
	public void setChapterDao(ChapterDaoI chapterDao) {
		this.chapterDao = chapterDao;
	}

	public ChapterDaoI getChapterDao() {
		return chapterDao;
	}

//	public ExamproblemDaoI getExamproblemDao() {
//		return examproblemDao;
//	}
//
//	@Autowired
//	public void setExamproblemDao(ExamproblemDaoI examproblemDao) {
//		this.examproblemDao = examproblemDao;
//	}

	@Autowired
	public void setAdminusersDao(AdminusersDaoI adminuserDao) {
		this.adminuserDao = adminuserDao;
	}

	public AdminusersDaoI getAdminusersDao() {
		return adminuserDao;
	}

	@Override
	public List<PMProblems> findAllProblemsByExamId(int examId, int userId, boolean firstLogin) {

		ProblemsCachManager problemsCachManager = ProblemsCachManager.getInstance();
		@SuppressWarnings("unchecked")
		List<PMProblems> pMProblems = (List<PMProblems>) problemsCachManager.getObject("examId" + examId);
		if (pMProblems == null) {
			pMProblems = problemsDao.findAllProblemsByExamId(examId);
			problemsCachManager.putObject("examId" + examId, pMProblems);
		}

		// 从缓冲中根据examID获取所有的examproblemsList
//		ExamproblemsCacheManager examproblemsManager = ExamproblemsCacheManager.getInstance();
//		@SuppressWarnings("unchecked")
		/*
		 * List<Examproblems> examproblemsList = (List<Examproblems>)
		 * examproblemsManager.getObject(examId + ""); if (examproblemsList ==
		 * null) { examproblemsList =
		 * examproblemDao.getDisplaySequenceByExamId(examId);
		 * examproblemsManager.putObject(examId + "", examproblemsList); }
		 */
		// 根据userID，examID获取该学生所有的Studentexamdetail列表
		List<Studentexamdetail> studentexamdetailList = null;
		// 如果不是第一次登陆再查询题目状态
		if (!firstLogin) {
			studentexamdetailList = studentexamdetailDao.getAllStudentexamdetailListByUserIdAndExamId(userId, examId);
		}
		if (studentexamdetailList != null) {
			for (int i = 0; i < pMProblems.size(); i++) {
				boolean flag = false;
				int currentProblemId = pMProblems.get(i).getId();
				for (int j = 0; j < studentexamdetailList.size(); j++) {
					if (currentProblemId == studentexamdetailList.get(j).getProblemId()) {
						flag = true;
						// 设置该题的状态
						pMProblems.get(i).setStatus(studentexamdetailList.get(j).getStatus());
						// 设置该题是否提交
						pMProblems.get(i).setProblemIsSubmit(studentexamdetailList.get(j).isFinished());
					}
				}
				if (!flag) {
					pMProblems.get(i).setStatus("");
					pMProblems.get(i).setProblemIsSubmit(false);
				}

				/*
				 * // 设置score for(int k=0;k<examproblemsList.size();k++) {
				 * if(examproblemsList.get(k).getProblemId() ==
				 * currentProblemId) {
				 * pMProblems.get(i).setProblemScore(examproblemsList
				 * .get(k).getScore()); break; } }
				 */
			}
		}
		// 为空表示所有题目的状态都没有
		else {
			for (int m = 0; m < pMProblems.size(); m++) {
				/*
				 * // 设置score for(int
				 * index=0;index<examproblemsList.size();index++) {
				 * if(examproblemsList.get(index).getProblemId() ==
				 * pMProblems.get(m).getId()) {
				 * pMProblems.get(m).setProblemScore
				 * (examproblemsList.get(index).getScore()); break; } }
				 */

				// 设置题目的初始化提交状态为空
				pMProblems.get(m).setStatus("");
				// 设置题目的提交状态为false
				pMProblems.get(m).setProblemIsSubmit(false);
			}
		}
		return pMProblems;
	}

	@Override
	public Problems findProblemById(int problemId) {
		return problemsDao.findProblemById(problemId);
	}

	@Override
	public List<PMProblems> findProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty, int teacherId, String sortContent, String sortType) {
		List<PMProblems> list = problemsDao.findProblemsByCondition(keywords, courseCode, chapterCode, source,
				difficulty, teacherId, sortContent, sortType);
		HashMap chapterMap = new HashMap();
		List<PMChapters> chapters = new ArrayList();
		if (!courseCode.equals("")) {
			chapters = chapterDao.findChaptersBycourseCode(courseCode);
		} else {
			chapters = chapterDao.findAllChapter();
		}
		for (int i = 0; i < chapters.size(); i++) // 建立章节的hashmap
		{
			PMChapters chapter = chapters.get(i);
			chapterMap.put(chapter.getCode(), chapter);
		}
		HashMap teacherMap = new HashMap();
		List<PMAdminusers> teacherList = adminuserDao.findAllAdminusers();
		for (int i = 0; i < teacherList.size(); i++) {
			teacherMap.put(teacherList.get(i).getId(), teacherList.get(i));
		}
		for (int i = 0; i < list.size(); i++) {
			PMProblems p = list.get(i);
			chapterCode = p.getChapterCode();
			PMChapters chapter = (PMChapters) chapterMap.get(chapterCode);
			if (chapter != null) {
				p.setCourseName(chapter.getName());
				p.setChapterName(chapter.getChapterName());
			} else {
				p.setCourseName("");
				p.setChapterName("");
			}
			PMAdminusers teacher = (PMAdminusers) teacherMap.get(p.getTeacherId().intValue());
			if (teacher != null)
				p.setTeacherName(teacher.getName());
			else
				p.setTeacherName("");
		}
		/*
		 *
		 * case "标题":hql=hql+" order by p.title";break; case "题目来源":hql=hql+
		 * " order by p.source";break; case "章节":hql=hql+" order by p.chapterId"
		 * ;break; case "难度":hql=hql+" order by p.difficulty";break; case
		 * "教师":hql=hql+" order by p.teacherId";break;
		 */
		// 对数据库不能进行排序的排序内容进行排序
		int position = 0;
		HashMap difficultyMap = new HashMap(); // 对题目难度建立hash表
		difficultyMap.put("易", 1); // 对难度进行划分
		difficultyMap.put("中", 2);
		difficultyMap.put("难", 3);
		Collator c = Collator.getInstance(Locale.CHINA);
		for (int i = 0; i < list.size() - 1; i++) {
			position = i;
			for (int j = i + 1; j < list.size(); j++) {
				if (sortContent.equals("标题")) {
					if (sortType.equals("升序")) // 进行升序
					{
						if (c.compare(list.get(j).getTitle(), list.get(position).getTitle()) < 0) {
							position = j;
						}
					} else {
						if (c.compare(list.get(j).getTitle(), list.get(position).getTitle()) >= 0) {
							position = j;
						}
					}
				}
				if (sortContent.equals("题目来源")) {
					if (list.get(j).getSource() == null)
						list.get(j).setSource("");
					if (list.get(position).getSource() == null)
						list.get(position).setSource("");
					if (sortType.equals("升序")) // 进行升序
					{
						if (c.compare(list.get(j).getSource(), list.get(position).getSource()) < 0) {
							position = j;
						}
					} else {
						if (c.compare(list.get(j).getSource(), list.get(position).getSource()) >= 0) {
							position = j;
						}
					}
				}
				if (sortContent.equals("章节")) {
					if (sortType.equals("升序")) // 进行升序
					{
						if (c.compare(list.get(j).getChapterName(), list.get(position).getChapterName()) < 0) {
							position = j;
						}
					} else {
						if (c.compare(list.get(j).getChapterName(), list.get(position).getChapterName()) >= 0) {
							position = j;
						}
					}
				}
				if (sortContent.equals("难度")) {
					int d1 = 0, d2 = 0;
					if (difficultyMap.get(list.get(position).getDifficulty()) != null)
						d1 = (int) difficultyMap.get(list.get(position).getDifficulty());
					if (difficultyMap.get(list.get(j).getDifficulty()) != null)
						d2 = (int) difficultyMap.get(list.get(j).getDifficulty());
					if (sortType.equals("升序")) // 进行升序
					{

						if (d1 >= d2) {
							position = j;
						}
					} else {
						if (d1 > d2) {
							position = j;
						}
					}
				}
				if (sortContent.equals("教师")) {
					if (sortType.equals("升序")) // 进行升序
					{
						if (c.compare(list.get(j).getTeacherName(), list.get(position).getTeacherName()) < 0) {
							position = j;

						}
					} else {
						if (c.compare(list.get(j).getTeacherName(), list.get(position).getTeacherName()) > 0) {
							position = j;
						}
					}
				}
			}
			if (i != position) {
				PMProblems temp = list.get(i);
				list.set(i, list.get(position));
				list.set(position, temp);
			}
		}
		return list;
	}

	@Override
	public List<PMProblems> findExcludeProblemsByCondition(String keywords, String courseCode, String chapterCode,
			String source, String difficulty, String excludeCategory) {
		List<PMProblems> list = problemsDao.findExcludeProblemsByCondition(keywords, courseCode, chapterCode, source,
				difficulty, excludeCategory);
		HashMap chapterMap = new HashMap();
		List<PMChapters> chapters = new ArrayList();
		if (!courseCode.equals("")) {
			chapters = chapterDao.findChaptersBycourseCode(courseCode);
		} else {
			chapters = chapterDao.findAllChapter();
		}
		for (int i = 0; i < chapters.size(); i++) // 建立章节的hashmap
		{
			PMChapters chapter = chapters.get(i);
			chapterMap.put(chapter.getCode(), chapter);
		}

		for (int i = 0; i < list.size(); i++) {
			PMProblems p = list.get(i);
			chapterCode = p.getChapterCode();
			PMChapters chapter = (PMChapters) chapterMap.get(chapterCode);
			if (chapter != null) {
				p.setCourseName(chapter.getName());
				p.setChapterName(chapter.getChapterName());
			} else {
				p.setCourseName("");
				p.setChapterName("");
			}

			// // 处理该题目所属的二级分类
			// // 1 取出该题目的题目分类id
			// String categoryIds = p.getCategory();
			// if (categoryIds != null&&!categoryIds.equals("")) {
			// // 2 以，分隔开，转换成数组，以便取值
			// String[] valueArr = categoryIds.split(",");
			//
			// String CategoryString = "";
			// ProblemCategory pc = new ProblemCategory();
			//
			// // 循环添加分类的名称
			// for (int j = 0; j < valueArr.length; j++) {
			// if(!valueArr[j].equals("")){
			// // 查找该二级分类
			// pc =
			// problemCategoryDao.findProblemCategoryById(Integer.parseInt(valueArr[j]));
			// CategoryString = CategoryString + pc.getName() + "、<br>";
			// }
			// }
			// p.setBelong(CategoryString);
			// }
		}
		return list;
	}

	@Override
	public PMProblems findProblemBelowCategoryById(String categoryIds) {
		PMProblems p = new PMProblems();
		// 默认设置为空
		p.setBelong("");
		if (categoryIds != null && !categoryIds.equals("")) {
			// 以，分隔开，转换成数组，以便取值
			String[] valueArr = categoryIds.split(",");

			String CategoryString = "";
			ProblemCategory pc = new ProblemCategory();
			ProblemCategory pcParent = new ProblemCategory();
			int mlength = 0;
			// 找到父分类名字最长的长度
			for (int j = 0; j < valueArr.length; j++) {
				if (!valueArr[j].equals("")) {
					// 查找该二级分类
					pc = problemCategoryDao.findProblemCategoryById(Integer.parseInt(valueArr[j]));
					// 查找该二级分类的父分类
					pcParent = problemCategoryDao.findProblemCategoryById(pc.getParentId());
					if (pcParent.getName().length() > mlength) {
						mlength = pcParent.getName().length();
					}
				}
			}
			// 循环添加分类的名称
			for (int j = 0; j < valueArr.length; j++) {
				if (!valueArr[j].equals("")) {
					// 查找该二级分类
					pc = problemCategoryDao.findProblemCategoryById(Integer.parseInt(valueArr[j]));
					pcParent = problemCategoryDao.findProblemCategoryById(pc.getParentId());
					// 得到该父类名称前需要加几个空格，以控制显示整齐
					int pclength = mlength - pcParent.getName().length();
					String space = "";
					for (int k = 0; k < pclength; k++) {
						space = space + "&emsp;";
					}
					String parentName = space + pcParent.getName();
					// 组合成String语句
					CategoryString = CategoryString + pc.getId() + "：" + parentName + "—>" + pc.getName() + "<br>";

				}
			}
			p.setBelong(CategoryString);
		}
		return p;
	}

	@Override
	public PMProblems viewProblemDetailInformationById(int id, int examId) {
		return null;
	}

//	@Override
//	public PMProblems viewProblemDetailInformationById(int id, int examId) {
//		Problems problem = problemsDao.findProblemById(id);
//		List<Problemtestcases> testcases = problemtestcasesDao.getProblemtestcasesByProblemId(id);
//		PMProblems p = new PMProblems();
//		p.setTitle(problem.getTitle());
//		p.setAuthor(problem.getAuthor());
//		p.setSource(problem.getSource());
//		p.setDifficulty(problem.getDifficulty());
//		p.setTimeLimit(problem.getTimeLimit());
//		p.setMemoryLimit(problem.getMemoryLimit());
//		p.setSource(problem.getSource());
//		p.setDescription(problem.getDescription());
//		p.setInputRequirement(problem.getInputRequirement());
//		p.setOutputRequirement(problem.getOutputRequirement());
//		p.setCheckSimilarity(problem.getCheckSimilarity());
//		p.setSimilarityThreshold(problem.getSimilarityThreshold());
//		p.setChapterCode(problem.getchapterCode());
//		p.setSrcCodeLanguage(problem.getSrcCodeLanguage());
//		PMChapters chapter = chapterDao.findChaptersByCode(problem.getchapterCode());
//		if (chapter != null)
//			p.setChapterName(chapter.getName());
//		else
//			p.setChapterName("");
//		p.setSampleInput(problem.getSampleInput());
//		p.setSampleOuput(problem.getSampleOuput());
//		p.setSrcCodeLanguage(problem.getSrcCodeLanguage());
//		p.setSourceCode(problem.getSourceCode());
//		p.setScoreGrade(problem.getScoreGrade());
//		List<String> testcaseIdList = new ArrayList<String>();
//		List<String> inputList = new ArrayList<String>();
//		List<String> outputList = new ArrayList<String>();
//		for (int i = 0; i < testcases.size(); i++) {
//			String testcaseId = String.valueOf(testcases.get(i).getId());
//			String input = testcases.get(i).getInput();
//			String output = testcases.get(i).getOutput();
//			testcaseIdList.add(testcaseId);
//			inputList.add(input);
//			outputList.add(output);
//		}
//		p.setTestcaseId(testcaseIdList);
//		p.setInput(inputList);
//		p.setOutput(outputList);
//		if (examId != 0) {
//			Examproblems expro = examproblemDao.getExamproblemsByExamIdAndProblemId(examId, id);
//			if (expro != null) {
//				p.setBestBefore(expro.getBestBefore());
//				p.setScoreCoef(expro.getScoreCoef());
//				p.setDeadline(expro.getDeadline());
//			}
//		}
//		return p;
//	}

	@Override
	public void editProblem(PMProblems problem) {
		problemsDao.editProblem(problem);

	}

	@Override
	public void updateProblems(Problems problems) {
		problemsDao.updateProblems(problems);
	}


	@Override
	public void deleteProblem(int id) {
		problemsDao.deleteProblem(id);
	}

	@Override
	public PMProblemInfo findProblemInfoByIdAndExamId(int id, int examId) {
		// TODO Auto-generated method stub
		Problems problem = problemsDao.findProblemById(id);
		PMProblemInfo problemInfo = new PMProblemInfo();
		// 讲Problems中信息拷贝到PMProblemInfo中
		BeanUtils.copyProperties(problem, problemInfo);
		// 根据章节信息获取章节的中文名字
		PMChapters chapters = chapterDao.findChaptersByCode(problem.getchapterCode());
		problemInfo.setChapterName(chapters.getName());

		// Examproblems expro = examproblemDao
		// .getExamproblemsByExamIdAndProblemId(examId, id);
		// problemInfo.setBestBefore(expro.getBestBefore());
		// problemInfo.setScoreCoef(expro.getScoreCoef());
		// problemInfo.setDeadline(expro.getDeadline());

		return problemInfo;
	}

	@Override
	public PMProblemInfo findProblemInfoById(int problemId) {
		Problems problem = problemsDao.findProblemById(problemId);
		PMProblemInfo problemInfo = new PMProblemInfo();
		// 讲Problems中信息拷贝到PMProblemInfo中
		BeanUtils.copyProperties(problem, problemInfo);
		// 根据章节信息获取章节的中文名字
		PMChapters chapters = chapterDao.findChaptersByCode(problem.getchapterCode());
		problemInfo.setChapterName(chapters.getName());
		return problemInfo;
	}

	@Override
	public List<PMProblems> findProblemsByCategory(String category) {

		List<PMProblems> list = problemsDao.findProblemsByCategory(category);

		HashMap chapterMap = new HashMap();
		List<PMChapters> chapters = new ArrayList();
		chapters = chapterDao.findAllChapter();
		for (int i = 0; i < chapters.size(); i++) // 建立章节的hashmap
		{
			PMChapters chapter = chapters.get(i);
			chapterMap.put(chapter.getCode(), chapter);
		}
		for (int i = 0; i < list.size(); i++) {
			PMProblems p = list.get(i);
			String chapterCode = p.getChapterCode();
			PMChapters chapter = (PMChapters) chapterMap.get(chapterCode);
			if (chapter != null) {
				p.setCourseName(chapter.getName());
				p.setChapterName(chapter.getChapterName());
			} else {
				p.setCourseName("");
				p.setChapterName("");
			}
			// 开始处理该题目的所属其他分类
			// 1 先将该题目的category取出,转换成数组
			String categoryIds = p.getCategory();
			String[] valueArr = categoryIds.split(",");
			String otherCategoryString = "";
			ProblemCategory pc = new ProblemCategory();
			ProblemCategory pc_parent = new ProblemCategory();
			// 获取最长的父类名字长度
			int mlength = 0;
			for (int j = 0; j < valueArr.length; j++) {
				if (!valueArr[j].equals("") && Integer.parseInt(valueArr[j]) != Integer.parseInt(category)) {
					// 查找该二级分类
					pc = problemCategoryDao.findProblemCategoryById(Integer.parseInt(valueArr[j]));
					if (pc.getParentId() != 0) {
						// 查找该二级分类的父分类
						pc_parent = problemCategoryDao.findProblemCategoryById(pc.getParentId());
						if (pc_parent.getName().length() > mlength) {
							mlength = pc_parent.getName().length();
						}
					}
				}
			}
			// 循环添加其他分类的名称
			for (int k = 0; k < valueArr.length; k++) {
				if (!valueArr[k].equals("") && Integer.parseInt(valueArr[k]) != Integer.parseInt(category)) {
					// 查找该二级分类
					pc = problemCategoryDao.findProblemCategoryById(Integer.parseInt(valueArr[k]));
					if (pc.getParentId() != 0) {
						// 查找该二级分类的父分类
						pc_parent = problemCategoryDao.findProblemCategoryById(pc.getParentId());

						// 得到该父类名称前需要加几个空格，以控制显示整齐
						int pclength = mlength - pc_parent.getName().length();
						String space = "";
						for (int l = 0; l < pclength; l++) {
							space = space + "&emsp;";
						}
						String parentName = space + pc_parent.getName();

						otherCategoryString = otherCategoryString + parentName + "—>" + pc.getName() + "<br>";
					}
				}
			}
			p.setOtherCategory(otherCategoryString);
		}
		return list;
	}

	@Override
	public String addProblemClassification(int id, String category,int duration) {
		try {
			if(Integer.parseInt(category)==-1){
				return "二级分类不能为空！";
			}
			// 保存题目分类
			String res = problemsDao.addProblemClassification(id, category,duration);
			if (res != null) {
				return res; // 返回错误信息
			}
			return null;

		} catch (Exception e) {
			return e.toString();
		}
	}

	@Override
	public List<PMProblems> findProblemClassifications(int problemId) {
		// 查询该题目所有的题目分类category
		String res = problemsDao.findProblemClassifications(problemId);
		List<PMProblems> list = new ArrayList<PMProblems>();
		if(res!=null){
			// 1 先将该题目的category取出,转换成数组
			String[] valueArr = res.split(",");

			ProblemCategory pc = new ProblemCategory();
			ProblemCategory pc_parent = new ProblemCategory();

			// 循环添加其他分类的名称
			for (int j = 0; j < valueArr.length; j++) {
				// 查找该二级分类
				if (!valueArr[j].equals("")) {
					PMProblems p = new PMProblems();
					pc = problemCategoryDao.findProblemCategoryById(Integer.parseInt(valueArr[j]));
					// 设置二级分类的名称
					p.setId(problemId);
					p.setSecondClassificationId(Integer.parseInt(valueArr[j]));
					p.setSecondClassification(pc.getName());
					if (pc.getParentId() != 0) {
						// 查找该二级分类的父分类
						pc_parent = problemCategoryDao.findProblemCategoryById(pc.getParentId());
						p.setFirstClassification(pc_parent.getName());
						p.setFirstClassificationId(pc_parent.getId());
					}
					list.add(p);
				}

			}
		}
		return list;
	}

	@Override
	public int findProblemDuration(int problemId) {
		// 查询该题目的做题预估时间
		int duration = problemsDao.findProblemDuration(problemId);
		return duration;

	}


	@Override
	public String deleteProblemClassification(Integer id, String secondClassification) {
		try {
			String res = problemsDao.deleteProblemClassification(id, secondClassification);
			if (res != null) {
				return res; // 返回错误信息
			}
			return null;

		} catch (Exception e) {
			return e.toString();
		}
	}

}
