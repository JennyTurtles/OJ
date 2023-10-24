package edu.dhu.exam.service;

import edu.dhu.exam.dao.*;
import edu.dhu.exam.model.Adminusers;
import edu.dhu.exam.model.DataGrid;
import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.PMExam;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {
    @Resource
    private ExamDaoI examDao;
    @Resource
    private ExamDaoNew examDaoNew;
    @Resource
    private ItrainProblemCatDaoI itrainProblemCatDao;
    @Resource
    private AdminusersDaoI adminusersDao;
    @Resource
    private AdminusersDaoNew adminusersDaoNew;
    @Resource
    private ItrainProblemCatDaoNew itrainProblemCatDaoNew;

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
//            dg.setTotalLines(totalLines);
            dg.setTotal(totalLines);
            // 设置每页显示多少行
//            dg.setPageLines(pMExam.getRows());
            dg.setPageNum(pMExam.getRows());
            // 设置总的页数,总页数 ＝ (总行数／每页显示多少行)向上取整
            dg.setTotalPages((long) (Math.ceil((double) dg.getTotal() / (double) dg.getPageNum())));
            // 设置当前显示第几页
            dg.setCurrentPage(pMExam.getPage());
            // 设置当前页的数据行数量
            dg.setCurrentPageLineNum(pMExamList.size());
            // 设置当前页的所有数据行
//            dg.setRows(pMExamList);
            dg.setList(pMExamList);
            return dg;
        }
    }

//    @Transactional
//    public DataGrid dataGrid2(PMExam pMExam) {
//
//        DataGrid dg = new DataGrid();
//        long totalLines;
//        List<Exam> theExamList;
//        // 如果学生没有登录
//        if (pMExam.getStudentId() == null) {
//            // 查询所有的考试
//            // 获取总的行数
//            totalLines = examDaoNew.getAllExamsNum();
//            // 分页查找当前页的数据
//            int offset = (pMExam.getPage() - 1) * pMExam.getRows();
//            RowBounds rowBounds = new RowBounds(offset, pMExam.getRows());
//            theExamList = examDaoNew.getExamsByPage(rowBounds);
//        } else {
//            // 查询该学生所有符合条件的考试
//            // 获取总的行数
//            totalLines = examDaoNew.getAllExamsNumByStuId(pMExam.getStudentId());
//            // 分页查找当前页的数据
//            int offset = (pMExam.getPage() - 1) * pMExam.getRows();
//            RowBounds rowBounds = new RowBounds(offset, pMExam.getRows());
//            theExamList = examDaoNew.getExamsByPageAndStuId(pMExam.getStudentId(), rowBounds);
//        }
//
//        // 如果考试查询结果为空
//        if (theExamList == null) {
//            return null;
//        } else {
//            List<PMExam> pMExamList = new ArrayList<PMExam>();
//            // 将Exam List转换为PMExam List
//            changeModel(theExamList, pMExamList);
//
//            for (int i = 0; i < pMExamList.size(); i++) {
//                if(pMExamList.get(i).getType()!=null&&pMExamList.get(i).getType().equals("iTraining")){
//                    int catNumber = itrainProblemCatDaoNew.getExamCategoryCount(pMExamList.get(i).getId());
//                    pMExamList.get(i).setProblemNum(catNumber);
//                }
//                pMExamList.get(i).setPage(pMExam.getPage());
//                pMExamList.get(i).setRows(pMExam.getRows());
//
//                // 设置比赛状态
//                pMExamList.get(i).setStatus(
//                        compareTime(pMExamList.get(i).getStarttime(), pMExamList.get(i).getEndtime(), new java.util.Date()));
//
//                // 根据老师id查找并设置老师名字
////                Adminusers teacher = adminusersDao.get(Adminusers.class, pMExamList.get(i).getTeacherId());
//                Adminusers teacher = adminusersDaoNew.get(pMExamList.get(i).getTeacherId());
//
//                if (teacher == null) {
//                    pMExamList.get(i).setTeacherName(" ");
//                } else {
//                    pMExamList.get(i).setTeacherName(teacher.getName());
//                }
//                //设置考试类别
//                pMExamList.get(i).setExamType(theExamList.get(i).getType());
//            }
//
//            // 设置总的记录行数
//            dg.setTotalLines(totalLines);
//            // 设置每页显示多少行
//            dg.setPageLines(pMExam.getRows());
//            // 设置总的页数,总页数 ＝ (总行数／每页显示多少行)向上取整
//            dg.setTotalPages((long) (Math.ceil((double) dg.getTotalLines() / (double) dg.getPageLines())));
//            // 设置当前显示第几页
//            dg.setCurrentPage(pMExam.getPage());
//            // 设置当前页的数据行数量
//            dg.setCurrentPageLineNum(pMExamList.size());
//            // 设置当前页的所有数据行
//            dg.setRows(pMExamList);
//            return dg;
//        }
//    }
}
