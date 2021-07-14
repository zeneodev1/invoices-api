package com.zeneo.invoice.dto;

import com.zeneo.invoice.dao.User;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }

}
