package edu.dhu.exam.dao.impl;

import edu.dhu.exam.dao.StudentTrainCatDetailDaoI;
import edu.dhu.exam.model.PMStudenttraincatDetail;
import edu.dhu.problem.model.StudentTrainCatDetail;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("studentTrainCatDetailDao")
public class StudentTrainCatDetailDaoImpl extends BaseDaoImpl<StudentTrainCatDetail>
		implements StudentTrainCatDetailDaoI {

	@Override
	public List<StudentTrainCatDetail> getTrainCatDetailList(int examId, int userId) {
		String hql = "select stc from StudentTrainCatDetail stc where examId =:examId and userId =:userId";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		params.put("userId", userId);
		
		return this.find(hql, params);
	}

	@Override
	public StudentTrainCatDetail getStudentTrainCatDetailInfo(int userId, int examId, int catId) {
		String hql = "select stc from StudentTrainCatDetail stc where examId =:examId and userId =:userId and catId =:catId";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("examId", examId);
		params.put("userId", userId);
		params.put("catId", catId);
				
		return this.get(hql, params);
	}

	@Override
	public List<Map<String, Object>> getStudentTrainCatRecord(int userId, int examId) {
		Map<String,Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select stcd.examId,ip.catId,ip.rowX,ip.colY,concat_ws("+"'->'"+",pc2.name,pc1.name) name,pc1.description,ip.lowerLimit,ip.upperLimit,stcd.score,ip.score totalScore,stcd.finished,ip.bestBefore,ip.scoreCoef,ip.deadline  ");
		sql.append("FROM gdoj.studenttraincatdetail stcd  ");
		sql.append("left join gdoj.itrainprobcat ip  on stcd.catId = ip.catId and stcd.examId = ip.examId ");
		sql.append("left join gdoj.problemcategory pc1 on ip.catId = pc1.id  ");
		sql.append("left join gdoj.problemcategory pc2 on pc1.parentId = pc2.id ");
		sql.append("where stcd.examId =:examId and stcd.userId =:userId ");
		sql.append("order by ip.rowX,ip.colY ");
		
		params.put("examId", examId);
		params.put("userId", userId);
			
		return this.findBySql(sql.toString(), params);
	}

	@Override
	public Map<String, Object> getCategoryInfo(int userId, int examId, int catId) {
		Map<String,Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select concat_ws('->',pc2.name,pc1.name) name,stcd.points,stcd.finished,ip.bestBefore,ip.scoreCoef,ip.deadline,stcd.ProbFinished,stcd.ProbSequence  ");
		sql.append("FROM gdoj.studenttraincatdetail stcd  ");
		sql.append("left join gdoj.itrainprobcat ip  on stcd.catId = ip.catId and stcd.examId = ip.examId ");
		sql.append("left join gdoj.problemcategory pc1 on stcd.catId = pc1.id  ");
		sql.append("left join gdoj.problemcategory pc2 on pc1.parentId = pc2.id ");
		sql.append("where stcd.examId =:examId and stcd.userId =:userId and stcd.catId =:catId ");
		
		params.put("examId", examId);
		params.put("userId", userId);
		params.put("catId", catId);
		
		List<Map<String,Object>> list = this.findBySql(sql.toString(), params);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public List<Map<String, Object>> getCategoryInfoList(PMStudenttraincatDetail pMStudenttraincatDetail, String role) {
		Map<String,Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select temp.proNum,ip.rowX,ip.colY, stcd.passTime,ip.examId,stcd.userId,ip.catId, users.studentNo,users.chineseName,users.banji ,concat_ws('->',pc2.name,pc1.name) name,stcd.points,ip.score totalScore,stcd.score,ip.lowerLimit,ip.upperLimit,stcd.ProbFinished,stcd.submit,stcd.finished  ");
		sql.append("FROM gdoj.studenttraincatdetail stcd  ");
		sql.append("left join gdoj.itrainprobcat ip  on stcd.catId = ip.catId and stcd.examId = ip.examId ");
		sql.append("left join gdoj.problemcategory pc1 on stcd.catId = pc1.id  ");
		sql.append("left join gdoj.problemcategory pc2 on pc1.parentId = pc2.id ");
		sql.append("left join gdoj.users on users.id = stcd.userId ");
		
		sql.append("LEFT JOIN (select userId,catId, count(*) proNum from gdoj.studenttrainprobdetail "
				+ "where examId =:examId and finished = 1 group by userId,catId) temp ");
		sql.append("on temp.userId = stcd.userId and temp.catId = stcd.catId  ");	
		
		sql.append("where stcd.examId =:examId and stcd.finished = 1 ");
		
		sql.append("and exists ( select * from gdoj.classstudents ct left join gdoj.examclasses ec on ec.classId = ct.classId  ");
		sql.append("left join gdoj.classes c on c.id = ec.classId  where stcd.userId = ct.userId and examId=:examId  ");
		
		//未选定班级
		if(pMStudenttraincatDetail.getClassId() == null || pMStudenttraincatDetail.getClassId() == 0){ 
			//未选定班级时，管理员可查看全部，非管理员只能查看自己班级
			if(!role.equals("admin")){
				//教师
				if(role.equals("teacher")){
					sql.append("and c.teacherId =:teacherId ");
					params.put("teacherId", pMStudenttraincatDetail.getTeacherId());
				}else if(role.equals("assistant")){ //助教
					sql.append("and c.id =(select assi.classId from assistantclass assi where assi.assistantId=:teacherId )  ");
				    params.put("teacherId", pMStudenttraincatDetail.getTeacherId());
				}
				
			}
			
				
		}else{
			//选定班级
			sql.append("and ec.classId =:classId  ");
			params.put("classId", pMStudenttraincatDetail.getClassId());
		}
		
		sql.append(") ");
		
		params.put("examId", pMStudenttraincatDetail.getExamId());
		
		if(pMStudenttraincatDetail.getStudentNo() != null && !("".equals(pMStudenttraincatDetail.getStudentNo()))){
			sql.append("and users.studentNo =:studentNo  ");
			params.put("studentNo", pMStudenttraincatDetail.getStudentNo());
		}
		
		if(pMStudenttraincatDetail.getChineseName() != null && !("".equals(pMStudenttraincatDetail.getChineseName()))){
			sql.append("and users.chineseName like '%" + pMStudenttraincatDetail.getChineseName() + "%'  "); 
		}
		
		if(pMStudenttraincatDetail.getPassTimeFrom() != null && pMStudenttraincatDetail.getPassTimeTo() != null
				&&!("".equals(pMStudenttraincatDetail.getPassTimeFrom())) && !("".equals(pMStudenttraincatDetail.getPassTimeTo()))){
			sql.append("and unix_timestamp(stcd.passTime) >=unix_timestamp(:passTimeFrom)  and unix_timestamp(stcd.passTime) <=unix_timestamp(:passTimeTo)  ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				params.put("passTimeFrom", sdf.parse(pMStudenttraincatDetail.getPassTimeFrom()));
				params.put("passTimeTo", sdf.parse(pMStudenttraincatDetail.getPassTimeTo()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		sql.append("order by stcd.passTime desc ,users.studentNo");
		
		return this.findBySql(sql.toString(), params);
	}
	
	
}
