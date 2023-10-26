package edu.dhu.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

// 需要实现UserDetail接口以便让SpringSecurity进行认证
@NoArgsConstructor
@Data
@Component
public class Account implements UserDetails{
    Integer ID;
    String name;
    private String username;
    private String password;
    private String Email;
    private String answer;
    private String question;
    private Date createDate;
    private Integer schoolID;
    private String role;
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account hr = (Account) o;
        return Objects.equals(username, hr.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(role)));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
