package edu.dhu.user.dao;

import edu.dhu.user.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginDao {

    @Select("select id,chineseName name,username,password,studentNo code from users where username = #{username}")
    public Account loginStudent(String username);

    @Select("select * from adminusers where account = #{username}")
    public Account loginAdmin(String username);
}
