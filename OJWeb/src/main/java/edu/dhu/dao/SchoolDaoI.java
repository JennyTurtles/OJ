package edu.dhu.dao;

import edu.dhu.model.School;
import edu.dhu.pageModel.PMSchool;

import java.util.List;

public interface SchoolDaoI extends BaseDaoI<School> {
	public School getSchoolByName(String name);

	public List<PMSchool> findAllShools();

	public boolean editSchool(PMSchool pmschool);

	public boolean addSchool(PMSchool pmschool);
}
