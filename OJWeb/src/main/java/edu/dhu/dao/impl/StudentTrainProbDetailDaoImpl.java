package edu.dhu.dao.impl;

import edu.dhu.dao.StudentTrainProbDetailDaoI;
import edu.dhu.model.StudentTrainProbDetail;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("studentTrainProbDetailDao")
public class StudentTrainProbDetailDaoImpl extends BaseDaoImpl<StudentTrainProbDetail>
			implements StudentTrainProbDetailDaoI{

	@Override
	public List<Map<String, Object>> getStudentTrainProRecord(int userId, int examId) {
		StringBuffer sql = new StringBuffer();
		Map<String,Object> params = new HashMap<String, Object>();
		
		sql.append("select stpd.problemId,stpd.catId,p.title,stpd.status,stpd.starttime,stpd.finished  ");
		sql.append("from gdoj.studenttrainprobdetail stpd  ");
		sql.append("left join gdoj.problems p on stpd.problemId = p.id  ");
		sql.append("where stpd.examId=:examId and stpd.userId =:userId ");
		
		params.put("examId", examId);
		params.put("userId", userId);
		
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public StudentTrainProbDetail getStudentTrainProbDetail(int userId, int examId, int catId, int problemId) {
		String hql = "select stpd from StudentTrainProbDetail stpd where stpd.userId =:userId and stpd.examId =:examId and stpd.catId =:catId and stpd.problemId =:problemId";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("examId", examId);
		params.put("catId", catId);
		params.put("problemId", problemId);
		
		List<StudentTrainProbDetail> res = this.find(hql, params);
		if(res.isEmpty())
			return null;
		else
			return res.get(0);
	}

	@Override
	public StudentTrainProbDetail getStatusByUserIdAndExamIdAndProId(int userId, int examId, int proId) {
		String hql = "select stpd from StudentTrainProbDetail stpd where stpd.userId =:userId and stpd.examId =:examId and stpd.problemId =:problemId";
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("userId", userId);
		params.put("examId", examId);
		params.put("problemId", proId);
		
		return this.get(hql, params);
	}

	@Override
	public void updateStudentTrainProbDetail(StudentTrainProbDetail stpd) {
		this.update(stpd);
	}

	@Override
	public List<Map<String, Object>> getDrawProblems(int userId, int examId, int catId) {
		String sql = "select problemId from gdoj.studenttrainprobdetail where examId =:examId and userId =:userId and catId !=:catId";
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("userId", userId);
		params.put("examId",examId);
		params.put("catId", catId);
		
		return this.findBySql(sql, params);
	}
	
	
}
