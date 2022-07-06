package com.jerry.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "myuser")
public class User extends BaseEntity {

  @Column(unique = true)
  private String username;

  private String password;
  private String role;

  @OneToMany(mappedBy = "user")
  private List<ShopCart> ShopCartList;
}
