package edu.dhu.user.controller;

import edu.dhu.global.model.DecodeToken;
import edu.dhu.user.dao.AccountDao;
import edu.dhu.global.model.RespBean;
import edu.dhu.user.model.Adminusers;
import edu.dhu.user.model.Users;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


// 增删改查个人信息
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountDao accountDao;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('student','admin','teacher','assistant')")
    public RespBean getAccount(HttpServletRequest request) {
        // 此处已经经过拦截器，token一定是合法的，无因此需异常处理
        DecodeToken decodeToken = new DecodeToken(request);
        String userId = decodeToken.getUserId();
        String role = decodeToken.getRole();

        Map<String,String> map = new HashMap<>();
        map.put("role",role);
        if (role.equals("student")){
            Users users = accountDao.getUserByID(Integer.parseInt(userId));
            map.put("name",users.getChineseName());
            map.put("username",users.getUsername());
        }
        else{
            Adminusers adminusers = accountDao.getAdminUserByID(Integer.parseInt(userId));
            map.put("name",adminusers.getName());
            map.put("username",adminusers.getAccount());
        }
        return RespBean.ok("查询成功", map);
    }
}
