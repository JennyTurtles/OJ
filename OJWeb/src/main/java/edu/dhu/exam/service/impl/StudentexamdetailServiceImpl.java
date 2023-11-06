package edu.dhu.exam.service.impl;

import edu.dhu.exam.dao.StudentexamdetailDaoI;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.problem.model.PMProblemsStatus;
import edu.dhu.problem.model.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("studentexamdetailService")
@Transactional
public class StudentexamdetailServiceImpl implements StudentexamdetailServiceI {

	private StudentexamdetailDaoI studentexamdetailDao;

	public StudentexamdetailDaoI getStudentexamdetailDao() {
		return studentexamdetailDao;
	}

	@Autowired
	public void setStudentexamdetailDao(
			StudentexamdetailDaoI studentexamdetailDao) {
		this.studentexamdetailDao = studentexamdetailDao;
	}

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
		// TODO Auto-generated method stub
		return studentexamdetailDao.editStudentScore(solution);
	}

	@Override
	public int editStudentDetailFinished(Solution solution) {
		// TODO Auto-generated method stub
		return studentexamdetailDao.editStudentDetailFinished(solution);
	}

}
