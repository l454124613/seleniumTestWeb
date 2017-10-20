package com.ciic.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lixuecheng on 2017/10/20.
 */
@ResponseStatus(HttpStatus.GONE)
public class DelectFailedException extends RuntimeException{
    public DelectFailedException(String action,String  msg) {
        super("删除"+action+"失败 '" + msg + "'.");
    }}
