package edu.dhu.global.dao;


import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.global.model.Log;

import java.util.List;

public interface LogDaoI extends BaseDaoI<Log> {

	public List<Log> getAllLog(); // 查找所有的log日志

	public List<Log> getLogByCondition(String type, String timeFrom,
									   String timeTo);//

	public boolean deleteById(int id); // 通过id删除

	public Log getLogById(int id);
}
