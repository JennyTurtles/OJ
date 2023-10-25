package edu.dhu.user.controller;

import edu.dhu.user.model.Account;
import edu.dhu.global.model.RespBean;
import edu.dhu.user.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    LoginService loginService;

    @PostMapping(value ={"/student","/admin"})
    public RespBean loginStudent(@RequestBody Account account) {
        return loginService.login(account);
    }
}
