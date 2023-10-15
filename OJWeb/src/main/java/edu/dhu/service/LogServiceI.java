package edu.dhu.service;

import edu.dhu.model.Log;

import java.util.List;

public interface LogServiceI {

	public List<Log> getAllLog(); // 获取所有的log

	public List<Log> getLogByCondition(String type, String timeFrom,
			String timeTo);

	public boolean WriteLog(Log log); // 写日志

	public boolean WriteLog(String type, String content); // 写日志

	public boolean WriteLog(String type, String abstractContent, String content); // 写日志
	
	public boolean WriteLog(int userId, String type, String content); // 写日志
	
	public boolean WriteLog(int userId, String userType, String type,
			String abstractContent, String content); // webservice写日志
}
