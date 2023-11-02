package edu.dhu.exam.controller;

import edu.dhu.exam.model.PMExam;
import edu.dhu.exam.service.ExamService;
import edu.dhu.exam.ws.MyWebSocketHandler;
import edu.dhu.global.model.DecodeToken;
import edu.dhu.global.model.RespBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamService examService;
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
        return RespBean.ok("获取考试列表成功！",examService.dataGrid(pMExam));
    }
}
