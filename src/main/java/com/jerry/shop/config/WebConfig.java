package com.jerry.shop.config;

import com.jerry.shop.service.ProductService;
import com.jerry.shop.service.ShopCartService;
import com.jerry.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
  private UserService userService;
  private ProductService productService;
  private ShopCartService shopCartService;

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    userService.initData();
    productService.initData();
    shopCartService.initData();
    System.out.println("ShopCart init data complete.");
  }
}
