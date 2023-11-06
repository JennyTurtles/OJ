package edu.dhu.problem.dao;

import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.problem.model.Examproblems;

import java.util.Date;
import java.util.List;

public interface ExamproblemDaoI extends BaseDaoI<Examproblems> {

	// 根据ExamId获取最大的展示序号
	public int getMaxDisplaySequenceByExamId(int examId);

	// 根据ExamId从(start,end]减一
	public void SubDisplaySequence(int examId, int start, int end);

	// 根据ExamId从[start,end)加一
	public void AddDisplaySequence(int examId, int start, int end);

	public int getDisplaySequence(int examId, int problemId);

	public List<Examproblems> getProblemsUndo(int userId, int examId);

	public int getExamproblemNum(int examId); // 获取考试题目数量

	// 根据examID和problemID获取Examproblems
	public Examproblems getExamproblemsByExamIdAndProblemId(int examId,
			int problemId);

	// 根据examID和problemID获取Examproblems的题目分数
	public float getScoreByExamIdAndProblemId(int examId, int problemId);

	public List<Examproblems> getDisplaySequenceByExamId(int examId);

	public boolean alterScore(int id, float score, int examId); // 更改题目分数

	public boolean alterDeadline(int id, Date deadline, Date updateTime,
			int examId); // 更新截止时间

	// 根据examID获取所有的Examproblems列表
	public List<Examproblems> getExamproblemsListByExamId(int examId);

	public boolean alterbestBeforeAndscoreCoef(Integer id, Date bestBefore,
			Date updateTime, float scoreCoef, Integer examId);
}
