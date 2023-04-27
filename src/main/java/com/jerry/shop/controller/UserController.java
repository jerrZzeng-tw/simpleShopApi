package com.jerry.shop.controller;

import com.jerry.shop.dto.UserDto;
import com.jerry.shop.exception.ApiException;
import com.jerry.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Api(tags = "使用者資訊 API")
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @ApiOperation("取得所有使用者資訊")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserDto> findUsers() {
        return userService.findUsers();
    }

    @ApiOperation("取得單一使用者資訊")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/{name}")
    public UserDto findUser(@PathVariable String name) {
        return userService.findByUsername(name).orElseThrow(ApiException::noData);
    }

    /**
     * 取得符合該角色使用者
     *
     * @return string 腳色名稱
     */
    @ApiOperation("取得所有角色")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles")
    public List<String> findRoles() {
        return userService.findRoles();
    }

    /**
     * 取得符合該角色使用者
     *
     * @param role 腳色名稱
     * @return user 使用者
     */
    @ApiOperation("取得該角色所有使用者")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles/{role}")
    public List<UserDto> listUser(@PathVariable String role) {
        return userService.findByRole(role);
    }
}
