package com.jerry.shop.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket userAPI() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("使用者")
        .select()
        .paths(PathSelectors.regex("/users.*"))
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .build()
        .apiInfo(this.usersInfo());
  }

  private ApiInfo usersInfo() {
    return new ApiInfoBuilder().title("使用者 API").description("使用者 API").version("1.0").build();
  }

  @Bean
  public Docket productAPI() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("產品")
        .select()
        .paths(PathSelectors.regex("/product.*"))
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .build()
        .apiInfo(this.productInfo());
  }

  private ApiInfo productInfo() {
    return new ApiInfoBuilder().title("產品 API").description("產品 API").version("1.0").build();
  }

  @Bean
  public Docket shopCartAPI() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("購物車")
        .select()
        .paths(PathSelectors.regex("/shopCart.*"))
        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
        .build()
        .apiInfo(this.shopCartInfo());
  }

  private ApiInfo shopCartInfo() {
    return new ApiInfoBuilder().title("購物車 API").description("購物車 API").version("1.0").build();
  }
}
