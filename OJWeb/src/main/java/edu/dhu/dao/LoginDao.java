package edu.dhu.dao;

import edu.dhu.model.LoginInf;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Component
public interface LoginDao {

    @Select("select * from users where username = #{username} and password = #{password}")
    public Account loginStudent(LoginInf loginInf);

    @Select("select * from adminusers where account = #{username} and password = #{password}")
    public Account loginTeacher(LoginInf loginInf);

    @Select("select * from users where ID = #{ID}")
    public Account getStudentByID(Integer ID);

    @Select("select * from adminusers where ID = #{ID}")
    public Account getTeacherByID(Integer ID);
}
