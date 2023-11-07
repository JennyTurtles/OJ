package edu.dhu.ws;


import com.alibaba.fastjson2.JSON;
import edu.dhu.cache.*;
import edu.dhu.exam.dao.StudentexaminfoDaoI;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.PMExam;
import edu.dhu.exam.model.Studentexamdetail;
import edu.dhu.exam.model.Studentexaminfo;
import edu.dhu.exam.service.ExamLogServiceI;
import edu.dhu.exam.service.ExamServiceI;
import edu.dhu.exam.service.StudentexamdetailServiceI;
import edu.dhu.exam.ws.SolutionWebSocketHandler;
import edu.dhu.global.model.Log;
import edu.dhu.global.service.LogServiceI;
import edu.dhu.problem.model.*;
import edu.dhu.problem.service.*;
//import edu.dhu.service.OJWS;
import edu.dhu.solution.model.Submittedcode;
import edu.dhu.solution.service.SubmittedcodeServiceI;
import edu.dhu.user.model.Json;
import edu.dhu.user.model.PMAdminusers;
import edu.dhu.user.model.PMUser;
import edu.dhu.user.model.Users;
import edu.dhu.user.service.AdminusersServiceI;
import edu.dhu.user.service.UserServiceI;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@DubboService
public class OJWSImpl implements OJWS {
	
	@Resource 
    private WebServiceContext webServiceContext;
    @Resource
    private AdminusersServiceI adminServiceI;
    @Resource
    private UserServiceI userService;
    @Resource
    private ExamServiceI examService;
    @Resource
    private ProblemsServiceI problemsServiceI;
    @Resource
    private ProblemtestcasesServiceI problemtestcasesServiceI;
    @Resource
    private ExamproblemServiceI examproblemService;
    @Resource
    private StudentexamdetailServiceI studentexamdetailServiceI;
    @Resource
    private SubmittedcodeServiceI submittedcodeService;
    @Resource
    private ProblemsServiceI problemsService;
    @Resource
    private StudentexamdetailServiceI studentexamdetailService;
    @Resource
    private ExamLogServiceI examlogService;
    @Resource
    private LogServiceI logService;
    @Resource
    SolutionWebSocketHandler solutionWebSocketHandler;
    @Resource
    private SolutionServiceI solutionService;
    @Resource
    private GradeProblemServiceI gradeProblemService;
    @Resource
    private CheckSimilarityServiceI checkSimilarityService;
    @Resource
    private WrongcasesServiceI wrongcasesService;
    @Resource
    private StudentexaminfoDaoI studentexaminfoDao;
//    @Resource
//    private ItrainProblemCatServiceI itrainProblemCatService;
//    @Resource
//    private StudentTrainCatDetailServiceI studentTrainCatDetailService;
    @Resource
    private StudentTrainProbDetailServiceI studentTrainProbDetailService;

    private static Lock lockGetSolution = new ReentrantLock();// 锁对象
    private static Lock lockUpdateSolution = new ReentrantLock();// 锁对象
    private static Lock lockGetExamProblem = new ReentrantLock();// 锁对象
    private static Lock lockSubmitThisProblem = new ReentrantLock();// 锁对象
    private static Lock lockSubmitCode = new ReentrantLock();// 锁对象

	// 给给个学生分配一个锁，让学生可以并行提交代码，但是每个学生只能线性提交代码
    private final static HashMap<Integer, ReentrantLock> lockMap = new HashMap<>();

    OJWSUtil ojwsUtil = new OJWSUtil();
    Date nowdate = new Date();// 创建一个时间对象，获取到当前的时间
    String timeStr = ojwsUtil.SimpleDateFormat(nowdate);

