package edu.dhu.exam.controller;

import edu.dhu.exam.model.Exam;
import edu.dhu.exam.service.ExamService;
import edu.dhu.user.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
