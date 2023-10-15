package edu.dhu.ws;

import edu.dhu.cache.WSUsersCacheManager;
import edu.dhu.pageModel.PMUser;
import edu.dhu.service.UserServiceI;
import edu.dhu.util.OJWSUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@Component
public class OJWSImpl2 implements OJWS2{

    @Resource
    private UserServiceI userService;
    @Override
    public String WS_Login(String userName, String password) {
        String xmlString;
        OJWSUtil oj = new OJWSUtil();
        PMUser user = new PMUser();
        WSUsersCacheManager wsusersCacheManager = WSUsersCacheManager.getInstance();
        user = (PMUser) wsusersCacheManager.getObject(userName + "-" + password);
        if (user == null) {
            user = GetUser(userName, password);
            xmlString = oj.createXmlToString(user);
            wsusersCacheManager.putObject(userName + "-" + password, user);
        }
        xmlString = oj.createXmlToString(user);
        return xmlString;
    }
    private PMUser GetUser(String userName, String password) {
        PMUser user = new PMUser();
        PMUser userReturn = null;
        user.setUsername(userName);
        user.setPassword(password);
        // UserServiceImpl userService = new UserServiceImpl();
        try {
            userReturn = userService.login(user);
        } catch (Exception e) {
            userReturn = new PMUser();
            userReturn.setRspMsg("服务器错误,请稍后重试!");
        }

        if (userReturn == null || userReturn.getId() == null) {
            userReturn = new PMUser();
            userReturn.setRspMsg("用户名或密码错误");
        } else {
            userReturn.setRspMsg("Success");
            WebServiceContext wsContext = new org.apache.cxf.jaxws.context.WebServiceContextImpl();
            MessageContext mc = wsContext.getMessageContext();
            if (mc != null) {
                HttpServletRequest servletContext = (javax.servlet.http.HttpServletRequest) mc
                        .get(MessageContext.SERVLET_REQUEST);
                userReturn.setIp(servletContext.getRemoteAddr());
            }
        }
        return userReturn;
    }
}