    // 将登陆人信息转换成xml字符串格式
    @Override
    public String wsLogin(String userName, String password) {
        String xmlString;
        OJWSUtil oj = new OJWSUtil();
        PMUser user = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        user = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (user == null) {
            user = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, user);
        }
        xmlString = oj.createXmlToString(user);
        return xmlString;
    }

    private PMUser GetUser(String userName, String password) {
        PMUser user = new PMUser();
        PMUser userReturn = null;
        user.setUsername(userName);
        user.setPassword(password);
        try {
            userReturn = userService.login(user);
        } catch (Exception e) {
            userReturn = new PMUser();
            userReturn.setRspMsg("服务器错误,请稍后重试!");
        }

        if (userReturn == null || userReturn.getId() == null) {
            userReturn = new PMUser();
            userReturn.setRspMsg("用户名或密码错误");
        } else {
            userReturn.setRspMsg("Success");
            WebServiceContext wsContext = new org.apache.cxf.jaxws.context.WebServiceContextImpl();
            MessageContext mc = wsContext.getMessageContext();
            if (mc != null) {
                HttpServletRequest servletContext = (HttpServletRequest) mc
                        .get(MessageContext.SERVLET_REQUEST);
                userReturn.setIp(servletContext.getRemoteAddr());
            }
        }
        return userReturn;
    }

    @Override
    public String wsGetExamList(String username, String password) {
        // 将根据学生Id获取的考试列表转换成xml格式的字符串
        String xmlString;
        List<PMExam> pmelist = new ArrayList();
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(username + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(username, password);
            wsusersCacheManager.putObject(username + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                pmelist = examService.getExamsByUserId(userReturn.getId());
                xmlString = oj.ExamListToXml(pmelist);
            } catch (Exception e) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    @Override
    public String wsGetExamById(String userName, String password, int examId) {
        String xmlString;
        // Exam exam = new Exam();
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                WSExamCacheManager examCacheManager = WSExamCacheManager.getInstance();
                Exam exam = new Exam();
                xmlString = (String) examCacheManager.getObject("exam_" + examId);
                if (xmlString == null) {
                    exam = examService.getExamById(examId);
                    xmlString = oj.ExamByIdToXml(exam);
                    examCacheManager.putObject("exam_" + examId, xmlString);
                }
            } catch (Exception e) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    /**
     * 生成XML格式的字符串 将ProblemsList即考试列表转换成xml格式
     */
    @Override
    public byte[] wsGetProblem(String userName, String password, int examId, int problemId) {
        // TODO Auto-generated method stub
        String xmlString;
        byte[] data = new byte[0];
        PMProblemInfo proInfo = new PMProblemInfo();
        List<Problemtestcases> procasesList = new ArrayList();
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            String key = userReturn.getUsername() + userReturn.getId();

            if (key.length() < 10) {
                key = key.concat(new String("0000000000").substring(0, 10 - key.length()));

            }
            try {
                WSProblemsCachManager problemsCacheManager = WSProblemsCachManager.getInstance();
                xmlString = (String) problemsCacheManager.getObject("examProblemId_" + examId + "_" + problemId);
                if (xmlString == null) {
                    proInfo = problemsServiceI.findProblemInfoById(problemId);
                    procasesList = problemtestcasesServiceI.getProblemtestcasesByProblemId(problemId);
                    xmlString = oj.ProblemsListToXml(proInfo, procasesList);
                    problemsCacheManager.putObject("examProblemId_" + examId + "_" + problemId, xmlString);
                }
                // 开始加密
            } catch (Exception e) {
                e.printStackTrace();
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！!!!</rspMsg><time>"
                        + timeStr + "</time></root>";
            }
            data = Encrypt.encrypt(key, xmlString);

        }
        return data;
    }

    private static Lock lockAddExamInfo = new ReentrantLock();

    /**
     * 生成XML格式的字符串 将ExamProblemsList转换成xml格式
     */
    @Override
    public String wsGetExamProblems(String userName, String password, int examId) {
        Exam exam = examService.getExamByExamId(examId);
        Date local_now = new Date();
        if (local_now.getTime() < exam.getStarttime().getTime()) {
            String xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>考试未开始！</rspMsg><time>"
                    + timeStr + "</time></root>";
            return xmlString;
        } else {
            lockAddExamInfo.lock();
            String xmlString = null;
            List<PMProblems> examproblemsList = new ArrayList();
            OJWSUtil oj = new OJWSUtil();
            PMUser userReturn = new PMUser();
            try {
                WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
                userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
                if (userReturn == null) {
                    userReturn = GetUser(userName, password);
                    wsusersCacheManager.putObject(userName + "-" + password, userReturn);
                }
                if (userReturn.getRspMsg().equals("Success")) {
                    int userId = userReturn.getId();
                    Studentexaminfo examinfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(userReturn.getId(),
                            examId);
                    if (examinfo == null) {// first to join
                        examinfo = new Studentexaminfo();

                        UUID uuidStr = UUID.randomUUID();
                        String loginuuid = uuidStr.toString().replaceAll("\\-", "");
                        examinfo.setExamId(examId);
                        examinfo.setUserId(userReturn.getId());
                        examinfo.setScore(0);
                        examinfo.setSolved(0);
                        examinfo.setSubmit(0);
                        examinfo.setRank(0);
                        examinfo.setFirstloginTime(new Date());
                        examinfo.setLoginUUID(loginuuid);
                        studentexaminfoDao.save(examinfo);

                    }
                    WSExamproblemsCacheManager examproblemsCacheManager = WSExamproblemsCacheManager.getInstance();
                    boolean flag = examService.checkExamByUserIdAndExamId(examId, userId);
                    if (flag) {
                        xmlString = (String) examproblemsCacheManager.getObject("exam_" + examId);
                        if (xmlString == null) {
                            examproblemsList = examproblemService.getProblemsInfoByExamId(examId);
                            xmlString = oj.ExamproblemsInfoListToXml(examproblemsList);
                            examproblemsCacheManager.putObject("exam_" + examId, xmlString);
                        }
                    } else {
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>该生不允许参加本场考试！</rspMsg><time>"
                                + timeStr + "</time></root>";
                    }

                } else {
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                            + timeStr + "</time></root>";
                }
            } catch (Exception e) {
                e.printStackTrace();
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            } finally {
                lockAddExamInfo.unlock();
            }
            return xmlString;
        }


    }
    
    /**
     * 生成XML格式的字符串   参加考试类型为智能训练页面数据
     */
//    @Override
//  	public String wsGetExamProCatagorys(String userName, String password, int examId) {
//    	 Exam exam = examService.getExamByExamId(examId);
//         Date local_now = new Date();
//         String xmlString = null;
//         if (local_now.getTime() < exam.getStarttime().getTime()) {
//             xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>考试未开始！</rspMsg><time>"
//                     + timeStr + "</time></root>";
//         }else{
//        	 lockAddExamInfo.lock();
//             OJWSUtil oj = new OJWSUtil();
//             PMUser userReturn = new PMUser();
//             try{
//                 userReturn = GetUser(userName, password);
////            	 WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
////                 userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
////                 if (userReturn == null) {
////                     userReturn = GetUser(userName, password);
////                     wsusersCacheManager.putObject(userName + "-" + password, userReturn);
////                 }
//                 if(userReturn.getRspMsg().equals("Success")){
//                	 int userId = userReturn.getId();
//                     Studentexaminfo examinfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(userId,
//                             examId);
//                     //当前题目标题
//                     String currentProTitle = null;
//
//                     Map<String,Object> data = new HashMap<String, Object>();
//
//                     if(examinfo == null){
//                    	 examinfo = new Studentexaminfo();
//
//                         UUID uuidStr = UUID.randomUUID();
//                         String loginuuid = uuidStr.toString().replaceAll("\\-", "");
//                         examinfo.setExamId(examId);
//                         examinfo.setUserId(userId);
//                         examinfo.setScore(0);
//                         examinfo.setSolved(0);
//                         examinfo.setSubmit(0);
//                         examinfo.setRank(0);
//                         examinfo.setFirstloginTime(new Date());
//                         examinfo.setLoginUUID(loginuuid);
//
//                         studentexaminfoDao.save(examinfo);
//                     }else{
//                    	 //获取当前题目标题
//                    	 if(examinfo.getCurrentProb() != null)
//                    	 {
//                    		 Problems pro = problemsService.findProblemById(examinfo.getCurrentProb());
//                        	 currentProTitle = pro.getTitle();
//                    	 }
//                    	 data.put("examId", examId);
//                    	 data.put("currentProCatagory", examinfo.getCurrentCat());
//                    	 data.put("currentProTitle", currentProTitle);
//                    	 data.put("currentProId", examinfo.getCurrentProb());
//                     }
//
//
////                     WSExamProCatagoryCacheManager examProCatagoryCacheManager = WSExamProCatagoryCacheManager.getInstance();
//                     boolean flag = examService.checkExamByUserIdAndExamId(examId, userId);
//
//                     if(flag){
//                         List<Map<String,Object>> examProCatgoryList = itrainProblemCatService.getExamCatagoryList(examId,0);
//                         List<StudentTrainCatDetail> trainCatDetailList = studentTrainCatDetailService.getTrainCatDetailList(examId,userId);
//
//                         xmlString = oj.ExamProCatagoryInfoListToXml(examProCatgoryList,trainCatDetailList,data);
////                    	 xmlString = (String) examProCatagoryCacheManager.getObject("proCatexam_" + examId);
////                         if (xmlString == null) {
////                             List<Map<String,Object>> examProCatgoryList = itrainProblemCatService.getExamCatagoryList(examId,0);
////                             List<StudentTrainCatDetail> trainCatDetailList = studentTrainCatDetailService.getTrainCatDetailList(examId,userId);
////
////                             xmlString = oj.ExamProCatagoryInfoListToXml(examProCatgoryList,trainCatDetailList,data);
////                             examProCatagoryCacheManager.putObject("proCatexam_" + examId, xmlString);
////                         }
//
//                     }else{
//                    	 xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>该生不允许参加本场考试！</rspMsg><time>"
//                                 + timeStr + "</time></root>";
//                     }
//
//                 }else{
//                	 xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                             + timeStr + "</time></root>";
//                 }
//             }catch (Exception e) {
//            	 e.printStackTrace();
//                 xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//                         + timeStr + "</time></root>";
//			}finally{
//				lockAddExamInfo.unlock();
//			}
//         }
//  		return xmlString;
//  	}
     

	/**
     * 生成XML格式的字符串 将ExamDetailList转换成xml格式
     */
    @Override
    public String wsGetExamDetail(String userName, String password, int examId) {
        // TODO Auto-generated method stub
        String xmlString;

        List<Studentexamdetail> studentexamdetailList = new ArrayList();
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                int userId = userReturn.getId();
                WSStudentexamdetailCacheManager studetailCacheManager = WSStudentexamdetailCacheManager.getInstance();
                xmlString = (String) studetailCacheManager.getObject("exam_" + examId + "_" + userId);
                if (xmlString == null) {
                    studentexamdetailList = studentexamdetailServiceI
                            .getAllStudentexamdetailListByUserIdAndExamId(userId, examId);
                    xmlString = oj.ExamdetailListToXml(studentexamdetailList);
                    studetailCacheManager.putObject("exam_" + examId + "_" + userId, xmlString);
                }
            } catch (Exception e) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    // 获取SubmittedCode表中的信息返回
    @Override
    public String wsSubmittedCode(String userName, String password, int problemId) {
        String xmlString = null;

        List<Submittedcode> submittedcodeList = new ArrayList();
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                int userId = userReturn.getId();
                Users u = userService.getUser(userId);
                int schoolId = u.getSchoolId();
                submittedcodeList = checkSimilarityService.getSubmittedListByProblemIdAndSchoolId(problemId, schoolId);
                xmlString = oj.SubmittedcodeListToXml(submittedcodeList);
            } catch (Exception e) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    @Override
    public String wsViewWrongCase(String userName, String password, int examId, int problemId, int caseId,
                                   boolean caseInfo) {
        // TODO Auto-generated method stub
        String xmlString = null;
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                int userId = userReturn.getId();
                PMWrongAndCorrect pMWrongAndCorrect = wrongcasesService.WS_getHint(userId, examId, problemId, caseId,
                        caseInfo);
                xmlString = oj.ViewWrongCaseToXml(pMWrongAndCorrect);
            } catch (Exception e) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    @Override
    public String wsGetExamProblemStatus(String userName, String password, int examId, int problemId) {
        // TODO Auto-generated method stub
        String xmlString = null;
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                int userId = userReturn.getId();
                PMWrongAndCorrectIds pMWrongAndCorrectIds = wrongcasesService.WS_getAllWrongAndRightCases(userId,
                        examId, problemId);
                xmlString = oj.ExamProblemStatusToXml(pMWrongAndCorrectIds);
            } catch (Exception e) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    /*
     * 先判断LockMap中当前userid是否存在，若不存在则赋值加锁，存在就加上锁否则空的话直接赋值，加锁最后遍历找到userid对应的锁进行释放
     */
    public synchronized Lock getUserLock(int userId) {
        if (lockMap.containsKey(userId)) {
            return lockMap.get(userId);
        } else {
            ReentrantLock lock = new ReentrantLock();
            lockMap.put(userId, lock);
            return lockMap.get(userId);
        }
    }

    /**
     * 将xml转换成相应对象 提交代码操作
     */
    @Override
    public String wsSubmitCode(String userName, String password, String codeXml) {
        lockSubmitCode.lock();// 获得锁
        String xmlString = null;
        OJWSUtil oj = new OJWSUtil();
        Solution solution = new Solution();
        try {
            PMUser userReturn = new PMUser();
            WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
            userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
            if (userReturn == null) {
                userReturn = GetUser(userName, password);
                wsusersCacheManager.putObject(userName + "-" + password, userReturn);
            }
            int userId = userReturn.getId();

            if (userReturn.getRspMsg().equals("Success")) {

                // 此处获取xml中solution的相关信息；
                solution = oj.SubmitCodeXmltoSolution(codeXml);
                if (solution.getSourceCode().length() > 64000) {
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>代码长度不能超过64000个字符</rspMsg><time>"
                            + timeStr + "</time></root>";
                    return xmlString;
                }
                solution.setUserid(userId);
                // 设置提交时间
                solution.setSubmitTime(new Date());
                // 获取考试开始时间和结束时间
                Exam exam = examService.getExamById(solution.getExamId());
                Date now = new Date();
                Date startTime = exam.getStarttime();
                Date endTime = exam.getEndtime();
                Date deadline = examproblemService.getDeadline(solution.getExamId(), solution.getProblemId());
                // 如果考试开始之前提交代码
                if (now.getTime() < startTime.getTime()) {
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>考试还没开始，无法提交代码！</rspMsg><time>"
                            + timeStr + "</time></root>";
                } else if (deadline != null && now.getTime() > deadline.getTime()) {

                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>已经超过截止时间不能提交代码！</rspMsg><time>"
                            + timeStr + "</time></root>";
                }
                // 如果考试结束之后提交代码
                else if (now.getTime() > endTime.getTime()) {
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>考试已经结束，无法再提交代码！</rspMsg><time>"
                            + timeStr + "</time></root>";
                } else {
                    // 根据problemID查找Problems
                    Problems problem = problemsService.findProblemById(solution.getProblemId());
                    // 根据userId+examId+problemId到studentExamDetail表查找
                    Studentexamdetail studentexamdetail = studentexamdetailService.getStatusByUserIDexamIDproblemId(
                            solution.getUserid(), solution.getExamId(), solution.getProblemId());

                    // 提交本题之后再次提交代码
                    if (studentexamdetail != null && studentexamdetail.isFinished()) {
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>已经提交过本题，不能再提交代码！</rspMsg><time>"
                                + timeStr + "</time></root>";
                        return xmlString;
                    }
                    // 更新Problems表的submit字段，增加1
                    if (problem.getSubmit() == null) {
                        problem.setSubmit(1);
                    } else {
                        problem.setSubmit(problem.getSubmit() + 1);
                    }
                    // 此处获取xml中wrongcases的caseId,output信息
                    List<Wrongcases> wrongcases = new ArrayList();
                    wrongcases = oj.SubmitCodeXmltoWrongcases(codeXml);
                    solution = submittedcodeService.WS_submitCode(problem, solution, studentexamdetail, wrongcases, now,
                            startTime, endTime);
                    if (solution.getId() > 0) {

                        float score;
                        if (solution.getScore() > 0) {
                            score = solution.getScore();
                        } else {
                            // 获取该题的所得的分数情况
                            score = gradeProblemService.gradeProblemBySolution(solution);
                        }

                        // 此处算分
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg>"
                                + "<time>" + timeStr + "</time>" + "<solutionId>" + solution.getId() + "</solutionId>"
                                + "<score>" + score + "</score>" + "</root>";
                    } else {
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>代码提交失败</rspMsg><time>"
                                + timeStr + "</time></root>";
                    }
                }
            } else {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                        + timeStr + "</time></root>";
            }
        } catch (Exception e) {
            String sOut = "";
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement s : trace) {
                sOut += "\tat " + s + "\r\n";
            }
            // 异常信息最大记录19000个字符，数据库该字段最大为20K
            int count = sOut.length() > 19000 ? 19000 : sOut.length();
            sOut = sOut.substring(0, count - 1);
            int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e.getLocalizedMessage().length();
            String localMessage = "";
            if (e.getLocalizedMessage() != null) {
                localMessage = e.getLocalizedMessage().substring(0, leng - 1);
            }
            String type = "(WS)代码提交";
            String userType = "student";
            String content = sOut;
            String abstractContent = "学生id:" + solution.getUserid() + "考试id:" + solution.getExamId() + "题目id:"
                    + solution.getProblemId() + "\n" + localMessage;
            logService.WriteLog(solution.getUserid(), userType, type, abstractContent, content);

            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                    + timeStr + "</time>" + "<Exception>" + sOut + "</Exception></root>";
        } finally {
            lockSubmitCode.unlock();
        }

        return xmlString;
    }

    PMSubmitProblemInfo wssolution = new PMSubmitProblemInfo();

    @Override
    public String wsSubmitThisProblem(String userName, String password, String codeXml) {
        lockSubmitThisProblem.lock();// 获得锁        
        String xmlString = null;
        String key = "submitThisProblem_" + userName;
        try {
            OJWSUtil oj = new OJWSUtil();

            PMUser userReturn = new PMUser();
            WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
            userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
            if (userReturn == null) {
                userReturn = GetUser(userName, password);
                wsusersCacheManager.putObject(userName + "-" + password, userReturn);
            }
            if (userReturn.getRspMsg().equals("Success")) {
                try {
                    int userId = userReturn.getId();
                    // 此处从xml中获取solution中的值
                    wssolution = oj.SubProblemXmltoSolution(codeXml);
                    wssolution.setUserId(userId);
                    // 根据userID，examID，problemID在Studentexamdetail中查找获取studentexamdetail记录
                    Studentexamdetail studentexamdetail = studentexamdetailService.getStatusByUserIDexamIDproblemId(
                            wssolution.getUserId(), wssolution.getExamId(), wssolution.getProblemId());
                    // Studentexamdetail为空说明还没有裁判过，则提示用户
                    if (studentexamdetail == null) {
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>必须先提交代码才能提交本题。</rspMsg><time>"
                                + timeStr + "</time></root>";

                    } else if (studentexamdetail.isFinished()) {
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>不能重复提交</rspMsg><time>"
                                + timeStr + "</time></root>";

                    } else {
                        Json json = solutionService.WS_submitThisProblem(studentexamdetail, wssolution);
                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>"
                                + (json.isSuccess() ? "Success" : json.getMsg()) + "</rspMsg><time>" + timeStr + "</time></root>";
                    }

                } catch (Exception e) {
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                            + timeStr + "</time></root>";
                }

            } else {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                        + timeStr + "</time></root>";
            }
            return xmlString;
        } catch (Exception e) {
            String sOut = "";
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement s : trace) {
                sOut += "\tat " + s + "\r\n";
            }
            // 异常信息最大记录19000个字符，数据库该字段最大为20K
            int count = sOut.length() > 19000 ? 19000 : sOut.length();
            sOut = sOut.substring(0, count - 1);
            int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e.getLocalizedMessage().length();
            String localMessage = "";
            if (e.getLocalizedMessage() != null) {
                localMessage = e.getLocalizedMessage().substring(0, leng - 1);
            }
            Log log = new Log();
            log.setType("提交本题");
            log.setOptime(new Date());
            log.setUserId(wssolution.getUserId());
            log.setUserType("student");
            log.setContent(sOut);
            log.setAbstractContent("学生id:" + wssolution.getUserId() + "考试id:" + wssolution.getExamId() + "题目id:"
                    + wssolution.getProblemId() + "\n" + localMessage);
            logService.WriteLog(log);

            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>提交本题失败</rspMsg><time>"
                    + timeStr + "</time></root>";
            return xmlString;
        } finally {
            lockSubmitThisProblem.unlock();// 释放锁
            // System.out.println(pMWrongAndCorrectIds.getUserId()+"的线程结束+????????????????????????????/");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String wsGetSolutions(String account, String password, int number) {
        lockGetSolution.lock();
        String xmlString = null;

        try {
            // //裁判机调用，验证管理员
            PMAdminusers pmAdminusers = null;
            WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
            pmAdminusers = (PMAdminusers) wsusersCacheManager.getObject(account + "-" + password);
            if (pmAdminusers == null) {
                pmAdminusers = adminServiceI.getAdminuserByAccount(account);
                wsusersCacheManager.putObject(account + "-" + password, pmAdminusers);
            }

            if (pmAdminusers == null || !pmAdminusers.getPassword().equals(password)) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>failure, wrong username or password</rspMsg><time>"
                        + timeStr + "</time></root>";
                return xmlString;
            }

            List<Solution> solutionList = solutionService.getSolutionsByNumber(2 * number);
            List<Solution> ResultSolutionList = new ArrayList<Solution>();
            if (solutionList.isEmpty() || solutionList.size() == 0) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
                        + timeStr + "</time><solution/></root>";
            } else {
                int length = solutionList.size();
                for (int i = 0; i < length && ResultSolutionList.size() < number; i++) {
                	try {
                        Solution solution = solutionList.get(i);
                        //获取本场考试类型
                        Exam exam = examService.getExamById(solution.getExamId());
                        if("iTraining".equals(exam.getType())){
                        	StudentTrainProbDetail stpd = studentTrainProbDetailService.getStatusByUserIdAndExamIdAndProId(solution.getUserid(),
                        			solution.getExamId(), solution.getProblemId());
                        	if(stpd == null){
                        		solution.setStatus("SKIP");
                                solution.setRemark("userid error");
                                solutionService.updateSolution(solution);
                                continue;
                        	}
                        	if(solution.getId() < stpd.getSolutionId()){
                        	    solution.setStatus("SKIP");
                        		solution.setRemark("is not newest");
                                solutionService.updateSolution(solution);
                                continue;
                        	}else{
                        		stpd.setStatus("QUEUE");
                                solution.setStatus("QUEUE");
                                studentTrainProbDetailService.updateStudentTrainProbDetail(stpd);
                                solutionService.updateSolution(solution);
                                ResultSolutionList.add(solution);
                        	}
                        }else{
                        	Studentexamdetail examDetail = studentexamdetailServiceI.getStatusByUserIDexamIDproblemId(
                                    solution.getUserid(), solution.getExamId(), solution.getProblemId());
                            if (examDetail == null) {
                                solution.setStatus("SKIP");
                                solution.setRemark("userid error");
                                // System.out.println("跳过了");
                                solutionService.updateSolution(solution);
                                continue;
                            }
                            if (solution.getId() < examDetail.getSolutionId()) {
                                solution.setStatus("SKIP");
                                solution.setRemark("is not newest");
                                solutionService.updateSolution(solution);
                                continue;
                            } else {
                                examDetail.setStatus("QUEUE");
                                solution.setStatus("QUEUE");
                                studentexamdetailServiceI.updateStudentexamdetail(examDetail);
                                solutionService.updateSolution(solution);
                                ResultSolutionList.add(solution);
                            }
                            /*
                             * examDetail.setStatus("QUEUE"); //begin transaction //
                             * CommonDAO.updateMore(new Object[]{solution,
                             * examDetailBean});
                             * solutionService.updateSolution(solution);
                             * studentexamdetailServiceI
                             * .updateStudentexamdetail(examDetail);
                             */
                            // }

                        }						
					} catch (Exception e) {
						e.printStackTrace();
					}
             
                }
                OJWSUtil oj = new OJWSUtil();
                xmlString = oj.GetSolutionsToXml(ResultSolutionList);
            }

        } catch (Exception ex) {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>failure</rspMsg><time>"
                    + timeStr + "</time></root>";
        } finally {
            lockGetSolution.unlock();
            //System.out.println("解锁啦"+Calendar.getInstance().getTime());
        }
        // }
        return xmlString;

    }

    @Override
    public String wsUpdateResult(String account, String password, byte[] result) {

        lockUpdateSolution.lock();

        OJWSUtil oj = new OJWSUtil();
        String xmlString = null;
        try {
            // 裁判机调用，验证管理员
            // 裁判机调用，验证管理员
            String resultXml = Encrypt.decrypt("judge123", result);

            PMAdminusers pmAdminusers = null;
            WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
            pmAdminusers = (PMAdminusers) wsusersCacheManager.getObject(account + "-" + password);
            if (pmAdminusers == null) {
                pmAdminusers = adminServiceI.getAdminuserByAccount(account);
                wsusersCacheManager.putObject(account + "-" + password, pmAdminusers);
            }

            if (pmAdminusers == null || !pmAdminusers.getPassword().equals(password)) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>failure, wrong username or password</rspMsg><time>"
                        + timeStr + "</time></root>";
                return xmlString;
            }
            // 此处获取xml中solution的相关信息；

            Solution solution = new Solution();
            solution = oj.UpdateResultXmltoSolution(resultXml);
            Solution getexamuserIdSolution = solutionService.getSolutionById(solution.getId());
            solution.setExamId(getexamuserIdSolution.getExamId());
            solution.setUserid(getexamuserIdSolution.getUserid());
            getexamuserIdSolution.setStatus(solution.getStatus());
            getexamuserIdSolution.setCorrectCaseIds(solution.getCorrectCaseIds());
            getexamuserIdSolution.setRemark(solution.getRemark());

            // 根据problemID查找Problems
            Problems problem = problemsService.findProblemById(solution.getProblemId());
            problem.setSubmit(problem.getSubmit() + 1);
            
            //判断该场考试类型
            Exam exam = examService.getExamById(solution.getExamId());
            if("iTraining".equals(exam.getType())){
            	StudentTrainProbDetail stpd = studentTrainProbDetailService.getStatusByUserIdAndExamIdAndProId(
            			solution.getUserid(), solution.getExamId(), solution.getProblemId());
            	
            	stpd.setStatus(solution.getStatus());
            	if(solution.getId() >= stpd.getSolutionId())
            		studentTrainProbDetailService.updateStudentTrainProbDetail(stpd);
            }else{
            	// 根据userId+examId+problemId到studentExamDetail表查找
                Studentexamdetail studentexamdetail = studentexamdetailService.getStatusByUserIDexamIDproblemId(
                        solution.getUserid(), solution.getExamId(), solution.getProblemId());

                studentexamdetail.setStatus(solution.getStatus());
                if(solution.getId() >= studentexamdetail.getSolutionId())
                	studentexamdetailService.updateStudentexamdetail(studentexamdetail);
            }

            // 此处获取xml中wrongcases的caseId,output信息
            List<Wrongcases> wrongcases = new ArrayList();
            wrongcases = oj.SubmitCodeXmltoWrongcases(resultXml);
            solution = submittedcodeService.WS_updateResult(problem, getexamuserIdSolution,wrongcases);

            // **通过WebSocket通知用户结果**
            PMWrongAndCorrectIds res = (PMWrongAndCorrectIds) solutionService.getAllWrongAndRightCases(solution.getUserid(), solution.getExamId(),solution.getProblemId()).getData();
            if (res != null) {
                String jsonString = JSON.toJSONString(res);
                SolutionWebSocketHandler.sendToUser(solution.getUserid() + "", jsonString);
            }

            if (solution.getId() == -1) {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Failure</rspMsg><time>"
                        + timeStr + "</time></root>";
            } else {
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
                        + timeStr + "</time></root>";
            }

        } catch (Exception e) {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                    + timeStr + "</time></root>";
        } finally {
            lockUpdateSolution.unlock();
        }

        return xmlString;

    }

