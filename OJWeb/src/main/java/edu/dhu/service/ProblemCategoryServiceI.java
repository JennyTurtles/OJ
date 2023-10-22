//package edu.dhu.service;
//
//import edu.dhu.model.ProblemCategory;
//
//import java.util.List;
//import java.util.Map;
//
//public interface ProblemCategoryServiceI {
//	// 获取所有的题目分类
//	public List<ProblemCategory> findAllProblemCategory();
//
//	public List<ProblemCategory> findAllProblemCategoryByParentId(int parentId);
//
//	public ProblemCategory findProblemCategoryById(int id);
//
//	//添加题目分类
//	public int addProblemCategory(ProblemCategory problemCategory);
//
//	public String editProblemCategoryById(ProblemCategory problemCategory);
//
//	//删除题目分类
//	public String deleteFirstProblemCategoryById(int id);
//
//	// 获取所有的一级分类
//	public List<Map<String,Object>> getAllPriCatgory();
//	// 根据一级分类Id获取二级分类
//	public List<Map<String,Object>> getSecCatagoryByPriCatId(int parentId,int examId);
//	// 根据二级分类ID获取二级分类
//	public ProblemCategory getDescriptionBySecCatId(int secCatId);
//}
