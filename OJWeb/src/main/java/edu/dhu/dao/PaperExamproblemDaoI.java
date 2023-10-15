package edu.dhu.dao;

import edu.dhu.model.PaperExamproblems;

import java.util.List;

public interface PaperExamproblemDaoI extends BaseDaoI<PaperExamproblems> {

	// 根据PaperExamId获取最大的展示序号
	public int getPaperMaxDisplaySequenceByExamId(int paperexamId);
	
	// 根据PaperExamId从[start,end)加一
	public void AddDisplaySequence(int paperexamId, int start, int end);
	
	public int getPaperExamproblemNum(int paperexamId); // 获取笔试考试题目数量
	
	public List<PaperExamproblems> findAllPaperExamProblemsByPaperExamId(int paperexamId);//根据考试id获取考试里的题目信息
}
