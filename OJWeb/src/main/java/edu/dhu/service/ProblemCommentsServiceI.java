//package edu.dhu.service;
//
//import edu.dhu.model.ProblemComment;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//public interface ProblemCommentsServiceI {
//
//	//教师端添加题解
//	public String addProCommentByTch(ProblemComment problemComment);
//
//	//教师端修改题解
//	public String editProblemCommentByTch(ProblemComment problemComment);
//
//	//修改待审核题解并提交
//	public String editProblemCommentReviewByTch(ProblemComment problemComment);
//
//	//获取待审核题解的个数
//	public int getWaitProCommentNum();
//
//	//获取未审核的题解列表
//	public List<ProblemComment> getProblemCommentsByWaitStatus(String[] conditions,String[] languages);
//
//	//根据题解Id获取题解信息
//	public ProblemComment getProblemCommentDetailById(int id);
//
//	//根据Id更新题解的状态
//	public String reviewProblemCommentById(int id,String status);
//
//	//根据题目Id获取题解列表
//	public List<ProblemComment> getProblemCommentsByProId(int proId,String status,String[] conditions,String[] languages);
//
//	//根据Id删除题解
//	public String delProblemCommentById(int id);
//
//	//根据Id获取作者相关信息
//	public Map<String,Object> getProCommentAuthorById(int pcId);
//
//	//根据题目Id删除题解
//	public void delProblemCommentByProId(int problemId);
//
//	//判断上传视频是否符合上传要求
//	public String checkVideo(String content);
//
//	//根据题目的id查询该题所有题解的标签
//	public Set<String> getProCommentTags(int proId);
//
//	//获取所有待审核题解信息的的题解标签
//	public Set<String> getProCommentTagsByWait();
//}
