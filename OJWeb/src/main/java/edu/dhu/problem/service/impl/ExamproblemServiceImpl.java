package edu.dhu.problem.service.impl;

import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.exam.dao.ExamDaoI;
import edu.dhu.exam.dao.PaperExamDaoI;
import edu.dhu.exam.model.DataGrid;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.PaperExamproblems;
import edu.dhu.problem.dao.ChapterDaoI;
import edu.dhu.problem.dao.ExamproblemDaoI;
import edu.dhu.problem.dao.ProblemDaoI;
import edu.dhu.problem.dao.ProblemsDaoI;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.ExamproblemServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("examproblemService")
@Transactional
public class ExamproblemServiceImpl implements ExamproblemServiceI {

	private static final Logger logger = Logger
			.getLogger(ExamproblemServiceImpl.class);

	@Resource
	private ExamproblemDaoI examproblemDao;

//	private PaperExamproblemDaoI paperexamproblemDao;
	
	private ChapterDaoI chapterDao;

	private ProblemDaoI problemDao;
	private ProblemsDaoI problemsDao;
	
	
	private ExamDaoI examDao;
	private PaperExamDaoI paperexamDao;
	private AdminusersDaoI adminuserDao;
	public ExamproblemDaoI getExamproblemDao() {
		return examproblemDao;
	}

	@Autowired
	public void setExamproblemDao(ExamproblemDaoI examproblemDao) {
		this.examproblemDao = examproblemDao;
	}

//	public PaperExamproblemDaoI getPaperExamproblemDao() {
//		return paperexamproblemDao;
//	}
//
//	@Autowired
//	public void setPaperExamproblemDao(PaperExamproblemDaoI paperexamproblemDao) {
//		this.paperexamproblemDao = paperexamproblemDao;
//	}


	
	public ExamDaoI getExamDao() {
		return examDao;
	}

	@Autowired
	public void setExamDao(ExamDaoI examDao) {
		this.examDao = examDao;
	}

	public PaperExamDaoI getPaperExamDao() {
		return paperexamDao;
	}

	@Autowired
	public void setPaperExamDao(PaperExamDaoI paperexamDao) {
		this.paperexamDao = paperexamDao;
	}

	
	public ChapterDaoI getChapterDao() {
		return chapterDao;
	}

	@Autowired
	public void setChapterDao(ChapterDaoI chapterDao) {
		this.chapterDao = chapterDao;
	}

	public ProblemDaoI getProblemDao() {
		return problemDao;
	}

	@Autowired
	public void setProblemDao(ProblemDaoI problemDao) {
		this.problemDao = problemDao;
	}


	public ProblemsDaoI getProblemsDao() {
		return problemsDao;
	}

	@Autowired
	public void setProblemsDao(ProblemsDaoI problemsDao) {
		this.problemsDao = problemsDao;
	}

	@Autowired
	public void setAdminusersDao(AdminusersDaoI adminuserDao) {
		this.adminuserDao = adminuserDao;
	}

	public AdminusersDaoI getAdminusersDao() {
		return adminuserDao;
	}

