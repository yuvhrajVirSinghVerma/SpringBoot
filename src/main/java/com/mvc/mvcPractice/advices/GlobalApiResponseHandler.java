package com.mvc.mvcPractice.advices;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GlobalApiResponseHandler implements ResponseBodyAdvice {
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        System.out.println("global resp "+body);

        if(body instanceof ApiResponse) {
            return body;
        }
        if(body instanceof ApiError)return new ApiResponse<>((ApiError) body,null);
        return new ApiResponse<>(null,body);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }
}
