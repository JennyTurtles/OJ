package edu.dhu.controller;

import edu.dhu.model.LoginInf;
import edu.dhu.model.RespBean;
import edu.dhu.util.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/token")
public class TestToken {
    @Resource
    private TokenUtils TokenUtils;

    @PostMapping("")
    public RespBean testToken(LoginInf loginInf) {
        return RespBean.ok("token验证成功");
    }
}
