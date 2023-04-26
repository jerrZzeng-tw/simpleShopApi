package com.jerry.shop.config;

import com.jerry.shop.dto.ResDto;
import com.jerry.shop.enums.CodeMessage;
import com.jerry.shop.exception.ApiException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResDto) {
            return body;
        } else {
            return new ResDto<>(CodeMessage.SUCCESS.getCode(), CodeMessage.SUCCESS.getMessage(), body);
        }
    }

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ResDto<Object>> apiEexceptionHandler(ApiException apiException) {
        ResDto<Object> resDto = new ResDto<>(apiException);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(resDto, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResDto<Object>> exceptionHandler(Exception exception) {
        ResDto<Object> resDto = new ResDto<>(CodeMessage.ERROR.getCode(), CodeMessage.ERROR.getMessage(), null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(resDto, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ResDto<Object>> exceptionHandler(AuthenticationException exception) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(ResDto.authenticateFail(), headers, HttpStatus.OK);
    }
}
