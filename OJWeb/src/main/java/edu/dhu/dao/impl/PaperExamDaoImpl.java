package edu.dhu.dao.impl;

import edu.dhu.dao.PaperExamDaoI;
import edu.dhu.model.PaperExam;
import edu.dhu.pageModel.PMPaperExam;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository("paperexamDao")
public class PaperExamDaoImpl extends BaseDaoImpl<PaperExam> implements PaperExamDaoI {
	
	@Override
	public List<PaperExam> getAllPaperExamsOrderByEndTime(int teacherId, String roleName) {
		// 获取所有考试，依据结束时间逆序排列
		// TODO Auto-generated method stub
		String hql;
		if (roleName.equals("assistant")) {
			hql = "select * from paperexam where id in (select examId from examclasses where classId in (select classId from assistantclass where assistantId="+teacherId+"))";	
			List list = this.getCurrentSession().createSQLQuery(hql).  
				    addEntity(PaperExam.class).    //指定将查询的记录行转换成Person实体  
				     list(); 
			return list;
		}
		if (teacherId == 0) {
			hql = "from PaperExam e order by e.end_time desc";
			return this.find(hql);
		} else {
			// hql = "select distinct e from Exam e,Examclasses ec,Classes c
			// where ec.examId = e.id and ec.classId = c.id and c.teacherId =
			// :teacherId order by e.endtime desc";
			hql = "from PaperExam e where e.teacherId=:teacherId order by e.end_time desc";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("teacherId", teacherId);
			return this.find(hql, params);
		}
	}
	
	@Override
	public List<PaperExam> getAllPaperExamBySchoolIdOrderByEndTime(int schoolId) {
		// TODO Auto-generated method stub
		String hql = "from PaperExam e where exists (select ad.id from Adminusers ad where e.teacherId=ad.id and ad.schoolId=:schoolId) order by e.end_time desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schoolId", schoolId);
		return this.find(hql, params);
	}

	@Override
	public int updatePaperExam(PMPaperExam pMPaperExam) {
		// TODO Auto-generated method stub
		String hql = "update PaperExam set name=:name,start_time=:starttime,end_time=:endtime,description=:description,teacherId=:teacherId,update_time=:update_time"
				+ " where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("name", pMPaperExam.getName());
		q.setParameter("starttime", pMPaperExam.getStart_time());
		q.setParameter("endtime", pMPaperExam.getEnd_time());
		q.setParameter("description", pMPaperExam.getDescription());
		q.setParameter("teacherId", pMPaperExam.getTeacherId());
		q.setParameter("update_time", pMPaperExam.getUpdate_Time());
		q.setParameter("id", pMPaperExam.getId());
		int num = q.executeUpdate();
		return num;
	}

	
	@Override
	public void paperexamAdd(PMPaperExam pMPaperExam) {   //保存笔试题目
		// TODO Auto-generated method stub
		PaperExam paperexam = new PaperExam();
		paperexam.setName(pMPaperExam.getName());
		paperexam.setTeacherId(pMPaperExam.getTeacherId());
		paperexam.setStart_time(pMPaperExam.getStart_time());
		paperexam.setEnd_time(pMPaperExam.getEnd_time());
		paperexam.setDescription(pMPaperExam.getDescription());
		paperexam.setUpdate_Time(pMPaperExam.getUpdate_Time());
		paperexam.setCreate_Time(pMPaperExam.getCreate_Time());
		paperexam.setProblem_Num(0);
		this.save(paperexam);
	}

	
	@Override
	public boolean updateExamproblemNum(int paperexamId, int num) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间显示格式
		String updateTime = sdf.format(date);// 将当前时间格式化为需要的类型

		String hql = "update PaperExam set problem_Num=" + num + ",update_Time='" + updateTime + "' where id=" + paperexamId;
		int result = this.executeHql(hql);

		if (result == 1)
			return true;
		else
			return false;
	}


	@Override
	public List<PaperExam> getPaperExamByProblemId(int paperproblemId) {
		// TODO Auto-generated method stub
		String hql = "from PaperExam e where e.id in (select ex.examId from PaperExamproblems ex where problemId=" + paperproblemId
				+ ")";
		return this.find(hql);
	}
	
	public List<PaperExam> getExamByPaperProblemId(int paperproblemId) {//根据paperproblemId查询该题是否被考试收录
		String hql = "from PaperExam e where e.id in (select ex.examId from PaperExamproblems ex where problemId=" + paperproblemId
				+ ")";
		return this.find(hql);
	}
	
	@Override
	public List<PaperExam> getPaperExamsNotInClass(int classId,int teacherId){
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String hql = "from PaperExam e where e.id not in (select ex.paperexamId from PaperExamclasses ex where ex.classId=" + classId
				+ ") and e.teacherId=" + teacherId + " and e.end_time>:time";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("time", time);
		return q.list();
	}
	
	
}
