package edu.dhu.problem.service.impl;

import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.global.util.SimilarityByLine;
import edu.dhu.problem.dao.ProblemsDaoI;
import edu.dhu.problem.dao.SubmittedcodeDaoI;
import edu.dhu.problem.model.PMSimilarityObj;
import edu.dhu.problem.model.Problems;
import edu.dhu.problem.service.CheckSimilarityServiceI;
import edu.dhu.solution.model.Submittedcode;
import edu.dhu.user.model.Adminusers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("checkSimilarityService")
@Transactional
public class CheckSimilarityServiceImpl implements CheckSimilarityServiceI {

	@Resource
	private SubmittedcodeDaoI submittedcodeDao;
	@Resource
	private ProblemsDaoI problemsDao;
	@Resource
	private AdminusersDaoI adminusersDao;

	@Autowired
	public void setAdminusersDaoI(AdminusersDaoI adminusersDao) {
		this.adminusersDao = adminusersDao;
	}

	public AdminusersDaoI getAdminusersDaoI() {
		return adminusersDao;
	}

	public SubmittedcodeDaoI getSubmittedcodeDao() {
		return submittedcodeDao;
	}

	@Autowired
	public void setSubmittedcodeDao(SubmittedcodeDaoI submittedcodeDao) {
		this.submittedcodeDao = submittedcodeDao;
	}

	public ProblemsDaoI getProblemsDao() {
		return problemsDao;
	}

	@Autowired
	public void setProblemsDao(ProblemsDaoI problemsDao) {
		this.problemsDao = problemsDao;
	}

	@Override
	public List<Submittedcode> getSubmittedListByProblemIdAndSchoolId(
			int problemId, int schoolId) {
		List<Submittedcode> submittedcodeList = submittedcodeDao
				.getSubmittedListByProblemIdAndSchoolId(problemId, schoolId);
		return submittedcodeList;
	}

	@Override
	public PMSimilarityObj checkSimilarityById(int solutionId,
											   String sourceCode, int problemId) {
		// 构造需要返回的对象
		PMSimilarityObj pMSimilarityObj = new PMSimilarityObj();
		pMSimilarityObj.setSelfSolutionId(solutionId);
		// 根据problemID获取problem
		Problems problem = problemsDao.findProblemById(problemId);
		pMSimilarityObj.setProblemName(problem.getTitle());
		// 首先获取该题目是否检查相似度
		boolean checkSimilarity = problem.getCheckSimilarity();
		if (!checkSimilarity) {
			pMSimilarityObj.setOverSimilarity(false);
			pMSimilarityObj.setSimilarityValue(-1);
		} else {
			int tid = problem.getTeacherId();
			Adminusers adminuser = adminusersDao.get(Adminusers.class, tid);
			int schoolId = adminuser.getSchoolId();
			// 根据problemID,schoolId获取本校所有需要对比的Submittedcode list
			List<Submittedcode> submittedcodeList = submittedcodeDao
					.getSubmittedListByProblemIdAndSchoolId(problemId, schoolId);
			// 如果Submittedcode中不存在数据，则说明没人提交过本题
			if (submittedcodeList == null || submittedcodeList.isEmpty()) {
				pMSimilarityObj.setOverSimilarity(false);
			} else {
				// 获取相似度的阈值
				float similarityThreshold = problem.getSimilarityThreshold();
				double theMaxSimilarity = 0;
				int theMaxSolutionId = 0;
				// 循环将该代码依次和其他代码做比较，获取最大的相似度值以及对应的solutionID值
				String code1 = sourceCode;
				for (int i = 0; i < submittedcodeList.size(); i++) {
					String code2 = submittedcodeList.get(i).getProcessedCode1();
					double similarity = SimilarityByLine.getSimilarity(code1,
							false, code2, true);
					Double similarityObj = new Double(similarity);
					Double theMaxSimilarityObj = new Double(theMaxSimilarity);
					if (theMaxSimilarityObj.compareTo(similarityObj) < 0) {
						theMaxSolutionId = submittedcodeList.get(i)
								.getSolutionId();
						theMaxSimilarity = similarity;
					}
				}
				if (theMaxSimilarity > similarityThreshold) {
					pMSimilarityObj.setOverSimilarity(true);
				} else {
					pMSimilarityObj.setOverSimilarity(false);
				}
				pMSimilarityObj.setOtherSolutionId(theMaxSolutionId);
				pMSimilarityObj.setSimilarityValue((float) theMaxSimilarity);
			}
		}
		return pMSimilarityObj;
	}

}
