package edu.dhu.dao;

import edu.dhu.model.Account;
import edu.dhu.model.LoginInf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginDao {

    @Select("select * from users where username = #{username}")
    public Account loginStudent(LoginInf loginInf);

    @Select("select * from adminusers where username = #{username}")
    public Account loginAdmin(LoginInf loginInf);

}