//	@Override
//	public String wsIsPermit(String userName, String password, int examId, String uuid) {
//		String xmlString;
//		// Exam exam = new Exam();
//		OJWSUtil oj = new OJWSUtil();
//		PMUser userReturn = new PMUser();
//		WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//		userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//		if (userReturn == null) {
//			userReturn = GetUser(userName, password);
//			wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//		}
//		if (userReturn.getRspMsg().equals("Success")) {
//			try {
//				Studentexaminfo examinfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(userReturn.getId(),
//						examId);
//
//				if (uuid.equals("") || uuid.equals(null)) {
//
//				} else {// not first, but no uuid

//				}
//
//				// if (uuid.equals(examinfo.getLoginUUID())) {
//				xmlString = oj.ExamInfoUUIDToXml(examId, examinfo.getLoginUUID());
//				// } else {
//				// xmlString = "<?xml version=\"1.0\" encoding=\"GBK\"
//				// standalone=\"no\"?><root><rspMsg>本场考试不允许更换电脑，需要教师在本场考试的“学生管理”里点“允许换座”，才可更换电脑。</rspMsg><time>"
//				// + timeStr + "</time></root>";
//				// }
//
//			} catch (Exception e) {
//				xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//						+ timeStr + "</time></root>";
//			}
//
//		} else {
//			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//					+ timeStr + "</time></root>";
//		}
//		return xmlString;
//	}

    @Override
    public String wsIsPermit(String userName, String password, int examId, String uuid) {
        String xmlString = "";
         Exam exam = new Exam();
         exam = examService.getExamById(examId);
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            try {
                Studentexaminfo examinfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(userReturn.getId(),
                        examId);
                if(examinfo == null){  //新增对examinfo判空  by_zzb 2021.1.9
                	examinfo = new Studentexaminfo();
                	 // 学生第一次使用客户端进入考试
                	examinfo.setExamId(examId);
                	examinfo.setUserId(userReturn.getId());
                	examinfo.setScore(0);
                	examinfo.setSolved(0);
                	examinfo.setSubmit(0);
                	examinfo.setRank(0);
                	examinfo.setFirstloginTime(new Date());
    				if (!exam.getAllowChangeSeat()) {
    					examinfo.setLoginUUID(uuid);
    				}
    				HttpServletRequest req = (HttpServletRequest) webServiceContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
    				if (req.getRemoteAddr() != null) {
    					examinfo.setLoginIp(req.getRemoteAddr());
    				} 
    				studentexaminfoDao.save(examinfo);
    				xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
                            + timeStr + "</time></root>";
                }else if (examinfo.getLoginUUID() == null || "".equals(examinfo.getLoginUUID())) {
        		   //第一次登陆
                   examinfo.setLoginUUID(uuid);
                   studentexaminfoDao.save(examinfo);
                   xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
                           + timeStr + "</time></root>";
        	   
                } else if (examinfo.getLoginUUID().equals(uuid)) {
                    //是同一台电脑登陆
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
                            + timeStr + "</time></root>";
                } else {
                    //不是同一台电脑
                	 HttpServletRequest req = (HttpServletRequest) webServiceContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
//                    HttpServletRequest req = ServletActionContext.getRequest();
                    String preIp = examinfo.getLoginIp();
                    String newIp = req.getRemoteAddr();
                    String preName = studentexaminfoDao.getStuByExamIdAndIp(examinfo.getExamId(), newIp);
                    if (preName == null) {
                        examlogService.WriteExamLog(userReturn.getId(),"尝试换座", userReturn.getChineseName() + "(" + userReturn.getStudentNo() + ")尝试从" + newIp + "ip登录，原ip为" + preIp +
                                "。新ip地址" + newIp + "无人登录过");
                    } else {
                        examlogService.WriteExamLog(userReturn.getId(),"尝试换座", userReturn.getChineseName() + "(" + userReturn.getStudentNo() + ")尝试从" + newIp + "ip登录，原ip为" + preIp +
                                "。新ip地址" + newIp + "曾由" + preName + "(" + userReturn.getStudentNo() + ")登录过");
                    }
                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>本场考试不允许更换电脑，需要教师在本场考试的“学生管理”里点“允许换座”，才可更换电脑。</rspMsg><time>"
                            + timeStr + "</time></root>";
                }
            } catch (Exception e) {
            	e.printStackTrace();
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
                        + timeStr + "</time></root>";
            }
        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
                    + timeStr + "</time></root>";
        }
        return xmlString;
    }

    // 裁判机获取题目信息
    @Override
    public byte[] wsGetProblem4Judge(String userName, String password, int problemId) {
        String xmlString;
        byte[] data = new byte[0];
        PMProblemInfo proInfo = new PMProblemInfo();
        List<Problemtestcases> procasesList = new ArrayList();
        OJWSUtil oj = new OJWSUtil();
        PMUser userReturn = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (userReturn == null) {
            userReturn = GetUser(userName, password);
            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
        }
        if (userReturn.getRspMsg().equals("Success")) {
            String key = userReturn.getUsername() + userReturn.getId();

            if (key.length() < 10) {
                key = key.concat(new String("0000000000").substring(0, 10 - key.length()));

            }
            try {
                WSProblemsCachManager problemsCacheManager = WSProblemsCachManager.getInstance();
                xmlString = (String) problemsCacheManager.getObject("judge_problemId_" + problemId);
                if (xmlString == null) {
                    proInfo = problemsServiceI.findProblemInfoById(problemId);
                    procasesList = problemtestcasesServiceI.getProblemtestcasesByProblemId(problemId);
                    xmlString = oj.ProblemsListToXml(proInfo, procasesList);
                    problemsCacheManager.putObject("judge_problemId_" + problemId, xmlString);
                }
                // 开始加密
            } catch (Exception e) {
                e.printStackTrace();
                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！!!!</rspMsg><time>"
                        + timeStr + "</time></root>";
            }
            data = Encrypt.encrypt(key, xmlString);

        }
        return data;
    }

    @Override
    public String wsGetExamProCatagorys(String userName, String password, int examId) {
        return null;
    }

    @Override
    public String wsCanDoCategory(String userName, String password, int examId) {
        return null;
    }

    @Override
    public byte[] wsDrawProblem(String userName, String password, int examId, int catId) {
        return new byte[0];
    }

    @Override
    public String wsSubmitHistory(String userName, String password, int examId) {
        return null;
    }

    @Override
    public String wsItrainSubmitCode(String userName, String password, int catId, String codeXml) {
        return null;
    }

    @Override
    public String wsSkipThisProblem(String userName, String password, int examId, int catId, int problemId) {
        return null;
    }

    @Override
    public String wsItrainSubmitThisProblem(String userName, String password, int catId, String codeXml, String continueTrain) {
        return null;
    }

    @Override
    public String wsPassThisCategory(String userName, String password, int examId, int catId) {
        return null;
    }

    //智能训练页面点击开始做题操作
