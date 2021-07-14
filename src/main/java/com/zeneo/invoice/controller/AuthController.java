package com.zeneo.invoice.controller;

import com.zeneo.invoice.config.JwtUtil;
import com.zeneo.invoice.dao.User;
import com.zeneo.invoice.dto.LoginRequest;
import com.zeneo.invoice.dto.RegisterRequest;
import com.zeneo.invoice.dto.UserInfo;
import com.zeneo.invoice.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/auth/login")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful Login",
                            content = { @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(defaultValue = "OK")) },
                            headers = {
                                @Header(name = HttpHeaders.AUTHORIZATION, description = "JWT token")
                            })
            }
        )
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
         User user = authService.login(request);
         return ResponseEntity.ok().header(
                 HttpHeaders.AUTHORIZATION,
                 jwtUtil.generateAccessToken(user)
         ).body("Ok");
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully",
                            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserInfo.class)) },
                            headers = {
                                    @Header(name = HttpHeaders.AUTHORIZATION, description = "JWT token")
                            })
            }
    )
    @PostMapping("/auth/register")
    public ResponseEntity<UserInfo> register(@RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok().header(
                HttpHeaders.AUTHORIZATION,
                jwtUtil.generateAccessToken(user)
        ).body(user.toUserInfo());
    }

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserInfo.class)) })
            }
    )
    @Parameters(
        {
            @Parameter(name = HttpHeaders.AUTHORIZATION,
                    in = ParameterIn.HEADER,
                    schema = @Schema(type = "string", example = "Bearer token"))
        }
    )
    @GetMapping("/user-info")
    public ResponseEntity<UserInfo> getUserInfo(Authentication authentication) {
        UserInfo user = authService.getUserInfo(((User) authentication.getPrincipal()).getId());
        return ResponseEntity.ok()
                .body(user);
    }
}
