package edu.dhu.exam.dao;


import edu.dhu.exam.model.Adminusers;
import edu.dhu.exam.model.PMAdminusers;

import java.util.List;

public interface AdminusersDaoI extends BaseDaoI<Adminusers> {

	// 查找所有的管理员老师的用户信息
	public List<PMAdminusers> findAllAdminusers();

	public boolean editAdminuser(PMAdminusers pmadminusers); // 编辑教师信息

	public boolean addAdminuser(PMAdminusers pmadminusers); // 添加教师

	public boolean deleteAdminuser(PMAdminusers pmadminusers); // 删除教师

	public boolean editPassword(PMAdminusers pmadminusers); // 修改密码

	public boolean addAssistant(PMAdminusers pmadminusers); // 添加助教

	public List<PMAdminusers> findAllAssistant(PMAdminusers pmadminusers); // 查看所有助教信息

	public boolean editAssistant(PMAdminusers pmadminusers); // 编辑助教信息

	public List<Adminusers> getAssistantByClassId(int classId); // 获取班级所属的助教

	public List<Adminusers> getAssistantNotBelongClass(int classId,
			String account, String name); // 查看不属于班级的助教

	public Adminusers getAdminuserByAccount(String account);

	public boolean addTeacher(PMAdminusers pmadminusers);

	public List<PMAdminusers> findAllAdminusersInProblems();

	public List<Adminusers> getAdminusersBySchoolId(int schoolId);

	public List<PMAdminusers> findAllAdminusersInPaperProblems();
	
	public Adminusers getAdminuserById(int adminId);

}
