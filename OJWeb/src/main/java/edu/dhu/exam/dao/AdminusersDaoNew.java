package edu.dhu.exam.dao;

import edu.dhu.exam.model.Adminusers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminusersDaoNew {

    @Select("select * from adminusers where id =#{teacherId}")
    Adminusers get(Integer teacherId);
}
