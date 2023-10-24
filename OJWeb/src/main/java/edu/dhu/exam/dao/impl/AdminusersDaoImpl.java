package edu.dhu.exam.dao.impl;

import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.exam.model.Adminusers;
import edu.dhu.exam.model.PMAdminusers;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("adminusersDao")
public class AdminusersDaoImpl extends BaseDaoImpl<Adminusers> implements
		AdminusersDaoI {

//	private AssistantClassDaoI assistantClassDao;
//	private ProblemsDaoI problemsDao;
//	private ClassesDaoI classesDao;
//	private SchoolDaoI schoolDao;
//
//	public SchoolDaoI getSchoolDao() {
//		return schoolDao;
//	}
//
//	@Autowired
//	public void setSchoolDao(SchoolDaoI schoolDao) {
//		this.schoolDao = schoolDao;
//	}
//
//	@Autowired
//	public void setClassesDao(ClassesDaoI classesDao) {
//		this.classesDao = classesDao;
//	}
//
//	public ClassesDaoI getClassesDao() {
//		return classesDao;
//	}
//
//	public ProblemsDaoI getProblemsDao() {
//		return problemsDao;
//	}
//
//	@Autowired
//	public void setProblemsDao(ProblemsDaoI problemsDao) {
//		this.problemsDao = problemsDao;
//	}
//
//	@Autowired
//	public void setAssistantClassDao(AssistantClassDaoI assistantClassDao) {
//		this.assistantClassDao = assistantClassDao;
//	}
//
//	public AssistantClassDaoI getAssistantClassDao() {
//		return assistantClassDao;
//	}

	@Override
	public List<PMAdminusers> findAllAdminusers() {
		// TODO Auto-generated method stub
		String hql = "from Adminusers where role='teacher'";
		List<Adminusers> adminusersList = this.find(hql);
		List<PMAdminusers> pmadminusersList = new ArrayList<PMAdminusers>();
//		for (int i = 0; i < adminusersList.size(); i++) {
//			Adminusers adminusers = adminusersList.get(i);
//			School s = schoolDao.get(School.class, adminusers.getSchoolId());
//			PMAdminusers p = new PMAdminusers();
//			p.setId(adminusers.getId());
//			p.setName(adminusers.getName());
//			p.setSchoolId(adminusers.getSchoolId());
//			p.setSchoolname(s.getName());
//			p.setAccount(adminusers.getAccount());
//			p.setRole(adminusers.getRole());
//			p.setEmail(adminusers.getEmail());
//			pmadminusersList.add(p);
//		}
		return pmadminusersList;
	}

	@Override
	public boolean editAdminuser(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set name=:name,role=:role,email=:email where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		String name = pmadminusers.getName();
		String role = pmadminusers.getRole();
		String email = pmadminusers.getEmail();
		int id = pmadminusers.getId();
		q.setParameter("name", name);
		q.setParameter("role", role);
		q.setParameter("email", email);
		q.setParameter("id", id);
		int result = q.executeUpdate();
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean addAdminuser(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "from Adminusers where account=:account";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("account", pmadminusers.getAccount());
		List<Adminusers> list = q.list(); // 如果账号以及存在,则不能添加
		if (list.size() == 0) {
			Adminusers adminuser = new Adminusers();
			adminuser.setAccount(pmadminusers.getAccount());
			adminuser.setPassword(pmadminusers.getPassword());
			adminuser.setName(pmadminusers.getName());
			adminuser.setRole(pmadminusers.getRole());
			adminuser.setEmail(pmadminusers.getEmail());
	        adminuser.setActive(pmadminusers.getActive());
	        adminuser.setSchoolId(pmadminusers.getSchoolId());
			Integer result = (Integer) this.save(adminuser);
			if (result != null)
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public boolean editPassword(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String password = pmadminusers.getPassword();
		int id = pmadminusers.getId();
		String hql = "update Adminusers set password=:password where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("password", password);
		q.setParameter("id", id);
		int result = q.executeUpdate();
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteAdminuser(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		int id = pmadminusers.getId();
		Adminusers user = this.get(Adminusers.class, id);
		String hql = "delete from Adminusers where id=" + id;
		int result = this.executeHql(hql);
		if (result > 0) {
			if (user != null && user.getRole().equals("assistant")) // 如果输助教则删除AssistantClass表中的信息
			{
				hql = "delete from AssistantClass where assistantId="
						+ pmadminusers.getId();
				this.executeHql(hql);
			}
			return true;
		} else
			return false;
	}

	@Override
	public boolean addAssistant(PMAdminusers pmadminusers) {
//		// TODO Auto-generated method stub
//		String hql = "from Adminusers where account=:account";
//		Query q = this.getCurrentSession().createQuery(hql);
//		q.setParameter("account", pmadminusers.getAccount());
//		List<Adminusers> list = q.list(); // 如果账号以及存在,则不能添加
//		if (list.size() == 0) {
//			Adminusers user = new Adminusers();
//			user.setAccount(pmadminusers.getAccount());
//			user.setPassword(pmadminusers.getPassword());
//			user.setName(pmadminusers.getName());
//			user.setRole("assistant");
//			user.setEmail(pmadminusers.getEmail());
//			user.setActive(1);
//			user.setSchoolId(pmadminusers.getSchoolId());
//			this.save(user);
//			if (user.getId() != null) {
//				int assistantId = user.getId().intValue();
//				String classIds = pmadminusers.getClassIds();
//				if (classIds != null && classIds.length() > 0) {
//					String classId[] = classIds.split(",");
//					for (int i = 0; i < classId.length; i++) {
//						boolean result = assistantClassDao.addAssistantClass(
//								assistantId, Integer.parseInt(classId[i]));
//						if (result == false)
//							return false;
//					}
//				}
//				return true;
//			} else
//				return false;
//		} else
//			return false;
		return true;
	}

	@Override
	public List<PMAdminusers> findAllAssistant(PMAdminusers pmadminusers) { // 查看所有助教
		// TODO Auto-generated method stub
		List<PMAdminusers> pmadminusersList = new ArrayList<PMAdminusers>();
//		if (pmadminusers.getId() != 0) {// 普通教师登陆
//			String hql = "from Adminusers where role='assistant'";
//			List<Adminusers> adminusersList = this.find(hql);
//			List<Classes> classesList = classesDao
//					.findClassByTeacherId(pmadminusers.getId());
//			ArrayList<Integer> a = new ArrayList<Integer>();
//
//			for (int j = 0; j < classesList.size(); j++) {
//				a.add(j, classesList.get(j).getId());
//			}
//
//			for (int i = 0; i < adminusersList.size(); i++) {
//				ArrayList<Integer> comment = new ArrayList<Integer>();
//				ArrayList<Integer> ac = new ArrayList<Integer>();
//				comment.addAll(a);
//				Adminusers assistant = adminusersList.get(i);
//
//				List<AssistantClass> assistantclasses = assistantClassDao
//						.getAssistantClassByAssistantId(assistant.getId());
//				for (int j = 0; j < assistantclasses.size(); j++) {
//					ac.add(j, assistantclasses.get(j).getClassId());
//				}
//				comment.retainAll(ac);
//				if (assistant.getSchoolId() == pmadminusers.getSchoolId()
//						&& comment.size() != 0) {
//					PMAdminusers p = new PMAdminusers();
//					p.setId(assistant.getId());
//					p.setName(assistant.getName());
//					p.setAccount(assistant.getAccount());
//					p.setRole(assistant.getRole());
//					p.setEmail(assistant.getEmail());
//					pmadminusersList.add(p);
//				}
//
//			}
//
//		} else {// 管理员登陆
//			String hql = "";
//			if (pmadminusers.getSchoolId() == 0) {
//				hql = "from Adminusers where role='assistant'";
//			} else {
//				hql = "from Adminusers where role='assistant' and schoolId="
//						+ pmadminusers.getSchoolId();
//			}
//
//			List<Adminusers> adminusersList = this.find(hql);
//			pmadminusersList = new ArrayList<PMAdminusers>();
//			for (int i = 0; i < adminusersList.size(); i++) {
//				Adminusers assistant = adminusersList.get(i);
//				PMAdminusers p = new PMAdminusers();
//				p.setId(assistant.getId());
//				p.setName(assistant.getName());
//				p.setAccount(assistant.getAccount());
//				p.setRole(assistant.getRole());
//				p.setEmail(assistant.getEmail());
//				pmadminusersList.add(p);
//
//			}
//		}

		return pmadminusersList;
	}

	@Override
	public boolean editAssistant(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set name=:name,email=:email where id=:id";
		Query q = this.getCurrentSession().createQuery(hql);
		String name = pmadminusers.getName();
		String role = pmadminusers.getRole();
		String email = pmadminusers.getEmail();
		int id = pmadminusers.getId();
		q.setParameter("name", name);
		q.setParameter("email", email);
		q.setParameter("id", id);
		int result = q.executeUpdate();
		if (result > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<Adminusers> getAssistantByClassId(int classId) {
		// TODO Auto-generated method stub
		String hql = "select distinct(a) from Adminusers a,AssistantClass ac where a.id=ac.assistantId and ac.classId="
				+ classId;
		return this.find(hql);
	}

	@Override
	public List<Adminusers> getAssistantNotBelongClass(int classId,
			String account, String name) {
		// TODO Auto-generated method stub
		String hql = "select distinct(a) from Adminusers a where a.role='assistant' and a.id not in (select ac.assistantId from AssistantClass ac where ac.classId=:classId)";
		if (account != null && account.length() > 0) {
			hql = hql + " and a.account like :account";
		}
		if (name != null && name.length() > 0) {
			hql = hql + " and a.name like :name";
		}
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("classId", classId);
		if (account != null && account.length() > 0)
			q.setParameter("account", "%" + account + "%");
		if (name != null && name.length() > 0)
			q.setParameter("name", "%" + name + "%");
		return q.list();
	}

	@Override
	public Adminusers getAdminuserByAccount(String account) {
		// TODO Auto-generated method stub
		String hql = "from Adminusers where account='" + account + "'";
		List<Adminusers> list = this.find(hql);
		if (list != null && list.size() != 0) {
			return list.get(0);
		} else
			return null;
	}

	@Override
	public boolean addTeacher(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "from Adminusers where account=:account";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setParameter("account", pmadminusers.getAccount());
		List<Adminusers> list = q.list(); // 如果账号以及存在,则不能添加
		if (list.size() == 0) {
			Adminusers adminuser = new Adminusers();
			adminuser.setAccount(pmadminusers.getAccount());
			adminuser.setPassword(pmadminusers.getPassword());
			adminuser.setName(pmadminusers.getName());
			adminuser.setRole("teacher");
			adminuser.setEmail(pmadminusers.getEmail());
			adminuser.setActive(1);
			adminuser.setSchoolId(pmadminusers.getSchoolId());
			// adminuser.setUuid(pmadminusers.getUuid());
			adminuser.setQuestion(pmadminusers.getQuestion());
			adminuser.setAnswer(pmadminusers.getAnswer());
			Integer result = (Integer) this.save(adminuser);
			if (result != null)
				return true;
			else
				return false;
		} else
			return false;
	}

	@Override
	public List<PMAdminusers> findAllAdminusersInProblems() {
		String hql="select DISTINCT a from Problems p, Adminusers a where a.id=p.teacherId";
		List<Adminusers> adminusersList = this.find(hql);
		List<PMAdminusers> pmadminusersList = new ArrayList<PMAdminusers>();
		for (int i = 0; i < adminusersList.size(); i++) {
			Adminusers adminusers = adminusersList.get(i);
				PMAdminusers p = new PMAdminusers();
				p.setId(adminusers.getId());
				p.setName(adminusers.getName());
				p.setAccount(adminusers.getAccount());
				p.setRole(adminusers.getRole());
				p.setEmail(adminusers.getEmail());
				pmadminusersList.add(p);
			}
		return pmadminusersList;
	}

	@Override
	public List<PMAdminusers> findAllAdminusersInPaperProblems() {
		String hql="select DISTINCT a from Paper_Problems p, Adminusers a where a.id=p.teacherId";
		List<Adminusers> adminusersList = this.find(hql);
		List<PMAdminusers> pmadminusersList = new ArrayList<PMAdminusers>();
		for (int i = 0; i < adminusersList.size(); i++) {
			Adminusers adminusers = adminusersList.get(i);
				PMAdminusers p = new PMAdminusers();
				p.setId(adminusers.getId());
				p.setName(adminusers.getName());
				p.setAccount(adminusers.getAccount());
				p.setRole(adminusers.getRole());
				p.setEmail(adminusers.getEmail());
				pmadminusersList.add(p);
			}
		return pmadminusersList;
	}



	@Override
	public List<Adminusers> getAdminusersBySchoolId(int schoolId) {
		// TODO Auto-generated method stub
		String hql;
		if(schoolId != 0){
			hql = "from Adminusers where role='teacher' and schoolId ='"
					+ schoolId + "'";
		}else{
			hql = "select distinct ad from Adminusers ad where ad.role='teacher'";
		}
		List<Adminusers> adminusersList = this.find(hql);
		return adminusersList;
	}

	@Override
	public Adminusers getAdminuserById(int adminId) {
		return this.get(Adminusers.class, adminId);
	}


	
}