//	@Override
//	public String wsCanDoCategory(String userName, String password, int examId) {
//		String xmlString = null;
//		OJWSUtil oj = new OJWSUtil();
//        PMUser userReturn = new PMUser();
//
//        try {
//       	 	WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//       	 	userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//       	 	if (userReturn == null) {
//       	 		userReturn = GetUser(userName, password);
//       	 		wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//       	 	}
//            int userId = userReturn.getId();
//       	 	if(userReturn.getRspMsg().equals("Success"))
//       	 	{
//       	 		Map<String,Object> data = new HashMap<String, Object>();
//
//       	 		Studentexaminfo examinfo = studentexaminfoDao.getStudentexaminfoByUserIdAnExamId(userId,examId);
//                data.put("currentProcategory", examinfo.getCurrentCat());
//
//                String choice = "";
//                if(examinfo.getCurrentCat() == null){
//                	choice = "finished";
//                }else{
//                	choice = studentTrainCatDetailService.getItrainCatChoice(userId,examId,examinfo.getCurrentCat());
//                }
//
//       	 		data.put("choice", choice);
//
//       	 		List<Map<String,Object>> category = studentTrainCatDetailService.getCanChoiceProCategory(userId,examId);
//       	 		String catIds = "";
//       	 		for(int i = 0;i < category.size();i++)
//       	 			if(i != category.size() - 1)
//       	 				catIds = catIds + category.get(i).get("catId")+",";
//       	 			else
//       	 				catIds = catIds + category.get(i).get("catId");
//       	 		data.put("catIds", catIds);
//
//       	 		xmlString = oj.CanDoCategoryInfoToXml(data);
//       	 	}else{
//       	 		xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                    + timeStr + "</time></root>";
//       	 	}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//                    + timeStr + "</time></root>";
//		}
//
//		return xmlString;
//	}

	//智能训练页面点击"我要抽题"操作
