//package edu.dhu.service;
//
//import edu.dhu.model.StudentTrainCatDetail;
//import edu.dhu.pageModel.PMStudenttraincatDetail;
//
//import java.util.List;
//import java.util.Map;
//
//public interface StudentTrainCatDetailServiceI {
//	//根据examId和userId获取TrainCatDetail数据集
//	public List<StudentTrainCatDetail> getTrainCatDetailList(int examId,int userId);
//	//获取开始做题页面弹出框的page选项
//	public String getItrainCatChoice(int userId,int examId,int catId);
//	//获取切换题目类别下拉框分类数据
//	public List<Map<String,Object>> getCanChoiceProCategory(int userId,int examId);
//	//获取抽取的题目Id
//	public Map<String,Object> getExtractProIdDataSource(int userId,int examId,int catId);
//	//获取做题记录页面数据
//	public Map<String,Object> getSubmitHistoryDataSoure(int userId,int examId);
//	//暂时跳过本题目操作
//	public boolean skipThisProblem(int userId,int examId,int catId,int proId);
//	//我要通关操作
//	public List<Map<String,Object>> passThisCategory(int userId,int examId,int catId);
//	//提交本题之后返回数据源
//	public Map<String,Object> getAfterSubmitProblemDataSource(int userId,int examId,int catId);
//	//获取做题界面类别信息
//	public Map<String,Object> getCategoryInfo(int userId,int examId,int catId);
//	//获取本关状态信息
//	public StudentTrainCatDetail getStudentTrainCatDetailInfo(int userId,int examId,int catId);
//	//获取学生通关信息
//	public List<Map<String,Object>> getCategoryInfoList(PMStudenttraincatDetail pMStudenttraincatDetail,String role);
//	//撤销该学生的通关记录
//	public String deletePassCategory(int examId,int userId,int catId);
//}
