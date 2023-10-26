package edu.dhu.global.config;

import edu.dhu.global.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Resource
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") //拦截所有请求，判断token是否合法决定是否需要登录
                .excludePathPatterns("/login/student","/login/admin","/login","/user/signUser",
                        "/user/findUserByStudentNoSchoolId","/school/getSchoolByName","/error",
                        "/user/findStudentByusername","/user/confirmQuestionAnswer","/user/updatePasswordByUserName");
    }

//    @Override
//    protected void addViewControllers(ViewControllerRegistry registry) {
//        // 注册访问 /login 转向 page-login.html 页面
//        registry.addViewController("/login").setViewName("page-login.html");
//        super.addViewControllers(registry);
//    }
//
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        super.addResourceHandlers(registry);
//    }
}

