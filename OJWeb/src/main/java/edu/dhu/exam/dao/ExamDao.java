package edu.dhu.exam.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;

@Mapper
public interface ExamDao {
    @Select("select endtime from exam where id = #{examId}")
    Date getExamEndTime(Integer examId);
}
