package edu.dhu.dao.impl;

import edu.dhu.dao.ExamDaoI;
import edu.dhu.model.Exam;
import edu.dhu.pageModel.PMExam;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository("examDao")
public class ExamDaoImpl extends BaseDaoImpl<Exam> implements ExamDaoI {

	@Override
	public long getAllExamsNum() {
		String totalhql = "select count(*) from Exam";
		return this.count(totalhql).longValue();
	}

	@Override
	public List<Exam> getAllExams() {
		String hql = "from Exam e order by e.starttime desc";
		return this.find(hql);
	}

	@Override
	public List<Exam> getExamsByPage(int page, int rows) {
		String hql = "from Exam e order by e.endtime desc";
		return this.find(hql, page, rows);
	}

	@Override
	public long getAllExamsNumByStuId(int id) {
		String totalhql = "select count(distinct e.id ) from Exam e,Examclasses ec,Classes c,Classstudents cs where ec.examId = e.id and ec.classId = c.id and cs.classId = c.id and cs.userId = :id and UNIX_TIMESTAMP(starttime) < UNIX_TIMESTAMP(current_timestamp()) + 1 * 60 * 60 and (endtime>current_timestamp() or UNIX_TIMESTAMP(endtime) > UNIX_TIMESTAMP(current_timestamp())-180*24 * 60 * 60)";
		// 传递学生id参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.count(totalhql, params).longValue();
	}

	@Override
	public List<Exam> getExamsByStuId(int id) {
		String hql = "select distinct e from Exam e,Examclasses ec,Classes c,Classstudents cs where ec.examId = e.id and ec.classId = c.id and cs.classId = c.id and cs.userId = :id and UNIX_TIMESTAMP(starttime) < UNIX_TIMESTAMP(current_timestamp()) + 1 * 60 * 60 and (endtime>current_timestamp() or UNIX_TIMESTAMP(endtime) > UNIX_TIMESTAMP(current_timestamp())-180*24 * 60 * 60) order by e.endtime desc";
		// 传递学生id参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return this.find(hql, params);
	}

	@Override
	public List<Exam> getExamsByPageAndStuId(int studentId, int page, int rows) {
		String hql = "select distinct e from Exam e,Examclasses ec,Classes c,Classstudents cs where ec.examId = e.id and ec.classId = c.id and cs.classId = c.id and cs.userId = :studentId and UNIX_TIMESTAMP(starttime) < UNIX_TIMESTAMP(current_timestamp()) + 1 * 60 * 60 and (endtime>current_timestamp() or UNIX_TIMESTAMP(endtime) > UNIX_TIMESTAMP(current_timestamp())-180*24 * 60 * 60) order by e.endtime desc";
		// 传递学生id参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", studentId);
		return this.find(hql, params, page, rows);
	}
	
	//教师的查看所有考试页面的
	@Override
	public List<Exam> getAllTeacherExamsOrderByEndTime(int teacherId, String roleName) {
		// 获取所有考试，依据结束时间逆序排列
		// TODO Auto-generated method stub
		String hql;
		if (roleName.equals("assistant")) {
			hql = "select * from exam where id in (select examId from examclasses where classId in (select classId from assistantclass where assistantId="+teacherId+"))";	
			List list = this.getCurrentSession().createSQLQuery(hql).  
				    addEntity(Exam.class).    //指定将查询的记录行转换成Person实体  
				     list(); 
			return list;
		}
		if (teacherId == 0) {
			hql = "from Exam e order by e.endtime desc";
			return this.find(hql);
		} else {

			//hql = "select distinct e from Exam e,Examclasses ecs,Classes c where e.teacherId=:teacherId or (e.teacherId!=:teacherId and ecs.examId = e.id and ecs.classId = c.id and c.teacherId =:teacherId ) order by e.endtime desc";
			hql = "SELECT DISTINCT e.* FROM Exam e LEFT JOIN Examclasses ec on e.id = ec.examId  "
					+ "LEFT JOIN Classes c on c.id = ec.classId  "
					+ "where e.teacherId =:teacherId or c.teacherId =:teacherId  "
					+ "order by e.endtime desc ";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("teacherId", teacherId);
			//return this.find(hql, params);
			return this.findBySql(hql, params, Exam.class);
		}
	}
	
	//获取该教师的所有班级参加的考试
	@Override
	public List<Exam> getAllExamsOrderByEndTime(int teacherId, String roleName) {
		// 获取所有考试，依据结束时间逆序排列
		// TODO Auto-generated method stub
		String hql;
		if (roleName.equals("assistant")) {
			hql = "select * from exam where id in (select examId from examclasses where classId in (select classId from assistantclass where assistantId="+teacherId+"))";	
			List list = this.getCurrentSession().createSQLQuery(hql).  
				    addEntity(Exam.class).    //指定将查询的记录行转换成Person实体  
				     list(); 
			return list;
		}
		if (teacherId == 0) {
			hql = "from Exam e order by e.endtime desc";
			return this.find(hql);
		} else {
			 hql = "select distinct e from Exam e,Examclasses ecs,Classes c"
				 		+ " where ecs.examId = e.id and ecs.classId = c.id and c.teacherId =:teacherId "
				 		+ " order by e.endtime desc";
//			hql = "from Exam e where e.teacherId=:teacherId order by e.endtime desc";
			
			//hql语句不支持union,使用sql
//			String sql = "select * from Exam e,Examclasses ec "
//					+ "where ec.examId = e.id and ec.classId in "
//					+ "(select id from classes where teacherId="+teacherId+" "
//					+ "union all select id from classes_his where teacherId="+teacherId+")  "
//					+ "order by e.endtime desc";
//			SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
//			query.addEntity(Exam.class);
//			List<Exam> examList = query.list(); 
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("teacherId", teacherId);
			return this.find(hql, params);
		}
	}