	private void changeModel(List<Examproblems> l, List<PMExamproblem> nl) {
		if (l != null && l.size() > 0) {
			for (Examproblems t : l) {
				PMExamproblem u = new PMExamproblem();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}
//	private void changePaperModel(List<PaperExamproblems> l, List<PMPaperExamproblem> nl) {
//		if (l != null && l.size() > 0) {
//			for (PaperExamproblems t : l) {
//				PMPaperExamproblem u = new PMPaperExamproblem();
//				BeanUtils.copyProperties(t, u);
//				nl.add(u);
//			}
//		}
//	}

//	@Override
//	public DataGrid dataGrid(PMExamproblem pExamproblem) {
//		DataGrid dg = new DataGrid();
//		String hql, totalHql;
//		long totalLines = 0;
//		List<Examproblems> theExamproblemsList = null;
//		if (pExamproblem.getExamId() != null) {
//			hql = "select e from Examproblems e where e.examId=:examId order by e.displaySequence";
//			totalHql = "select count(*) from Examproblems e where e.examId=:examId";
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("examId", pExamproblem.getExamId());
//			totalLines = examproblemDao.count(totalHql, params);
//			theExamproblemsList = examproblemDao.find(hql, params,
//					pExamproblem.getPage(), pExamproblem.getRows());
//		}
//		if (theExamproblemsList == null) {
//			logger.info("++++++++查找考试题目列表为空++++++++");
//			return null;
//		} else {
//			logger.info("++++++++查找考试题目列表成功++++++++");
//			List<PMExamproblem> thePMExamproblemList = new ArrayList<PMExamproblem>();
//			changeModel(theExamproblemsList, thePMExamproblemList);
//			HashMap problemMap = new HashMap();
//			List<Problems> problemList = problemsDao
//					.findExamProblems(pExamproblem.getExamId());
//			for (int i = 0; i < problemList.size(); i++) {
//				problemMap.put(problemList.get(i).getId(), problemList.get(i));
//			}
//			HashMap chapterMap = new HashMap();
//			List<PMChapters> chapterList = chapterDao.findAllChapter();
//			for (int i = 0; i < chapterList.size(); i++) {
//				chapterMap
//						.put(chapterList.get(i).getCode(), chapterList.get(i));
//			}
//			for (int i = 0; i < thePMExamproblemList.size(); i++) {
//				PMExamproblem ep = thePMExamproblemList.get(i);
//				ep.setPage(pExamproblem.getPage());
//				ep.setRows(pExamproblem.getRows());
//				Problems p = (Problems) problemMap.get(ep.getProblemId());
//				if (p != null) {
//					PMChapters c = (PMChapters) chapterMap.get(p
//							.getchapterCode());
//					ep.setTitle(p.getTitle());
//					ep.setDescription(p.getDescription());
//					ep.setSource(p.getSource());
//					ep.setDifficulty(p.getDifficulty());
//					if (c != null)
//						ep.setChapterName(c.getChapterName());
//					else
//						ep.setChapterName("");
//				}
//			}
//			dg.setTotalLines(totalLines);
//			dg.setPageLines(pExamproblem.getRows());
//			dg.setTotalPages((long) (Math.ceil((double) dg.getTotalLines()
//					/ (double) dg.getPageLines())));
//			dg.setCurrentPage(pExamproblem.getPage());
//			dg.setCurrentPageLineNum(thePMExamproblemList.size());
//			dg.setRows(thePMExamproblemList);
//			return dg;
//		}
//
//	}


	@Override
	public DataGrid dataGrid(PMExamproblem pExamproblem) {
		return null;
	}

	@Override
	public List<Examproblems> getProblemByExamId(int examId) {
		// TODO Auto-generated method stub
		List<Examproblems> theExamproblemsList = null;
		String hql;
		hql = "select e from Examproblems e where e.examId=:examId order by e.displaySequence";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		theExamproblemsList = examproblemDao.find(hql, params);
		if (theExamproblemsList == null) {
			logger.info("++++++++查找考试题目列表为空++++++++");
		} else {
			logger.info("++++++++查找考试题目列表成功++++++++");
		}
		return theExamproblemsList;
	}

	@Override
	public List<PaperExamproblems> getPaperProblemByExamId(int paperexamId) {
		return null;
	}

	@Override
	//TODO add by 周海水
	public List<PMProblems> getProblemsInfoByExamId(int examId) {
		List<PMProblems> theExamproblemsList = null;

		theExamproblemsList = problemsDao.findAllProblemsByExamId(examId);
		if (theExamproblemsList == null) {
			logger.info("++++++++查找考试题目列表为空++++++++");
		} else {
			logger.info("++++++++查找考试题目列表成功++++++++");
		}
		return theExamproblemsList;
	}

//	@Override
//	public List<PaperExamproblems> getPaperProblemByExamId(int paperexamId) {
//		// TODO Auto-generated method stub
//		List<PaperExamproblems> theExamproblemsList = null;
//		String hql;
//		hql = "select e from PaperExamproblems e where e.examId=:paperexamId order by e.displaySequence";
//
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("paperexamId", paperexamId);
//		theExamproblemsList = paperexamproblemDao.find(hql, params);
//		if (theExamproblemsList == null) {
//			logger.info("++++++++查找考试题目列表为空++++++++");
//		} else {
//			logger.info("++++++++查找考试题目列表成功++++++++");
//		}
//		return theExamproblemsList;
//	}
//
	@Override
	public String remove(PMExamproblem pExamproblem) {
		int maxDisplaySequence = -1;
		if (pExamproblem.getId() != null) {
			Examproblems theExamProblem = examproblemDao.get(
					Examproblems.class, pExamproblem.getId());
			if (theExamProblem != null) {
				// id正确
				maxDisplaySequence = getMaxDisplaySequence(theExamProblem
						.getExamId());
				if (maxDisplaySequence != -1
						&& maxDisplaySequence != theExamProblem
								.getDisplaySequence()) {
					// 删除的不是最后一道题，需要修正展示顺序
					examproblemDao.SubDisplaySequence(
							theExamProblem.getExamId(),
							theExamProblem.getDisplaySequence(),
							maxDisplaySequence);
				}
				// 删除
				examproblemDao.delete(theExamProblem);
				// 将exam表中对应id的考试的题目总数-1
				Exam theExam = examDao.get(Exam.class,
						theExamProblem.getExamId());
				theExam.setProblemNum(theExam.getProblemNum() - 1);
				// not done yet
				return "success";
			}
		}
		return null;
	}

	@Override
	public String alterDisplaySequence(PMExamproblem pExamproblem) {
		int maxDisplaySequence = -1;
		if (pExamproblem.getId() != null
				&& pExamproblem.getDisplaySequence() > 0) {
			Examproblems theExamProblem = examproblemDao.get(
					Examproblems.class, pExamproblem.getId());
			// id确实存在
			if (theExamProblem != null) {
				// 显示顺序没有变化
				if (theExamProblem.getDisplaySequence() == pExamproblem
						.getDisplaySequence())
					return "success";
				// 获取最大的显示序号
				maxDisplaySequence = getMaxDisplaySequence(theExamProblem
						.getExamId());

				// 修改的显示序号不能大于最大的显示序号
				if (pExamproblem.getDisplaySequence() <= maxDisplaySequence) {
					if (pExamproblem.getDisplaySequence() > theExamProblem
							.getDisplaySequence()) {
						// 修正显示顺序
						examproblemDao.SubDisplaySequence(
								theExamProblem.getExamId(),
								theExamProblem.getDisplaySequence(),
								pExamproblem.getDisplaySequence());
						// 保存
						theExamProblem.setDisplaySequence(pExamproblem
								.getDisplaySequence());
						examproblemDao.save(theExamProblem);
						return "success";

					}
					if (pExamproblem.getDisplaySequence() < theExamProblem
							.getDisplaySequence()) {
						// 修正显示顺序
						examproblemDao.AddDisplaySequence(
								theExamProblem.getExamId(),
								pExamproblem.getDisplaySequence(),
								theExamProblem.getDisplaySequence());
						// 保存
						theExamProblem.setDisplaySequence(pExamproblem
								.getDisplaySequence());
						examproblemDao.save(theExamProblem);
						return "success";
					}
				}
			}
		}
		return null;
	}

	@Override
	public String add(PMExamproblem pExamproblem) {
		int maxDisplaySequence = -1;
		if (pExamproblem.getScore() > 0) {
			// Score存在，且大于0
			if (pExamproblem.getExamId() != null) {
				// 参数ExamId存在
				Exam theExam = examDao
						.get(Exam.class, pExamproblem.getExamId());
				if (theExam != null) {
					// ExamId对应的考试存在
					if (pExamproblem.getProblemId() != null) {
						// 参数ProblemId存在
						Problems theProblem = problemDao.get(Problems.class,
								pExamproblem.getProblemId());
						if (theProblem != null) {
							// ProblemId对应的考试存在
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("examId", pExamproblem.getExamId());
							params.put("problemId", pExamproblem.getProblemId());
							String findExamproblemHql = "select e from Examproblems e where e.examId=:examId and e.problemId=:problemId";
							Examproblems theExamproblem = examproblemDao.get(
									findExamproblemHql, params);
							if (theExamproblem == null) {
								// 该考试中不存在这道题
								// 获取最大的展示序号
								maxDisplaySequence = getMaxDisplaySequence((pExamproblem
										.getExamId()));
								if (pExamproblem.getDisplaySequence() > 0
										&& pExamproblem.getDisplaySequence() <= maxDisplaySequence + 1) {
									// 展示序号存在，且正确
									// System.out.println("运行到这了！");
									if (maxDisplaySequence != pExamproblem
											.getDisplaySequence() - 1) {
										// 如果不是最后一道题,需要调整顺序
										examproblemDao.AddDisplaySequence(
												pExamproblem.getExamId(),
												pExamproblem
														.getDisplaySequence(),
												maxDisplaySequence + 1);
									}
									// 添加
									theExamproblem = new Examproblems();
									theExamproblem.setExamId(pExamproblem
											.getExamId());
									theExamproblem.setProblemId(pExamproblem
											.getProblemId());
									theExamproblem.setScore(pExamproblem
											.getScore());
									theExamproblem
											.setDisplaySequence(pExamproblem
													.getDisplaySequence());
									examproblemDao.save(theExamproblem);
									// 将exam表中对应id的考试的题目总数+1
									theExam.setProblemNum(theExam
											.getProblemNum() + 1);
									examDao.saveOrUpdate(theExam);
									return "success";
								}
							}
						}
					}
				}
			}
		}
		return null;

	}

	

	
	@Override
	public int getMaxDisplaySequence(int examId) {
		return examproblemDao.getMaxDisplaySequenceByExamId(examId);
	}

	@Override
	public DataGrid getDataGridNotInExam(PMExamproblem pExamproblem) {
		return null;
	}


//	@Override
//	public DataGrid getDataGridNotInExam(PMExamproblem pExamproblem) {
//		DataGrid dg = new DataGrid();
//		String hql, totalHql;
//		long totalLines = 0;
//		String sortContent = pExamproblem.getSortContent();
//		String sortType = pExamproblem.getSortType();
//		List<Problems> theProblemList = null;
//		if (pExamproblem.getExamId() != null) {
//			// ExamId存在
//			Exam theExam = examDao.get(Exam.class, pExamproblem.getExamId());
//			// 筛选条件
//			String keywords = pExamproblem.getKeywords();
//			String courseCode = pExamproblem.getCourseCode();
//			String chapterCode = pExamproblem.getChapterCode();
//			String difficulty = pExamproblem.getDifficulty();
//			String source = pExamproblem.getSource();
//			int teacherId = pExamproblem.getTeacherId();
//			String eliminateExams = pExamproblem.getEliminateExams();
//			String condition = ""; // 用于拼接hql
//			if (theExam != null) {
//				// Exam存在
//				if (keywords.equals("") == false) {
//					condition = condition
//							+ " and (title like :keywords or description like :keywords)";
//				}
//				if (!courseCode.equals("") && chapterCode.equals("")) {
//					condition = condition + " and chapterCode like'"
//							+ courseCode + "%'";
//				}
//				if (!chapterCode.equals("")) {
//					condition = condition + " and chapterCode='" + chapterCode+"'";
//				}
//				if (source.equals("") == false) {
//					condition = condition + " and source like :source";
//				}
//				if (difficulty.equals("全部") == false) {
//					condition = condition + " and difficulty='" + difficulty
//							+ "'";
//				}
//				if (teacherId != 0) // 筛选老师
//				{
//					condition = condition + " and teacherId=" + teacherId;
//				}
//				if (sortContent.equals("编号")) {
//					condition = condition + " order by p.id";
//					if (sortType.equals("升序"))
//						condition = condition + " asc";
//					else
//						condition = condition + " desc";
//				}
//				String condition2 = "";
//				if (eliminateExams != null) {
//					String[] EExams = eliminateExams.split(";");
//
//					if (EExams.length > 0) {
//						for (int i = 0; i < EExams.length; i++) {
//							if (!EExams[i].equals("")) {
//								int examId = Integer.parseInt(EExams[i]);
//								if (examId != pExamproblem.getExamId())
//									condition2 = condition2 + " or e.examId="
//											+ examId;
//							}
//						}
//					}
//				}
//				hql = "select p from Problems p where p.id not in(select e.problemId from Examproblems e where e.examId=:examId "
//						+ condition2 + ")" + condition;
//				Map<String, Object> param = new HashMap<String, Object>();
//				param.put("examId", pExamproblem.getExamId());
//				if (keywords.equals("") == false)
//					param.put("keywords", "%" + pExamproblem.getKeywords()
//							+ "%");
//				if (source.equals("") == false)
//					param.put("source", "%" + pExamproblem.getSource() + "%");
//				theProblemList = problemDao.find(hql, param);
//			}
//		}
//		if (theProblemList == null) {
//			return null;
//		} else if (theProblemList.size() == 0) {
//			return dg;
//		} else {
//			HashMap chapterMap = new HashMap();
//			List<PMChapters> chapters = new ArrayList();
//			String courseCode = pExamproblem.getCourseCode();
//			if (!courseCode.equals("")) {
//				chapters = chapterDao.findChaptersBycourseCode(courseCode);
//			} else {
//				chapters = chapterDao.findAllChapter();
//			}
//			for (int i = 0; i < chapters.size(); i++) // 建立章节的hashmap
//			{
//				PMChapters chapter = chapters.get(i);
//				chapterMap.put(chapter.getCode(), chapter);
//			}
//			HashMap teacherMap = new HashMap();
//			List<PMAdminusers> teacherList = adminuserDao.findAllAdminusers();
//			for (int i = 0; i < teacherList.size(); i++) {
//				teacherMap.put(teacherList.get(i).getId(), teacherList.get(i));
//			}
//			List<PMProblem> thePMProblemList = new ArrayList<PMProblem>();
//			for (Problems t : theProblemList) {
//				PMProblem u = new PMProblem();
//				u.setId(t.getId());
//				u.setTitle(t.getTitle());
//				u.setDescription(t.getDescription());
//				u.setSource(t.getSource());
//				u.setDifficulty(t.getDifficulty());
//				String chapterCode = t.getchapterCode();
//				PMChapters chapter = (PMChapters) chapterMap.get(chapterCode);
//				if (chapter != null) {
//					u.setChapterName(chapter.getChapterName());
//					u.setCourseName(chapter.getName());
//				}
//
//				PMAdminusers teacher = (PMAdminusers) teacherMap.get(t
//						.getTeacherId().intValue());
//				if (teacher != null)
//					u.setTeacherName(teacher.getName());
//				thePMProblemList.add(u);
//			}
//			dg.setTotalLines(totalLines);
//			dg.setRows(thePMProblemList);
//			List<PMProblem> list = thePMProblemList;
//			int position = 0;
//			HashMap difficultyMap = new HashMap(); // 对题目难度建立hash表
//			difficultyMap.put("易", 1); // 对难度进行划分
//			difficultyMap.put("中", 2);
//			difficultyMap.put("难", 3);
//			Collator c = Collator.getInstance(Locale.CHINA);
//			for (int i = 0; i < list.size() - 1; i++) {
//				position = i;
//				for (int j = i + 1; j < list.size(); j++) {
//					if (sortContent.equals("标题")) {
//						if (sortType.equals("升序")) // 进行升序
//						{
//							if (c.compare(list.get(j).getTitle(),
//									list.get(position).getTitle()) < 0) {
//								position = j;
//							}
//						} else {
//							if (c.compare(list.get(j).getTitle(),
//									list.get(position).getTitle()) >= 0) {
//								position = j;
//							}
//						}
//					}
//					if (sortContent.equals("题目来源")) {
//						if (list.get(j).getSource() == null)
//							list.get(j).setSource("");
//						if (list.get(position).getSource() == null)
//							list.get(position).setSource("");
//						if (sortType.equals("升序")) // 进行升序
//						{
//							if (c.compare(list.get(j).getSource(),
//									list.get(position).getSource()) < 0) {
//								position = j;
//							}
//						} else {
//							if (c.compare(list.get(j).getSource(),
//									list.get(position).getSource()) >= 0) {
//								position = j;
//							}
//						}
//					}
//					if (sortContent.equals("章节")) {
//						if (sortType.equals("升序")) // 进行升序
//						{
//							if (c.compare(list.get(j).getChapterName(), list
//									.get(position).getChapterName()) < 0) {
//								position = j;
//							}
//						} else {
//							if (c.compare(list.get(j).getChapterName(), list
//									.get(position).getChapterName()) >= 0) {
//								position = j;
//							}
//						}
//					}
//					if (sortContent.equals("难度")) {
//						int d1 = 0, d2 = 0;
//						if (difficultyMap.get(list.get(position)
//								.getDifficulty()) != null)
//							d1 = (int) difficultyMap.get(list.get(position)
//									.getDifficulty());
//						if (difficultyMap.get(list.get(j).getDifficulty()) != null)
//							d2 = (int) difficultyMap.get(list.get(j)
//									.getDifficulty());
//						if (sortType.equals("升序")) // 进行升序
//						{
//
//							if (d1 >= d2) {
//								position = j;
//							}
//						} else {
//							if (d1 > d2) {
//								position = j;
//							}
//						}
//					}
//					if (sortContent.equals("教师")) {
//						if (sortType.equals("升序")) // 进行升序
//						{
//							if (c.compare(list.get(j).getTeacherName(), list
//									.get(position).getTeacherName()) < 0) {
//								position = j;
//
//							}
//						} else {
//							if (c.compare(list.get(j).getTeacherName(), list
//									.get(position).getTeacherName()) > 0) {
//								position = j;
//							}
//						}
//					}
//				}
//				if (i != position) {
//					PMProblem temp = list.get(i);
//					list.set(i, list.get(position));
//					list.set(position, temp);
//				}
//			}
//			return dg;
//		}
//	}

	
	@Override
	public int getExamproblemNum(int examId) {
		return examproblemDao.getExamproblemNum(examId);
	}


	
	@Override
	public Examproblems getExamproblemsByExamIdAndProblemId(int examId,
															int problemId) {
		return examproblemDao.getExamproblemsByExamIdAndProblemId(examId,
				problemId);
	}

	@Override
	public PMExamproblem getProblemByExamProblemId(int id) { // 根据examproblem
																// id获取题目信息
		// TODO Auto-generated method stub
		Examproblems examproblem = examproblemDao.get(Examproblems.class, id);
		PMExamproblem p = new PMExamproblem();
		if (examproblem != null) {
			p.setId(id);
			p.setScore(examproblem.getScore());
			p.setProblemId(examproblem.getProblemId());
			p.setDisplaySequence(examproblem.getDisplaySequence());
			p.setDeadline(examproblem.getDeadline());
			if (examproblem.getProblemId() != null) {
				Problems pd = problemDao.get(Problems.class,
						examproblem.getProblemId());
				if (pd != null) {
					p.setDescription(pd.getDescription());
					PMChapters pmc = chapterDao.findChaptersByCode(pd
							.getchapterCode());
					if (pmc != null) {
						p.setChapterName(pmc.getName());
					}
					p.setDifficulty(pd.getDifficulty());
					p.setSource(pd.getSource());
					p.setTitle(pd.getTitle());

				}

			}
			return p;
		}
		return null;
	}

	@Override
	public boolean alterScore(int id, float score, int examId) { // 更改考试 题目的分数
		// TODO Auto-generated method stub

		return examproblemDao.alterScore(id, score, examId);
	}

	@Override
	public boolean alterDeadline(int id, Date deadline, Date updateTime,
			int examId) {
		// TODO Auto-generated method stub
		return examproblemDao.alterDeadline(id, deadline, updateTime, examId);
	}

	@Override
	public boolean alterbestBeforeAndscoreCoef(Integer id, Date bestBefore,
			Date updateTime, float scoreCoef, Integer examId) {
		// TODO Auto-generated method stub
		return examproblemDao.alterbestBeforeAndscoreCoef(id, bestBefore,
				updateTime, scoreCoef, examId);
	}

	@Override
	public Date getDeadline(int examId, int problemId) {
		Examproblems examproblems = examproblemDao
				.getExamproblemsByExamIdAndProblemId(examId,problemId);
		Date deadline = examproblems.getDeadline();
		return deadline;
	}

}
