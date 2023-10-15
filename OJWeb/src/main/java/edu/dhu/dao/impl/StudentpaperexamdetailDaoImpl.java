package edu.dhu.dao.impl;

import edu.dhu.dao.StudentpaperexamdetailDaoI;
import edu.dhu.model.Studentpaperexamdetail;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("studentpaperexamdetailDao")
public class StudentpaperexamdetailDaoImpl extends BaseDaoImpl<Studentpaperexamdetail> implements
		StudentpaperexamdetailDaoI {

	@Override
	public List<Studentpaperexamdetail> getAnswerByProblemId(int paperexamId,int paperproblemId){
		String hql = "from Studentpaperexamdetail e where e.exam_id="  + paperexamId + " and e.problem_id=" + paperproblemId;
		return this.find(hql);
	}
	
	@Override
	public List<Studentpaperexamdetail> getEachPaperProblemDetailByProblemId(int paperexamId,int paperproblemId){
		String hql = " SELECT studentNo,chineseName,sp.choice,sp.fill1,sp.fill2,sp.fill3,sp.fill4,sp.accuracy,type "
				+ " from student_paper_exam_detail as sp, users as us ,paper_problems as pp "
				+ " where sp.user_id = us.id and sp.exam_id=" + paperexamId + "  and sp.problem_id=" + paperproblemId +" and pp.id="+paperproblemId ;
		List eachpaperproblemlist = this.getCurrentSession().createSQLQuery(hql).list(); 
		return eachpaperproblemlist;
	}
	
}
