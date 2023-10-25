package edu.dhu.user.service;

import edu.dhu.user.model.Account;
import edu.dhu.global.model.RespBean;


public interface LoginService {
    RespBean login(Account account);
}
