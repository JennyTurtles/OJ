package edu.dhu.exam.dao;

import edu.dhu.exam.model.ItrainProbCatgory;

import java.util.List;
import java.util.Map;

public interface ItrainProblemCatDaoI extends BaseDaoI<ItrainProbCatgory>{
	//获取二级分类下的题目列表
	public List<Map<String,Object>> findProblemsBySecCatgory(int secCatId,int examId);
	// 获取二级分类下的可用题目数量
	public List<Map<String,Object>> getUseProNum(int secCatId,int examId);
	// 获取该考试下所有的二级分类列表
	public List<Map<String,Object>> getExamCatagoryList(int examId,int secCatId);
	/*// 获取做题顺序下拉列表
	public List<Map<String,Object>> getProblemSeq(int examId);*/
	//获取做题顺序数据源
	public List<Map<String,Object>> getCatagoryByexamId(int examId);
	//获取二级分类的截止日期
	public ItrainProbCatgory getItrainProbCatgory(int examId,int catId);
	//获取类别描述信息和时间限制信息
	public Map<String,Object> getCatDescriptionAndTimelimit(int examId,int catId);
	//获取考试里的分类信息
	public List<ItrainProbCatgory> getCatagoryListByExamId(int examId);
	//获取该场考试类别数目
	public int getExamCategoryCount(int examId);

	
}
