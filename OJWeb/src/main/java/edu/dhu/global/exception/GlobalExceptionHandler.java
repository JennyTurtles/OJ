package edu.dhu.global.exception;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import edu.dhu.global.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ServiceException.class)
    public RespBean serviceException(ServiceException e) {
        if (e.getCode().equals("401"))
            return new RespBean(401, e.getMessage(), null);
        else
            return RespBean.error("未知错误");
    }

    @ExceptionHandler(AuthenticationException.class)
    public RespBean serviceException(AuthenticationException e) {
        return RespBean.error(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public RespBean serviceException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException)
            return RespBean.error("用户名或学号已存在！");
        else
            return RespBean.error("数据库错误");
    }
}
