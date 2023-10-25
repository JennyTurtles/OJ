package edu.dhu.exam.dao;

import edu.dhu.exam.model.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.sql.Date;
import java.util.List;

@Mapper
public interface ExamDaoNew {
    @Select("select endtime from exam where id = #{examId}")
    Date getExamEndTime(Integer examId);
}
