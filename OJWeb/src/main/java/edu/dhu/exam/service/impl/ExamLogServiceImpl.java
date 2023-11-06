package edu.dhu.exam.service.impl;

import com.opensymphony.xwork2.ActionContext;
import edu.dhu.exam.dao.ExamLogDaoI;
import edu.dhu.exam.model.ExamLog;
import edu.dhu.exam.service.ExamLogServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service("examlogService")
@Transactional
public class ExamLogServiceImpl implements ExamLogServiceI {
	
	private ExamLogDaoI examlogDao;
	
	@Autowired
	public void setExamlogDao(ExamLogDaoI examlogDao) {
		this.examlogDao = examlogDao;
	}

	public ExamLogDaoI getExamlogDao() {
		return examlogDao;
	}

	
	//网页端  学生换座写入log
	@Override
	public boolean WriteExamLog(String type, String content,Integer userId) {
		ExamLog examlog = new ExamLog();
		Calendar cal = Calendar.getInstance();
		Date optime = cal.getTime();
		examlog.setOptime(optime);
		Map<String, Object> session = ActionContext.getContext().getSession();
		examlog.setUserId(userId);
		examlog.setType(type);
		examlog.setContent(content);
		int result = (Integer) examlogDao.save(examlog);
		if (result > 0)
			return true;
		else
			return false;
	}
	
	
	@Override
	public boolean WriteExamLog(int userId, String type, String content) {
		ExamLog examlog = new ExamLog();
		Calendar cal = Calendar.getInstance();
		Date optime = cal.getTime();
		examlog.setOptime(optime);
		examlog.setUserId(userId);
		examlog.setType(type);
		examlog.setContent(content);
		int result = (Integer) examlogDao.save(examlog);
		if (result > 0)
			return true;
		else
			return false;
	}

}
