package edu.dhu.dao;

import edu.dhu.model.Studentpaperexamdetail;

import java.util.List;

public interface StudentpaperexamdetailDaoI extends BaseDaoI<Studentpaperexamdetail>{
	public List<Studentpaperexamdetail> getAnswerByProblemId(int paperexamId,int paperproblemId);
	
	public List<Studentpaperexamdetail> getEachPaperProblemDetailByProblemId(int paperexamId,int paperproblemId);
}
