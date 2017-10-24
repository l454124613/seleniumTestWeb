package com.ciic.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.ciic.test.rest.RestResultResponse;
import com.ciic.test.tools.mycode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lixuecheng on 2017/10/23.
 * 确保session已赋值
 */
@RestController
public class Common {
    @RequestMapping(value = "/com",method = RequestMethod.GET)
    Object comment(){
//        Cookie[] cookies=   httpRequest.getCookies();
//        boolean hasUser=false;
//        for (Cookie c:cookies){
//            if(c.getName().equals("user")){
//
//              String user=  mycode.decode(c.getValue());
//                JSONObject jsStr = JSONObject.parseObject(user);
//                String isrem=jsStr.getString("isrem");
//                if(isrem.equals("1")){
//                    hasUser=true;
//                }
//              break;
//            }
//
//        }


        return new RestResultResponse(true,"","ok");
    }
}
