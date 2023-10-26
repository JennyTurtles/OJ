package edu.dhu.user.dao;


import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.user.model.PMSchool;
import edu.dhu.user.model.School;

import java.util.List;

public interface SchoolDaoI extends BaseDaoI<School> {
	public School getSchoolByName(String name);

	public List<PMSchool> findAllShools();

	public boolean editSchool(PMSchool pmschool);

	public boolean addSchool(PMSchool pmschool);
}
