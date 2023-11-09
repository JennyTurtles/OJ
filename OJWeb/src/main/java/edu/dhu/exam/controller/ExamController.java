package edu.dhu.exam.controller;

import com.opensymphony.xwork2.ActionContext;
import edu.dhu.exam.dao.StudentexaminfoDaoI;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.PMExam;
import edu.dhu.exam.model.Studentexaminfo;
import edu.dhu.exam.service.ExamLogServiceI;
import edu.dhu.exam.service.impl.ExamServiceImpl;
import edu.dhu.global.model.CookieInfo;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.RespBean;
import edu.dhu.global.service.RedisServiceI;
import edu.dhu.problem.model.Solution;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamServiceImpl examService;
    @Resource
    StudentexaminfoDaoI studentexaminfoDao;
    @Resource
    ExamLogServiceI examlogService;
    @Resource
    RedisServiceI redisService;
//    private static Lock lockAddExamInfo = new ReentrantLock();

    @GetMapping("sync_time")
    public RespBean syncTime(Integer examId) {
        Long res = examService.syncTime(examId);
        Map<String, Long> map = new HashMap<>();
        map.put("leftTime",res);
        if (res != null)
            return RespBean.ok("获取考试结束时间成功！",map);
        else
            return RespBean.error("考试ID不存在！");
    }

    @GetMapping("/examList")
    @Transactional
    public RespBean getExamList(Integer pageNum,Integer pageSize, HttpServletRequest request) {
        if (pageNum < 1 || pageSize < 1)
            return RespBean.error("参数错误！");
        PMExam pMExam = new PMExam();
        pMExam.setPage(pageNum);
        pMExam.setRows(pageSize);
        DecodeToken decodeToken = new DecodeToken(request);
        String userId = decodeToken.getUserId();
        pMExam.setStudentId(Integer.parseInt(userId));
        return RespBean.ok("获取考试列表成功！", examService.dataGrid(pMExam));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("/addExamInfo")
    public RespBean addExamInfo(@RequestBody PMExam pMExam,HttpServletRequest req, HttpServletResponse response) // 添加信息到examinfo
    {
//        lockAddExamInfo.lock();
        DecodeToken decodeToken = new DecodeToken(req);
        String userId = decodeToken.getUserId();
        String studentNo = decodeToken.getStudentNo();
        String key = "addExamInfo_" + userId;
        // redis分布式锁
        try {
            // 获取锁失败就结束
            if (!redisService.lock(key, 30)) {
                return RespBean.error("提交本题不能过快,请稍后重试");
            }
        } catch (Exception e) {
            return RespBean.error("服务器错误,请重试");
        }
        boolean locked = false;
        try {
//            HttpServletRequest req = ServletActionContext.getRequest();
//            HttpServletResponse response = ServletActionContext.getResponse();
            Studentexaminfo examinfo = new Studentexaminfo();
            String cookieName = "exam" + userId + pMExam.getId();
            Exam exam = new Exam();
            exam = examService.getExamById(pMExam.getId());
            examinfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(Integer.parseInt(userId), pMExam.getId());
            if (examinfo == null) {
//                session.put("firstLogin", true); // 学生第一次进入考试
                UUID uuid = UUID.randomUUID();
                String loginuuid = uuid.toString().replaceAll("\\-", "");
                Studentexaminfo studentexaminfo = new Studentexaminfo();

                studentexaminfo.setExamId(pMExam.getId());
                studentexaminfo.setUserId(Integer.valueOf(userId));
                studentexaminfo.setScore(0);
                studentexaminfo.setSolved(0);
                studentexaminfo.setSubmit(0);
                studentexaminfo.setRank(0);
                studentexaminfo.setFirstloginTime(new Date());
                if (!exam.getAllowChangeSeat()) {
                    studentexaminfo.setLoginUUID(loginuuid);
                }

                if (req.getRemoteAddr() != null) {
                    studentexaminfo.setLoginIp(req.getRemoteAddr());
//                    // 将uuid存入cookie
                    CookieInfo cookieInfo = new CookieInfo();

                    try {
                        if (!exam.getAllowChangeSeat()) {
                            boolean result = cookieInfo.addCookie(req, response, cookieName, loginuuid);
                            if (result) {
                                studentexaminfoDao.save(studentexaminfo);
                                return RespBean.ok("成功保存信息！");
                            } else {
                                return RespBean.error("cookie信息保存失败");
                            }
                        } else {
                            studentexaminfoDao.save(studentexaminfo);
                            return RespBean.ok("成功保存信息！");
                        }
                    } catch (Exception e) {
                        return RespBean.error("cookie信息保存失败");
                    }
                } else {
                    return RespBean.error("获取不到Ip地址");
                }

            } else {
                // 2020.3.15修改 zzb
//                session.put("firstLogin", false);// 学生不是第一次进入考试
                // 如果该考试禁止换座位
                if (!exam.getAllowChangeSeat()) {
                    // 获取该学生存在数据库的uuid
                    String loginuuid = examinfo.getLoginUUID();
                    String preIp = examinfo.getLoginIp();
                    // 检查cookie
                    CookieInfo cookieInfo = new CookieInfo();
                    // 如果学生不是第一次考试，且学生的uuid不为空，则进行判断是否换了座位。
                    if (loginuuid != null) {
                        try {
                            boolean result = cookieInfo.getCookie(req, response, cookieName, loginuuid);
                            if (!result) {
                                String newIp = req.getRemoteAddr();
                                String preName = studentexaminfoDao.getStuByExamIdAndIp(examinfo.getExamId(), newIp);
                                // TODO ChineseName
                                if (preName == null) {
                                    examlogService.WriteExamLog("尝试换座",
                                            "sessionInfo.getChineseName()" + "(" + studentNo + ")尝试从"
                                                    + newIp + "ip登录，原ip为" + preIp + "。新ip地址" + newIp + "无人登录过", Integer.valueOf(userId));
                                } else {
                                    examlogService.WriteExamLog("尝试换座",
                                            "sessionInfo.getChineseName()" + "(" + studentNo + ")尝试从"
                                                    + newIp + "ip登录，原ip为" + preIp + "。新ip地址" + newIp + "曾由" + preName
                                                    + "(" + studentNo + ")登录过", Integer.valueOf(userId));
                                }
                                return RespBean.error("本场考试不允许更换电脑，需要教师在本场考试的“学生管理”里点“允许换座”，才可更换电脑。如果是同一台电脑第二次进入考试，也请教师同样操作");
                            } else {
                                return RespBean.ok("考试允许换座位！");
                            }
                        } catch (Exception e) {
                            return RespBean.error("本场考试不允许更换电脑，需要教师在本场考试的“学生管理”里点“允许换座”，才可更换电脑。如果是同一台电脑第二次进入考试，也请教师同样操作");
                        }
                    } else {// 如果学生不是第一次考试，且学生的uuid为空，说明老师允许该学生换座位，且已经将该学生的uuid置为空一次。
                        UUID uuid = UUID.randomUUID(); // 重新生成该学生的uuid
                        loginuuid = uuid.toString().replaceAll("\\-", "");
                        // 修改该学生的该场考试的uuid
                        examinfo.setLoginUUID(loginuuid);
                        examinfo.setLoginIp(req.getRemoteAddr());
                        // !此处有魔改，未存入cookie
                        studentexaminfoDao.updateStudentexaminfo(examinfo);
                        // 将uuid保存进cookie和数据库中
//                        boolean result = cookieInfo.addCookie(req, response, cookieName, loginuuid);
//                        if (result) {
//                            studentexaminfoDao.updateStudentexaminfo(examinfo);
//
//                            j.setSuccess(true);
//                        } else {
//                            j.setSuccess(false);
//                            j.setMsg("cookie信息更新失败");
//                        }
                    }
                } else { // 如果该考试允许换座位
                    return RespBean.ok("考试允许换座位！");
                }
            }
        } catch (Exception e) {
            return RespBean.error("本场考试不允许更换电脑，需要教师在本场考试的“学生管理”里点“允许换座”，才可更换电脑。如果是同一台电脑第二次进入考试，也请教师同样操作");
        } finally {
//            lockAddExamInfo.unlock();
             redisService.unLock(key);
        }
        return RespBean.ok("成功保存信息！");
    }
}
