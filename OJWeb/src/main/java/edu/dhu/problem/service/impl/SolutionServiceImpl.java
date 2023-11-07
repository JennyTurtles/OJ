package edu.dhu.problem.service.impl;

import edu.dhu.cache.ExamCacheManager;
import edu.dhu.exam.dao.ExamDaoI;
import edu.dhu.exam.dao.ItrainProblemCatDaoI;
import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.dao.StudentexaminfoDaoI;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.ExamStudent;
import edu.dhu.exam.model.ItrainProbCatgory;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.Constant;
import edu.dhu.global.model.RespBean;
import edu.dhu.problem.dao.*;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.CheckSimilarityServiceI;
import edu.dhu.problem.service.SimilaritywarningServiceI;
import edu.dhu.problem.service.SolutionServiceI;
import edu.dhu.solution.service.SubmittedcodeServiceI;
import edu.dhu.user.dao.UserDaoI;
import edu.dhu.user.model.Json;
import edu.dhu.user.model.Users;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("solutionService")
@Transactional
public class SolutionServiceImpl implements SolutionServiceI {
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private GradeProblemServiceImpl gradeProblemService;
	@Resource
	private WrongcasesDaoI WrongcasesDao;
	@Resource
	private StudentexamdetailServiceI studentexamdetailService;

	@Resource
	private CheckSimilarityServiceI checkSimilarityService;
	@Resource
	private SimilaritywarningServiceI similaritywarningService;
	@Resource
	private SubmittedcodeServiceI submittedcodeService;
	@Resource
	private SimilaritywarningDaoI similaritywarningDao;
	@Resource
	private SubmittedcodeDaoI submittedcodeDao;
	@Resource
	private StudentTrainDetailDaoI studentTrainDetailDao;
	@Resource
	private SolutionDaoI solutionDao;
	@Resource
	private UserDaoI userDao;
	@Resource
	private ExamproblemDaoI examproblemDao;
	@Resource
	private ProblemsDaoI problemsDao;

//	private SimilaritywarningDaoI similaritywarningDao;
	private StudentexamdetailDaoI studentexamdetailDao;
	private StudentexaminfoDaoI studentexaminfoDao;
//	private SubmittedcodeDaoI submittedcodeDao;
	private ExamDaoI examDao;
//	private ClassesServiceI classesServiceI;
//	private ClassstudentsDaoI classstudentsDao;
//	private ItrainproblemDaoI itrainproblemDao;
//	private StudentTrainDetailDaoI studentTrainDetailDao;
//	private StudentTrainInfoDaoI studentTrainInfoDao;
//	private GradeProblemServiceI gradeProblemService;
	private ItrainProblemCatDaoI itrainProblemCatDao;

	@Autowired
	public void setStudentexamdetailDao(StudentexamdetailDaoI studentexamdetailDao) {
		this.studentexamdetailDao = studentexamdetailDao;
	}

	public StudentexamdetailDaoI getStudentexamdetailDao() {
		return studentexamdetailDao;
	}

	@Override
	public boolean submitCode(Solution solution) {
		return solutionDao.submitCode(solution);
	}

	@Autowired
	private void setStudentexaminfoDao(StudentexaminfoDaoI studentexaminfoDao) {
		this.studentexaminfoDao = studentexaminfoDao;
	}

	public StudentexaminfoDaoI getStudentexaminfoDao() {
		return studentexaminfoDao;
	}


	@Override
	public String[] getCorrectCaseIds(int solutionId) {
		// 根据solutionID获取整条记录
		Solution solution = solutionDao.get(Solution.class, solutionId);
		// 返回正确的测试用例
		// System.out.println("solution 的正确测试用例字断值为:" +
		// solution.getCorrectCaseIds());

		if (solution.getCorrectCaseIds() != null && solution.getCorrectCaseIds() != "-1") {
			return solution.getCorrectCaseIds().split(",");
		}
		return null;
	}

	// 根据userID,examID,problemID查找solutionID最大的记录
	@Override
	public Solution getLastSolutionByUserIdExamIdProblemId(int userId, int examId, int problemId) {
		return solutionDao.getLastSolutionByUserIdExamIdProblemId(userId, examId, problemId);
	}

	@Override
	public void updateSolution(Solution solution) {
		solutionDao.update(solution);
	}

	@Override
	public List<PMSolution> getExamSubmitSolution(boolean isLast, int examId, int teacherId, int nowPage, int pageSize, String displaySequence, String studentNo, String name, String banji, float similarity, String searchTime) {
		return null;
	}

	@Override
	public List<PMSolution> getTrainSubmitSolution(boolean isLast, int examId, int teacherId, int nowPage, int pageSize, String studentNo, String name, String banji, float similarity, String searchTime) {
		return null;
	}

