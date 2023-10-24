package edu.dhu.exam.controller;

import edu.dhu.exam.model.Exam;
import edu.dhu.exam.model.PMExam;
import edu.dhu.exam.service.ExamService;
import edu.dhu.global.util.DecodeToken;
import edu.dhu.user.model.RespBean;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamService examService;
    @GetMapping("sync_time")
    public RespBean syncTime(Integer examId) {
        Long res = examService.syncTime(examId);
        if (res != null)
            return RespBean.ok("获取考试结束时间成功！",new Exam(res));
        else
            return RespBean.error("考试ID不存在！");
    }

    @GetMapping("/examList")
    @Transactional
    public RespBean getExamList(Integer pageNum,Integer pageSize, HttpServletRequest request) {
        PMExam pMExam = new PMExam();
        pMExam.setPage(pageNum);
        pMExam.setRows(pageSize);
        DecodeToken decodeToken = new DecodeToken(request);
        String userId = decodeToken.getUserId();
        pMExam.setStudentId(Integer.parseInt(userId));
        return RespBean.ok("获取考试列表成功！",examService.dataGrid2(pMExam));
    }
}
