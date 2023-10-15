package edu.dhu.dao.impl;

import edu.dhu.dao.SchoolDaoI;
import edu.dhu.model.Adminusers;
import edu.dhu.model.School;
import edu.dhu.pageModel.PMSchool;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("schoolDao")
public class SchoolDaoImpl extends BaseDaoImpl<School> implements SchoolDaoI {

	@Override
	public School getSchoolByName(String name) {
		// TODO Auto-generated method stub
		String hql = "from School where name='" + name + "'";
		School school = this.get(hql);
		return school;
	}

	@Override
	public List<PMSchool> findAllShools() {
		// TODO Auto-generated method stub
		String hql = "from School order by name asc";
		List<School> schools = this.find(hql);
		List<PMSchool> pmschools = new ArrayList<PMSchool>();
		for (int i = 0; i < schools.size(); i++) {
			School school = schools.get(i);
			PMSchool p = new PMSchool();
			p.setId(school.getId());
			p.setName(school.getName());
			pmschools.add(p);
		}
		return pmschools;
	}

	@Override
	public boolean editSchool(PMSchool pmschool) {
		// TODO Auto-generated method stub
		String hql = "from School where name=:name";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("name", pmschool.getName());
		List<Adminusers> list = q.list(); // 如果学校名已存在,则不能添加
		if (list.size() == 0) {
			String name = pmschool.getName();
			int id = pmschool.getId();
			hql = "update School set name=:name where id=:id";
			q = this.getCurrentSession().createQuery(hql);
			q.setParameter("name", name);
			q.setParameter("id", id);
			int result = q.executeUpdate();
			if (result > 0)
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public boolean addSchool(PMSchool pmschool) {
		// TODO Auto-generated method stub
		String hql = "from School where name=:name";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("name", pmschool.getName());
		List<Adminusers> list = q.list(); // 如果学校名已存在,则不能添加
		if (list.size() == 0) {
			School school = new School();
			school.setName(pmschool.getName());
			Integer result = (Integer) this.save(school);
			if (result != null)
				return true;
			else
				return false;
		} else
			return false;
	}
}