	@Override
	public Solution getLastSolutionByUserIdExamIdProblemIdAndStatus(int userId, int examId, int problemId,
			String status) {
		return solutionDao.getLastSolutionByUserIdExamIdProblemIdAndStatus(userId, examId, problemId, status);
	}

//	@Override
//	public List<PMSolution> getExamSubmitSolution(boolean isLast, int examId, int teacherId, int nowPage, int pageSize,
//												  String displaySequence, String studentNo, String name, String banji, float similarity, String searchTime) {
//		List<Solution> solutionList = solutionDao.getExamSubmitSolution(isLast, examId,teacherId, nowPage, pageSize,
//					displaySequence, studentNo, name, banji, similarity, searchTime);
//		UsersCacheManager userManager = UsersCacheManager.getInstance();
//		List<Users> userList = (List<Users>) userManager.getObject("exam_" + examId);
//		if (userList == null) {
//			if(teacherId==4){//如果是管理员，获取该考试的全部学生
//				userList = userDao.getUsersByExamId(examId);
//			}else{
//				userList = userDao.getUsersByExamIdAndTeacherId(examId, teacherId);
//			}
//			userManager.putObject("exam_" + examId, userList);
//		}
//		HashMap<Integer, Users> userMap = new HashMap<Integer, Users>();
//		List<PMSolution> pList = new ArrayList<PMSolution>();
//		for (int i = 0; i < userList.size(); i++)
//			userMap.put(userList.get(i).getId(), userList.get(i));
//		ExamproblemsCacheManager examproblemsManager = ExamproblemsCacheManager.getInstance();
//		List<Examproblems> examproblemList = (List<Examproblems>) examproblemsManager.getObject(examId + "");
//		if (examproblemList == null) {
//			examproblemList = examproblemDao.getDisplaySequenceByExamId(examId); // 所有dispalysequence
//			examproblemsManager.putObject(examId + "", examproblemList);
//		}
//		HashMap<Integer, Examproblems> examproblemMap = new HashMap<Integer, Examproblems>();
//		List<Problems> problemList = problemsDao.findExamProblems(examId);
//		HashMap<Integer, Problems> problemMap = new HashMap<Integer, Problems>();
//		for (int i = 0; i < examproblemList.size(); i++) {
//			examproblemMap.put(examproblemList.get(i).getProblemId(), examproblemList.get(i));
//			problemMap.put(problemList.get(i).getId(), problemList.get(i));
//		}
//		HashMap<Integer, Boolean> submitedDetailMap = new HashMap<Integer, Boolean>(); // 已经提交的detail
//		List<Object[]> submitedDetailList;
//		submitedDetailList = studentexamdetailDao.getSubmitedDetailByExamId(examId);
//
//		for (int i = 0; i < submitedDetailList.size(); i++) // 已经提交的solution
//		{
//			if (submitedDetailList.get(i) != null) {
//				Object[] obj = submitedDetailList.get(i);
//				Integer si = (Integer) obj[1];
//				submitedDetailMap.put(si.intValue(), true);
//			}
//
//		}
//		for (int i = 0; i < solutionList.size(); i++) {
//			PMSolution p = new PMSolution();
//			Solution s = solutionList.get(i);
//
//			Users user = (Users) userMap.get(s.getUserid());
//			if (user != null) {
//				p.setStudentNo(user.getStudentNo());
//				p.setChineseName(user.getChineseName());
//				p.setBanji(user.getBanji());
//			}
//			p.setId(s.getId());
//			p.setSubmitTime(s.getSubmitTime());
//			p.setProblemId(s.getProblemId());
//			Examproblems ex = (Examproblems) examproblemMap.get(s.getProblemId());
//			if (ex != null)
//				p.setDisplaySequence((ex).getDisplaySequence());
//			Problems problem = (Problems) problemMap.get(s.getProblemId());
//
////			 if (problem != null) {
////			 p.setTitle(problem.getTitle());
////			 p.setProblemId(problem.getId());
////			 p.setSimilarityThreshold(problem.getSimilarityThreshold());
////			 p.setFlag(false);
//			 // 判断学生是否属于当前登陆老师的班级
////			 List<Classes> classList = classesServiceI
////			 .findNotEndClassByTeacherId(problem.getTeacherId());
////			 for (int j = 0; j < classList.size(); j++) {
////			 boolean result = classstudentsDao.findClassStudentByUserId(
////			 user.getId(), classList.get(j).getId());
////			 if (result) {
////			 p.setFlag(true);
////			 break;
////			 }
////			 }
////			 }
//
//			if (problem != null) {
//				p.setTitle(problem.getTitle());
//				p.setProblemId(problem.getId());
//				p.setSimilarityThreshold(problem.getSimilarityThreshold());
//				p.setFlag(true);
//			}
//			p.setStatus(s.getStatus());
//			p.setScore(s.getScore());
//			p.setSimilarity(s.getSimilarity());
//			p.setLanguage(s.getLanguage());
//			p.setCorrectCaseIds(s.getCorrectCaseIds());
//			p.setSourceCode(s.getSourceCode());
//			p.setSimilarId(s.getSimilarId());
//			if (submitedDetailMap.get(s.getId()) != null)
//				p.setSubmited(true);
//			else
//				p.setSubmited(false);
//			pList.add(p);
//		}
//		// TODO Auto-generated method stub
//		return pList;
//	}
//
//	@Override
//	public List<PMSolution> getTrainSubmitSolution(boolean isLast, int examId,int teacherId, int nowPage, int pageSize,
//			 String studentNo, String name, String banji, float similarity, String searchTime) {
//		List<Solution> solutionList = solutionDao.getTrainSubmitSolution(isLast, examId,teacherId, nowPage, pageSize,
//					studentNo, name, banji, similarity, searchTime);
//		List<Users> userList = new ArrayList<Users>();
//		if(teacherId==4){//如果是管理员，获取该考试的全部学生
//			userList = userDao.getUsersByExamId(examId);
//		}else{
//			userList = userDao.getUsersByExamIdAndTeacherId(examId, teacherId);
//		}
//		HashMap<Integer, Users> userMap = new HashMap<Integer, Users>();
//		List<PMSolution> pList = new ArrayList<PMSolution>();
//		for (int i = 0; i < userList.size(); i++)
//			userMap.put(userList.get(i).getId(), userList.get(i));
////		ExamproblemsCacheManager examproblemsManager = ExamproblemsCacheManager.getInstance();
////		List<Itrainproblems> trainproblemList = (List<Itrainproblems>) examproblemsManager.getObject(examId + "");
////		if (trainproblemList == null) {
////			trainproblemList = itrainproblemDao.getProblemsByExamId(examId); // 获取所有该训练所有的题目
////			examproblemsManager.putObject(examId + "", trainproblemList);
////		}
//		List<Itrainproblems> trainproblemList =itrainproblemDao.getProblemsByExamId(examId); // 获取所有该训练所有的题目
//		HashMap<Integer, Itrainproblems> trainproblemMap = new HashMap<Integer, Itrainproblems>();
//		List<Problems> problemList = problemsDao.findTrainProblems(examId);
//		HashMap<Integer, Problems> problemMap = new HashMap<Integer, Problems>();
//		for (int i = 0; i < trainproblemList.size(); i++) {
//			trainproblemMap.put(trainproblemList.get(i).getProblemId(), trainproblemList.get(i));
//			problemMap.put(problemList.get(i).getId(), problemList.get(i));
//		}
//		HashMap<Integer, Boolean> submitedDetailMap = new HashMap<Integer, Boolean>(); // 已经提交的detail
//		List<Object[]> submitedDetailList;
//		submitedDetailList = studentTrainDetailDao.getSubmitedDetailByExamId(examId);
//
//		if(submitedDetailList.size()>0){
//			for (int i = 0; i < submitedDetailList.size(); i++) // 已经提交的solution
//			{
//				if (submitedDetailList.get(i) != null) {
//					Object[] obj = submitedDetailList.get(i);
//					Integer si = (Integer) obj[1];
//					submitedDetailMap.put(si.intValue(), true);
//				}
//			}
//
//		}
//		if(solutionList.size()>0){
//			//开始设置数据
//			for (int i = 0; i < solutionList.size(); i++) {
//				PMSolution p = new PMSolution();
//				Solution s = solutionList.get(i);
//
//				Users user = (Users) userMap.get(s.getUserid());
//				if (user != null) {
//					p.setStudentNo(user.getStudentNo());
//					p.setChineseName(user.getChineseName());
//					p.setBanji(user.getBanji());
//				}
//				p.setId(s.getId());
//				p.setSubmitTime(s.getSubmitTime());
//				p.setProblemId(s.getProblemId());
//				Itrainproblems ex = (Itrainproblems) trainproblemMap.get(s.getProblemId());
//				if (ex != null)
//					p.setDisplaySequence((ex).getCatId()); //分类id
//				Problems problem = (Problems) problemMap.get(s.getProblemId());
//
//
//				if (problem != null) {
//					p.setTitle(problem.getTitle());
//					p.setProblemId(problem.getId());
//					p.setSimilarityThreshold(problem.getSimilarityThreshold());
//					p.setFlag(true);
//				}
//				p.setStatus(s.getStatus());
//				p.setScore(s.getScore());
//				p.setSimilarity(s.getSimilarity());
//				p.setLanguage(s.getLanguage());
//				p.setCorrectCaseIds(s.getCorrectCaseIds());
//				p.setSourceCode(s.getSourceCode());
//				p.setSimilarId(s.getSimilarId());
//				if (submitedDetailMap.get(s.getId()) != null)
//					p.setSubmited(true);
//				else
//					p.setSubmited(false);
//				pList.add(p);
//			}
//		}
//		return pList;
//	}

