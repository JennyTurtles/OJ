package edu.dhu.user.service.impl;

import edu.dhu.user.dao.SchoolDaoI;
import edu.dhu.user.model.PMSchool;
import edu.dhu.user.model.School;
import edu.dhu.user.service.SchoolServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("schoolService")
@Transactional
public class SchoolServiceImpl implements SchoolServiceI {

	private SchoolDaoI schoolDao;

	public SchoolDaoI getSchoolDao() {
		return schoolDao;
	}

	@Autowired
	public void setSchoolDao(SchoolDaoI schoolDao) {
		this.schoolDao = schoolDao;
	}

	@Override
	public PMSchool getSchoolByName(String name) {
		// TODO Auto-generated method stub
		School school = schoolDao.getSchoolByName(name);
		PMSchool pmschool = new PMSchool();
		if (school != null) {
			pmschool.setId(school.getId());
			pmschool.setName(school.getName());
		} else {
			pmschool = null;
		}
		return pmschool;
	}

	@Override
	public List<PMSchool> findAllShools() {
		// TODO Auto-generated method stub
		return schoolDao.findAllShools();
	}

	@Override
	public School getSchoolById(int schoolId) {
		// TODO Auto-generated method stub
		return schoolDao.get(School.class, schoolId);
	}

	@Override
	public boolean editSchool(PMSchool pmschool) {
		// TODO Auto-generated method stub
		return schoolDao.editSchool(pmschool);
	}

	@Override
	public boolean addSchool(PMSchool pmschool) {
		// TODO Auto-generated method stub
		return schoolDao.addSchool(pmschool);
	}

}
