package edu.dhu.dao.impl;

import edu.dhu.dao.StudentpaperexaminfoDaoI;
import edu.dhu.model.Studentpaperexaminfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("studentpaperexaminfoDao")

public class StudentpaperexaminfoDaoImpl extends BaseDaoImpl<Studentpaperexaminfo>
implements StudentpaperexaminfoDaoI {

	
	@Override
	public List<Studentpaperexaminfo> getAllStudentsScoreOrderByPaperExamID(int paperexamId){
		// TODO Auto-generated method stub
		String hql = "select u.studentNo,u.chineseName,(case when si.score is null then 0 else si.score end) "
				+ "from Users u LEFT JOIN Classstudents c on u.id = c.userId LEFT JOIN student_paper_exam_info si on si.user_id = u.id and si.exam_id ="+ paperexamId;
		List studentpaperexamlist = this.getCurrentSession().createSQLQuery(hql).list(); 
		return studentpaperexamlist;
	}
	
	@Override
	public List<Studentpaperexaminfo> getStudentsScoreOrderByPaperExamID(int classId,int paperexamId){
		// TODO Auto-generated method stub
		if(classId==0){
			String hql = "select u.studentNo,u.chineseName,(case when si.score is null then 0 else si.score end)  "
					+ "from Users u LEFT JOIN Classstudents c on u.id = c.userId  "
					+ " LEFT JOIN student_paper_exam_info si on si.user_id = u.id and si.exam_id = " + paperexamId 
					+ " LEFT JOIN paper_exam_class pc on pc.paper_Exam_id = " + paperexamId 
					+ "  where pc.class_id =  c.classId";
			List studentpaperexamlist = this.getCurrentSession().createSQLQuery(hql).list(); 
			return studentpaperexamlist;
		}else{
			String hql = "select u.studentNo,u.chineseName,  (case when si.score is null then 0 else si.score end) "
				+ "from Users u LEFT JOIN Classstudents c on u.id = c.userId "
				+ "LEFT JOIN student_paper_exam_info si  on u.id = si.user_id  and si.exam_id =" + paperexamId
				+ " where c.classId =" + classId +"  order by si.score desc";
			List studentpaperexamlist = this.getCurrentSession().createSQLQuery(hql).list(); 
			return studentpaperexamlist;
		}
	}
}
