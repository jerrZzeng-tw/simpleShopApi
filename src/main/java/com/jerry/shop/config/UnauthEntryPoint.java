package com.jerry.shop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jerry.shop.dto.ResDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 存取到未授權的 restful api 時，會導到這個 class 進行後續處理
 */
public class UnauthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(UnauthEntryPoint.class);

    // 用來處理 json <-> object 轉換。ObjectMapper class 會讀 POJO 裡的 @JsonIgnore, @JsonProperty annotation 設定
    private static final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
    ;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error(authException.getMessage(), authException);


        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), ResDto.unauthorized());
    }

}