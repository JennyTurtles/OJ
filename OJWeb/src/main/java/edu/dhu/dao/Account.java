package edu.dhu.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Account{
    Integer ID;
    String name;
    Integer institutionID;

    private Boolean enabled;

    private String username;

    private String password;

    private String role;


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

}
