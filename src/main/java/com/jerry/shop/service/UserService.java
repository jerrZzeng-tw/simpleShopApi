package com.jerry.shop.service;

import com.jerry.shop.ConstantKey;
import com.jerry.shop.dto.UserDto;
import com.jerry.shop.entity.User;
import com.jerry.shop.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;

    @Transactional
    public List<UserDto> findUsers() {
        return userRepo.findAll().stream().map(UserDto::valueOf).collect(Collectors.toList());
    }

    public Optional<UserDto> findByUsername(String username) {
        return userRepo.findByUsername(username).map(UserDto::valueOf);
    }

    public boolean login(String username, String password) {
        Optional<User> user = userRepo.findByUsernameAndPassword(username, password);
        return user.isPresent();
    }

    public List<String> findRoles() {
        return userRepo.findRoles();
    }

    public List<UserDto> findByRole(String role) {
        return userRepo.findByRole(role).stream().map(UserDto::valueOf).collect(Collectors.toList());
    }

    public void initData() {
        var userList = List.of(User.builder()
                .username("admin")
                .password("$2a$10$CK/CgQCP5mollG7i0rzi8eWmk1P6gC9GdUaHtFPSxqW5Q9pAQAR3K")
                .role("ROLE_ADMIN")
                .createdBy(ConstantKey.SYS_ID)
                .createdAt(LocalDateTime.now())
                .build(), User.builder()
                .username("user1")
                .password("$2a$10$CK/CgQCP5mollG7i0rzi8eWmk1P6gC9GdUaHtFPSxqW5Q9pAQAR3K")
                .role("ROLE_USER")
                .createdBy(ConstantKey.SYS_ID)
                .createdAt(LocalDateTime.now())
                .build(), User.builder()
                .username("user2")
                .password("$2a$10$CK/CgQCP5mollG7i0rzi8eWmk1P6gC9GdUaHtFPSxqW5Q9pAQAR3K")
                .role("ROLE_USER")
                .createdBy(ConstantKey.SYS_ID)
                .createdAt(LocalDateTime.now())
                .build());
        userRepo.saveAll(userList);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            user.get().initAuthorities();
            return user.get();
        } else {
            throw new UsernameNotFoundException("查無使用者 : " + username);
        }
    }
}
