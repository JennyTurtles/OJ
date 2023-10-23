package edu.dhu.user.controller;

import com.auth0.jwt.JWT;
import edu.dhu.user.dao.AccountDao;
import edu.dhu.user.model.LoginInf;
import edu.dhu.user.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


// 增删改查个人信息
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountDao accountDao;

    @GetMapping("")
    public RespBean getAccount(HttpServletRequest request) {
        // 此处已经经过拦截器，token一定是合法的，无因此需异常处理
        String userId = JWT.decode(request.getHeader("Authorization")).getAudience().get(0);
        String userRole = JWT.decode(request.getHeader("Authorization")).getClaims().get("role").asString();
        if (userRole.equals("student"))
            return RespBean.ok("查询成功", new LoginInf(accountDao.getStudentNameByID(Integer.parseInt(userId)),userRole));
        else
            return RespBean.ok("查询成功", new LoginInf(accountDao.getAdminNameByID(Integer.parseInt(userId)),userRole));
    }
}
