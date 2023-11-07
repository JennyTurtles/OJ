package edu.dhu.global.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import edu.dhu.global.exception.Constants;
import edu.dhu.global.exception.ServiceException;
import edu.dhu.global.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

@Component
@Slf4j
public class WebSocketJwtInterceptor implements HandshakeInterceptor {

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        //获取参数
        String token = serverHttpRequest.getServletRequest().getParameter("token");
        if(StrUtil.isBlank(token)){
            return false;
        }
        try {
            tokenUtil.verifyToken(token);
        } catch (JWTVerificationException e) {
            return false;
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
        attributes.put("userId", userId);
        attributes.put("role", userRole);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//        log.info("连接已经建立!");
    }
}
