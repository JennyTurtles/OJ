package edu.dhu.user.service.impl;

import com.opensymphony.xwork2.ActionContext;
import edu.dhu.exam.model.DataGrid;
import edu.dhu.user.dao.UserDaoI;
import edu.dhu.user.model.Json;
import edu.dhu.user.model.PMUser;
import edu.dhu.user.model.Users;
import edu.dhu.user.service.UserServiceI;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

//注解userService   在action中使用
@Service("userService")
@Transactional
public class UserServiceImpl implements UserServiceI {

	// Logger for this class
	private static final Logger logger = Logger
			.getLogger(UserServiceImpl.class);

	private UserDaoI userDao;

	public UserDaoI getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Override
	public PMUser save(PMUser pUser) {
		Users t = new Users();
		// 利用Spring的工具类来复制 表单的内容pUser复制到表的实体类user用于保存到数据库
		BeanUtils.copyProperties(pUser, t, new String[] { "password" });
		userDao.save(t);
		return pUser;
	}

	// 很好的实现查询然后判读用户是否正确 用户名+密码登录
	@Override
	public PMUser login(PMUser user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getUsername());
		params.put("pwd", user.getPassword());
		Users theUser = userDao.get(
				"from Users t where t.username =:name and t.password=:pwd",
				params);
		if (theUser == null) {
			// logger.info("++++++++数据库中查找不到该用户++++++++");
			return null;
		} else {
			BeanUtils.copyProperties(theUser, user);
			// logger.info("用户名:" + user.getUsername() + "密码:" +
			// user.getPassword());
			return user;
		}
	}

	/*
	 * 返回数据表格的格式 count 和list列表 得到总人数和用户列表
	 */

	@Override
	public DataGrid datagrid(PMUser user) {
		DataGrid dg = new DataGrid();
//		// 查询所有的user数据
//		String hql = "from Users t";
//		String totalhql = "select count(*) from Users t ";
//		// 传递的参数
//		Map<String, Object> params = new HashMap<String, Object>();
//		// 排序的语句
//		if (user.getUsername() != null && !user.getUsername().trim().equals("")) {
//			hql = "from Users t where t.username like :name";
//			totalhql = "select count(*) from Users t where t.username like :name";
//			params.put("name", "%%" + user.getUsername().trim() + "%%");
//		}
//		List<Users> l = userDao.find(hql, params, user.getPage(),
//				user.getRows());
//
//		List<PMUser> nl = new ArrayList<PMUser>();
//
//		// 把users的数据转换成PMUser的格式
//		changeModel(l, nl);
//		// 设置总的记录行数
//		dg.setTotalLines(userDao.count(totalhql, params));
//		// 设置每页显示多少行
//		dg.setPageLines(user.getRows());
//		// 设置总的页数,总页数 ＝ (总行数／每页显示多少行)向上取整
//		dg.setTotalPages((long) (Math.ceil((double) dg.getTotalLines()
//				/ (double) dg.getPageLines())));
//		// 设置当前显示第几页
//		dg.setCurrentPage(user.getPage());
//		// 设置当前页的数据行数量
//		dg.setCurrentPageLineNum(nl.size());
//		// 设置当前页的所有数据行
//		dg.setRows(nl);
//		logger.info("要查询的userName" + user.getUsername());
//		logger.info(dg.getTotalLines());
//		logger.info(dg.getRows());
		return dg;
	}

	// 把tuser的数据转换成user的格式
	private void changeModel(List<Users> l, List<PMUser> nl) {

		if (l != null && l.size() > 0) {
			for (Users t : l) {
				PMUser u = new PMUser();
				BeanUtils.copyProperties(t, u);
				nl.add(u);
			}
		}
	}

	/*
	 * 删除用户
	 */
	@Override
	public void remove(String username) {
		String[] nUsernames = username.split(",");
		String hql = "delete Users t where t.username in(";
		for (int i = 0; i < nUsernames.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nUsernames[i] + "'";
		}
		hql += ")";
		logger.info(hql);
		userDao.executeHql(hql);
	}

	// 修改用户的信息
	@Override
	public boolean editUserInfo(PMUser user) {
		if (user.getUsername() != null && !user.getUsername().trim().equals("")) {
			Users theUser = userDao.get(Users.class, user.getId());
			theUser.setUsername(user.getUsername());
			theUser.setChineseName(user.getChineseName());
			theUser.setEmail(user.getEmail());
			theUser.setAnswer(user.getAnswer());
			theUser.setQuestion(user.getQuestion());
			userDao.save(theUser);
			return true;
		} else {
			return false;
		}
	}

	// 修改用户的密码
	@Override
	public boolean editUserPassword(PMUser user) {
		if (user.getPassword() != null && !user.getPassword().trim().equals("")) {
			Users theUser = userDao.get(Users.class, user.getId());
			theUser.setPassword(user.getPassword());
			userDao.save(theUser);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean editUserInfoByTeacher(int id, String studentNo,
			String chineseName, String username, String banji, String email) {
		// TODO Auto-generated method stub
		String hql = "update Users set studentNo='" + studentNo
				+ "',chineseName='" + chineseName + "', username='" + username
				+ "', banji='" + banji + "', email='" + email + "' where id="
				+ id;
		int result = userDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public PMUser findStudentByStudentNo(String studentNo) {
		// TODO Auto-generated method stub
		String hql = "from Users where studentNo='" + studentNo + "'";
		Users user = userDao.get(hql);
		if (user != null) {
			PMUser p = new PMUser();
			p.setId(user.getId());
			p.setStudentNo(user.getStudentNo());
			p.setChineseName(user.getChineseName());
			p.setUsername(user.getUsername());
			p.setEmail(user.getEmail());
			p.setBanji(user.getBanji());
			return p;
		}
		return null;
	}

	@Override
	public boolean insertUser(PMUser user) {
		// TODO Auto-generated method stub
		String studentNo = user.getStudentNo();
		String chineseName = user.getChineseName();
		String banji = user.getBanji();
		Date createDate = new Date();
		int schoolId = user.getSchoolId();
		Users u = new Users();
		u.setChineseName(chineseName);
		u.setStudentNo(studentNo);
		u.setCreateDate(createDate);
		u.setBanji(banji);
		u.setSchoolId(schoolId);
		u.setUsername("");
		u.setPassword("");
		userDao.save(u);
		return true;
	}

	@Override
	public List<PMUser> getAllStudents() {
		// TODO Auto-generated method stub
		List<Users> users = userDao.getAllStudents();
		List<PMUser> plist = new ArrayList<PMUser>();
		for (int i = 0; i < users.size(); i++) {
			Users user = users.get(i);
			PMUser p = new PMUser();
			p.setId(user.getId());
			p.setUsername(user.getUsername());
			p.setChineseName(user.getChineseName());
			p.setStudentNo(user.getStudentNo());
			p.setBanji(user.getBanji());
			p.setEmail(user.getEmail());
			plist.add(p);
		}
		return plist;
	}

	@Override
	public Users getUserPssword(int id) {
		// TODO Auto-generated method stub
		Users user = userDao.get(Users.class, id);
		if (user == null)
			return null;
		else
			return user;
	}

	@Override
	public Users getUser(int id) {
		// TODO Auto-generated method stub
		Users user = userDao.get(Users.class, id);
		if (user == null)
			return null;
		else
			return user;
	}

	@Override
	public Users getUserByStudentNo(String studentNo) {
		// TODO Auto-generated method stub
		Users user = userDao.getUserByStudentNo(studentNo);
		if (user == null)
			return null;
		else
			return user;
	}

	@Override
	public Users userAuthenticate(String userName, String passWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Json userLogin(PMUser user) {
		Json j = new Json();
//		PMUser u = login(user);
//		if (u != null) {
//			SessionInfo sessionInfo = new SessionInfo();
//			sessionInfo.setUserId(u.getId());
//			sessionInfo.setStudentNo(u.getStudentNo());
//			sessionInfo.setLoginName(u.getUsername());
//			sessionInfo.setRoleNames("student");
//			sessionInfo.setLoginPassword(u.getPassword());
//			sessionInfo.setIp(IpUtil.getIpAddr(ServletActionContext
//					.getRequest()));
//			sessionInfo.setFlag(u.getFlag());
//			sessionInfo.setBanji(u.getBanji());
//			sessionInfo.setEmail(u.getEmail());
//			sessionInfo.setChineseName(u.getChineseName());
//
//			// 将登录信息放到session中
//			Map<String, Object> session = ActionContext.getContext()
//					.getSession();
//			session.put("sessionInfo", sessionInfo);
//			j.setObj(sessionInfo);
//			j.setSuccess(true);
//			j.setMsg("登录成功");
//		} else {
//			j.setSuccess(false);
//			j.setMsg("登录失败");
//		}
		return j;
	}

	@Override
	public List<PMUser> findStudentsByCondition(String studentNo,
			String chineseName, String stu_userName) {
		List<Users> users = userDao.findStudentsByCondition(studentNo,
				chineseName, stu_userName);
		List<PMUser> plist = new ArrayList<PMUser>();

		for (int i = 0; i < users.size(); i++) {
			Users user = users.get(i);
			PMUser p = new PMUser();
			p.setId(user.getId());
			p.setUsername(user.getUsername());
			p.setChineseName(user.getChineseName());
			p.setStudentNo(user.getStudentNo());
			p.setBanji(user.getBanji());
			p.setEmail(user.getEmail());
			plist.add(p);
		}

		return plist;
	}

	@Override
	public Users findUserByStudentNoSchoolId(String studentNo, int schoolId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String hql = "from Users where studentNo='" + studentNo
				+ "' and schoolId='" + schoolId + "'";
		Users user = userDao.get(hql);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public boolean updateSignStudent(PMUser user) {
		Date createDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		String time = dateFormat.format(createDate);
		// TODO Auto-generated method stub
		String hql = "update Users set password='" + user.getPassword()
				+ "',chineseName='" + user.getChineseName() + "', username='"
				+ user.getUsername() + "', banji='" + user.getBanji()
				+ "', email='" + user.getEmail() + "'," + "createDate='" + time
				+ "' , question='" + user.getQuestion() + "', answer='"
				+ user.getAnswer() + "'where studentNo='" + user.getStudentNo()
				+ "' and schoolId='" + user.getSchoolId() + "'";
		int result = userDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean updateStudentBanji(Users user) {
		Date createDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		String time = dateFormat.format(createDate);
		// TODO Auto-generated method stub
		String hql = "update Users set banji='" + user.getBanji()
				+ "'where studentNo='" + user.getStudentNo()
				+ "' and schoolId='" + user.getSchoolId() + "'";
		int result = userDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean addSignStudent(PMUser user) {
		// TODO Auto-generated method stub
		String username = user.getUsername();
		String password = user.getPassword();
		int schoolId = user.getSchoolId();
		String studentNo = user.getStudentNo();
		String chineseName = user.getChineseName();
		String banji = user.getBanji();
		Date createDate = new Date();
		String email = user.getEmail();
		String question = user.getQuestion();
		String answer = user.getAnswer();

		Users u = new Users();
		u.setUsername(username);
		u.setPassword(password);
		u.setChineseName(chineseName);
		u.setSchoolId(schoolId);
		u.setStudentNo(studentNo);
		u.setCreateDate(createDate);
		u.setBanji(banji);
		u.setEmail(email);
		u.setQuestion(question);
		u.setAnswer(answer);
		userDao.save(u);
		return true;
	}

	@Override
	public Users findStudentByusername(String username) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Users user = userDao.getUserByUsername(username);
		if (user == null)
			return null;
		else
			return user;
	}

	@Override
	public boolean resetTPW(PMUser user) {
		// TODO Auto-generated method stub
		String hql = "update Users set uuid='" + user.getUuid()
				+ "' where username='" + user.getUsername() + "'";
		int result = userDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean updatePasswordByUuid(PMUser user) {
		// TODO Auto-generated method stub
		String hql = "update Users set password='" + user.getPassword()
				+ "',uuid=' ' where uuid='" + user.getUuid() + "'";
		int result = userDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}

	@Override
	public List<PMUser> findStudentsByCondition(PMUser user) {
		// TODO Auto-generated method stub
		List<Users> users = userDao.findStudentsByCondition(user);
		List<PMUser> plist = new ArrayList<PMUser>();

		for (int i = 0; i < users.size(); i++) {
			Users u = users.get(i);
			PMUser p = new PMUser();
			p.setId(u.getId());
			p.setUsername(u.getUsername());
			p.setChineseName(u.getChineseName());
			p.setStudentNo(u.getStudentNo());
			p.setBanji(u.getBanji());
			p.setEmail(u.getEmail());
			plist.add(p);
		}

		return plist;
	}

	@Override
	public boolean updatePasswordByUserName(PMUser user) {
		// TODO Auto-generated method stub
		String hql = "update Users set password='" + user.getPassword()
				+ "' where username='" + user.getUsername() + "'";
		int result = userDao.executeHql(hql);
		if (result == 1)
			return true;
		else
			return false;
	}
	
	@Override
	public void selectForUpdate(int userId) {
		String sqlString ="select id from gdoj.users where id="+userId+" for update ";
		userDao.executesql(sqlString);
	}
}
