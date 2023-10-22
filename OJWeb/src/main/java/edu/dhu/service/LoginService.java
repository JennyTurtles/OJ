package edu.dhu.service;

import cn.hutool.core.bean.BeanUtil;
import edu.dhu.dao.Account;
import edu.dhu.dao.LoginDao;
import edu.dhu.model.LoginInf;
import edu.dhu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class LoginService {

    @Resource
    private LoginDao loginDao;

    public LoginInf loginStudent(LoginInf loginInf)
    {
        String role = loginInf.getRole();
        Account account = loginDao.loginStudent(loginInf);

        if (account == null)
            return null; // 用户名不存在

        if(check(loginInf.getPassword(),account.getPassword())){
            BeanUtil.copyProperties(account, loginInf, true);
            String token = TokenUtils.genToken(account.getID().toString(),account.getPassword(), role);
            loginInf.setToken(token);
            loginInf.setID(account.getID());
            loginInf.setInstitutionID(account.getInstitutionID());
//            List<String> rs = Arrays.asList(loginInf.getRole().split(";"));
//            List<String> roleNameList = roleMapper.selectNameByRoleID(rs);
//            String loginInfRoleName = "";
//            for(int i = 0;i < roleNameList.size();i ++) {
//                loginInfRoleName += roleNameList.get(i) + ";";
//            }
//            loginInf.setRoleName(loginInfRoleName);
            return loginInf;
        }
        else
            return loginInf; // 如果返回不带token的Inf，表示密码错误
    }

    public LoginInf loginTeacher(LoginInf loginInf)
    {
        String role = loginInf.getRole();
        Account account = loginDao.loginStudent(loginInf);

        if (account == null)
            return null; // 用户名不存在

        if(check(loginInf.getPassword(),account.getPassword())){
            BeanUtil.copyProperties(account, loginInf, true);
            String token = TokenUtils.genToken(account.getID().toString(),account.getPassword(), role);
            loginInf.setToken(token);
            loginInf.setID(account.getID());
            loginInf.setInstitutionID(account.getInstitutionID());
//            List<String> rs = Arrays.asList(loginInf.getRole().split(";"));
//            List<String> roleNameList = roleMapper.selectNameByRoleID(rs);
//            String loginInfRoleName = "";
//            for(int i = 0;i < roleNameList.size();i ++) {
//                loginInfRoleName += roleNameList.get(i) + ";";
//            }
//            loginInf.setRoleName(loginInfRoleName);
            return loginInf;
        }
        else
            return loginInf; // 如果返回不带token的Inf，表示密码错误
    }

    // 核对密码是否正确
    private boolean check(String targetPassword, String truePassword){
//        return passwordEncoder.matches(targetPassword,truePassword); // 后端不再进行加密
        return targetPassword.equals(truePassword);
    }
}