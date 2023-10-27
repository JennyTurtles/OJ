package edu.dhu.user.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.RespBean;
import edu.dhu.user.model.PMUser;
import edu.dhu.user.model.Users;
import edu.dhu.user.service.UserServiceI;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static edu.dhu.global.util.PasswordUtil.getBCryptPassword;

@RestController
@RequestMapping("/user")
public class UserController{

	@Resource
	private UserServiceI userService;
//	@Resource
//	private ClassstudentsDaoI classstudentsDao;
//	@Resource
//	private ClassesDaoI classesDao;
//	@Resource
//	private AdminusersDaoI adminusersDao;

	// 返回的页面
	public String user() {
		return "user";
	}

	public String userAdd() {
		return "userAdd";
	}

	public String userEdit() {
		return "userEdit";
	}

	// 前台的注册
//	public void reg() {
//		Json j = new Json();
//		try {
//			user.setFlag("1");
//			userService.save(user);
//			j.setSuccess(true);
//			j.setMsg("注册成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			j.setSuccess(false);
//			j.setMsg(e.getMessage().toString());
//		}
//		super.writeJson(j);
//	}
//
//	// 前台登陆页面
//	public void login() {
//		Json j = userLogin(user);
//		super.writeJson(j);
//	}
//
//	public Json userLogin(PMUser user) {
//		Json j = new Json();
//		PMUser u = userService.login(user);
//		if (u != null) {
//			SessionInfo sessionInfo = new SessionInfo();
//			sessionInfo.setUserId(u.getId());
//			sessionInfo.setStudentNo(u.getStudentNo());
//			sessionInfo.setLoginName(u.getUsername());
//			sessionInfo.setRoleNames("student");
//			sessionInfo.setLoginPassword(u.getPassword());
//			sessionInfo.setFlag(u.getFlag());
//			sessionInfo.setBanji(u.getBanji());
//			sessionInfo.setEmail(u.getEmail());
//			sessionInfo.setChineseName(u.getChineseName());
//			sessionInfo.setIp(IpUtil.getIpAddr(ServletActionContext.getRequest()));
//			// 将登录信息放到session中
//			Map<String, Object> session = ActionContext.getContext().getSession();
//			session.put("sessionInfo", sessionInfo);
//
//			// String ping = redisService.ping();
//			// if(ping == null){
//			// j.setObj(null);
//			// j.setSuccess(false);
//			// j.setMsg("不能ping通redis服务器");
//			// logger.info("ping: "+ping);
//			// return j;
//			// }
//
//			j.setObj(sessionInfo);
//			j.setSuccess(true);
//			j.setMsg("登录成功");
//
//			// String json = JSON.toJSONStringWithDateFormat(sessionInfo,
//			// "yyyy-MM-dd HH:mm:ss");
//			// redisService.set("session_user_"+sessionInfo.getUserId(), json,
//			// 1800);
//		} else {
//			j.setSuccess(false);
//			j.setMsg("登录失败");
//		}
//		return j;
//	}
//
//	// 退出登录
//	public void logout() {
//		// 删除session中用户登录信息
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		session.remove("sessionInfo");
//		// redisService.del("session_user_"+user.getId());
//		Json j = new Json();
//		j.setSuccess(true);
//		j.setMsg("退出登录成功");
//		super.writeJson(j);
//	}
//
//	// 删除操作
//	public void remove() {
//		userService.remove(user.getIds());
//		Json j = new Json();
//		j.setSuccess(true);
//		j.setMsg("删除成功");
//		super.writeJson(j);
//	}
//
//	// 修改用户信息
	@PostMapping("/editUserInfo")
	public RespBean editUserInfo(@RequestBody PMUser user,HttpServletRequest request) {
		// 从session中获取登录的用户id
		DecodeToken decodeToken = new DecodeToken(request);
		String id = decodeToken.getUserId();
		user.setId(Integer.valueOf(id));

		boolean b = userService.editUserInfo(user);
		if (b) {
			return RespBean.ok("修改用户信息成功");
		} else {
			return RespBean.error("修改用户信息失败");
		}
	}
//
//	public void editUserInfoByTeacher() // 老师修改学生的信息
//	{
//		int id = user.getId();
//		String chineseName = user.getChineseName();
//		String username = user.getUsername();
//		String banji = user.getBanji();
//		String email = user.getEmail();
//		String studentNo = user.getStudentNo();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			boolean result = userService.editUserInfoByTeacher(id, studentNo, chineseName, username, banji, email);
//			if (result == true) {
//				j.setSuccess(true);
//				j.setMsg("修改学生信息成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("修改学生信息失败");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
	// 修改用户密码
	@PostMapping("/editUserPassword")
	public RespBean editUserPassword(@RequestBody PMUser user,HttpServletRequest request) {
		DecodeToken decodeToken = new DecodeToken(request);
		String id = decodeToken.getUserId();
		user.setId(Integer.valueOf(id));
		user.setPassword(getBCryptPassword(user.getPassword()));
		boolean b = userService.editUserPassword(user);
		if (b) {
			return RespBean.ok("修改用户密码成功");
		} else {
			return RespBean.error("修改用户密码失败");
		}
	}
//
//	public void editUserPasswordByTeacher() { // 老师修改学生密码
//		// 从session中获取登录的用户id
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		boolean b = userService.editUserPassword(user);
//
//		Json j = new Json();
//		if (b) {
//			j.setSuccess(true);
//			j.setMsg("修改用户密码成功");
//			logger.info("********修改用户密码成功********");
//		} else {
//			j.setSuccess(false);
//			j.setMsg("修改用户密码失败");
//		}
//		super.writeJson(j);
//	}
//
//	public void getAllStudents() // 获取所有的学生信息
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMUser> userList = userService.getAllStudents();
//			if (userList != null) {
//				j.setSuccess(true);
//				j.setMsg("获取学生信息成功");
//				j.setObj(userList);
//				logger.info("获取学生信息成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("获取学生信息失败");
//				logger.info("获取学生信息失败");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	public void getPassword() // 获取学生的密码
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			Users user = userService.getUserPssword(this.user.getId().intValue()); // 用户信息
//			if (user != null) {
//				j.setSuccess(true);
//				j.setMsg("获取学生账号成功");
//				j.setObj(user);
//				logger.info("获取学生账号成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("学生账号不存在！");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	public void getUser() // 获取学生的信息
//	{
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			Users user = userService.getUser(this.user.getId().intValue()); // 用户信息
//			if (user != null) {
//				PMUser p = new PMUser();
//				p.setId(user.getId());
//				p.setUsername(user.getUsername());
//				p.setChineseName(user.getChineseName());
//				p.setStudentNo(user.getStudentNo());
//				p.setEmail(user.getEmail());
//				p.setBanji(user.getBanji());
//				j.setSuccess(true);
//				j.setMsg("获取学生信息成功");
//				j.setObj(p);
//				logger.info("获取学生信息成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("学生账号不存在！");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
//	public void getUserByStudentNo() // 根据学号获取学生的信息
//	{
//		String studentNo = user.getStudentNo();
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			Users user = userService.getUserByStudentNo(studentNo); // 用户信息
//			if (user != null) {
//				PMUser p = new PMUser();
//				p.setUsername(user.getUsername());
//				p.setChineseName(user.getChineseName());
//				p.setStudentNo(user.getStudentNo());
//				p.setBanji(user.getBanji());
//				p.setPassword(user.getPassword());
//				j.setSuccess(true);
//				j.setMsg("获取学生信息成功");
//				j.setObj(p);
//				logger.info("获取学生信息成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("学生学号不存在！");
//				super.writeJson(j);
//			}
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录。");
//			super.writeJson(j);
//		}
//	}
//
	@PostMapping("/findUserByStudentNoSchoolId")
	public RespBean findUserByStudentNoSchoolId(@RequestBody Users user,HttpServletRequest request) // 根据学号学校Id获取学生的信息
	{
		String studentNo = user.getStudentNo();
		int schoolId = user.getSchoolId();
		DecodeToken decodeToken = null;
		try {
			decodeToken = new DecodeToken(request);
		}catch (JWTDecodeException ignored){};
		if (decodeToken != null) { // 需要修改
			String role = decodeToken.getRole();
			if (role.equals("student") && !decodeToken.getStudentNo().equals(studentNo)) {
				return RespBean.error("请尝试安全操作");
			}
			user = userService.findUserByStudentNoSchoolId(studentNo, schoolId); // 用户信息
			if (user != null) {
				return RespBean.ok("获取学生信息成功",user);
			} else {
				return RespBean.error("学生学号不存在！");
			}
		}else {
			return RespBean.error("请先登录。");
		}
	}
//
//	public void getUserAdd() // 添加学生的信息
//	{
//		String studentNo = user.getStudentNo();
//		String chineseName = user.getChineseName();
//		String username = "";
//		String banji = user.getBanji();
//		String password = "";
//		int classId = user.getId();
//
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		try {
//			if (sessionInfo != null) {
//
//				Classes onclass = classesDao.get(Classes.class, classId);
//				if (onclass != null) {
//					int teacherId = onclass.getTeacherId();
//					Adminusers adminuser = adminusersDao.get(Adminusers.class, teacherId);
//					if (adminuser != null) {
//						int schoolId = adminuser.getSchoolId();
//						Users user = userService.findUserByStudentNoSchoolId(studentNo, schoolId); // 用户信息
//						if (user != null) {
//							boolean result = classstudentsDao.findClassStudentByUserId(user.getId(), classId); // 如果为true则表明该学生已在表中
//							if (result) {
//								j.setSuccess(false);
//								j.setMsg("该学生已存在，不能重复添加！");
//								super.writeJson(j);
//							} else {
//								result = classstudentsDao.insertClassStudent(user.getId(), classId); // 强用户插入classstudents表
//								// 更新学生人数
//								int studentsNum = classstudentsDao.getClassStudentsNum(classId);
//								boolean results = classesDao.updateClassStudentsNum(classId, studentsNum);
//								if (results) {
//									j.setSuccess(true);
//									j.setMsg("添加学生信息成功");
//									logger.info("添加学生信息成功");
//									super.writeJson(j);
//								} else {
//									j.setSuccess(false);
//									j.setMsg("添加学生信息失败");
//									super.writeJson(j);
//								}
//
//							}
//
//						} else {
//							boolean flag = false;
//							PMUser pMuser = new PMUser();
//							// 将pMProblemTestCaseAdd 同属性的数据复制到problems
//							pMuser.setBanji(banji);
//							pMuser.setSchoolId(schoolId);
//							pMuser.setChineseName(chineseName);
//							pMuser.setStudentNo(studentNo);
//							pMuser.setUsername(username);
//							pMuser.setPassword(password);
//							flag = userService.addSignStudent(pMuser);
//							if (flag) {
//								Users u = userService.findUserByStudentNoSchoolId(studentNo, schoolId);
//								if (u != null) {
//									boolean result = classstudentsDao.findClassStudentByUserId(u.getId(), classId); // 如果为true则表明该学生已在表中
//									if (result == false)
//										result = classstudentsDao.insertClassStudent(u.getId(), classId); // 强用户插入classstudents表
//								}
//
//								// 更新学生人数
//								int studentsNum = classstudentsDao.getClassStudentsNum(classId);
//								boolean results = classesDao.updateClassStudentsNum(classId, studentsNum);
//								j.setSuccess(true);
//								j.setMsg("添加学生信息成功");
//								logger.info("添加学生信息成功");
//								super.writeJson(j);
//							} else {
//								j.setSuccess(false);
//								j.setMsg("添加学生信息失败");
//								super.writeJson(j);
//							}
//
//						}
//					}
//				}
//			} else {
//				j.setSuccess(false);
//				j.setMsg("请先登录。");
//				super.writeJson(j);
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	// 按条件查找学生
//	public void findStudentsByCondition() {
//
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		Json j = new Json();
//		if (sessionInfo != null) {
//			List<PMUser> userList = new ArrayList<PMUser>();
//			if (user.getSchoolId() == 0) {
//				userList = userService.getAllStudents();
//
//			} else {
//				userList = userService.findStudentsByCondition(user);
//			}
//
//			if (userList != null && userList.size() != 0) {
//				j.setSuccess(true);
//				j.setMsg("获取学生信息成功");
//				j.setObj(userList);
//				logger.info("获取学生信息成功");
//				super.writeJson(j);
//			} else {
//				j.setSuccess(false);
//				j.setMsg("获取学生信息失败");
//				logger.info("获取学生信息失败");
//				super.writeJson(j);
//			}
//
//		} else {
//			j.setSuccess(false);
//			j.setMsg("请先登录");
//			super.writeJson(j);
//		}
//	}

	@PostMapping("/signUser")
	@Transactional
	public RespBean signUser(@RequestBody PMUser user) {

		Users pmuser = userService.findStudentByusername(user.getUsername());
		if (pmuser == null) {
			Users u = userService.findUserByStudentNoSchoolId(user.getStudentNo(), user.getSchoolId()); // 用户信息
			if (u != null) {
				if (u.getUsername() == "" || u.getUsername().equals("")) {
					user.setPassword(getBCryptPassword(user.getPassword()));
					boolean results = userService.updateSignStudent(user);
					if (results) {
						return RespBean.ok("修改学生注册信息成功");
					} else {
						return RespBean.error("修改学生注册信息失败");
					}
				} else {
					return RespBean.error("1",u);
				}
			} else {
				user.setPassword(getBCryptPassword(user.getPassword()));
				boolean results = userService.addSignStudent(user);
				if (results) {
					return RespBean.ok("添加学生注册信息成功");
				} else {
					return RespBean.error("添加学生注册信息失败");
				}
			}
		} else {
			return RespBean.error("该用户名已存在");
		}
	}

	@PostMapping("/findStudentByusername")
	public RespBean findStudentByusername(@RequestBody PMUser user) {
		Users pmuser = userService.findStudentByusername(user.getUsername());
		if (pmuser != null) {
			pmuser.setAnswer(null);
			pmuser.setPassword(null);
			return RespBean.ok("查询学生信息成功",pmuser);
		} else {
			return RespBean.error("查询学生信息失败");
		}
	}
//
	@PostMapping("/confirmQuestionAnswer")
	public RespBean confirmQuestionAnswer(@RequestBody PMUser user) {
		Users pmuser = userService.findStudentByusername(user.getUsername());
		if (pmuser != null) {
			if (pmuser.getAnswer() == null)
				return RespBean.error("该学生未提供答案");
			if(pmuser.getAnswer().equals(user.getAnswer()) ){
				return RespBean.ok("问题答案正确");
			}else{
				return RespBean.error("问题答案错误");
			}
		} else {
			return RespBean.error("查询学生信息失败");
		}
	}
//
//	public void getUserByStudentNoClassId() {
//		Json j = new Json();
//		int classId = user.getId();
//		String studentNo = user.getStudentNo();
//
//		Map<String, Object> session = ActionContext.getContext().getSession();
//		SessionInfo sessionInfo = (SessionInfo) session.get("sessionInfo");
//		if (sessionInfo != null) {
//			Classes onclass = classesDao.get(Classes.class, classId);
//			if (onclass != null) {
//				int teacherId = onclass.getTeacherId();
//				Adminusers adminuser = adminusersDao.get(Adminusers.class, teacherId);
//				if (adminuser != null) {
//					int schoolId = adminuser.getSchoolId();
//					Users user = userService.findUserByStudentNoSchoolId(studentNo, schoolId); // 用户信息
//					if (user != null) // users表中存在用户则只在classStudents表中插入数据
//					{
//						PMUser p = new PMUser();
//						p.setUsername(user.getUsername());
//						p.setChineseName(user.getChineseName());
//						p.setStudentNo(user.getStudentNo());
//						p.setBanji(user.getBanji());
//						p.setPassword(user.getPassword());
//						p.setEmail(user.getEmail());
//						j.setSuccess(true);
//						j.setMsg("获取学生信息成功");
//						j.setObj(p);
//						logger.info("获取学生信息成功");
//						super.writeJson(j);
//					} else {
//						j.setSuccess(false);
//						j.setMsg("学生不存在！");
//						super.writeJson(j);
//					}
//				}
//
//			} else {
//				j.setSuccess(false);
//				j.setMsg("请先登录");
//				super.writeJson(j);
//			}
//
//		}
//	}
//
//	public void resetTPW() {// 重置学生密码
//		// 返回前台的json数据
//		Json j = new Json();
//		UUID uuid = UUID.randomUUID();
//		user.setUuid(uuid.toString().replaceAll("\\-", ""));
//		boolean result = userService.resetTPW(user);
//		if (result == true) {
//			try {
//
//				SendMail mail = new SendMail();
//				// 收信人
//				String[] list = { user.getEmail() };
//				// mail.setMailTo(list, "cc");
//				mail.setMailTo(list, "to");
//				// 发信人
//				mail.setMailFrom("dhuoj_noreply@163.com");
//				// mail.setMailFrom("duzhen");
//				// 邮件主题
//				mail.setSubject("OJ系统重置密码激活邮件");
//				// 邮件发送时间
//				mail.setSendDate(new Date());
//				// html格式邮件
//				// 邮件内容
//				String context = "<html>" + "<body>"
//						+ "您将重置在线作业提交OJ系统的登录密码，如果这是您在进行的操作，请点击链接(如果点击无效，请复制以下链接并在浏览器中打开):<br>"
//						+ "<a href='http://218.193.156.209:8080/oj/user/resetPassword.jsp?account=" + user.getUsername()
//						+ "&uuid=" + user.getUuid() + "'>http://218.193.156.209:8080/oj/user/resetPassword.jsp?account="
//						+ user.getUsername() + "&uuid=" + user.getUuid() + "</a>" + "</body>" + "</html>";
//				mail.addHtmlContext(context);
//				// txt格式邮件
//				// mail.addTextContext("");
//				mail.send();
//				System.out.println("send success");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			logger.info("重置密码邮件发送成功");
//			j.setSuccess(true);
//			j.setMsg("重置密码邮件发送成功");
//			super.writeJson(j);
//		} else {
//			logger.info("重置密码邮件发送失败");
//			j.setSuccess(false);
//			j.setMsg("重置密码邮件发送失败");
//			super.writeJson(j);
//		}
//	}
//
//	public void updatePasswordByUuid() {
//		// 返回前台的json数据
//		Json j = new Json();
//		boolean result = userService.updatePasswordByUuid(user);
//		if (result == true) {
//			logger.info("重置密码成功");
//			j.setSuccess(true);
//			j.setMsg("重置密码成功");
//			super.writeJson(j);
//		} else {
//			logger.info("重置密码失败");
//			j.setSuccess(false);
//			j.setMsg("重置密码失败");
//			super.writeJson(j);
//		}
//	}
//
	@PostMapping("/updatePasswordByUserName")
	public RespBean updatePasswordByUserName(@RequestBody PMUser user) {
		user.setPassword(getBCryptPassword(user.getPassword()));
		boolean result = userService.updatePasswordByUserName(user);
		if (result) {
			return RespBean.ok("重置密码成功");
		} else {
			return RespBean.error("重置密码失败");
		}
	}

}
