package edu.dhu.dao;

import edu.dhu.model.Itrainproblems;

import java.util.List;
import java.util.Map;

public interface ItrainproblemDaoI extends BaseDaoI<Itrainproblems>{
	//根据examId与二级分类catId获取该场考试的题目
	public List<Itrainproblems> getItrainProblem(Map<String,Object> params);
	//获取该场考试下已存在的题目
	public List<Map<String,Object>> findExitsProblemsByExamId(int examId);
	
	//根据examId+catId+problemId获取题目信息
	public Itrainproblems getItrainPro(int examId,int problemId);
	//根据examId获取该考试下的题目
	public List<Itrainproblems> getProblemsByExamId(int examId);
	
}
