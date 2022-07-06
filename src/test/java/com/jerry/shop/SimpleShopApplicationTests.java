package com.jerry.shop;

import com.jerry.shop.dto.ResDto;
import com.jerry.shop.dto.UserDto;
import com.jerry.shop.enums.CodeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleShopApplicationTests {

  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  void getUsers() {
    ResolvableType userListType = ResolvableType.forClassWithGenerics(List.class, UserDto.class);
    ResolvableType resType = ResolvableType.forClassWithGenerics(ResDto.class, userListType);
    ParameterizedTypeReference<ResDto<List<UserDto>>> typeReference =
        ParameterizedTypeReference.forType(resType.getType());
    ResponseEntity<ResDto<List<UserDto>>> response =
        testRestTemplate.exchange("/users/", HttpMethod.GET, null, typeReference);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(CodeMessage.SUCCESS.getCode(), response.getBody().getCode());
    ResDto<List<UserDto>> users = response.getBody();
    Assertions.assertEquals(3, users.getData().size());
  }

  @Test
  void getUser() {
    ResolvableType resType = ResolvableType.forClassWithGenerics(ResDto.class, UserDto.class);
    ParameterizedTypeReference<ResDto<UserDto>> typeReference =
        ParameterizedTypeReference.forType(resType.getType());
    String name = "admin";
    ResponseEntity<ResDto<UserDto>> response =
        testRestTemplate.exchange("/users/" + name, HttpMethod.GET, null, typeReference);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(CodeMessage.SUCCESS.getCode(), response.getBody().getCode());
    ResDto<UserDto> userDto = response.getBody();
    Assertions.assertNotNull(userDto);

    name = "test";
    response = testRestTemplate.exchange("/users/" + name, HttpMethod.GET, null, typeReference);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(CodeMessage.NODATA.getCode(), response.getBody().getCode());
  }
}