//	@Override
//	public byte[] wsDrawProblem(String userName, String password, int examId, int catId) {
//		String xmlString = null;
//        byte[] data = new byte[0];
//        OJWSUtil oj = new OJWSUtil();
//        PMUser userReturn = new PMUser();
//        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//        userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//
//        if (userReturn == null) {
//            userReturn = GetUser(userName, password);
//            wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//        }
//        if (userReturn.getRspMsg().equals("Success")){
//        	String key = userReturn.getUsername() + userReturn.getId();
//
//            if (key.length() < 10) {
//                key = key.concat(new String("0000000000").substring(0, 10 - key.length()));
//
//            }
//            try {
//
//            	WSItrainProblemsCachManager itrainproblemsCacheManager = WSItrainProblemsCachManager.getInstance();
//                xmlString = (String) itrainproblemsCacheManager.getObject("itrainCatExamPro_" + examId);
//            	if(xmlString == null){
//            		//返回客户端数据源
//                	Map<String,Object> mapResult = studentTrainCatDetailService.getExtractProIdDataSource(userReturn.getId(),examId,catId);
//
//            		if((int)mapResult.get("problemId") != 0)
//            		{
//            			xmlString = oj.ProblemsListToXml((PMProblemInfo)mapResult.get("proInfo"), (List<Problemtestcases>)mapResult.get("procasesList"));
//                		itrainproblemsCacheManager.putObject("itrainCatExamPro_" + examId, xmlString);
//            		}else
//            		{
//            			List<Map<String,Object>> category = (List<Map<String, Object>>) mapResult.get("nextCategory");
//            			String catIds = "";
//               	 		for(int i = 0;i < category.size();i++)
//               	 		{
//               	 			if(i == category.size() - 1)
//               	 				catIds += category.get(i). get("catId");
//               	 			else
//               	 				catIds += category.get(i).get("catId") + ",";
//               	 		}
//            			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>本关所有题目已完成，请选择其它类别!</rspMsg><time>"
//                                + timeStr + "</time><ids>"+ catIds +"</ids></root>";
//            		}
//            	}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//                xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！!!!</rspMsg><time>"
//                        + timeStr + "</time></root>";
//			}
//            data = Encrypt.encrypt(key, xmlString);
//        }else{
//        	xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                    + timeStr + "</time></root>";
//        }
//		return data;
//	}

