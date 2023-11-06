package edu.dhu.exam.service.impl;

import edu.dhu.exam.dao.*;
import edu.dhu.exam.model.DataGrid;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.PMExam;
import edu.dhu.exam.service.ExamServiceI;
import edu.dhu.user.model.Adminusers;
import edu.dhu.user.model.Json;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamServiceI {
    @Resource
    private ExamDaoI examDao;
    @Resource
    private ExamDaoNew examDaoNew;
    @Resource
    private ItrainProblemCatDaoI itrainProblemCatDao;
    @Resource
    private AdminusersDaoI adminusersDao;

    public Long syncTime(Integer examId) {
        Date date = examDaoNew.getExamEndTime(examId);
        if (date == null)
            return null;
        return date.getTime();
    }

    private String compareTime(java.util.Date startTime, java.util.Date endTime, java.util.Date now) {
        // 如果 开始时间 > 结束时间 则考试时间设置错误
        if (startTime.getTime() > endTime.getTime()) {
            return "考试时间设置错误";
        } else {
            // 如果考试还未到时间，还要等一小时以上，提示"请等待考试开始"
            if (startTime.getTime() - now.getTime() > 3600000) {
                return "考试未开始";
            }
            // 如果是一小时以内，则提示"离考试还有几分几秒"
            else if ((startTime.getTime() - now.getTime() > 0) && (startTime.getTime() - now.getTime() < 3600000)) {
                long time1 = startTime.getTime();
                long time2 = now.getTime();
                long diff = time1 - time2;
                // 计算分钟
                long minute = diff / (60 * 1000);
                // 计算秒
                long second = diff / 1000 - (minute * 60);
                return "还有" + minute + ":" + second;
            } else if ((now.getTime() > startTime.getTime()) && (now.getTime() < endTime.getTime())) {
                return "考试进行中";
            } else if (now.getTime() > endTime.getTime()) {
                return "考试已结束";
            } else {
                return "";
            }
        }
    }

    // 将Exam List转换为PMExam List
    private void changeModel(List<Exam> l, List<PMExam> nl) {
        if (l != null && l.size() > 0) {
            for (Exam t : l) {
                PMExam u = new PMExam();
                BeanUtils.copyProperties(t, u);
                nl.add(u);
            }
        }
    }

    @Transactional
    public DataGrid dataGrid(PMExam pMExam) {

        DataGrid dg = new DataGrid();
        long totalLines;
        List<Exam> theExamList;
        // 如果学生没有登录
        if (pMExam.getStudentId() == null) {
            // 查询所有的考试
            // 获取总的行数
            totalLines = examDao.getAllExamsNum();
            // 分页查找当前页的数据
            theExamList = examDao.getExamsByPage(pMExam.getPage(), pMExam.getRows());
        } else {
            // 查询该学生所有符合条件的考试
            // 获取总的行数
            totalLines = examDao.getAllExamsNumByStuId(pMExam.getStudentId());
            // 分页查找当前页的数据
            theExamList = examDao.getExamsByPageAndStuId(pMExam.getStudentId(), pMExam.getPage(), pMExam.getRows());
        }

        // 如果考试查询结果为空
        if (theExamList == null) {
            return null;
        } else {
            List<PMExam> pMExamList = new ArrayList<PMExam>();
            // 将Exam List转换为PMExam List
            changeModel(theExamList, pMExamList);

            for (int i = 0; i < pMExamList.size(); i++) {
                if(pMExamList.get(i).getType()!=null&&pMExamList.get(i).getType().equals("iTraining")){
                    int catNumber = itrainProblemCatDao.getExamCategoryCount(pMExamList.get(i).getId());
                    pMExamList.get(i).setProblemNum(catNumber);
                }
                pMExamList.get(i).setPage(pMExam.getPage());
                pMExamList.get(i).setRows(pMExam.getRows());

                // 设置比赛状态
                pMExamList.get(i).setStatus(
                        compareTime(pMExamList.get(i).getStarttime(), pMExamList.get(i).getEndtime(), new java.util.Date()));

                // 根据老师id查找并设置老师名字
                Adminusers teacher = adminusersDao.get(Adminusers.class, pMExamList.get(i).getTeacherId());

                if (teacher == null) {
                    pMExamList.get(i).setTeacherName(" ");
                } else {
                    pMExamList.get(i).setTeacherName(teacher.getName());
                }
                //设置考试类别
                pMExamList.get(i).setExamType(theExamList.get(i).getType());
            }
            // 设置总的记录行数
            dg.setTotal(totalLines);
            // 设置每页显示多少行
            dg.setPageSize(pMExam.getRows());
            // 设置总的页数,总页数 ＝ (总行数／每页显示多少行)向上取整
            dg.setTotalPages((long) (Math.ceil((double) dg.getTotal() / (double) dg.getPageNum())));
            // 设置当前显示第几页
            dg.setPageNum(pMExam.getPage());
            // 设置当前页的数据行数量
            dg.setCurrentPageLineNum(pMExamList.size());
            // 设置当前页的所有数据行
            dg.setList(pMExamList);
            return dg;
        }
    }

    @Override
    public List<PMExam> getExamsByUserId(int userId) {
        List<Exam> theExamList;
        theExamList = examDao.getExamsByStuId(userId);
        // 如果考试查询结果为空
        if (theExamList == null) {
            return null;
        } else {

            List<PMExam> pMExamList = new ArrayList<PMExam>();
            // 将Exam List转换为PMExam List
            changeModel(theExamList, pMExamList);

            for (int i = 0; i < pMExamList.size(); i++) {
                // 设置比赛状态
                pMExamList.get(i).setStatus(
                        compareTime(pMExamList.get(i).getStarttime(), pMExamList.get(i).getEndtime(), new java.util.Date()));

                // 根据老师id查找并设置老师名字
                Adminusers teacher = adminusersDao.get(Adminusers.class, pMExamList.get(i).getTeacherId());
                if (teacher == null) {
                    pMExamList.get(i).setTeacherName("");
                } else {
                    pMExamList.get(i).setTeacherName(teacher.getName());
                }
                //设置考试类别
                pMExamList.get(i).setExamType(theExamList.get(i).getType());
            }
            return pMExamList;
        }
    }
//    @Override
//    public List<PMExam> getAllExamsOrderByEndTime(int teacherID, String roleName) { // 获取所有的班级
//        // TODO Auto-generated method stub
//        List<Exam> examList = examDao.getAllExamsOrderByEndTime(teacherID, roleName);
//        List<PMExam> plist = new ArrayList<PMExam>();
//        for (int i = 0; i < examList.size(); i++) {
//            Exam exam = examList.get(i);
//            PMExam p = new PMExam();
//            p.setId(exam.getId());
//            p.setName(exam.getName());
//            p.setDescription(exam.getDescription());
//            p.setStarttime(exam.getStarttime());
//            p.setEndtime(exam.getEndtime());
//            if(exam.getType()!=null&&exam.getType().equals("iTraining")){
//                int catNumber = itrainProblemCatDao.getExamCategoryCount(exam.getId());
//                p.setProblemNum(catNumber);
//            }else{
//                p.setProblemNum(exam.getProblemNum());
//            }
//            p.setCanGetHint(exam.getCanGetHint());
//            p.setPartialScore(exam.getPartialScore());
//            p.setLanguage(exam.getLanguage());
//            int teacherId = exam.getTeacherId();
//            p.setTeacherId(exam.getTeacherId());
//            p.setExamType(exam.getType());
//            Adminusers teacher = adminusersDao.get(Adminusers.class, teacherId);
//            if (teacher != null) {
//                p.setTeacherName(teacher.getName());
//            }
//
//            //获取该场考试参与班级和学生的数量
//            List<Map<String,Object>> res = classesDao.getClassAndStudentNumByExamId(exam.getId());
//
//            p.setClassNum(res.size());
//            int sum = 0;
//            String noStuClassId = "";
//            String noStuClassName = "";
//            for(int k = 0;k < res.size();k++){
//                int num = Integer.parseInt(res.get(k).get("studentNum").toString());
//                sum += num;
//                if((Integer.parseInt(res.get(k).get("teacherId").toString()) == teacherID
//                        || teacherID == 0) &&
//                        Integer.parseInt(res.get(k).get("studentNum").toString()) == 0){
//                    if("".equals(noStuClassId) && "".equals(noStuClassName)){
//                        noStuClassId += res.get(k).get("classId");
//                        noStuClassName += res.get(k).get("name");
//                    }else{
//                        noStuClassId = noStuClassId + "," + res.get(k).get("classId");
//                        noStuClassName = noStuClassName + "," + res.get(k).get("name");
//                    }
//                }
//            }
//            p.setStuNum(sum);
//
//            p.setNoStuClassId(noStuClassId);
//            p.setNoStuClassName(noStuClassName);
//
//            plist.add(p);
//        }
//        return plist;
//    }

    @Override
    public List<Exam> getExamByProblemId(int problemId) {
        return examDao.getExamByProblemId(problemId);
    }



    @Override
    public void examAdd(PMExam pexam) {
        examDao.examAdd(pexam);
    }

    @Override
    public int updateExam(PMExam pexam) {
        return examDao.updateExam(pexam);
    }

    @Override
    public int updateAllowCSbyexamId(PMExam pexam) {
        return examDao.updateAllowCSbyexamId(pexam);
    }

    @Override
    public Json getStudentRank(int userId, int examId) {
        return null;
    }

    @Override
    public List<PMExam> getExamsNotInClass(int classId,int teacherId) {
        List<Exam> examList = examDao.getExamsNotInClass(classId,teacherId);
        List<PMExam> plist = new ArrayList<PMExam>();
        for (int i = 0; i < examList.size(); i++) {
            Exam exam = examList.get(i);
            PMExam p = new PMExam();
            p.setId(exam.getId());
            p.setName(exam.getName());
            p.setDescription(exam.getDescription());
            p.setStarttime(exam.getStarttime());
            p.setEndtime(exam.getEndtime());
            p.setProblemNum(exam.getProblemNum());
            p.setCanGetHint(exam.getCanGetHint());
            p.setPartialScore(exam.getPartialScore());
            p.setLanguage(exam.getLanguage());
            int teacherID = exam.getTeacherId();
            Adminusers teacher = adminusersDao.get(Adminusers.class, teacherID);
            if (teacher != null) {
                p.setTeacherName(teacher.getName());
            }
            plist.add(p);
        }
        return plist;
    }

    @Override
    public boolean checkExamByUserIdAndExamId(int examId, int userId) {
        // 首先获取该学生的所有考试列表
        List<Exam> examList = examDao.getExamsByStuId(userId);
        for (int i = 0; i < examList.size(); i++) {
            if (examList.get(i).getId() == examId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Exam getExamById(int examId) {
        return examDao.get(Exam.class, examId);
    }

    @Override
    public List<PMExam> getAllExamsOrderByEndTime(int teacherID, String roleName) {
        return null;
    }

    @Override
    public List<PMExam> getExamsByTeacherId(int adminId) {
        // TODO Auto-generated method stub
        List<Exam> list = examDao.getExamsByTeacherId(adminId);
        List<PMExam> l = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Exam e = list.get(i);
            PMExam p = new PMExam();
            p.setId(e.getId());
            p.setName(e.getName());
            l.add(p);
        }
        return l;
    }

    @Override
    public List<PMExam> getAllExamsOrderByEndTime(PMExam pMExam, String roleName) {
        return null;
    }

//    @Override
//    public List<PMExam> getAllExamsOrderByEndTime(PMExam pMExam, String roleName) {
//        // TODO Auto-generated method stub
//        List<Exam> examList;
//        if (pMExam.getTeacherId() != 0) {
//            examList = examDao.getAllTeacherExamsOrderByEndTime(pMExam.getTeacherId(), roleName);
//        } else {
//            examList = examDao.getAllExamBySchoolIdOrderByEndTime(pMExam.getSchoolId());
//        }
//        List<PMExam> plist = new ArrayList<PMExam>();
//        for (int i = 0; i < examList.size(); i++) {
//            Adminusers teacher = adminusersDao.get(Adminusers.class, examList.get(i).getTeacherId());
//
////			if (teacher.getSchoolId() == pMExam.getSchoolId()) {
//            Exam exam = examList.get(i);
//            PMExam p = new PMExam();
//            p.setId(exam.getId());
//            p.setName(exam.getName());
//            p.setDescription(exam.getDescription());
//            p.setStarttime(exam.getStarttime());
//            p.setEndtime(exam.getEndtime());
//            if(exam.getType()!=null&&exam.getType().equals("iTraining")){
//                int catNumber = itrainProblemCatDao.getExamCategoryCount(exam.getId());
//                p.setProblemNum(catNumber);
//            }else{
//                p.setProblemNum(exam.getProblemNum());
//            }
//
//            p.setCanGetHint(exam.getCanGetHint());
//            p.setPartialScore(exam.getPartialScore());
//            p.setLanguage(exam.getLanguage());
//            int teacherId = exam.getTeacherId();
//            p.setTeacherId(exam.getTeacherId());
//            p.setExamType(exam.getType());
//
//            if (teacher != null) {
//                p.setTeacherName(teacher.getName());
//            }
//
//            //获取该场考试参与班级和学生的数量
//            List<Map<String,Object>> res = classesDao.getClassAndStudentNumByExamId(exam.getId());
//
//            p.setClassNum(res.size());
//            int sum = 0;
//            String noStuClassId = "";
//            String noStuClassName = "";
//            for(int k = 0;k < res.size();k++){
//                int num = Integer.parseInt(res.get(k).get("studentNum").toString());
//                sum += num;
//                if((Integer.parseInt(res.get(k).get("teacherId").toString()) == pMExam.getTeacherId()
//                        || pMExam.getTeacherId() == 0) &&
//                        Integer.parseInt(res.get(k).get("studentNum").toString()) == 0){
//                    if("".equals(noStuClassId) && "".equals(noStuClassName)){
//                        noStuClassId += res.get(k).get("classId");
//                        noStuClassName += res.get(k).get("name");
//                    }else{
//                        noStuClassId = noStuClassId + "," + res.get(k).get("classId");
//                        noStuClassName = noStuClassName + "," + res.get(k).get("name");
//                    }
//                }
//            }
//            p.setStuNum(sum);
//
//            p.setNoStuClassId(noStuClassId);
//            p.setNoStuClassName(noStuClassName);
//
//            plist.add(p);
////			}
//        }
//        return plist;
//    }

//    @Override
//    public Json getStudentRank(int userId, int examId) {
//        Json j = new Json();// 数据返回类型
//        int examJoinerSize = 0;
//        int classSize = 0;
//        // 班级排名
//        // 验证班级，考试信息
//        // 参与考试的学生
//        UsersCacheManager userManager = UsersCacheManager.getInstance();
//        List<Users> userList = (List<Users>) userManager.getObject("2_" + "exam_" + examId);
//        if (userList == null) {
//            userList = userDao.getUsersByExamId(examId);
//            userManager.putObject("2_" + "exam_" + examId, userList);
//        }
//        examJoinerSize = userList.size();
//        HashMap userMap = new HashMap();
//        for (int i = 0; i < userList.size(); i++) {
//            int Id = userList.get(i).getId();
//            userMap.put(Id, userList.get(i));
//        }
//        // classstudent表对应数据 userId与ClassId多对1关系，即userId不会出现重复
//        ClassstudentsCacheManager studentsManager = ClassstudentsCacheManager.getInstance();
//        List<Classstudents> classstudentList = (List<Classstudents>) studentsManager.getObject("2_" + "exam_" + examId);
//        if (classstudentList == null) {
//            classstudentList = classstudentsDao.getClassStudentsByExamId(examId);
//            studentsManager.putObject("2_" + "exam_" + examId, classstudentList);
//        }
//        HashMap classstudentMap = new HashMap();
//        HashMap<Integer, Integer> classCountMap = new HashMap<>();
//        // 统计班级
//        for (int m = 0; m < classstudentList.size(); m++) {
//            Classstudents student = classstudentList.get(m);
//            int classIdTmp = student.getClassId();
//            classstudentMap.put(student.getUserId(), classIdTmp); // 建立学生与班级的hash表
//            if (classCountMap.containsKey(classIdTmp)) {
//                classCountMap.put(classIdTmp, classCountMap.get(classIdTmp) + 1);
//            } else {
//                classCountMap.put(classIdTmp, 1);
//            }
//        }
//        // 获取考试信息放入缓存
//        ExamCacheManager examManager = ExamCacheManager.getInstance();
//        List<Exam> examList = (List<Exam>) examManager.getObject("2_" + "exam_" + "all");
//        if (examList == null) {
//            examList = examDao.getAllExams();
//            examManager.putObject("2_" + "exam_" + "all", examList);
//        }
//        HashMap<Integer, Exam> examMap = new HashMap<>();
//        for (int i = 0; i < examList.size(); i++) {
//            int examId2 = examList.get(i).getId();
//            examMap.put(examId2, examList.get(i));
//        }
//
//        int classId = 0;
//        classId = (int) classstudentMap.get(userId);
//        classSize = classCountMap.get(classId);
//        // 1.4验证学生信息
//        if (!classstudentMap.containsKey(userId)) {
//            j.setMsg("不在考试班级内");
//            j.setSuccess(false);
//            return j;
//        } else {
//            // 获取所有考试信息
//            StudentexaminfoCacheManager infoManager = StudentexaminfoCacheManager.getInstance();
//            List<Studentexaminfo> allExamInfoList = (List<Studentexaminfo>) infoManager
//                    .getObject("2_" + 0 + "_" + examId);
//            if (allExamInfoList == null) {
//                allExamInfoList = studentexaminfoDao.getClassExamScore(examId, 0); // 所有info
//                infoManager.putObject("2_" + 0 + "_" + examId, allExamInfoList); // 存入cache
//            }
//            if (allExamInfoList.size() <= 0) {
//                j.setMsg("当前考试没有提交的题目");
//                j.setSuccess(false);
//                return j;
//            }
//
//            // 根据学生成绩和rank重新排序
//            sortScore(allExamInfoList);
//            // 声明数据返回结果中的所有成绩列表
//            List<PMExamScore> allRanKList = new ArrayList<>();
//            List<PMExamScore> classRankList = new ArrayList<>();
//            List<PMExamScore> dateRankList = new ArrayList<>();
//            List<PMExamScore> dateClassList = new ArrayList<>();
//            //
//            boolean isMoreClass = true;// 声明是否存在多个班级的变量
//            PMExamScore pmExamScore;
//            PMExamScore selfPMExamScore = new PMExamScore();
//            PMExamScore selfClassPMExamScore = new PMExamScore();
//            Users user;
//            int tmpI=0;
//            for (Studentexaminfo stuInfo : allExamInfoList) {
//                // 装填返回数据
//                if(stuInfo.getSubmit()==0){
//                    continue;
//                }
//                pmExamScore = new PMExamScore();
//                user = (Users) userMap.get(stuInfo.getUserId());
//                pmExamScore.setStudentNo(user.getStudentNo());
//                pmExamScore.setScore(stuInfo.getScore());
//                allRanKList.add(pmExamScore);
//                // 创建指向自己的索引
//                if (stuInfo.getUserId() == userId) {
//                    selfPMExamScore = pmExamScore;
//                }
//                // 切割学生所在班级ID
//                int currentStudentClassId = (int) classstudentMap.get(stuInfo.getUserId());
//                if (currentStudentClassId == classId) {
//                    PMExamScore pmExamScoreTmp = new PMExamScore();
//                    BeanUtils.copyProperties(pmExamScore, pmExamScoreTmp);
//                    classRankList.add(pmExamScoreTmp);
//                    if (stuInfo.getUserId() == userId) {
//                        selfClassPMExamScore = pmExamScoreTmp;
//                    }
//                }
//            }
//            // 同上创建定位自己的索引
//            PMExamScore selfDatePMExamScore = new PMExamScore();
//            PMExamScore selfClassDatePMExamScore = new PMExamScore();
//            // 检测七天内的
//            StudentexamdetailCacheManager detailManager = StudentexamdetailCacheManager.getInstance();
//            List<Studentexamdetail> detailList = (List<Studentexamdetail>) detailManager.getObject(0 + "_" + examId);
//            if (detailList == null) {
//                detailList = studentexamdetailDao.getClassStudentexamdetail(examId, 0); // 所有detail信息
//                detailManager.putObject(0 + "_" + examId, detailList);
//            }
//            java.util.Date date=examMap.get(examId).getStarttime();
//            //获取服务器
//            Long nowStamp =System.currentTimeMillis();
//            Long sevenDatePreStamp = nowStamp-604800000; //后边的数字是七天时间差的时间戳
//            Long examStart=examMap.get(examId).getStarttime().getTime();//获取考试开始时间
//            long  threshold =(sevenDatePreStamp-examStart)/1000;//需要显示七天的阈值
//            HashMap<Integer, List<Studentexamdetail>> detailMap = new HashMap<>();
//            boolean belowThreshold=false;
//            for (int i = 0; i < detailList.size(); i++) {
//                List<Studentexamdetail> userDetailList = new ArrayList<>();
//                if(!detailList.get(i).isFinished()){
//                    continue;
//                }
//                int tmp = detailList.get(i).getUserId();
//                long elapsedTime=detailList.get(i).getElapsedTime();
//                if (elapsedTime<threshold) {
//                    belowThreshold=true;
//                    continue;
//                }
//                if (detailMap.containsKey(tmp)) {
//                    userDetailList = detailMap.get(tmp);
//                    userDetailList.add(detailList.get(i));
//                } else {
//                    userDetailList.add(detailList.get(i));
//                }
//                detailMap.put(tmp, userDetailList);
//            }
//            if (!belowThreshold) {
//                detailMap.clear();
//            }
//            for (Map.Entry<Integer, List<Studentexamdetail>> entry : detailMap.entrySet()) {
//                List<Studentexamdetail> tmp = entry.getValue();
//                int key = entry.getKey();
//                float score = 0.00f;
//                int submitNum = 0;
//                int elapsedTime=0;
//                int rank = 0;
//                for (int i = 0; i < tmp.size(); i++) {
//                    score += tmp.get(i).getScore();
//                    submitNum += tmp.get(i).getSubmit();
//                    elapsedTime+=tmp.get(i).getElapsedTime();
//                }
//                score=(float)Math.round(score*100)/100;
//                rank = elapsedTime + submitNum * 30;
//                PMExamScore pmExamScore2 = new PMExamScore();
//                pmExamScore2.setUserId(key);
//                pmExamScore2.setScore(score);
//                pmExamScore2.setRank(rank);
//                Users users = (Users) userMap.get(key);
//                pmExamScore2.setStudentNo(users.getStudentNo());
//                dateRankList.add(pmExamScore2);
//                if (key == userId) {
//                    selfDatePMExamScore = pmExamScore2;
//                }
//                int currenClassId = (int) classstudentMap.get(key);
//                if (currenClassId == classId) {
//                    PMExamScore pmExamScoreTmp = new PMExamScore();
//                    BeanUtils.copyProperties(pmExamScore2, pmExamScoreTmp);
//                    dateClassList.add(pmExamScoreTmp);
//                    if (key == userId) {
//                        selfClassDatePMExamScore = pmExamScoreTmp;
//                    }
//                }
//            }
//
//            sortScore2(dateRankList);
//            sortScore2(dateClassList);
//
//            // 判断是否存在多个班级
//            if (classRankList.size() == allRanKList.size()) {
//                isMoreClass = false;
//            }
//
//            int allRankNo = allRanKList.indexOf(selfPMExamScore) + 1;
//            int classRankNo = classRankList.indexOf(selfClassPMExamScore) + 1;
//            int dateRankNo = dateRankList.indexOf(selfDatePMExamScore) + 1;
//            int dateClassRankNO = dateClassList.indexOf(selfClassDatePMExamScore) + 1;
//            if (classRankList.size() > 10) {
//                classRankList = classRankList.subList(0, 10);
//            }
//            if (allRanKList.size() > 10) {
//                allRanKList = allRanKList.subList(0, 10);
//            }
//            if (dateClassList.size() > 10) {
//                dateClassList = dateClassList.subList(0, 10);
//            }
//            if (dateRankList.size() > 10) {
//                dateRankList = dateRankList.subList(0, 10);
//            }
//            PMRankInfo rankInfo = new PMRankInfo();
//            rankInfo.setMoreClass(isMoreClass);
//            rankInfo.setClassRankNum(classRankNo);
//            rankInfo.setExamRankNum(allRankNo);
//            rankInfo.setDateClassRankNum(dateClassRankNO);
//            rankInfo.setDateExamRankNum(dateRankNo);
//            rankInfo.setScore(selfPMExamScore.getScore());
//            rankInfo.setDateScore(selfDatePMExamScore.getScore());
//            rankInfo.setClassRankList(classRankList);
//            rankInfo.setExamRankList(allRanKList);
//            rankInfo.setDateClassRankList(dateClassList);
//            rankInfo.setDateExamRankList(dateRankList);
//            rankInfo.setClassSize(classSize);
//            rankInfo.setExamJoinerSize(examJoinerSize);
//
//            j.setObj(rankInfo);
//            j.setSuccess(true);
//            return j;
//        }
//
//    }

    @Override
    public Exam getExamByExamId(int examId) {
        return examDao.getExamByExamId(examId);
    }

//    public void sortScore(List<Studentexaminfo> pscoreList) {
//        for (int i = 0; i < pscoreList.size() - 1; i++) // 按照score从大到小排序
//        {
//            int max = i;
//            for (int j = i + 1; j < pscoreList.size(); j++) {
//                Studentexaminfo score1 = pscoreList.get(max);
//                Studentexaminfo score2 = pscoreList.get(j);
//                if (score1.getScore() < score2.getScore()) // 使用选择排序
//                {
//                    max = j;
//                }
//
//            }
//            if (max != i) {
//                Studentexaminfo score1 = pscoreList.get(max);
//                Studentexaminfo score2 = pscoreList.get(i);
//                Studentexaminfo temp = score1;
//                pscoreList.set(max, score2);
//                pscoreList.set(i, temp);
//            }
//        }
//        for (int i = 0; i < pscoreList.size() - 1; i++) // 对于相同score，按照rank从小到大排序
//        {
//            int min = i;
//            for (int j = i + 1; j < pscoreList.size(); j++) {
//                Studentexaminfo score1 = pscoreList.get(min);
//                Studentexaminfo score2 = pscoreList.get(j);
//                if (score1.getScore() == score2.getScore()) // 使用选择排序
//                {
//                    if (score1.getRank() > score2.getRank())
//                        min = j;
//                } else
//                    break;
//
//            }
//            if (min != i) {
//                Studentexaminfo score1 = pscoreList.get(min);
//                Studentexaminfo score2 = pscoreList.get(i);
//                Studentexaminfo temp = score1;
//                pscoreList.set(min, score2);
//                pscoreList.set(i, temp);
//            }
//        }
//    }
//
//    public void sortScore2(List<PMExamScore> pscoreList) {
//        for (int i = 0; i < pscoreList.size() - 1; i++) // 按照score从大到小排序
//        {
//            int max = i;
//            for (int j = i + 1; j < pscoreList.size(); j++) {
//                PMExamScore score1 = pscoreList.get(max);
//                PMExamScore score2 = pscoreList.get(j);
//                if (score1.getScore() < score2.getScore()) // 使用选择排序
//                {
//                    max = j;
//                }
//
//            }
//            if (max != i) {
//                PMExamScore score1 = pscoreList.get(max);
//                PMExamScore score2 = pscoreList.get(i);
//                PMExamScore temp = score1;
//                pscoreList.set(max, score2);
//                pscoreList.set(i, temp);
//            }
//        }
//        for (int i = 0; i < pscoreList.size() - 1; i++) // 对于相同score，按照rank从小到大排序
//        {
//            int min = i;
//            for (int j = i + 1; j < pscoreList.size(); j++) {
//                PMExamScore score1 = pscoreList.get(min);
//                PMExamScore score2 = pscoreList.get(j);
//                if (score1.getScore() == score2.getScore()) // 使用选择排序
//                {
//                    if (score1.getRank() > score2.getRank())
//                        min = j;
//                } else
//                    break;
//
//            }
//            if (min != i) {
//                PMExamScore score1 = pscoreList.get(min);
//                PMExamScore score2 = pscoreList.get(i);
//                PMExamScore temp = score1;
//                pscoreList.set(min, score2);
//                pscoreList.set(i, temp);
//            }
//        }
//    }

}
