package edu.dhu.service;

import edu.dhu.model.Users;
import edu.dhu.pageModel.DataGrid;
import edu.dhu.pageModel.Json;
import edu.dhu.pageModel.PMUser;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserServiceI {

	// 保存用户信息用于注册
	public PMUser save(PMUser user);

	// 登录使用
	public PMUser login(PMUser user);

	// 删除用户
	public void remove(String ids);

	// 修改用户信息
	public boolean editUserInfo(PMUser user);

	// 修改用户密码
	public boolean editUserPassword(PMUser user);

	// 获取用户列表和数量
	public DataGrid datagrid(PMUser user);

	public boolean editUserInfoByTeacher(int id, String studentNo,
			String chineseName, String username, String banji, String email); // 教师更改学生信息

	public PMUser findStudentByStudentNo(String studentNo); // 根据学号查询学生是否存在

	public boolean insertUser(PMUser user);

	public List<PMUser> getAllStudents(); // 获取所有的学生

	public Users getUserPssword(int id); // 获取用户的密码

	public Users getUser(int id); // 获取学生信息

	public Users getUserByStudentNo(String studentNo); // 通过学号获取学生信息

	public Users userAuthenticate(String userName, String passWord);

	public Json userLogin(PMUser user);

	public List<PMUser> findStudentsByCondition(String studentNo,
			String chineseName, String stu_userName);

	public Users findUserByStudentNoSchoolId(String studentNo, int schoolId);

	public boolean updateSignStudent(PMUser user);// 修改学生注册信息
	
	public boolean updateStudentBanji(Users user);// 修改学生班级信息

	public boolean addSignStudent(PMUser user);// 添加学生注册信息

	public Users findStudentByusername(String username);// 根据用户名查找

	public boolean resetTPW(PMUser user);// 忘记密码时的重置密码发送邮件操作

	public boolean updatePasswordByUuid(PMUser user);

	public List<PMUser> findStudentsByCondition(PMUser user);

	public boolean updatePasswordByUserName(PMUser user);

	public void selectForUpdate(int userid);//一个事务锁的抢占
}
