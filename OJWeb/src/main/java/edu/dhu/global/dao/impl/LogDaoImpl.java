package edu.dhu.global.dao.impl;

import edu.dhu.exam.dao.impl.BaseDaoImpl;
import edu.dhu.global.dao.LogDaoI;
import edu.dhu.global.model.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("logDao")
public class LogDaoImpl extends BaseDaoImpl<Log> implements LogDaoI {

	@Override
	public List<Log> getAllLog() {
		// TODO Auto-generated method stub
		String hql = "from Log order by optime desc";
		return this.find(hql);
	}

	@Override
	public List<Log> getLogByCondition(String type, String timeFrom,
			String timeTo) {
		// TODO Auto-generated method stub
		String hql = "from Log where id is not null";
		if (type != null && type.equals("全部") == false
				&& type.equals("") == false) {
			hql = hql + " and type='" + type + "'";
		}
		if (timeFrom != null && timeTo != null && timeFrom.equals("") == false
				&& timeTo.equals("") == false) {
			String temp;
			if (timeFrom.compareTo(timeTo) > 0) {
				temp = timeFrom;
				timeFrom = timeTo;
				timeTo = temp;
			}
			hql = hql + " and optime between '" + timeFrom + "' and '" + timeTo
					+ "'";
		}
		hql = hql + " order by optime desc";
		System.out.println(hql);
		return this.find(hql);
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		String hql = "delete from Log where id=" + id;
		int result = this.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public Log getLogById(int id) {
		// TODO Auto-generated method stub
		String hql = "from Log where id=" + id;
		return this.get(hql);
	}

}
