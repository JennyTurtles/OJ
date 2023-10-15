package edu.dhu.service;

import edu.dhu.model.School;
import edu.dhu.pageModel.PMSchool;

import java.util.List;

public interface SchoolServiceI {

	public PMSchool getSchoolByName(String name); // 根据学校姓名获取

	public List<PMSchool> findAllShools();

	public School getSchoolById(int schoolId);

	public boolean editSchool(PMSchool pmschool);// 修改学校名称

	public boolean addSchool(PMSchool pmschool);// 添加学校

}
