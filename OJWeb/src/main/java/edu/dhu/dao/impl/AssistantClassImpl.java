package edu.dhu.dao.impl;

import edu.dhu.dao.AssistantClassDaoI;
import edu.dhu.model.AssistantClass;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository("assistantClassDao")
public class AssistantClassImpl extends BaseDaoImpl<AssistantClass> implements
		AssistantClassDaoI {

	@Override
	public boolean addAssistantClass(int assistantId, int classId) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from AssistantClass where assistantId="
				+ assistantId + " and classId=" + classId;
		long n = this.count(hql);
		if (n == 0) // 表示数据库中没有该项数据则添加
		{
			AssistantClass ac = new AssistantClass();
			ac.setAssistantId(assistantId);
			ac.setClassId(classId);
			this.save(ac);
			if (ac.getId() != null) // 插入成功
			{
				return true;
			} else
				return false;
		} else
			// 如果有，则同样返回true
			return false;
	}

	@Override
	public List<AssistantClass> getAssistantClassByAssistantId(int assistantId) {
		// TODO Auto-generated method stub
		String hql = "from AssistantClass where assistantId=" + assistantId;
		return this.find(hql);
	}

	@Override
	public List<AssistantClass> getAssistantClassAndClassNotEnd(int teacherId,
			int assistantId) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		Date time = cal.getTime();
		String hql = "select distinct(ac) from AssistantClass ac,Classes c where ac.assistantId="
				+ assistantId + " and c.createTime>:time";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("time", time);
		return q.list();
	}

	@Override
	public boolean delAssistantClass(int assistantId, int classId) {
		// TODO Auto-generated method stub
		String hql = "delete from AssistantClass where assistantId="
				+ assistantId + " and classId=" + classId;
		int result = this.executeHql(hql);
		if (result > 0)
			return true;
		else
			return false;
	}

}
