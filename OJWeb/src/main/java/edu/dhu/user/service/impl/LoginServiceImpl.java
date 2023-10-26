package edu.dhu.user.service.impl;

import edu.dhu.user.model.Account;
import edu.dhu.global.model.RespBean;
import edu.dhu.user.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private edu.dhu.global.util.TokenUtils TokenUtils;
    @Override
    public RespBean login(Account account) {
        // 将用户输入的用户名和密码封装为Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
        // 传入authenticationManager，authenticationManager会调用UserDetailsService实现类中的loadUserByUsername方法去数据库中查询用户信息
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 获取用户信息
        account = (Account) authentication.getPrincipal();
        // 生成token
        String token = TokenUtils.genToken(account.getID().toString(),account.getRole(),account.getCode());
        // 生成响应对象
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("role",account.getRole());
        map.put("name",account.getName());
        return RespBean.ok("登陆成功!",map);
    }
}
