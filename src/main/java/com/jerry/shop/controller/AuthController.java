package com.jerry.shop.controller;

import com.jerry.shop.dto.AuthDto;
import com.jerry.shop.dto.ResDto;
import com.jerry.shop.helper.JWTHelper;
import com.jerry.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Api(tags = "使用者認證 API")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ApiOperation("使用者認證")
    @PostMapping("/login")
    public ResDto login(@RequestBody AuthDto authDto) {
        Authentication authAfterSuccessLogin = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDto.getUserName(), authDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);
        ;
        return ResDto.defult_ok(JWTHelper.getToken(authDto.getUserName(), authAfterSuccessLogin.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())));
    }

    @GetMapping("/logout")
    public ResDto logout() {
        return ResDto.defult_ok("");
    }
}