	@Override
	public List<PMSolution> exportExamSearchSolution(boolean isLast, int examId, String displaySequence,
			String studentNo, String name, String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		List<Solution> solutionList = solutionDao.exportExamSearchSolution(isLast, examId, displaySequence, studentNo,
				name, banji, similarity, searchTime);
		List<Users> userList = userDao.getUsersByExamId(examId);
		HashMap userMap = new HashMap();
		List<PMSolution> pList = new ArrayList<PMSolution>();
		for (int i = 0; i < userList.size(); i++)
			userMap.put(userList.get(i).getId(), userList.get(i));
		List<Examproblems> examproblemList = examproblemDao.getDisplaySequenceByExamId(examId);
		HashMap examproblemMap = new HashMap();
		List<Problems> problemList = problemsDao.findExamProblems(examId);
		HashMap problemMap = new HashMap();
		for (int i = 0; i < examproblemList.size(); i++) {
			examproblemMap.put(examproblemList.get(i).getProblemId(), examproblemList.get(i));
			problemMap.put(problemList.get(i).getId(), problemList.get(i));
		}
		for (int i = 0; i < solutionList.size(); i++) {
			PMSolution p = new PMSolution();
			Solution s = solutionList.get(i);
			Users user = (Users) userMap.get(s.getUserid());
			if (user != null) {
				p.setStudentNo(user.getStudentNo());
				p.setChineseName(user.getChineseName());
				p.setBanji(user.getBanji());
			}
			p.setId(s.getId());
			p.setSubmitTime(s.getSubmitTime());
			p.setProblemId(s.getProblemId());
			Examproblems ex = (Examproblems) examproblemMap.get(s.getProblemId());
			if (ex != null)
				p.setDisplaySequence((ex).getDisplaySequence());
			Problems problem = (Problems) problemMap.get(s.getProblemId());
			if (problem != null) {
				p.setTitle(problem.getTitle());
				p.setProblemId(problem.getId());
				p.setSimilarityThreshold(problem.getSimilarityThreshold());
			}
			p.setStatus(s.getStatus());
			p.setScore(s.getScore());
			p.setSimilarity(s.getSimilarity());
			p.setLanguage(s.getLanguage());
			p.setCorrectCaseIds(s.getCorrectCaseIds());
			p.setSourceCode(s.getSourceCode());
			pList.add(p);
		}
		// TODO Auto-generated method stub
		return pList;
	}

	@Override
	public List<Solution> exportExamCode(int examId) {
		return solutionDao.exportExamCode(examId);
	}

	@Override
	public List<Solution> exportExamCode(int examId,int teacherId) {
		return solutionDao.exportExamCode(examId,teacherId);
	}


	@Override
	public List<Solution> exportTrainCode(int examId) {
		return solutionDao.exportTrainCode(examId);
	}

	@Override
	public List<Solution> exportTrainCode(int examId,int teacherId) {
		return solutionDao.exportTrainCode(examId,teacherId);
	}

	@Override
	public Solution getSolutionById(int solutionId) {
		return solutionDao.get(Solution.class, solutionId);
	}

	@Override
	public Solution getSourceCode(int id) {
		// TODO Auto-generated method stub
		return solutionDao.getSourceCode(id);
	}

