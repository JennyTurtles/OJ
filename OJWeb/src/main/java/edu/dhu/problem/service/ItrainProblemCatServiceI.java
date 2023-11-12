package edu.dhu.problem.service;

import edu.dhu.exam.model.ItrainProbCatgory;
import edu.dhu.problem.model.Itrainproblems;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ItrainProblemCatServiceI {
	//获取二级分类下的题目列表
	public List<Map<String,Object>> findProblemsBySecCatgory(int secCatId,int examId);
	// 获取二级分类下的可用题目数量
	public List<Map<String,Object>> getUseProNum(int secCatId,int examId);
	// 获取该考试下的所有分类列表
	public List<Map<String,Object>> getExamCatagoryList(int examId,int secCatId);
	// 向该场考试二级分类添加题目
	public String insertItrainProblem(int[] proIds, int[] manIds, int[] dura, ItrainProbCatgory itrainProcat, int pos, int direction);
	
	// 删除考试中二级分类
	public String delExamCatgoryProblem(int examId,int secCatId);
	//修改考试中的二级分类题目
	public String updateItrainProblem(int[] proIds,int[] manIds,Map<Integer,Integer> map,ItrainProbCatgory itrainProcat,int pos,int direction);
	//修改二级分类中的做题时间
	public String updateItrainTime(ItrainProbCatgory itrainProcat);
	// 获取做题顺序下拉列表
	public List<Map<String,Object>> getProblemSeq(int examId);
	//获取页面做题分类顺序可视化结构图填充数据元
	public Map<String,Object> getExamCatagorySeqGraph(int examId);
	
	//获取该场考试下已有的题目
	public List<Map<String,Object>> findExitsProblemsByExamId(int examId);
	
	/*//更改考试做题时间
	public String updateProDuration(Itrainproblems itrainPro);*/
	
	//修改分类做题顺序
	public String updateGraphSeq(ItrainProbCatgory itrainProcat,int position,int direction);
	
	/*//根据examID和分类Id获取分类序号sequence和名称Name
	public String getProCatagoryNameById(int examId,int catId);*/
	
	//获取二级分类的截止日期
	public Date getDeadline(int examId,int catId);
	
	//获取学生登录考试类别为智能训练首页数据
	public Map<String,Object> getStudentItrainCatagoryList(int examId,int userId);
	
	//获取类别描述信息和时间限制信息
	Map<String,Object> getCatDescriptionAndTimelimit(int examId,int catId);
	
	//获取做题记录Tree数据源
	Map<String,Object> getRecordTreeData(int userId,int examId);
	
	public ItrainProbCatgory getItrainProCategoryByExamIdAndCatId(int examId,int catId);
	
	//获取考试类别数目
	public int getExamCategoryCount(int examId);
	
	//根据examId获取该场考试所有的分类
	public List<ItrainProbCatgory> getAllItrainProbCategoryByExamId(int examId);
	
	//复制该场考试的某个类别
	public String addCategory(ItrainProbCatgory ipc);
	
	//获取该场考试所有的题目
	public List<Itrainproblems> getAllItrainProblemsByExamId(int examId);
	
	//复制该场考试的所有题目
	public String addItrainProblems(Itrainproblems ip);
}
