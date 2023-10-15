package edu.dhu.dao.impl;

import edu.dhu.dao.PMPaperExamScoreDaoI;
import edu.dhu.pageModel.PMPaperExamScore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("pmpaperexamscoreDao")
public class PMPaperExamScoreDaoImpl extends BaseDaoImpl<PMPaperExamScore> implements PMPaperExamScoreDaoI {

	@Override
	public List<PMPaperExamScore> findAllPaperExamProblemscoreByPaperExamId(int paperexamId){
		// TODO Auto-generated method stub
		String hql = "select ep.problem_id , type , content , "
				+ "sum(case when accuracy is null then 0 else accuracy end )as accuracy,count(user_id) as times ,count"
				+ " from  paper_exam_problems ep  left JOIN student_paper_exam_detail t  "
				+ "on ep.exam_id = t.exam_id and t.problem_id = ep.problem_id  "
				+ "LEFT JOIN paper_problems p  ON ep.problem_id = p.id "
				+ "where ep.exam_id =" + paperexamId + " group by problem_id";
		List pmpaperexamscorelist = this.getCurrentSession().createSQLQuery(hql).list(); 
		return pmpaperexamscorelist;
	
	}
	
	@Override
	public List<PMPaperExamScore> findPaperExamClassScore(int paperexamId,int classID){
		// TODO Auto-generated method stub
		String hql = "select ep.problem_id , type , content , "
				+ "sum(case when accuracy is null then 0 else accuracy end )as accuracy,count(user_id) as times ,count"
				+ " from  paper_exam_problems ep  left JOIN student_paper_exam_detail t  "
				+ "on ep.exam_id = t.exam_id and t.problem_id = ep.problem_id  "
				+ "LEFT JOIN paper_problems p  ON ep.problem_id = p.id "
				+ "LEFT JOIN classstudents cs ON t.user_id = cs.userId "
				+ "where ep.exam_id =" + paperexamId + " and cs.classId ="  + classID + " group by problem_id";
		List pmpaperexamscorelist = this.getCurrentSession().createSQLQuery(hql).list(); 
		return pmpaperexamscorelist;
	
	}

}
