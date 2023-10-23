package edu.dhu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 登陆的请求体和响应体都使用该model
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInf {
    private String username;
    private String password;
    private String role;
    private String token;
    private String name;

    public LoginInf(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
