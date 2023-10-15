package edu.dhu.dao;

import edu.dhu.pageModel.PMPaperExamScore;

import java.util.List;

public interface PMPaperExamScoreDaoI extends BaseDaoI<PMPaperExamScore>{
	
	public List<PMPaperExamScore> findAllPaperExamProblemscoreByPaperExamId(int paperexamId);
	
	public List<PMPaperExamScore> findPaperExamClassScore(int paperexamId,int classID);
}
