package com.ghh.reggie.common;

/**
 * 自定义业务异常类
 */
public class CustomExeception extends RuntimeException{
    public CustomExeception(String message){
        super(message);
    }
}
