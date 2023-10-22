//package edu.dhu.service;
//
//import edu.dhu.model.*;
//import edu.dhu.pageModel.DataGrid;
//import edu.dhu.pageModel.PMPaperExam;
//import edu.dhu.pageModel.PMPaperExamScore;
//import edu.dhu.pageModel.PMPaperExamproblem;
//
//import java.util.List;
//
//public interface PaperExamServiceI {
//
//	// 根据paperexamId获取本场考试的相关信息
//	public PaperExam getPaperExamById(int paperexamId);
//
//	public List<PaperExam> getPaperExamByProblemId(int paperproblemId);
//
//	public void paperexamAdd(PMPaperExam pMPaperExam); //添加笔试考试
//
//	public int updatePaperExam(PMPaperExam pMPaperExam); // 更新考试
//
//	public List<PMPaperExam> getAllPaperExamsOrderByEndTime(int teacherId, String roleName);  // 获取所有的笔试考试
//
//	public List<PMPaperExam> getAllPaperExamsOrderByEndTime(PMPaperExam pMPaperExam, String roleName);
//
//	public List<Studentpaperexaminfo> getAllStudentsScoreOrderByPaperExamID(int paperexamId); // 通过考试id获取所有学生的成绩
//
//	public List<Studentpaperexaminfo> getStudentsScoreOrderByPaperExamID(int classId,int paperexamId); // 通过学生id和考试id获取学生的成绩
//
//	public List<PaperExamproblems> findAllPaperExamProblemsByPaperExamId(int paperexamId); //根据考试的id获取该考试里包含的题目信息
//
//	public Paper_Problems findPaperProblemById(int paperproblemId);  //根据题目id获取题目详细信息
//
//	public List<PMPaperExamScore> findAllPaperExamProblemscoreByPaperExamId(int paperexamId);//根据考试id获取成绩信息
//
//	public List<PMPaperExamScore> findPaperExamClassScore(int paperexamId,int classID);//根据考试id获取成绩信息
//
//	public List<Studentpaperexamdetail> getAnswerByProblemId(int paperexamId,int paperproblemId); //根据考试id和题目id获取选项情况
//
//	public List<Studentpaperexamdetail> getEachPaperProblemDetailByProblemId(int paperexamId,int paperproblemId); //根据考试id和题目id获取一道题目的答题情况
//
//	// 增加笔试考试题目
//	public String addP(PMPaperExamproblem pPaperExamproblem);
//
//	// 获取指定paperexamId的最大显示号（即题目总数）
//	public int getPaperMaxDisplaySequence(int paperexamId);
//
//	 //获取不在PaperExamId号中的题目
//	public DataGrid getDataGridNotInPaperExam(PMPaperExamproblem pPaperExamproblem);
//
//	public int getPaperExamproblemNum(int paperexamId); // 获取笔试考试题目数量
//
//	public List<PaperExam> getExamByPaperProblemId(int paperproblemId);//根据paperproblemId查询该题是否被考试收录
//
//	public List<Paper_Problems> findPaperProblemByStuNo(int paperexamId,int StuNo);  ////根据学生的学号和考试id获取学生做的题目信息
//
//	public List<PMPaperExam> getPaperExamsNotInClass(int classId,int teacherId); // 获取班级没有参加的笔试考试
//}
