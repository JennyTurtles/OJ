package edu.dhu.dao;

import edu.dhu.model.ProblemComment;

import java.util.List;

public interface ProblemCommentDaoI {
	//教师端添加题解
	public String addProCommentByTch(ProblemComment problemComment);
	
	//获取待审核题解的个数
	public int getWaitProCommentNum();
	
	//获取未审核题解列表
	public List<ProblemComment> getProblemCommentsByWaitStatus(String[] conditions,String[] languages);
	
	//根据Id获取题解信息
	public ProblemComment getProblemCommentDetailById(int id);
	
	//更新改题解信息
	public void updateProblemComment(ProblemComment pc);
	
	//根据题目Id获取题解列表信息
	public List<ProblemComment> getProblemCommentsByProId(int proId,String status,String[] conditions,String[] languages);
	
	//删除题解
	public void delProblemCommentById(ProblemComment pc);
}
