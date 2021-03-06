package com.jerry.shop.service;

import com.jerry.shop.Constany;
import com.jerry.shop.dto.CartDetailDto;
import com.jerry.shop.dto.ShopCartDto;
import com.jerry.shop.entity.CartDetail;
import com.jerry.shop.entity.Product;
import com.jerry.shop.entity.ShopCart;
import com.jerry.shop.entity.User;
import com.jerry.shop.exception.ApiException;
import com.jerry.shop.repo.CartDetailRepo;
import com.jerry.shop.repo.ProductRepo;
import com.jerry.shop.repo.ShopCartRepo;
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
public class ShopCartService {

  private ShopCartRepo shopCartRepo;
  private CartDetailRepo cartDetailRepo;

  private UserRepo userRepo;

  private ProductRepo productRepo;

  @Transactional
  public List<ShopCartDto> findByUserId(Long userId) {
    return shopCartRepo.findByUserIdJoinUserJoinCarDetail(userId).stream()
        .map(ShopCartDto::valueOf)
        .collect(Collectors.toList());
  }

  @Transactional
  public ShopCartDto findByShopCartId(Long id) {
    return shopCartRepo.findById(id).map(ShopCartDto::valueOf).orElseThrow(ApiException::noData);
  }

  @Transactional
  public ShopCartDto order(Long userId, ShopCartDto shopCartDto) {
    ShopCart shopCart =
        ShopCart.builder()
            .user(User.builder().id(userId).build())
            .createdBy(Constany.SYS_ID)
            .createdAt(LocalDateTime.now())
            .build();
    shopCartRepo.save(shopCart);

    cartDetailRepo.saveAll(
        Optional.ofNullable(shopCartDto.getCartDetailDtoList())
            .map(
                t ->
                    t.stream()
                        .map(
                            c ->
                                CartDetail.builder()
                                    .shopCart(shopCart)
                                    .amount(c.getAmount())
                                    .product(
                                        Product.builder()
                                            .id(c.getProductId())
                                            .createdBy(Constany.SYS_ID)
                                            .createdAt(LocalDateTime.now())
                                            .build())
                                    .build())
                        .collect(Collectors.toList()))
            .orElseThrow(ApiException::fail));
    return ShopCartDto.valueOf(shopCart);
  }

  @Transactional
  public void deleteOrderById(Long id) {
    cartDetailRepo.deleteAllByShopCartId(id);
    shopCartRepo.deleteById(id);
  }

  public void updateCartDetail(CartDetailDto cartDetailDto) {
    cartDetailRepo.save(
        CartDetail.builder().id(cartDetailDto.getId()).amount(cartDetailDto.getAmount()).build());
  }

  @Transactional
  public void checkout(Long id) {
    ShopCart shopCart = shopCartRepo.findByIdWithLock(id).orElseThrow(ApiException::noData);
    shopCart.setCheckout(true);
    shopCart.setUpdatedAt(LocalDateTime.now());
    shopCart.setUpdatedBy(Constany.SYS_ID);
    shopCartRepo.save(shopCart);

    if (shopCart.getCartDetailList().isEmpty()) {
      throw ApiException.fail("????????????????????????");
    }
    shopCart
        .getCartDetailList()
        .forEach(
            t -> {
              Product product =
                  productRepo
                      .findByIdWithLock(t.getProduct().getId())
                      .orElseThrow(ApiException::fail);
              if (product.getInventory() >= t.getAmount()) {
                product.setInventory(product.getInventory() - t.getAmount());
                productRepo.save(product);
              } else {
                throw ApiException.fail(" product :" + product.getName() + " ????????????.");
              }
            });
  }

  public void initData() {
    var shopCartList =
        List.of(
            ShopCart.builder()
                .user(User.builder().id(2L).build())
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build(),
            ShopCart.builder()
                .user(User.builder().id(2L).build())
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build());
    shopCartRepo.saveAll(shopCartList);

    var cartDetailList =
        List.of(
            CartDetail.builder()
                .shopCart(ShopCart.builder().id(1L).user(User.builder().id(2L).build()).build())
                .product(Product.builder().id(1L).build())
                .amount(1)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build(),
            CartDetail.builder()
                .shopCart(ShopCart.builder().id(1L).user(User.builder().id(2L).build()).build())
                .product(Product.builder().id(2L).build())
                .amount(2)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build(),
            CartDetail.builder()
                .shopCart(ShopCart.builder().id(2L).user(User.builder().id(2L).build()).build())
                .product(Product.builder().id(1L).build())
                .amount(1)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build(),
            CartDetail.builder()
                .shopCart(ShopCart.builder().id(2L).user(User.builder().id(2L).build()).build())
                .product(Product.builder().id(2L).build())
                .amount(20000)
                .createdAt(LocalDateTime.now())
                .createdBy(Constany.SYS_ID)
                .build());
    cartDetailRepo.saveAll(cartDetailList);
  }
}