	@Override
	public List<Exam> getAllExamBySchoolIdOrderByEndTime(int schoolId) {
		// TODO Auto-generated method stub
		String hql = "from Exam e where exists (select ad.id from Adminusers ad where e.teacherId=ad.id and ad.schoolId=:schoolId) order by e.endtime desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		return this.find(hql, params);
	}

	@Override
	public boolean updateExamproblemNum(int examId, int num) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String updateTime = sdf.format(date);// 将当前时间格式化为需要的类型

		String hql = "update Exam set problemNum=" + num + ",updateTime='" + updateTime + "' where id=" + examId;
		int result = this.executeHql(hql);

		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public List<Exam> getExamByProblemId(int problemId) {
		// TODO Auto-generated method stub
		String hql = "from Exam e where e.id in (select ex.examId from Examproblems ex where problemId=" + problemId
				+ ")";
		return this.find(hql);
	}

	@Override
	public void examAdd(PMExam pexam) {
		// TODO Auto-generated method stub
		Exam exam = new Exam();
		exam.setName(pexam.getName());
		exam.setTeacherId(pexam.getTeacherId());
		exam.setStarttime(pexam.getStarttime());
		exam.setEndtime(pexam.getEndtime());
		exam.setDescription(pexam.getDescription());
		exam.setCanGetHint(pexam.isCanGetHint());
		exam.setSubmitOnlyAC(pexam.isSubmitOnlyAC());
		exam.setPartialScore(pexam.isPartialScore());
		exam.setLanguage(pexam.getLanguage());
		exam.setUpdateTime(pexam.getUpdateTime());
		exam.setProblemNum(0);
		exam.setAllowChangeSeat(pexam.getAllowChangeSeat());
		exam.setStudentViewScore(pexam.getStudentViewScore());
		exam.setType(pexam.getExamType());
		this.save(exam);
	}

	@Override
	public int updateExam(PMExam pexam) {
		// TODO Auto-generated method stub
		String hql = "update Exam set name=:name,starttime=:starttime,endtime=:endtime,description=:description,canGetHint=:canGetHint,partialScore=:partialScore,language=:language,teacherId=:teacherId,submitOnlyAC=:submitOnlyAC,updateTime=:updateTime, "
				+ "allowChangeSeat=:allowChangeSeat,studentViewScore=:studentViewScore,type=:type where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("name", pexam.getName());
		q.setParameter("starttime", pexam.getStarttime());
		q.setParameter("endtime", pexam.getEndtime());
		q.setParameter("description", pexam.getDescription());
		q.setParameter("canGetHint", pexam.isCanGetHint());
		q.setParameter("partialScore", pexam.isPartialScore());
		q.setParameter("submitOnlyAC", pexam.isSubmitOnlyAC());
		q.setParameter("language", pexam.getLanguage());
		q.setParameter("teacherId", pexam.getTeacherId());
		q.setParameter("updateTime", pexam.getUpdateTime());
		q.setParameter("id", pexam.getId());
		q.setParameter("allowChangeSeat", pexam.getAllowChangeSeat());
		q.setParameter("studentViewScore", pexam.getStudentViewScore());
		q.setParameter("type", pexam.getExamType());
		int num = q.executeUpdate();
		return num;
	}

	@Override
	public int updateAllowCSbyexamId(PMExam pexam) {
		// TODO Auto-generated method stub
		String hql = "update Exam set updateTime=:updateTime, " + "allowChangeSeat=:allowChangeSeat where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("updateTime", pexam.getUpdateTime());
		q.setParameter("id", pexam.getId());
		q.setParameter("allowChangeSeat", true);
		int num = q.executeUpdate();
		return num;
	}
	

	@Override
	public List<Exam> getExamsNotInClass(int classId,int teacherId) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String hql = "from Exam e where e.id not in (select ex.examId from Examclasses ex where ex.classId=" + classId
				+ ") and e.teacherId=" + teacherId + " and e.endtime>:time";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("time", time);
		return q.list();
	}

	@Override
	public List<Exam> getExamsByTeacherId(int adminId) { // 获取教师参加的没有结束的考试
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String hql = "from Exam e where e.id in (select ex.examId from Examclasses ex where ex.classId in (select c.id from Classes c where c.teacherId="
				+ adminId + ")) and e.endtime>:time order by e.endtime desc";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("time", time);
		List<Exam> list = q.list();
		return list;
	}

	@Override
	public Exam getExamByExamId(int examId) {
		String hql = "from Exam e where e.id =" + examId
				+ ")";
		List<Exam> examList = this.find(hql);
		Exam exam = examList.get(0);
		return exam;
		
	}
}
