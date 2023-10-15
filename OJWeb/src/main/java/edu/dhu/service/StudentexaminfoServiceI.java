package edu.dhu.service;

import edu.dhu.model.Studentexaminfo;

public interface StudentexaminfoServiceI {
	// 根据userId和examId获取Studentexaminfo记录
	public Studentexaminfo getStudentexaminfoByUserIdAnExamId(int userId,
			int examId);
	
	//查询此前ip的登录者
	public String getStuByExamIdAndIp(int examId,String ip);
	
	}
