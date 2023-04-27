package com.jerry.shop.controller;

import com.jerry.shop.dto.CartDetailDto;
import com.jerry.shop.dto.ShopCartDto;
import com.jerry.shop.exception.ApiException;
import com.jerry.shop.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "購物車 API")
@AllArgsConstructor
@RestController
@RequestMapping("shopCart")
public class ShopCartController {
    private ShopCartService shoppingService;

    @ApiOperation("取得使用者訂單")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public List<ShopCartDto> findShopCartBy(@PathVariable("userId") Long userId) {
        return shoppingService.findByUserId(userId);
    }

    @ApiOperation("取得使用者訂單明細")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @GetMapping("/{userId}/{orderId}")
    public ShopCartDto findDetailBy(@PathVariable("orderId") Long orderId, @PathVariable String userId) {
        return shoppingService.findByShopCartId(orderId);
    }

    @ApiOperation("新增訂單")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/{userId}")
    public ShopCartDto addOrder(@PathVariable("userId") Long userId,
                                @Validated(CartDetailDto.ValidAdd.class) @RequestBody ShopCartDto shopCartDto,
                                BindingResult result) {
        if (result.hasErrors()) {
            throw ApiException.fail(result.getFieldErrors()
                    .stream()
                    .map(t -> t.getField() + ":" + t.getDefaultMessage())
                    .collect(Collectors.joining(",")));
        }
        return shoppingService.order(userId, shopCartDto);
    }

    @ApiOperation("刪除整筆訂單")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId, @PathVariable String userId) {
        shoppingService.deleteOrderById(orderId);
    }

    @ApiOperation("更新訂單明細")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PutMapping("/{userId}/{orderId}")
    public void updateCartDetail(@PathVariable String orderId,
                                 @Validated(CartDetailDto.ValidUpdate.class) @RequestBody CartDetailDto cartDetailDto,
                                 @PathVariable String userId) {
        shoppingService.updateCartDetail(cartDetailDto);
    }

    @ApiOperation("訂單結帳")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("/checkout/{orderId}")
    public void checkout(@PathVariable Long orderId) {
        shoppingService.checkout(orderId);
    }
}
