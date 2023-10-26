package edu.dhu.user.dao;

import edu.dhu.exam.dao.BaseDaoI;
import edu.dhu.user.model.PMUser;
import edu.dhu.user.model.Users;

import java.util.List;

public interface UserDaoI extends BaseDaoI<Users> {

	public List<Users> getAllStudents(); // 获取所有的学生信息

	public List<Users> getUsersByExamId(int examId); // 获取班级的所有学生
	
	public List<Users> getUsersByExamIdAndTeacherId(int examId,int teacherId); // 获取班级的所有学生

	public List<Users> getUsersByClassId(int classId); // 获取班级的所有学生

	public Users getUserByStudentNo(String studentNo);

	public List<Users> findStudentsByCondition(String studentNo,
			String chineseName, String stu_userName); // 按条件查找学生

	public Users getUserByUsername(String username);
	
	public Users getUserByUserId(int userId);

	public List<Users> findStudentsByCondition(PMUser user);
	
	

}
