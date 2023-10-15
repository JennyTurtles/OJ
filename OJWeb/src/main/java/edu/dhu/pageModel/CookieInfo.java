package edu.dhu.pageModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2556479625850989812L;

	public boolean addCookie(HttpServletRequest request,
			HttpServletResponse response, String key, String uuid)
			throws Exception {
		// 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 创建每次访问的时候，我们都会回写一个Cookie给客户机，并且将Cookie的有效期设置为12小时,路径设置成整个web应用
		Cookie cookie = new Cookie(key, uuid);
		cookie.setMaxAge(12 * 60 * 60);
		response.addCookie(cookie);
		return true;

	}

	public boolean getCookie(HttpServletRequest request,
			HttpServletResponse response, String cookieName, String loginuuid) {
		// TODO Auto-generated method stub
		// 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 获得用户的时间cookie,并且获取值
		Cookie cookies[] = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				if (loginuuid.equals(cookies[i].getValue())) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean clearCookie(HttpServletRequest request,HttpServletResponse response,String name){
		 Cookie[] cookies = request.getCookies(); 
		 if (null==cookies) {  
             System.out.println("没有cookie==============");  
         } else {  
             for(Cookie cookie : cookies){  
                 if(cookie.getName().equals(name)){  
                     cookie.setValue(null);  
                     cookie.setMaxAge(0);// 立即销毁cookie  
                     cookie.setPath("/");  
                     System.out.println("被删除的cookie名字为:"+cookie.getName());  
                     response.addCookie(cookie);  
                     break;  
                 }  
             }
             return true;
         }
		return false;  
	}
}
