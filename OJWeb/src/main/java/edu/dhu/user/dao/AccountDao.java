package edu.dhu.user.dao;

import edu.dhu.user.model.Account;
import edu.dhu.user.model.Adminusers;
import edu.dhu.user.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountDao {
    @Select("select chineseName name from users where ID = #{ID}")
    public String getStudentNameByID(Integer ID);

    @Select("select * from users where ID = #{ID}")
    public Users getUserByID(Integer ID);

    @Select("select * from adminusers where ID = #{ID}")
    public Adminusers getAdminUserByID(Integer ID);

    @Select("select name from adminusers where ID = #{ID}")
    public String getAdminNameByID(Integer ID);
}
