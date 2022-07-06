package com.jerry.shop.service;

import com.jerry.shop.Constany;
import com.jerry.shop.dto.UserDto;
import com.jerry.shop.entity.User;
import com.jerry.shop.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
  private UserRepo userRepo;

  @Transactional
  public List<UserDto> findUsers() {
    return userRepo.findAll().stream().map(UserDto::valueOf).collect(Collectors.toList());
  }

  public Optional<UserDto> findByUsername(String username) {
    return userRepo.findByUsername(username).map(UserDto::valueOf);
  }

  public List<String> findRoles() {
    return userRepo.findRoles();
  }

  public List<UserDto> findByRole(String role) {
    return userRepo.findByRole(role).stream().map(UserDto::valueOf).collect(Collectors.toList());
  }

  public void initData() {
    var userList =
        List.of(
            User.builder()
                .username("admin")
                .password("1234")
                .role("ADMIN")
                .createdBy(Constany.SYS_ID)
                .createdAt(LocalDateTime.now())
                .build(),
            User.builder()
                .username("user1")
                .password("1234")
                .role("USER")
                .createdBy(Constany.SYS_ID)
                .createdAt(LocalDateTime.now())
                .build(),
            User.builder()
                .username("user2")
                .password("1234")
                .role("USER")
                .createdBy(Constany.SYS_ID)
                .createdAt(LocalDateTime.now())
                .build());
    userRepo.saveAll(userList);
  }
}
