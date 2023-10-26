package edu.dhu.global.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.dhu.user.model.Account;
import edu.dhu.global.exception.Constants;
import edu.dhu.global.exception.ServiceException;
import edu.dhu.global.util.TokenUtils;
import org.apache.cxf.security.SecurityContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //token在Authorization中
        String token = request.getHeader("Authorization");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(StrUtil.isBlank((token))){
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
        }
        try {
            tokenUtils.verifyToken(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }
        // 解析token，获取userId和userRole
        String userId;
        String userRole;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            userRole = JWT.decode(token).getClaims().get("role").asString();
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token解析失败，请重新登录");
        }

        // 将权限信息放入SecurityContext中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId,
                null, new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(userRole))));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return true;
    }
}



