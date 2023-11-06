package edu.dhu.exam.service;

public interface ExamLogServiceI {
	public boolean WriteExamLog(String type, String content,Integer userID); // 写日志
	
	public boolean WriteExamLog(int userId, String type, String content); // 写日志
}
