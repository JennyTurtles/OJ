package edu.dhu.user.service;

import edu.dhu.user.model.Account;
import edu.dhu.user.dao.LoginDao;
import edu.dhu.user.model.LoginInf;
import edu.dhu.util.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    @Resource
    private LoginDao loginDao;

    @Resource
    private TokenUtils TokenUtils;

    public LoginInf login(LoginInf loginInf,String type)
    {
        Account account = null;
        if (type.equals("student"))
            account = loginDao.loginStudent(loginInf);
        else if (type.equals("admin"))
            account = loginDao.loginAdmin(loginInf);
        if (account == null)
            return null; // 用户名不存在
        if(check(loginInf.getPassword(),account.getPassword())){
            String token = TokenUtils.genToken(account.getID().toString(),null);
            loginInf.setToken(token);
            loginInf.setName(account.getName());
            loginInf.setRole(account.getRole());
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