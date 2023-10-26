package edu.dhu.user.service.impl;

import edu.dhu.exam.dao.AdminusersDaoI;
import edu.dhu.user.model.Adminusers;
import edu.dhu.user.model.PMAdminusers;
import edu.dhu.user.service.AdminusersServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adminusersService")
public class AdminusersServiceImpl implements AdminusersServiceI {

	private AdminusersDaoI adminusersDao;
	private static final Logger logger = Logger
			.getLogger(AdminusersServiceImpl.class);

	@Autowired
	public void setAdminusersDaoI(AdminusersDaoI adminusersDao) {
		this.adminusersDao = adminusersDao;
	}

	public AdminusersDaoI getAdminusersDaoI() {
		return adminusersDao;
	}

	@Override
	public List<PMAdminusers> findAllAdminusers() {
		// TODO Auto-generated method stub
		return adminusersDao.findAllAdminusers();
	}

	@Override
	public PMAdminusers login(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", pmadminusers.getAccount());
		params.put("password", pmadminusers.getPassword());
		Adminusers teacher = adminusersDao
				.get("from Adminusers t where t.account =:account and t.password=:password",
						params);
		if (teacher == null) {
			logger.info("++++++++数据库中查找不到该用户++++++++");
			return null;
		} else {

			logger.info("用户名:" + pmadminusers.getAccount() + "密码:"
					+ pmadminusers.getPassword());
			pmadminusers.setId(teacher.getId());
			pmadminusers.setEmail(teacher.getEmail());
			pmadminusers.setName(teacher.getName());
			pmadminusers.setRole(teacher.getRole());
			pmadminusers.setActive(teacher.getActive());
			pmadminusers.setSchoolId(teacher.getSchoolId());
			return pmadminusers;
		}
	}

	@Override
	public PMAdminusers getAdminuserById(int id) {
		// TODO Auto-generated method stub
		Adminusers adminuser = adminusersDao.get(Adminusers.class, id);
		if (adminuser != null) {
			PMAdminusers pa = new PMAdminusers();
			pa.setAccount(adminuser.getAccount());
			pa.setName(adminuser.getName());
			pa.setId(adminuser.getId());
			pa.setEmail(adminuser.getEmail());
			pa.setRole(adminuser.getRole());
			return pa;
		} else
			return null;
	}

	@Override
	public boolean editAdminuser(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.editAdminuser(pmadminusers);
	}

	@Override
	public boolean addAdminuser(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.addAdminuser(pmadminusers);
	}

	@Override
	public Adminusers getPassword(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		Adminusers adminuser = adminusersDao.get(Adminusers.class,
				pmadminusers.getId());
		return adminuser;
	}

	@Override
	public boolean editPassword(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.editPassword(pmadminusers);
	}

	@Override
	public boolean deleteAminuser(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.deleteAdminuser(pmadminusers);
	}

	@Override
	public boolean addAssistant(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.addAssistant(pmadminusers);
	}

	@Override
	public List<PMAdminusers> findAllAssistant(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.findAllAssistant(pmadminusers);
	}

	@Override
	public boolean editAssistant(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.editAssistant(pmadminusers);
	}

	@Override
	public List<PMAdminusers> getAssistantByClassId(int classId) {
		// TODO Auto-generated method stub
		List<Adminusers> list = adminusersDao.getAssistantByClassId(classId);
		List<PMAdminusers> plist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Adminusers a = list.get(i);
			PMAdminusers p = new PMAdminusers();
			p.setId(a.getId());
			p.setAccount(a.getAccount());
			p.setName(a.getName());
			plist.add(p);
		}
		return plist;
	}

	@Override
	public List<PMAdminusers> getAssistantNotBelongClass(int classId,
			String account, String name) {
		// TODO Auto-generated method stub
		List<Adminusers> list = adminusersDao.getAssistantNotBelongClass(
				classId, account, name);
		List<PMAdminusers> plist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Adminusers a = list.get(i);
			PMAdminusers p = new PMAdminusers();
			p.setId(a.getId());
			p.setAccount(a.getAccount());
			p.setName(a.getName());
			plist.add(p);
		}
		return plist;
	}

	@Override
	public PMAdminusers getAdminuserByAccount(String account) {
		// TODO Auto-generated method stub

		Adminusers a = adminusersDao.getAdminuserByAccount(account);
		if (a != null) {
			PMAdminusers p = new PMAdminusers();
			p.setId(a.getId());
			p.setAccount(a.getAccount());
			p.setName(a.getName());
			p.setSchoolId(a.getSchoolId());
			p.setEmail(a.getEmail());
			p.setQuestion(a.getQuestion());
			p.setAnswer(a.getAnswer());
			p.setPassword(a.getPassword());
			return p;
		} else {
			return null;
		}
	}

	@Override
	public boolean addTeacher(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		return adminusersDao.addTeacher(pmadminusers);
	}

	@Override
	public boolean updateActiveByUuid(String uuid) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set active=1,uuid=''  where uuid='"
				+ uuid + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateTeacherByaccount(PMAdminusers pmadminusers) {
		String hql = "update Adminusers set password='"
				+ pmadminusers.getPassword() + "',email='"
				+ pmadminusers.getEmail() + "',question='"
				+ pmadminusers.getQuestion() + "' , " + " answer = '"
				+ pmadminusers.getAnswer() + "' where account='"
				+ pmadminusers.getAccount() + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateTeacherNoPasswordByaccount(PMAdminusers pmadminusers) {
		String hql = "update Adminusers set email='" + pmadminusers.getEmail()
				+ "',question='" + pmadminusers.getQuestion() + "' , "
				+ " answer = '" + pmadminusers.getAnswer()
				+ "' where account='" + pmadminusers.getAccount() + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean resetTPW(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set uuid='" + pmadminusers.getUuid()
				+ "' where account='" + pmadminusers.getAccount() + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updatePasswordByUuid(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set password='"
				+ pmadminusers.getPassword() + "',uuid=' ' where uuid='"
				+ pmadminusers.getUuid() + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updatePasswordByAccount(PMAdminusers pmadminusers) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set password='"
				+ pmadminusers.getPassword() + "' where account='"
				+ pmadminusers.getAccount() + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public List<PMAdminusers> findAllAdminusersInProblems() {
		// TODO Auto-generated method stub
		return adminusersDao.findAllAdminusersInProblems();
	}
	
	@Override
	public List<PMAdminusers> findAllAdminusersInPaperProblems() {
		// TODO Auto-generated method stub
		return adminusersDao.findAllAdminusersInPaperProblems();
	}

	@Override
	public List<Adminusers> getAdminusersBySchoolId(int schoolId) {
		// TODO Auto-generated method stub
		return adminusersDao.getAdminusersBySchoolId(schoolId);
	}

	@Override
	public boolean updateQueAndAnsByaccount(PMAdminusers pma) {
		// TODO Auto-generated method stub
		String hql = "update Adminusers set question='" + pma.getQuestion()
				+ "',answer='" + pma.getAnswer() + "', where account='"
				+ pma.getAccount() + "'";
		int result = adminusersDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean getAdminuserForUpdate(int id) {
		// TODO Auto-generated method stub
		String sqlString ="select id from adminusers where id ="+id+" for update";
		adminusersDao.executesql(sqlString);
		return true;
	}

}
