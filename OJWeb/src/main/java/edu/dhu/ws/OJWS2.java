package edu.dhu.ws;

import javax.jws.WebService;

@WebService
public interface OJWS2 {

	// 将登陆人的信息转换成xml格式
	public String WS_Login(String userName, String userPassword);
}
