package edu.dhu.global.service.impl;

import com.opensymphony.xwork2.ActionContext;
import edu.dhu.global.dao.LogDaoI;
import edu.dhu.global.model.Log;
import edu.dhu.global.service.LogServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("logService")
@Transactional
public class LogServiceImpl implements LogServiceI {

	private LogDaoI logDao;

	@Autowired
	public void setLogDao(LogDaoI logDao) {
		this.logDao = logDao;
	}

	public LogDaoI getLogDao() {
		return logDao;
	}

	@Override
	public List<Log> getAllLog() {
		// TODO Auto-generated method stub
		return logDao.getAllLog();
	}

	@Override
	public boolean WriteLog(Log log,Integer userId, String role) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		Date optime = cal.getTime();
		log.setOptime(optime);
		log.setUserType(role);
		log.setUserId(userId);
		int result = (Integer) logDao.save(log);
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean WriteLog(String type, String content) {
		return false;
	}

	@Override
	public boolean WriteLog(String type, String abstractContent, String content) {
		return false;
	}
//	@Override
//	public boolean WriteLog(Log log) {
//		// TODO Auto-generated method stub
//		Calendar cal = Calendar.getInstance();
//		Date optime = cal.getTime();
//		log.setOptime(optime);
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		if (sessionInfo != null) {
//			String userType = sessionInfo.getRoleNames();
//			log.setUserType(userType);
//			if (sessionInfo.getRoleNames() != null) {
//				int userId;
//				if (sessionInfo.getRoleNames().equals("teacher")
//						|| sessionInfo.getRoleNames().equals("admin")) {
//					userId = sessionInfo.getTeacherId();
//				} else
//					userId = sessionInfo.getUserId();
//				log.setUserId(userId);
//			}
//		}
//		int result = (Integer) logDao.save(log);
//		if (result > 0)
//			return true;
//		else
//			return false;
//	}

//	@Override
//	public boolean WriteLog(String type, String content) {
//		// TODO Auto-generated method stub
//		Log log = new Log();
//		Calendar cal = Calendar.getInstance();
//		Date optime = cal.getTime();
//		log.setOptime(optime);
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		if (sessionInfo != null) {
//
//			String userType = sessionInfo.getRoleNames();
//			log.setUserType(userType);
//			if (sessionInfo.getRoleNames() != null) {
//				int userId;
//				if (sessionInfo.getRoleNames().equals("teacher")
//						|| sessionInfo.getRoleNames().equals("admin")) {
//
//					userId = sessionInfo.getTeacherId();
//				} else
//					userId = sessionInfo.getUserId();
//				log.setUserId(userId);
//			}
//		}
//		log.setType(type);
//		log.setContent(content);
//		int result = (Integer) logDao.save(log);
//		if (result > 0)
//			return true;
//		else
//			return false;
//	}
//
//	@Override
//	public boolean WriteLog(String type, String abstractContent, String content) {
//		// TODO Auto-generated method stub
//		Log log = new Log();
//		Calendar cal = Calendar.getInstance();
//		Date optime = cal.getTime();
//		log.setOptime(optime);
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		if (sessionInfo != null) {
//
//			String userType = sessionInfo.getRoleNames();
//			log.setUserType(userType);
//			if (sessionInfo.getRoleNames() != null) {
//				int userId;
//				if (sessionInfo.getRoleNames().equals("teacher")
//						|| sessionInfo.getRoleNames().equals("admin")) {
//
//					userId = sessionInfo.getTeacherId();
//				} else
//					userId = sessionInfo.getUserId();
//				log.setUserId(userId);
//			}
//		}
//		log.setType(type);
//		log.setContent(content);
//		log.setAbstractContent(abstractContent);
//		int result = (Integer) logDao.save(log);
//		if (result > 0)
//			return true;
//		else
//			return false;
//	}

	@Override
	public List<Log> getLogByCondition(String type, String timeFrom,
			String timeTo) {
		// TODO Auto-generated method stub
		return logDao.getLogByCondition(type, timeFrom, timeTo);
	}

	@Override
	public boolean WriteLog(Log log) {
		return false;
	}

	@Override
	public boolean WriteLog(int userId, String userType, String type,
			String abstractContent, String content) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Log log = new Log();
		Calendar cal = Calendar.getInstance();
		Date optime = cal.getTime();
		log.setOptime(optime);
		log.setUserId(userId);
		log.setUserType(userType);
		log.setType(type);
		log.setContent(content);
		log.setAbstractContent(abstractContent);
		int result = (Integer) logDao.save(log);
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean WriteLog(int userId, String type, String content) {
		Log log = new Log();
		Calendar cal = Calendar.getInstance();
		Date optime = cal.getTime();
		log.setOptime(optime);
		log.setUserId(userId);
		log.setType(type);
		log.setContent(content);
		int result = (Integer) logDao.save(log);
		if (result > 0)
			return true;
		else
			return false;
	}


}
