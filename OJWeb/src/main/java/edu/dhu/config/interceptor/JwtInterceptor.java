package edu.dhu.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.dhu.dao.AccountDao;
import edu.dhu.model.Account;
import edu.dhu.dao.LoginDao;
import edu.dhu.exception.Constants;
import edu.dhu.exception.ServiceException;
import edu.dhu.util.TokenUtils;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
//import org.sys.rate.model.Admin;
//import org.sys.rate.model.Student;
//import org.sys.rate.model.Teacher;
//import org.sys.rate.service.admin.AdminService;
//import org.sys.rate.service.admin.StudentService;
//import org.sys.rate.service.admin.TeacherService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private AccountDao accountDao;
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //token放置在Authorization中
        String token = request.getHeader("Authorization");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(StrUtil.isBlank((token))){
            //没有token，重新获取
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
        }
        try {
            tokenUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }
        //获取token中的userid
        String userId;
        String userRole;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            userRole = JWT.decode(token).getClaims().get("role").asString();
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token解析失败，请重新登录");
        }

        // 根据token中的userid和role查询数据库
        if(userRole.equals("admin") || userRole.equals("assistant") || userRole.equals("teacher")) {
            Account account = accountDao.getAdminByID(Integer.parseInt(userId));
            if (account == null)
                throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        } else if (userRole.equals("student")) {
            Account account = accountDao.getStudentByID(Integer.parseInt(userId));
            if (account == null)
                throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }else{
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
        }
        return true;
    }
}



