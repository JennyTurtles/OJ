package edu.dhu.exam.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItrainProblemCatDaoNew {

    @Select("select count(*) from itrainProbCatgory where examId =#{id}")
    int getExamCategoryCount(Integer id);
}
