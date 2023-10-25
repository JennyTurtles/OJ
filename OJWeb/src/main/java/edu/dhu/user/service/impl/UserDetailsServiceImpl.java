package edu.dhu.user.service.impl;


import edu.dhu.user.dao.LoginDao;
import edu.dhu.user.model.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private LoginDao loginDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取当前请求的url，通过当前url区分应该从哪个表中查询用户信息。
        // 并不是最佳实践，loadUserByUsername方法的主要目标是加载用户详情，而不是处理HTTP请求。
        // springSecurity对多表用户登陆的支持并不好，这里只是一个简单的实现，以后可以进一步改进。
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        Account account = null;

        if (url.equals("/login/admin"))
            account = loginDao.loginAdmin(username);
        else if (url.equals("/login/student"))
            account = loginDao.loginStudent(username);
        if (account == null)
            throw new UsernameNotFoundException("用户名或密码错误！");
        if (account.getRole() == null)
            account.setRole("student");

        return account;
    }
}
