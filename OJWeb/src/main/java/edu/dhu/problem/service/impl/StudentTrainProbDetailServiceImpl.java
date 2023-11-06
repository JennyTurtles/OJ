package edu.dhu.problem.service.impl;

import edu.dhu.problem.dao.StudentTrainProbDetailDaoI;
import edu.dhu.problem.model.StudentTrainProbDetail;
import edu.dhu.problem.service.StudentTrainProbDetailServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("studentTrainProbDetailService")
@Transactional
public class StudentTrainProbDetailServiceImpl implements StudentTrainProbDetailServiceI {
	
	@Autowired
	private StudentTrainProbDetailDaoI studentTrainProbDetailDao;

	public StudentTrainProbDetailDaoI getStudentTrainProbDetailDao() {
		return studentTrainProbDetailDao;
	}

	public void setStudentTrainProbDetailDao(StudentTrainProbDetailDaoI studentTrainProbDetailDao) {
		this.studentTrainProbDetailDao = studentTrainProbDetailDao;
	}



	@Override
	public StudentTrainProbDetail getStudentTrainProbDetail(int userId, int examId, int catId, int problemId) {
		return studentTrainProbDetailDao.getStudentTrainProbDetail(userId,examId,catId,problemId);
	}

	@Override
	public StudentTrainProbDetail getStatusByUserIdAndExamIdAndProId(int userId, int examId, int proId) {
		return studentTrainProbDetailDao.getStatusByUserIdAndExamIdAndProId(userId,examId,proId);
	}

	@Override
	public void updateStudentTrainProbDetail(StudentTrainProbDetail stpd) {
		studentTrainProbDetailDao.updateStudentTrainProbDetail(stpd);
	}
	
	
}