//	@Override
//	public String wsSubmitHistory(String userName, String password, int examId) {
//		String xmlString = null;
//		OJWSUtil oj = new OJWSUtil();
//        PMUser userReturn = new PMUser();
//
//        try {
//        	WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//       	 	userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//       	 	if (userReturn == null) {
//       	 		userReturn = GetUser(userName, password);
//       	 		wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//       	 	}
//
//            int userId = userReturn.getId();
//            if(userReturn.getRspMsg().equals("Success")){
//            	WSSubmitHistoryCacheManager submitHistoryCacheManager = WSSubmitHistoryCacheManager.getInstance();
//            	//submitHistoryCacheManager.removeObject("submitHistory_"+examId);
//            	xmlString = (String) submitHistoryCacheManager.getObject("submitHistory_"+examId);
//
//            	if(xmlString == null)
//            	{
//            		Map<String,Object> mapResult = studentTrainCatDetailService.getSubmitHistoryDataSoure(userId,examId);
//
//                	xmlString = oj.SubmitHistoryToXml(mapResult);
//                	submitHistoryCacheManager.putObject("submitHistory_"+examId, xmlString);
//            	}
//
//
//            }else{
//            	xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                        + timeStr + "</time></root>";
//            }
//		} catch (Exception e) {
//			e.printStackTrace();
//            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//                    + timeStr + "</time></root>";
//		}
//		return xmlString;
//	}
//
//	@Override
//	public String wsItrainSubmitCode(String userName, String password, int catId, String codeXml) {
//		 lockSubmitCode.lock();// 获得锁
//	     String xmlString = null;
//	     OJWSUtil oj = new OJWSUtil();
//	     Solution solution = new Solution();
//	     try {
//	    	 PMUser userReturn = new PMUser();
//	         WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//	         userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//	         if (userReturn == null) {
//	               userReturn = GetUser(userName, password);
//	               wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//	          }
//	         int userId = userReturn.getId();
//
//	         if (userReturn.getRspMsg().equals("Success")) {
//	        	// 此处获取xml中solution的相关信息；
//	            solution = oj.SubmitCodeXmltoSolution(codeXml);
//	            if (solution.getSourceCode().length() > 64000) {
//	                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>代码长度不能超过64000个字符</rspMsg><time>"
//	                            + timeStr + "</time></root>";
//	                    return xmlString;
//	            }
//	            solution.setUserid(userId);
//                // 设置提交时间
//                solution.setSubmitTime(new Date());
//
//                // 获取考试开始时间和结束时间
//                Exam exam = examService.getExamById(solution.getExamId());
//                Date now = new Date();
//                Date startTime = exam.getStarttime();
//                Date endTime = exam.getEndtime();
//                Date deadline = itrainProblemCatService.getDeadline(solution.getExamId(),catId);
//                // 如果考试开始之前提交代码
//                if (now.getTime() < startTime.getTime()) {
//                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>考试还没开始，无法提交代码！</rspMsg><time>"
//                            + timeStr + "</time></root>";
//                }else if (deadline != null && now.getTime() > deadline.getTime()) {
//                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>已经超过截止时间不能提交代码！</rspMsg><time>"
//                            + timeStr + "</time></root>";
//                }else if (now.getTime() > endTime.getTime()) {
//                    xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>考试已经结束，无法再提交代码！</rspMsg><time>"
//                            + timeStr + "</time></root>";
//                }else{
//                	// 根据problemID查找Problems
//                    Problems problem = problemsService.findProblemById(solution.getProblemId());
//                    // 根据userId+examId+catId+problemId到studentTrainProbDetail表查找
//                	StudentTrainProbDetail stpd = studentTrainProbDetailService.getStudentTrainProbDetail(userId,solution.getExamId(),
//                			catId,solution.getProblemId());
//                	// 提交本题之后再次提交代码
//                    if (stpd != null && stpd.isFinished()) {
//                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>已经提交过本题，不能再提交代码！</rspMsg><time>"
//                                + timeStr + "</time></root>";
//                        return xmlString;
//                    }
//                    // 更新Problems表的submit字段，增加1
//                    if (problem.getSubmit() == null) {
//                        problem.setSubmit(1);
//                    } else {
//                        problem.setSubmit(problem.getSubmit() + 1);
//                    }
//                    // 此处获取xml中wrongcases的caseId,output信息
//                    List<Wrongcases> wrongcases = new ArrayList();
//                    wrongcases = oj.SubmitCodeXmltoWrongcases(codeXml);
//                    solution = submittedcodeService.wsItrainsubmitCode(problem, solution, stpd, wrongcases, now,
//                            stpd.getStartTime(), endTime);
//                    if (solution.getId() > 0) {
//
//                        float score;
//                        if (solution.getScore() > 0) {
//                            score = solution.getScore();
//                        } else {
//                            // 获取该题的所得的分数情况
//                            score = gradeProblemService.gradeItrainProblemBySolution(solution,catId);
//                        }
//
//                        // 此处算分
//                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg>"
//                                + "<time>" + timeStr + "</time>" + "<solutionId>" + solution.getId() + "</solutionId>"
//                                + "<score>" + score + "</score>" + "</root>";
//                    } else {
//                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>代码提交失败</rspMsg><time>"
//                                + timeStr + "</time></root>";
//                    }
//
//                }
//
//	         }else {
//	             xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//	                        + timeStr + "</time></root>";
//	         }
//
//		} catch (Exception e) {
//			String sOut = "";
//            StackTraceElement[] trace = e.getStackTrace();
//            for (StackTraceElement s : trace) {
//                sOut += "\tat " + s + "\r\n";
//            }
//            // 异常信息最大记录19000个字符，数据库该字段最大为20K
//            int count = sOut.length() > 19000 ? 19000 : sOut.length();
//            sOut = sOut.substring(0, count - 1);
//            int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e.getLocalizedMessage().length();
//            String localMessage = "";
//            if (e.getLocalizedMessage() != null) {
//                localMessage = e.getLocalizedMessage().substring(0, leng - 1);
//            }
//            String type = "(WS)代码提交";
//            String userType = "student";
//            String content = sOut;
//            String abstractContent = "学生id:" + solution.getUserid() + "考试id:" + solution.getExamId() + "题目id:"
//                    + solution.getProblemId() + "\n" + localMessage;
//            logService.WriteLog(solution.getUserid(), userType, type, abstractContent, content);
//
//            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//                    + timeStr + "</time>" + "<Exception>" + sOut + "</Exception></root>";
//		}finally {
//            lockSubmitCode.unlock();
//        }
//		return xmlString;
//	}
//
//	@Override
//	public String wsSkipThisProblem(String userName, String password, int examId, int catId, int problemId) {
//		String xmlString = null;
//        PMUser userReturn = new PMUser();
//
//        try {
//        	WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//       	 	userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//       	 	if (userReturn == null) {
//       	 		userReturn = GetUser(userName, password);
//       	 		wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//       	 	}
//
//       	 	if (userReturn.getRspMsg().equals("Success")){
//       	 		boolean flag = studentTrainCatDetailService.skipThisProblem(userReturn.getId(),examId,catId,problemId);
//       	 		if(flag){
//       	 			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
//                        + timeStr + "</time></root>";
//       	 		}else{
//       	 			xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>本关仅一题未完成，请继续本题，或点击我要通关</rspMsg><time>"
//                        + timeStr + "</time></root>";
//       	 		}
//       	 	}else{
//       	 		xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                    + timeStr + "</time></root>";
//       	 	}
//		} catch (Exception e) {
//			e.printStackTrace();
//            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！!!!</rspMsg><time>"
//                    + timeStr + "</time></root>";
//		}
//		return xmlString;
//	}
//
//	@Override
//	public String wsItrainSubmitThisProblem(String userName, String password, int catId, String codeXml,
//			String continueTrain) {
//		lockSubmitThisProblem.lock();// 获得锁
//        String xmlString = null;
//        try {
//        	OJWSUtil oj = new OJWSUtil();
//
//            PMUser userReturn = new PMUser();
//            WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//            userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//            if (userReturn == null) {
//                userReturn = GetUser(userName, password);
//                wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//            }
//            if (userReturn.getRspMsg().equals("Success")){
//            	try {
//            		int userId = userReturn.getId();
//                    // 此处从xml中获取solution中的值
//                    wssolution = oj.SubProblemXmltoSolution(codeXml);
//                    wssolution.setUserId(userId);
//
//            		StudentTrainProbDetail stpd = studentTrainProbDetailService.getStudentTrainProbDetail(userId, wssolution.getExamId(), catId, wssolution.getProblemId());
//
//            		if (stpd == null) {
//                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>必须先提交代码才能提交本题。</rspMsg><time>"
//                                + timeStr + "</time></root>";
//
//                    }else if (stpd.isFinished()) {
//                        xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>不能重复提交</rspMsg><time>"
//                                + timeStr + "</time></root>";
//
//                    } else {
//                        Json json = solutionService.wsItrainSubmitThisProblem(stpd, wssolution,continueTrain);
//                        if(json.isSuccess() && continueTrain.equals("yes")){
//                        	Map<String,Object> data = studentTrainCatDetailService.getAfterSubmitProblemDataSource(userId,wssolution.getExamId(),catId);
//                        	xmlString = oj.AfterSubmitProblemDataSourceToXml(data);
//                        }else
//                        	xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>"
//                                + (json.isSuccess() ? "Success" : json.getMsg()) + "</rspMsg><time>" + timeStr + "</time></root>";
//                    }
//
//
//				} catch (Exception e) {
//					 xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！</rspMsg><time>"
//	                            + timeStr + "</time></root>";
//				}
//
//            }else{
//            	 xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                         + timeStr + "</time></root>";
//            }
//            return xmlString;
//		} catch (Exception e) {
//			String sOut = "";
//            StackTraceElement[] trace = e.getStackTrace();
//            for (StackTraceElement s : trace) {
//                sOut += "\tat " + s + "\r\n";
//            }
//            // 异常信息最大记录19000个字符，数据库该字段最大为20K
//            int count = sOut.length() > 19000 ? 19000 : sOut.length();
//            sOut = sOut.substring(0, count - 1);
//            int leng = e.getLocalizedMessage().length() > 1800 ? 1800 : e.getLocalizedMessage().length();
//            String localMessage = "";
//            if (e.getLocalizedMessage() != null) {
//                localMessage = e.getLocalizedMessage().substring(0, leng - 1);
//            }
//            Log log = new Log();
//            log.setType("提交本题");
//            log.setOptime(new Date());
//            log.setUserId(wssolution.getUserId());
//            log.setUserType("student");
//            log.setContent(sOut);
//            log.setAbstractContent("学生id:" + wssolution.getUserId() + "考试id:" + wssolution.getExamId() + "题目id:"
//                    + wssolution.getProblemId() + "\n" + localMessage);
//            logService.WriteLog(log);
//
//            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>提交本题失败</rspMsg><time>"
//                    + timeStr + "</time></root>";
//            return xmlString;
//		}finally {
//           lockSubmitThisProblem.unlock();// 释放锁
//        }
//
//	}

