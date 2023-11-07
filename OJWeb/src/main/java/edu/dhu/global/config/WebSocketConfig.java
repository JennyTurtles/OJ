package edu.dhu.global.config;
import edu.dhu.exam.ws.SolutionWebSocketHandler;
import edu.dhu.global.config.interceptor.WebSocketJwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private SolutionWebSocketHandler solutionWebSocketHandler;
    @Resource
    private WebSocketJwtInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry//添加myHandler消息处理对象，和websocket访问地址
                .addHandler(solutionWebSocketHandler, "/solution/ws")
                //设置允许跨域访问
                .setAllowedOrigins("*")
                //添加拦截器可实现用户链接前进行权限校验等操作
                .addInterceptors(webSocketInterceptor);
    }
}
