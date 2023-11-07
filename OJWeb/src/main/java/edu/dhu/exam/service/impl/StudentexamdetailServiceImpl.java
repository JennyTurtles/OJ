package edu.dhu.exam.service.impl;

import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.global.model.Constant;
import edu.dhu.global.model.RespBean;
import edu.dhu.problem.dao.WrongcasesDaoI;
import edu.dhu.problem.model.PMProblemsStatus;
import edu.dhu.problem.model.PMWrongAndCorrectIds;
import edu.dhu.problem.model.Solution;
import edu.dhu.problem.model.Wrongcases;
import edu.dhu.problem.service.impl.GradeProblemServiceImpl;
import edu.dhu.problem.service.impl.SolutionServiceImpl;
import edu.dhu.problem.service.impl.WrongcasesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("studentexamdetailService")
@Transactional
public class StudentexamdetailServiceImpl implements StudentexamdetailServiceI {

	@Resource
	private StudentexamdetailDaoI studentexamdetailDao;
	@Override
	public Studentexamdetail getStatusByUserIDexamIDproblemId(int userId,
															  int examId, int problemId) {
		return studentexamdetailDao.getStatusByUserIDexamIDproblemId(userId,
				examId, problemId);
	}

	@Override
	public void updateStudentexamdetail(Studentexamdetail studentexamdetail) {
		studentexamdetailDao.updateStudentexamdetail(studentexamdetail);
	}

	@Override
	public void saveStudentexamdetail(Studentexamdetail studentexamdetail) {
		studentexamdetailDao.save(studentexamdetail);
	}

	@Override
	public PMProblemsStatus getProblemsStatusArrByIds(
			PMProblemsStatus pMProblemsStatus) {
		return studentexamdetailDao.getProblemsStatusArrByIds(pMProblemsStatus);
	}

	@Override
	public List<Studentexamdetail> getAllStudentexamdetailListByUserIdAndExamId(
			int userId, int examId) {
		return studentexamdetailDao
				.getAllStudentexamdetailListByUserIdAndExamId(userId, examId);
	}

	@Override
	public int editStudentScore(Solution solution) {
		return studentexamdetailDao.editStudentScore(solution);
	}

	@Override
	public int editStudentDetailFinished(Solution solution) {
		return studentexamdetailDao.editStudentDetailFinished(solution);
	}

}
