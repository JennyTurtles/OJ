package edu.dhu.dao;

import edu.dhu.model.StudentTrainCatDetail;
import edu.dhu.pageModel.PMStudenttraincatDetail;

import java.util.List;
import java.util.Map;

public interface StudentTrainCatDetailDaoI extends BaseDaoI<StudentTrainCatDetail>{
	//根据examId和userId获取TrainCatDetail数据集
	public List<StudentTrainCatDetail> getTrainCatDetailList(int examId,int userId);
	//根据userId,examId，catId获取StudentTrainCatDetail记录
	public StudentTrainCatDetail getStudentTrainCatDetailInfo(int userId,int examId,int catId);
	//获取分类做题记录
	public List<Map<String,Object>> getStudentTrainCatRecord(int userId,int examId);
	//获取做题界面的类别信息
	public Map<String,Object> getCategoryInfo(int userId,int examId,int catId);
	//获取学生通关信息
	public List<Map<String,Object>> getCategoryInfoList(PMStudenttraincatDetail pMStudenttraincatDetail,String role);
}