	@Override
	public List<PMSolution> getCopyList(int examId, int teacherId,String displaySequence, String studentNo, String name, String banji,
			float similarity, String searchTime) {
		// TODO Auto-generated method stub
		List<Users> userList = null;
		userList = userDao.getAllStudents();
		HashMap userMap = new HashMap();
		for (int i = 0; i < userList.size(); i++)
			userMap.put(userList.get(i).getId(), userList.get(i));
		List<Examproblems> examproblemList = examproblemDao.getDisplaySequenceByExamId(examId);
		HashMap examproblemMap = new HashMap();
		List<Problems> problemList = problemsDao.findExamProblems(examId);
		HashMap problemMap = new HashMap();
		for (int i = 0; i < examproblemList.size(); i++) {
			examproblemMap.put(examproblemList.get(i).getProblemId(), examproblemList.get(i));
			problemMap.put(problemList.get(i).getId(), problemList.get(i));
		}
		List<Solution> solutionCopyList = solutionDao.getSimilarityObject(examId);
		HashMap<Integer, Solution> solutionCopyMap = new HashMap<Integer, Solution>();
		for (int i = 0; i < solutionCopyList.size(); i++) {
			Solution solution = solutionCopyList.get(i);
			solutionCopyMap.put(solution.getId(), solution);
		}
		List<Object[]> object = solutionDao.getCopyList(examId,teacherId, displaySequence, studentNo, name, banji, similarity,
				searchTime);
		List<PMSolution> pList = new ArrayList<PMSolution>();
		for (int i = 0; i < object.size(); i++) {
			PMSolution p = new PMSolution();
			Object[] s = object.get(i);
			try {

				Integer.parseInt(s[0].toString());
				Users user = (Users) userMap.get(Integer.parseInt(s[0].toString()));
				if (user != null) {
					p.setStudentNo(user.getStudentNo());
					p.setChineseName(user.getChineseName());
					p.setBanji(user.getBanji());
				}
				p.setId((Integer) s[2]);
				p.setSubmitTime((Date) s[6]);
				p.setProblemId(Integer.parseInt(s[1].toString()));
				Examproblems ex = (Examproblems) examproblemMap.get(Integer.parseInt(s[1].toString()));
				if (ex != null) {
					p.setDisplaySequence(ex.getDisplaySequence());
				}
				Problems problem = (Problems) problemMap.get(Integer.parseInt(s[1].toString()));

				if (problem != null) {
					p.setTitle(problem.getTitle());
					p.setProblemId(problem.getId());
					p.setFlag(true);
				}
				p.setSimilarId((Integer) s[5]);
				if (s[5] != null) {
					Solution solution = solutionCopyMap.get(s[5]);
					Users user2 = (Users) userMap.get(solution.getUserid());
					if (user2 != null) {
						p.setStudentNo2(user2.getStudentNo());
						p.setChineseName2(user2.getChineseName());
					}
				}
				p.setSimilarity((Float) s[4]);
				p.setSimilarityThreshold(problem.getSimilarityThreshold());
				p.setSubmited((Boolean) s[7]);
				p.setEversubmit((Integer) s[8]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			pList.add(p);

		}
		return pList;
	}

	@Override
	public List<PMSolution> getTrainCopyList(int examId, int teacherId, String studentNo, String name, String banji, float similarity, String searchTime) {
		return null;
	}

	@Override
	public boolean deleteTrainSubmitBySolutionId(int solutionId, int reason, boolean isCopy, String remark) {
		return false;
	}

//	@Override
//	public List<PMSolution> getTrainCopyList(int examId, int teacherId, String studentNo, String name, String banji,
//			float similarity, String searchTime) {
//		// TODO Auto-generated method stub
//		List<Users> userList = null;
//		userList = userDao.getAllStudents();
//		HashMap userMap = new HashMap();
//		for (int i = 0; i < userList.size(); i++)
//			userMap.put(userList.get(i).getId(), userList.get(i));
//		List<Itrainproblems> trainproblemList =itrainproblemDao.getProblemsByExamId(examId);
////		List<Examproblems> examproblemList = examproblemDao.getDisplaySequenceByExamId(examId);
//		HashMap examproblemMap = new HashMap();
//		List<Problems> problemList = problemsDao.findTrainProblems(examId);
//		HashMap problemMap = new HashMap();
//		for (int i = 0; i < trainproblemList.size(); i++) {
//			examproblemMap.put(trainproblemList.get(i).getProblemId(), trainproblemList.get(i));
//			problemMap.put(problemList.get(i).getId(), problemList.get(i));
//		}
//		List<Solution> solutionCopyList = solutionDao.getSimilarityObject(examId);
//		HashMap<Integer, Solution> solutionCopyMap = new HashMap<Integer, Solution>();
//		if(solutionCopyList.size()>0){
//			for (int i = 0; i < solutionCopyList.size(); i++) {
//				Solution solution = solutionCopyList.get(i);
//				solutionCopyMap.put(solution.getId(), solution);
//			}
//		}
//
//		List<Object[]> object = solutionDao.getTrainCopyList(examId,teacherId, studentNo, name, banji, similarity,
//				searchTime);
//		List<PMSolution> pList = new ArrayList<PMSolution>();
//		if(object.size()>0){
//			for (int i = 0; i < object.size(); i++) {
//				PMSolution p = new PMSolution();
//				Object[] s = object.get(i);
//				try {
//
//					Integer.parseInt(s[0].toString());
//					Users user = (Users) userMap.get(Integer.parseInt(s[0].toString()));
//					if (user != null) {
//						p.setStudentNo(user.getStudentNo());
//						p.setChineseName(user.getChineseName());
//						p.setBanji(user.getBanji());
//					}
//					p.setId((Integer) s[2]);
//					p.setSubmitTime((Date) s[6]);
//					p.setProblemId(Integer.parseInt(s[1].toString()));
//					Itrainproblems ex = (Itrainproblems) examproblemMap.get(Integer.parseInt(s[1].toString()));
////					if (ex != null) {
////						p.setDisplaySequence(ex.getDisplaySequence());
////					}
//					Problems problem = (Problems) problemMap.get(Integer.parseInt(s[1].toString()));
//
//					if (problem != null) {
//						p.setTitle(problem.getTitle());
//						p.setProblemId(problem.getId());
//						p.setFlag(true);
//					}
//					p.setSimilarId((Integer) s[5]);
//					if (s[5] != null) {
//						Solution solution = solutionCopyMap.get(s[5]);
//						Users user2 = (Users) userMap.get(solution.getUserid());
//						if (user2 != null) {
//							p.setStudentNo2(user2.getStudentNo());
//							p.setChineseName2(user2.getChineseName());
//						}
//					}
//					p.setSimilarity((Float) s[4]);
//					p.setSimilarityThreshold(problem.getSimilarityThreshold());
//					p.setSubmited((Boolean) s[7]);
//					p.setEversubmit((Integer) s[8]);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				pList.add(p);
//
//			}
//		}
//		return pList;
//	}

	@Override
	public long getExamSubmitSolutionCount(boolean isLast, int examId,int teacherId, String displaySequence, String studentNo,
			String name, String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		return solutionDao.getExamSolutionCount(isLast, examId, teacherId,displaySequence, studentNo, name, banji, similarity,
				searchTime);
	}

	@Override
	public long getTrainSubmitSolutionCount(boolean isLast, int examId,int teacherId, String role,String studentNo,
			String name, String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		return solutionDao.getTrainSolutionCount(isLast, examId, teacherId,  role, studentNo, name, banji, similarity,
				searchTime);
	}

	@Override
	public List<Solution> exportExamLastCode(int examId) {
		// TODO Auto-generated method stub
		return solutionDao.exportExamLastCode(examId);
	}

	@Override
	public List<Solution> exportExamLastCode(int examId,int teacherId) {
		// TODO Auto-generated method stub
		return solutionDao.exportExamLastCode(examId,teacherId);
	}

	@Override
	public List<Solution> exportTrainLastCode(int examId) {
		// TODO Auto-generated method stub
		return solutionDao.exportTrainLastCode(examId);
	}

	@Override
	public List<Solution> exportTrainLastCode(int examId,int teacherId) {
		// TODO Auto-generated method stub
		return solutionDao.exportTrainLastCode(examId,teacherId);
	}

	@Override
	public List<Solution> exportClassExamCode(int examId, int classId) {
		// TODO Auto-generated method stub
		return solutionDao.exportClassExamCode(examId, classId);
	}

	@Override
	public List<Solution> exportClassTrainCode(int examId, int classId) {
		// TODO Auto-generated method stub
		return solutionDao.exportClassTrainCode(examId, classId);
	}

	@Override
	public List<Solution> exportClassExamLastCode(int examId, int classId) {
		// TODO Auto-generated method stub
		return solutionDao.exportClassExamLastCode(examId, classId);
	}

	@Override
	public List<Solution> exportClassTrainLastCode(int examId, int classId) {
		// TODO Auto-generated method stub
		return solutionDao.exportClassTrainLastCode(examId, classId);
	}

	@Override
	public boolean deleteSubmitBySolutionId(int solutionId, int reason, boolean isCopy, String remark) {

		// TODO Auto-generated method stub
		similaritywarningDao.deleteSubmitBySolutionId(solutionId, isCopy); // 删除Similaritywarning表中的submit
		Studentexamdetail detail = studentexamdetailDao.deleteFinishedBySolutionId(solutionId, reason); // 删除detail表中的finished
		if (detail != null) {
			int examId = detail.getExamId();
			int userId = detail.getUserId();
			String status = detail.getStatus();
			float score = detail.getScore();
			boolean finish = detail.isFinished();
			if (finish == true)
				studentexaminfoDao.deleteSubmit(userId, examId, score, status);
		}
		solutionDao.deleteSubmit(solutionId, reason, remark);
		submittedcodeDao.deleteCodeBySolutionId(solutionId); // 从Submittedcode表中删除
		return true;
	}

//	@Override
//	public boolean deleteTrainSubmitBySolutionId(int solutionId, int reason, boolean isCopy, String remark) {
//		/**
//		 * 1. 删除Similaritywarning表中的submit
//		 * 2. 撤销studenttrainprobdetail中该题
//		 * 3. 修改studenttraincatdetail中对应类别的信息
//		 * 4. 修改studentexaminfo中的信息
//		 * 5. 删除solution中的信息
//		 * 6. 删除提交的代码
//		 */
//
//		//1.删除Similaritywarning表中的submit
//		similaritywarningDao.deleteTrainSubmitBySolutionId(solutionId, isCopy);
//
//		//2. 撤销studenttrainprobdetail中该题
//		StudentTrainProbDetail detail = studentTrainDetailDao.deleteFinishedBySolutionId(solutionId, reason); // 删除detail表中的finished
//
//		//3. 修改studenttraincatdetail中对应类别的信息
//		if (detail != null) {
//			// 获取所撤销题目的信息
//			int de_examId = detail.getExamId();
//			int de_userId = detail.getUserId();
//			int de_catId = detail.getCatId();
//			int de_problemId = detail.getProblemId();
//			String de_status = detail.getStatus();
//			float de_oldPoints = detail.getPoints();
//			float de_oldScore = detail.getScoreCoef();
//			boolean de_finish = detail.isFinished();
//			// 获取类别的成绩数据
//			StudentTrainCatDetail oldTrainScore = new StudentTrainCatDetail();
//			oldTrainScore = studentTrainInfoDao.getUserTrainScore(de_userId, de_examId, de_catId);
//			String old_probFinished  =  oldTrainScore.getProbFinished();
//			float old_catScore = oldTrainScore.getScore();
//			// 新的积分
//			float newPoints = oldTrainScore.getPoints()-de_oldPoints;
//			// 新的成绩  根据新的积分进行判断  积分>=1 说明撤销之后也是通关 重新计算分数  否则成绩置空
//			if(newPoints>=1){
//				//更新points
//				oldTrainScore.setPoints(newPoints);
//				// 重新计算成绩
//				float newScore_c = gradeProblemService.calculateItrianCategoryScore(oldTrainScore);
//				oldTrainScore.setScore(newScore_c);
//			}else{  // 撤销之后  points<1
//				//更新points
//				oldTrainScore.setPoints(newPoints);
//				// 成绩置空
//				float newScore = 0;
//				oldTrainScore.setScore(newScore);
//				// 更新finished
//				oldTrainScore.setFinished(false);
//			}
//			float new_catScore = oldTrainScore.getScore();
//			// 修改ProbFinished
//			if(old_probFinished!=null){
//				// 转换成数组
//				String[] valueArr = old_probFinished.split(",");
//				if(valueArr.length==1){
//					oldTrainScore.setProbFinished(null);
//				}else{
//					//将之前的题目ID删掉
//					String newProbFinished = "";
//					for (int j = 0; j < valueArr.length; j++) {
//						if (!valueArr[j].equals("")&&Integer.parseInt(valueArr[j]) != de_problemId) {
//							newProbFinished = newProbFinished + valueArr[j] + ",";
//						}
//					}
//					//删除字符串中最后一个 ,
//					newProbFinished = newProbFinished.substring(0,newProbFinished.length() - 1);
//					oldTrainScore.setProbFinished(newProbFinished);
//				}
//
//				//更新此类别的信息
//				studentTrainInfoDao.updateTrainScore(oldTrainScore);
//			}
//
//
//			//4. 修改studentexaminfo中的信息
//			if (de_finish == true){
//				// 总成绩 - 该类别原来成绩 + 该类别现在成绩 ==》 总成绩 - （该类别原来成绩 - 该类别现在成绩）
//				float score = old_catScore - new_catScore;
//				studentexaminfoDao.deleteSubmit(de_userId, de_examId, score, de_status);
//			}
//		}
//		//5. 删除solution中的信息
//		solutionDao.deleteSubmit(solutionId, reason, remark);
//		//6. 从Submittedcode表中删除
//		submittedcodeDao.deleteCodeBySolutionId(solutionId);
//		return true;
//	}

	@Override
	public boolean isSubmited(int solutionId) {
		Studentexamdetail detail = studentexamdetailDao.getDetailBySolutionId(solutionId);
		if (detail != null) {
			Similaritywarning warning = similaritywarningDao.getSimilaritywarningBySolutionId(solutionId);
			if (warning != null && warning.getSubmited() == true) // submitd==1
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isTrainSubmited(int solutionId) {
		// TODO Auto-generated method stub
		StudentTrainProbDetail detail = studentTrainDetailDao.getTrainDetailBySolutionId(solutionId);
		if (detail != null) {
			Similaritywarning warning = similaritywarningDao.getSimilaritywarningBySolutionId(solutionId);
			if (warning != null && warning.getSubmited() == true) // submitd==1
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public Json submitThisProblem(Studentexamdetail studentexamdetail, PMWrongAndCorrectIds pMWrongAndCorrectIds,
								  Json j, boolean isOverSimilarity) {
		// TODO Auto-generated method stub
		Solution solution = null;
		if (studentexamdetail.getSolutionId() != null) {
			solution = getSolutionById(studentexamdetail.getSolutionId());
		} else {
			// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
			solution = getLastSolutionByUserIdExamIdProblemIdAndStatus(pMWrongAndCorrectIds.getUserId(),
					pMWrongAndCorrectIds.getExamId(), pMWrongAndCorrectIds.getProblemId(),
					studentexamdetail.getStatus());
		}

		// 如果是第一次提交。即只检查相似度，如果相似度超过阈值之后，将相似度值和另一个人的solutionID写入solution对应记录,只检查AC的
		if (pMWrongAndCorrectIds.getSubmitType().equals(Constant.SUBMIT_FIRST)) {
			// 首先根据examId获取submitOnlyAC属性
			// 根据examID查询该场考试的信息,先从缓冲中获取该场考试的信息
			ExamCacheManager examCacheManager = ExamCacheManager.getInstance();
			Exam exam = (Exam) examCacheManager.getObject("theExamById" + pMWrongAndCorrectIds.getExamId());
			if (exam == null) {
				exam = examDao.get(Exam.class, pMWrongAndCorrectIds.getExamId());
				examCacheManager.putObject("theExamById" + pMWrongAndCorrectIds.getExamId(), exam);
			}
			// 如果必须要AC后才能提交代码，并且用户没有AC,则提示用户
			if (exam.isSubmitOnlyAC() && !solution.getStatus().equals(new String("AC"))) {
				j.setSuccess(false);
				j.setMsg("只能AC之后才能提交本题");
				return j;
			} else {
				PMSimilarityObj pMSimilarityObj = new PMSimilarityObj();
				if (!solution.getStatus().equals(new String("AC"))) {// 不是AC不检查相似度
					pMSimilarityObj.setSelfSolutionId(solution.getId());
					pMSimilarityObj.setOverSimilarity(false);
					pMSimilarityObj.setSimilarityValue(-1);
				} else {
					// 根据solutionID，sourceCode，problemID获取包含超过相似度值，以及与哪一个solution相似的对象
					pMSimilarityObj = checkSimilarityService.checkSimilarityById(solution.getId(),
							solution.getSourceCode(), pMWrongAndCorrectIds.getProblemId());
				}
				// 相似度=-1表示该题不需要检查相似度
				if ((pMSimilarityObj.getSimilarityValue() != -1)) {
					// 更新solution表的相似度值
					solution.setSimilarId(pMSimilarityObj.getOtherSolutionId());
					solution.setSimilarity(pMSimilarityObj.getSimilarityValue());
					updateSolution(solution);
				}
				// 如果相似度超过相似度的阈值了
				if (pMSimilarityObj.isOverSimilarity()) {
					isOverSimilarity = true;
					// 涉嫌抄袭之后，插入一条数据到similaritywaring表
					Similaritywarning similaritywarning = new Similaritywarning();
					similaritywarning.setSolutionId(solution.getId());
					similaritywarning.setWarningTime(new Date());
					similaritywarning.setSubmited(false);
					similaritywarningService.saveSimilaritywarning(similaritywarning);
					// 并提示用户涉嫌抄袭
					j.setSuccess(true);
					j.setMsg("题目名:" + pMSimilarityObj.getProblemName() + " 涉嫌抄袭!");
					return j;
				} else {
					isOverSimilarity = false;

				}
			}
		}
		// 如果是涉嫌抄袭之后坚持提交代码
		else {
			// 更新similaritywarning表
			// 根据solutionID获取Similaritywarning
			if (solution.getStatus().equals(new String("AC"))) {
				Similaritywarning similaritywarning = similaritywarningService
						.getSimilaritywarningBySolutionId(solution.getId());
				// 设置已提交
				similaritywarning.setSubmited(true);
				similaritywarningService.updateSimilaritywarning(similaritywarning);
			}

			// TODO 发送邮件给老师
		}

		// 如果第一次提交没有超过相似度，或者涉嫌抄袭之后坚持第二次提交，则正常的提交本题
		if ((pMWrongAndCorrectIds.getSubmitType().equals(Constant.SUBMIT_FIRST) && !isOverSimilarity)
				|| pMWrongAndCorrectIds.getSubmitType().equals(Constant.SUBMIT_SECOND)) {
			// 正常的提交本题
			if (submittedcodeService.submitThisProblem(solution, studentexamdetail)) {
				j.setSuccess(true);
				j.setMsg("提交本题成功");
				return j;
			} else {
				j.setSuccess(false);
				j.setMsg("提交本题失败");
				return j;
			}
		}
		return null;
	}
//
	@Override
	public Json WS_submitThisProblem(Studentexamdetail studentexamdetail, PMSubmitProblemInfo WS_solution) {
		// TODO Auto-generated method stub
		Solution solution = null;
		Json j = new Json();
		if (studentexamdetail.getSolutionId() != null) {
			solution = getSolutionById(studentexamdetail.getSolutionId());
		} else {
			// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
			solution = getLastSolutionByUserIdExamIdProblemIdAndStatus(WS_solution.getUserId(), WS_solution.getExamId(),
					WS_solution.getProblemId(), studentexamdetail.getStatus());
		}
		Examproblems examproblems = examproblemDao.getExamproblemsByExamIdAndProblemId(WS_solution.getExamId(),
				WS_solution.getProblemId());
		Date deadline = examproblems.getDeadline();
		Date submitTime = solution.getSubmitTime();
		if (deadline != null && submitTime.after(deadline)) {
			j.setSuccess(false);
			j.setMsg("提交时间已经晚于本题截止时间不能提交本题");
			return j;
		}

		j = submitProblemCommon(WS_solution, solution);
		if(j.isSuccess()){
			if(WS_solution.isIfSubmit()){
				// 正常的提交本题
				if (submittedcodeService.submitThisProblem(solution, studentexamdetail)) {
					j.setSuccess(true);
					j.setMsg("提交本题成功");
					return j;
				} else {
					j.setSuccess(false);
					j.setMsg("提交本题失败");
					return j;
				}
			}else{
				j.setSuccess(true);
				j.setMsg("提交本题成功");
				return j;
			}
		}
		return j;
	}

	@Override
	public List<Solution> getSolutionsByNumber(int number) {
		return solutionDao.getSolutionsByNumber(number);
	}

	@Override
	public int editStudentScore(Solution solution) {
		// TODO Auto-generated method stub
		return solutionDao.editStudentScore(solution);
	}

	@Override
	public List<PMSolution> exportExamLastCode(boolean isLast, int examId, String displaySequence, String studentNo,
			String name, String banji, float similarity, String searchTime) {
		// TODO Auto-generated method stub
		List<Solution> solutionList = solutionDao.exportExamSearchSolution(isLast, examId, displaySequence, studentNo,
				name, banji, similarity, searchTime);
		List<Users> userList = userDao.getUsersByExamId(examId);
		HashMap userMap = new HashMap();
		List<PMSolution> pList = new ArrayList<PMSolution>();
		for (int i = 0; i < userList.size(); i++)
			userMap.put(userList.get(i).getId(), userList.get(i));
		List<Examproblems> examproblemList = examproblemDao.getDisplaySequenceByExamId(examId);
		HashMap examproblemMap = new HashMap();
		List<Problems> problemList = problemsDao.findExamProblems(examId);
		HashMap problemMap = new HashMap();
		for (int i = 0; i < examproblemList.size(); i++) {
			examproblemMap.put(examproblemList.get(i).getProblemId(), examproblemList.get(i));
			problemMap.put(problemList.get(i).getId(), problemList.get(i));
		}
		for (int i = 0; i < solutionList.size(); i++) {
			PMSolution p = new PMSolution();
			Solution s = solutionList.get(i);
			Users user = (Users) userMap.get(s.getUserid());
			if (user != null) {
				p.setStudentNo(user.getStudentNo());
				p.setChineseName(user.getChineseName());
				p.setBanji(user.getBanji());
				p.setUserid(user.getId());
			}
			p.setId(s.getId());
			p.setSubmitTime(s.getSubmitTime());
			p.setProblemId(s.getProblemId());
			Examproblems ex = (Examproblems) examproblemMap.get(s.getProblemId());
			if (ex != null)
				p.setDisplaySequence((ex).getDisplaySequence());
			Problems problem = (Problems) problemMap.get(s.getProblemId());
			if (problem != null) {
				p.setTitle(problem.getTitle());
				p.setProblemId(problem.getId());
				p.setSimilarityThreshold(problem.getSimilarityThreshold());
			}
			p.setStatus(s.getStatus());
			p.setScore(s.getScore());
			p.setSimilarity(s.getSimilarity());
			p.setLanguage(s.getLanguage());
			p.setCorrectCaseIds(s.getCorrectCaseIds());
			p.setSourceCode(s.getSourceCode());
			pList.add(p);
		}
		// TODO Auto-generated method stub
		return pList;
	}

	@Override
	public List<ExamStudent> getExamStudentInfo(int examId, int classId) {
		//获取班级学生数据后，进一步封装到list中
		List<Object[]> infoObj = solutionDao.getExamStudentInfo(examId,classId);
		List<ExamStudent> examStudentInfoList = new ArrayList<ExamStudent>();
		ExamStudent examStudentInfo;
		int count = 0;
		for (int i = 0; i < infoObj.size(); i++) {
			examStudentInfo = new ExamStudent();
			if(infoObj.get(i)[0] == null || infoObj.get(i)[0].equals("")){
				examStudentInfo.setId(-1);
			}else{
				examStudentInfo.setId((Integer) infoObj.get(i)[0]);
			}
			if(infoObj.get(i)[1] == null || infoObj.get(i)[1].equals("")){
				examStudentInfo.setStudentName("");
			}else{
				examStudentInfo.setStudentName((String) infoObj.get(i)[1]);
			}
			if(infoObj.get(i)[2] == null || infoObj.get(i)[2].equals("")){
				examStudentInfo.setStudentNo("");
			}else{
				examStudentInfo.setStudentNo((String) infoObj.get(i)[2]);
			}
			if(infoObj.get(i)[3] == null || infoObj.get(i)[3].equals("")){
				examStudentInfo.setClassName("");
			}else{
				examStudentInfo.setClassName((String) infoObj.get(i)[3]);
			}
			examStudentInfoList.add(i, examStudentInfo);
		}
		return examStudentInfoList;
	}

	@Override
	public Json WS_ItrainSubmitThisProblem(StudentTrainProbDetail stpd, PMSubmitProblemInfo WS_solution,String continueTrain) {
		Solution solution = null;
		Json j = new Json();
		if (stpd.getSolutionId() != null) {
			solution = getSolutionById(stpd.getSolutionId());
		} else {
			// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
			solution = getLastSolutionByUserIdExamIdProblemIdAndStatus(WS_solution.getUserId(), WS_solution.getExamId(),
					WS_solution.getProblemId(), stpd.getStatus());
		}
		ItrainProbCatgory ipc = itrainProblemCatDao.getItrainProbCatgory(stpd.getExamId(), stpd.getCatId());
		Date deadline = ipc.getDeadline();
		Date submitTime = solution.getSubmitTime();
		if (deadline != null && submitTime.after(deadline)) {
			j.setSuccess(false);
			j.setMsg("本关截止时间已过，请点 我要通关 结束本关");
			return j;
		}

		j = submitProblemCommon(WS_solution, solution);
		if(j.isSuccess()){
			if(WS_solution.isIfSubmit()){
				String res = submittedcodeService.itrainSubmitThisProblem(solution, stpd,continueTrain);
				// 正常的提交本题
				if (!(Constant.SUBMIT_FAILURE.equals(res))) {
					j.setSuccess(true);
					j.setMsg("提交本题成功");
					return j;
				} else {
					j.setSuccess(false);
					j.setMsg("提交本题失败");
					return j;
				}
			}else{
				j.setSuccess(true);
				j.setMsg("提交本题成功");
				return j;
			}
		}
		return j;
	}
	public Json submitProblemCommon(PMSubmitProblemInfo WS_solution,Solution solution){
		Json j = new Json();
		ExamCacheManager examCacheManager = ExamCacheManager.getInstance();
		Exam exam = (Exam) examCacheManager.getObject("theExamById" + WS_solution.getExamId());
		if (exam == null) {
			exam = examDao.get(Exam.class, WS_solution.getExamId());
			examCacheManager.putObject("theExamById" + WS_solution.getExamId(), exam);
		}
		// 如果必须要AC后才能提交代码，并且用户没有AC,则提示用户
		if (exam.isSubmitOnlyAC() && !solution.getStatus().equals(new String("AC"))) {
			j.setSuccess(false);
			j.setMsg("只能AC之后才能提交本题");
			return j;
		}else{
			// 无论是否抄袭都要更新similarity
			solution.setSimilarity(WS_solution.getSimilarity());
			// 如果相似度超过相似度的阈值了
			if (WS_solution.isOverSimilarity()) {
				// 如果抄袭了则更新solution表中的similarId
				solution.setSimilarId(WS_solution.getSimilarId());
				// 涉嫌抄袭之后，插入一条数据到similaritywarning表
				Similaritywarning similaritywarning = new Similaritywarning();
				similaritywarning.setSolutionId(solution.getId());
				similaritywarning.setWarningTime(new Date());
				similaritywarning.setSubmited(false);
				similaritywarningService.saveSimilaritywarning(similaritywarning);
				// 并提示用户涉嫌抄袭
			}
			updateSolution(solution);
			// 提交本题(包括不涉嫌抄袭提交和涉嫌抄袭提交)
			if (WS_solution.isIfSubmit()) {
				// 如果涉嫌之后坚持提交代码
				if (WS_solution.isOverSimilarity()) {
					// 更新similaritywarning表
					// 根据solutionID获取Similaritywarning
					Similaritywarning similaritywarning = similaritywarningService
										.getSimilaritywarningBySolutionId(solution.getId());
					// 设置已提交
					similaritywarning.setSubmited(true);
					similaritywarningService.updateSimilaritywarning(similaritywarning);
				}
			}
			j.setSuccess(true);
			return j;
		}

	}
//
//	@Override
//	public Json submitItrainThisProblem(StudentTrainProbDetail stpd, PMWrongAndCorrectIds pMWrongAndCorrectIds, Json j,
//			boolean isOverSimilarity, String continueTrain) {
//		Solution solution = null;
//		if (stpd.getSolutionId() != null) {
//			solution = getSolutionById(stpd.getSolutionId());
//		} else {
//			// 根据userId,examId,problemId以及Studenttrainprobdetail的status在solution中查找ID值最大的solution
//			solution = getLastSolutionByUserIdExamIdProblemIdAndStatus(pMWrongAndCorrectIds.getUserId(),
//					pMWrongAndCorrectIds.getExamId(), pMWrongAndCorrectIds.getProblemId(),
//					stpd.getStatus());
//		}
//		// 如果是第一次提交。即只检查相似度，如果相似度超过阈值之后，将相似度值和另一个人的solutionID写入solution对应记录,只检查AC的
//		if (pMWrongAndCorrectIds.getSubmitType().equals(Constant.SUBMIT_FIRST)) {
//			// 首先根据examId获取submitOnlyAC属性
//			// 根据examID查询该场考试的信息,先从缓冲中获取该场考试的信息
//			ExamCacheManager examCacheManager = ExamCacheManager.getInstance();
//			Exam exam = (Exam) examCacheManager.getObject("theExamById" + pMWrongAndCorrectIds.getExamId());
//			if (exam == null) {
//				exam = examDao.get(Exam.class, pMWrongAndCorrectIds.getExamId());
//
//				examCacheManager.putObject("theExamById" + pMWrongAndCorrectIds.getExamId(), exam);
//			}
//			// 如果必须要AC后才能提交代码，并且用户没有AC,则提示用户
//			if (exam.isSubmitOnlyAC() && !solution.getStatus().equals(new String("AC"))) {
//				j.setSuccess(false);
//				j.setMsg("只能AC之后才能提交本题");
//				return j;
//			} else {
//				PMSimilarityObj pMSimilarityObj = new PMSimilarityObj();
//				if (!solution.getStatus().equals(new String("AC"))) {// 不是AC不检查相似度
//					pMSimilarityObj.setSelfSolutionId(solution.getId());
//					pMSimilarityObj.setOverSimilarity(false);
//					pMSimilarityObj.setSimilarityValue(-1);
//				} else {
//					// 根据solutionID，sourceCode，problemID获取包含超过相似度值，以及与哪一个solution相似的对象
//					pMSimilarityObj = checkSimilarityService.checkSimilarityById(solution.getId(),
//							solution.getSourceCode(), pMWrongAndCorrectIds.getProblemId());
//				}
//				// 相似度=-1表示该题不需要检查相似度
//				if ((pMSimilarityObj.getSimilarityValue() != -1)) {
//					// 更新solution表的相似度值
//					solution.setSimilarId(pMSimilarityObj.getOtherSolutionId());
//					solution.setSimilarity(pMSimilarityObj.getSimilarityValue());
//					updateSolution(solution);
//				}
//				// 如果相似度超过相似度的阈值了
//				if (pMSimilarityObj.isOverSimilarity()) {
//					isOverSimilarity = true;
//					// 涉嫌抄袭之后，插入一条数据到similaritywaring表
//					Similaritywarning similaritywarning = new Similaritywarning();
//					similaritywarning.setSolutionId(solution.getId());
//					similaritywarning.setWarningTime(new Date());
//					similaritywarning.setSubmited(false);
//					similaritywarningService.saveSimilaritywarning(similaritywarning);
//					// 并提示用户涉嫌抄袭
//					j.setSuccess(true);
//					j.setMsg("题目名:" + pMSimilarityObj.getProblemName() + " 涉嫌抄袭!");
//					return j;
//				} else {
//					isOverSimilarity = false;
//
//				}
//			}
//		}
//		// 如果是涉嫌抄袭之后坚持提交代码
//		else {
//			// 更新similaritywarning表
//			// 根据solutionID获取Similaritywarning
//			if (solution.getStatus().equals(new String("AC"))) {
//				Similaritywarning similaritywarning = similaritywarningService
//						.getSimilaritywarningBySolutionId(solution.getId());
//				// 设置已提交
//				similaritywarning.setSubmited(true);
//				similaritywarningService.updateSimilaritywarning(similaritywarning);
//			}
//
//			// TODO 发送邮件给老师
//		}
//		// 如果第一次提交没有超过相似度，或者涉嫌抄袭之后坚持第二次提交，则正常的提交本题
//		if ((pMWrongAndCorrectIds.getSubmitType().equals(Constant.SUBMIT_FIRST) && !isOverSimilarity)
//				|| pMWrongAndCorrectIds.getSubmitType().equals(Constant.SUBMIT_SECOND)) {
//			String res = submittedcodeService.itrainSubmitThisProblem(solution, stpd, continueTrain);
//			// 正常的提交本题
//			if (!(Constant.SUBMIT_FAILURE.equals(res))) {
//				logger.info(
//						"用户ID为: " + pMWrongAndCorrectIds.getUserId() + "exam ID为:" + pMWrongAndCorrectIds.getExamId()
//								+ " problem ID为: " + pMWrongAndCorrectIds.getProblemId() + "提交本题成功");
//				j.setSuccess(true);
//				j.setMsg("提交本题成功");
//				j.setObj(res);
//				return j;
//			} else {
//				logger.info(
//						"用户ID为: " + pMWrongAndCorrectIds.getUserId() + "exam ID为:" + pMWrongAndCorrectIds.getExamId()
//								+ " problem ID为: " + pMWrongAndCorrectIds.getProblemId() + "提交本题失败");
//				j.setSuccess(false);
//				j.setMsg("提交本题失败");
//				return j;
//			}
//		}
//		return null;
//	}

	@Override
	public List<Map<String, Object>> getExamSubmitSolutionInfo(boolean isLast, int examId,int teacherId, int nowPage, int pageSize,
			String displaySequence, String studentNo, String name, String banji, float similarity, String searchTime) {
		return solutionDao.getExamSubmitSolutionInfo(isLast, examId,teacherId, nowPage, pageSize,
				displaySequence, studentNo, name, banji, similarity, searchTime);
	}

	@Override
	public List<Map<String,Object>> getCopyListInfo(int examId, int teacherId, String displaySequence, String studentNo,
			String name, String banji, float similarity, String searchTime) {
		List<Map<String,Object>>  copyInfo = solutionDao.getCopyListInfo(examId,teacherId, displaySequence, studentNo, name, banji, similarity,
				searchTime);
		return copyInfo;
	}

	@Override
	public List<Map<String, Object>> getTrainSubmitSolutionInfo(boolean isLast, int examId, int teacherId, String role,int nowPage,
			int pageSize, String studentNo, String name, String banji, float similarity, String searchTime) {
		return solutionDao.getTrainSubmitSolutionInfo(isLast, examId,teacherId, role,nowPage, pageSize,
				 studentNo, name, banji, similarity, searchTime);
	}

	@Override
	public List<Map<String, Object>> getTrainCopyListInfo(int examId, int teacherId, String role, String studentNo, String name,
			String banji, float similarity, String searchTime) {
		List<Map<String,Object>>  trainCopyInfo = solutionDao.getTrainCopyListInfo(examId,teacherId, role,studentNo, name, banji, similarity,
				searchTime);
		return trainCopyInfo;
	}

	@Override
	public RespBean getAllWrongAndRightCases(int userId, int examId, int problemId) {
		// 根据userID，examID，problemID在studentexamdetail表中查找该条记录
		Studentexamdetail studentexamdetail = studentexamdetailService.getStatusByUserIDexamIDproblemId(userId, examId,problemId);
		// 如果没有不存在记录
		if (studentexamdetail != null) {
			Solution s = null;
			if (studentexamdetail.getSolutionId() != null) {
				s = getSolutionById(studentexamdetail
						.getSolutionId());
			} else {
				// 根据userId,examId,problemId以及Studentexamdetail的status在solution中查找ID值最大的solution
				s = getLastSolutionByUserIdExamIdProblemIdAndStatus(userId,examId,problemId,studentexamdetail.getStatus());
			}
			float score;
			// 如果solution不为空,则代表用户提交了该题
			if (s != null) {
				if (s.getScore() > 0) {
					score = s.getScore();
				} else {
					// 获取该题的所得的分数情况
					score = gradeProblemService.gradeProblemBySolution(s);
				}
				// 根据solutionID查询该题所有的正确的测试用例
				String[] correctCaseIds = getCorrectCaseIds(s.getId());
				// 根据solutionID查询该题所有的错误的测试用例
				List<Wrongcases> wrongcases =WrongcasesDao.getWrongcasesBySolutionID(s.getId());

				PMWrongAndCorrectIds wrongAndCorrectIds = new PMWrongAndCorrectIds();
				wrongAndCorrectIds.setExamId(examId);
				wrongAndCorrectIds.setUserId(userId);
				wrongAndCorrectIds.setProblemId(problemId);
				wrongAndCorrectIds.setSolutionId(s.getId());
				wrongAndCorrectIds.setCorrectCaseIds(correctCaseIds);
				wrongAndCorrectIds.setWrongcases(wrongcases);
				wrongAndCorrectIds.setStatus(studentexamdetail.getStatus());
				// 如果分数出来了，则设置分数，返回页面
				if (score >= 0) {
					wrongAndCorrectIds.setScore(score);
				}
				// 设置提交次数
				wrongAndCorrectIds.setSubmit(studentexamdetail.getSubmit());
				// 设置remark
				if (s.getRemark() != null
						&& !s.getRemark().equals(new String(""))) {
					wrongAndCorrectIds
							.setRemark(switchStatusToRemark(studentexamdetail.getStatus())+ "\n具体信息如下:\n"+ s.getRemark());
				} else {
					wrongAndCorrectIds.setRemark(switchStatusToRemark(studentexamdetail.getStatus()));
				}

				return RespBean.ok("查询所有的正确和错误的测试用例成功",wrongAndCorrectIds);
			} else {
				PMWrongAndCorrectIds wrongAndCorrectIds = new PMWrongAndCorrectIds();
				wrongAndCorrectIds.setStatus("");
				wrongAndCorrectIds.setSubmit(0);
				wrongAndCorrectIds.setScore(-1);
				wrongAndCorrectIds.setCorrectCaseIds(null);
				wrongAndCorrectIds.setWrongcases(null);
				wrongAndCorrectIds.setRemark("");
				return RespBean.ok("正确和错误的测试用例为空");
			}
		} else {
			PMWrongAndCorrectIds wrongAndCorrectIds = new PMWrongAndCorrectIds();
			wrongAndCorrectIds.setStatus("");
			wrongAndCorrectIds.setSubmit(0);
			wrongAndCorrectIds.setScore(-1);
			wrongAndCorrectIds.setCorrectCaseIds(null);
			wrongAndCorrectIds.setWrongcases(null);
			wrongAndCorrectIds.setRemark("");
			return RespBean.ok("正确和错误的测试用例为空");
		}
	}

	private String switchStatusToRemark(String status) {
		switch (status) {
			case Constant.CODE_WAIT:
				return Constant.REMARK_WAIT;
			case Constant.CODE_QUEUE:
				return Constant.REMARK_QUEUE;
			case Constant.CODE_CE:
				return Constant.REMARK_CE;
			case Constant.CODE_TLE:
				return Constant.REMARK_TLE;
			case Constant.CODE_RE:
				return Constant.REMARK_RE;
			case Constant.CODE_WA:
				return Constant.REMARK_WA;
			case Constant.CODE_PE:
				return Constant.REMARK_PE;
			case Constant.CODE_OLE:
				return Constant.REMARK_OLE;
			case Constant.CODE_AC:
				return Constant.REMARK_AC;
			default:
				return "";
		}
	}


}
