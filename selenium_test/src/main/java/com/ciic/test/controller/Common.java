package com.ciic.test.controller;

import com.ciic.test.rest.RestResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lixuecheng on 2017/10/23.
 * 确保session已赋值
 */
@RestController
public class Common {
    @RequestMapping("/com")
    Object comment(){
        return new RestResultResponse(true,"","");
    }
}
