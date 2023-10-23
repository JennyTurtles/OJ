package edu.dhu.exam.service;

import edu.dhu.exam.dao.ExamDao;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;

@Service
public class ExamService {
    @Resource
    private ExamDao examDao;

    public Long syncTime(Integer examId) {
        Date date = examDao.getExamEndTime(examId);
        if (date == null)
            return null;
        return date.getTime();
    }
}
