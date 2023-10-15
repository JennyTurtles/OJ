package edu.dhu.service;

import edu.dhu.model.Examproblems;
import edu.dhu.model.PaperExamproblems;
import edu.dhu.pageModel.DataGrid;
import edu.dhu.pageModel.PMExamproblem;
import edu.dhu.pageModel.PMProblems;

import java.util.Date;
import java.util.List;


public interface ExamproblemServiceI {
	// 根据Examid，分页信息等获取考试题目
	public DataGrid dataGrid(PMExamproblem pExamproblem);

	// WebService根据ExamId查询考试题目
	public List<Examproblems> getProblemByExamId(int examId);
	
	// WebService根据PaperExamId查询考试题目
	public List<PaperExamproblems> getPaperProblemByExamId(int paperexamId);

	// 增加考试题目
	public String add(PMExamproblem pExamproblem);

	// 删除考试题目
	public String remove(PMExamproblem pExamproblem);

	// 修改考试题目的显示顺序
	public String alterDisplaySequence(PMExamproblem pExamproblem);

	// 获取指定examId的最大显示号（即题目总数）
	public int getMaxDisplaySequence(int examId);
	
	// 获取不在ExamId号中的题目
	public DataGrid getDataGridNotInExam(PMExamproblem pExamproblem);
	
	public int getExamproblemNum(int examId); // 获取考试题目数量
	

	// 根据examID，problemID在examproblems中获取该题
	public Examproblems getExamproblemsByExamIdAndProblemId(int examId,
			int problemId);

	// 通过examproblem表的id获取相关信息
	public PMExamproblem getProblemByExamProblemId(int id);

	public boolean alterScore(int id, float score, int examId); // 更改考试题目的分数

	public boolean alterDeadline(int id, Date deadline, Date updateTime,
			int examId); // 更新截止时间

	public boolean alterbestBeforeAndscoreCoef(Integer id, Date bestBefore,
			Date updateTime, float scoreCoef, Integer examId);
	
	public Date getDeadline(int examId,int problemId);

	//TODO add by 周海水
	public List<PMProblems> getProblemsInfoByExamId(int examId);

}
