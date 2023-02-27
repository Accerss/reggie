package com.ghh.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


/**
 * 全局异常拦截类
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
@ResponseBody
public class GlobaExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在(自定义异常)";
            return R.error(msg);
        }

        return R.error("未知错误");
    }

    /**
     * 异常处理办法
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomExeception.class)
    public R<String> exceptionHandler(CustomExeception ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
