package edu.dhu.user.dao;

import edu.dhu.user.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountDao {
    @Select("select chineseName name from users where ID = #{ID}")
    public String getStudentNameByID(Integer ID);

    @Select("select name from adminusers where ID = #{ID}")
    public String getAdminNameByID(Integer ID);
}
