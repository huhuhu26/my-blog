package com.ithuhu.exception;

import com.ithuhu.pojo.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // 兜底异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 开发阶段把真实错误打在控制台，方便排查
        e.printStackTrace();
        return Result.error("系统繁忙，请稍后再试");
    }

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(message);
    }
}

