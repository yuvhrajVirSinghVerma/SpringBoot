package com.mvc.mvcPractice.advices;

import lombok.Data;

@Data
public class ApiResponse<T>{
    private T data;
    private ApiError errors;
    public ApiResponse(ApiError err,T data){
        this.errors=err;
        this.data=data;
    }


}
