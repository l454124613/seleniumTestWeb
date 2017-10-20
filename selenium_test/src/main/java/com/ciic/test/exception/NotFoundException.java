package com.ciic.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lixuecheng on 2017/10/20.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(String action,String  msg) {
        super("无法找到"+action+" '" + msg + "'.");
    }
//    public NotFoundException(String userName) {
//        super("could not find user '" + userName + "'.");
//    }
}

