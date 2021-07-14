package com.zeneo.invoice.service;

import com.zeneo.invoice.dao.User;
import com.zeneo.invoice.dto.LoginRequest;
import com.zeneo.invoice.dto.RegisterRequest;
import com.zeneo.invoice.dto.UserInfo;
import com.zeneo.invoice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;


    public User login(LoginRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()
                        )
                );

        return  (User) authenticate.getPrincipal();
    }


    public User register(RegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return  userRepo.save(request.toUser());
    }

    public UserInfo getUserInfo(Integer id) {
        return  userRepo.findById(id)
                .orElseThrow(RuntimeException::new)
                .toUserInfo();
    }
}