//	@Override
//	public String wsPassThisCategory(String userName, String password, int examId, int catId) {
//		String xmlString = null;
//        PMUser userReturn = new PMUser();
//
//        try {
//        	WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
//       	 	userReturn = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
//       	 	if (userReturn == null) {
//       	 		userReturn = GetUser(userName, password);
//       	 		wsusersCacheManager.putObject(userName + "-" + password, userReturn);
//       	 	}
//       	 	if (userReturn.getRspMsg().equals("Success")){
//       	 		List<Map<String,Object>> category = studentTrainCatDetailService.passThisCategory(userReturn.getId(),examId,catId);
//       	 		String catIds = "";
//       	 		for(int i = 0;i < category.size();i++)
//       	 		{
//       	 			if(i == category.size() - 1)
//       	 				catIds += category.get(i). get("catId");
//       	 			else
//       	 				catIds += category.get(i).get("catId") + ",";
//       	 		}
//       	 		xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>Success</rspMsg><time>"
//                        + timeStr + "</time> <ids>"+ catIds +"</ids></root>";
//       	 	}else{
//       	 		xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>用户名或密码错误</rspMsg><time>"
//                    + timeStr + "</time></root>";
//       	 	}
//		} catch (Exception e) {
//			e.printStackTrace();
//            xmlString = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><rspMsg>服务器错误，请稍后重试！!!!</rspMsg><time>"
//                    + timeStr + "</time></root>";
//		}
//
//		return xmlString;
//	}

}
