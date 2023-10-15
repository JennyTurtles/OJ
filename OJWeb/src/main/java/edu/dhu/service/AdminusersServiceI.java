package edu.dhu.service;

import edu.dhu.model.Adminusers;
import edu.dhu.pageModel.PMAdminusers;

import java.util.List;

public interface AdminusersServiceI {

	public List<PMAdminusers> findAllAdminusers();

	public PMAdminusers login(PMAdminusers pmadminusers);

	public PMAdminusers getAdminuserById(int id); // 根据id获取单个老师的信息
	
	public boolean getAdminuserForUpdate(int id);

	public boolean editAdminuser(PMAdminusers pmadminusers); // 编辑教师信息

	public boolean addAdminuser(PMAdminusers pmadminusers); // 添加教师

	public Adminusers getPassword(PMAdminusers pmadminusers); // 获取用户密码

	public boolean editPassword(PMAdminusers pmadminusers); // 修改密码

	public boolean deleteAminuser(PMAdminusers pmadminusers); // 删除教师

	public boolean addAssistant(PMAdminusers pmadminusers); // 添加助教

	public List<PMAdminusers> findAllAssistant(PMAdminusers pmadminusers); // 查看所有助教信息

	public boolean editAssistant(PMAdminusers pmadminusers); // 修改助教

	public List<PMAdminusers> getAssistantByClassId(int classId); // 获取班级所属的助教

	public List<PMAdminusers> getAssistantNotBelongClass(int classId,
			String account, String name); // 查看不属于班级的助教

	public PMAdminusers getAdminuserByAccount(String account); // 通过账号查找

	public boolean addTeacher(PMAdminusers pmadminusers);// 注册教师

	public boolean updateActiveByUuid(String uuid);

	public boolean updateTeacherByaccount(PMAdminusers pmadminusers);

	public boolean resetTPW(PMAdminusers pmadminusers);// 忘记密码时的重置密码发送邮件操作

	public boolean updatePasswordByUuid(PMAdminusers pmadminusers);// 重置密码

	public List<PMAdminusers> findAllAdminusersInProblems();
	
	public List<PMAdminusers> findAllAdminusersInPaperProblems();

	public List<Adminusers> getAdminusersBySchoolId(int schoolId);

	public boolean updatePasswordByAccount(PMAdminusers pmadminusers);

	public boolean updateQueAndAnsByaccount(PMAdminusers pma);

	public boolean updateTeacherNoPasswordByaccount(PMAdminusers pmadminusers);

}
