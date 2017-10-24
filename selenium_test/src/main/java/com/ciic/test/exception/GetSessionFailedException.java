package com.ciic.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by lixuecheng on 2017/10/20.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class GetSessionFailedException extends RuntimeException{
    public GetSessionFailedException() {
        super("获取session失败");
//        resp.setHeader("Location", "/");
    }}
