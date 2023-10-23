package edu.dhu.controller;

import edu.dhu.model.LoginInf;
import edu.dhu.model.RespBean;
import edu.dhu.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    LoginService loginService;
    @PostMapping("/student")
    public RespBean loginStudent(LoginInf loginInf) {
        LoginInf res = loginService.login(loginInf,"student");
        if (res == null) // 未找到用户则返回用户名不存在
            return RespBean.error("用户名不存在!");
        else if (res.getToken() == null) // 未得到token则返回密码错误
            return RespBean.error("密码错误!");
        else {
            res.setPassword("");
            res.setRole("student");
            return RespBean.ok("登陆成功!", res);
        }
    }

    @PostMapping("/admin")
    public RespBean loginAdmin(LoginInf loginInf) {
        LoginInf res = loginService.login(loginInf,"admin");
        if (res == null) // 未找到用户则返回用户名不存在
            return RespBean.error("用户名不存在!");
        else if (res.getToken() == null) // 未得到token则返回密码错误
            return RespBean.error("密码错误!");
        else {
            res.setPassword("");
            return RespBean.ok("登陆成功!", res);
        }
    }
}
