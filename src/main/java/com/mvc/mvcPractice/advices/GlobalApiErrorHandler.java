package com.mvc.mvcPractice.advices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalApiErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return builErrorResponse(apiError);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity <ApiError> handler(MethodArgumentNotValidException e){
      List<String> errors= e.getBindingResult().getAllErrors().stream().map((msg)->msg.getDefaultMessage()).collect(Collectors.toList());
        ApiError errorRes=ApiError.builder().
                message(e.getMessage()).
                Errors(errors).
                status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorRes,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity <ApiResponse> handler(RuntimeException e){
        ApiError errorRes=ApiError.builder().
                message(e.getMessage()).
                status(HttpStatus.BAD_REQUEST).build();
        return builErrorResponse(errorRes);
    }



    private ResponseEntity<ApiResponse> builErrorResponse(ApiError apierr){
        return new ResponseEntity<>(new ApiResponse<>(apierr,null),apierr.getStatus());
    }
}

